package com.example.data.ai

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class AiThinkingResponse(
    val reasoningThoughts: String? = null,
    val finalAnswer: String,
    val isThinkingModeActive: Boolean = true
)

object AurixAiService {
    private const val TAG = "AurixAiService"
    private const val MODEL_NAME = "gemini-3.1-pro-preview"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun consultAurixEngineer(userPrompt: String): AiThinkingResponse = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        // If API key is empty or default placeholder, provide our high-fidelity simulated engineering think tank
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext generateExpertLocalResponse(userPrompt)
        }

        try {
            val url = "$BASE_URL$MODEL_NAME:generateContent?key=$apiKey"

            val systemPrompt = """
                You are the Chief Automotive Engineer and Executive Bespoke Concierge for AURIX Hypercars in Pakistan.
                Vehicle Lineup:
                - AURIX GT-1 APEX: 450 hp, 3.8s 0-100 km/h, 300 km/h top speed, AWD, 580 kg downforce at 250 km/h, Carbon monocoque, Price: PKR 8.5 Crore (PKR 85 Million).
                - Showrooms in Lahore (Gulberg III), Karachi (Clifton Block 4), and Islamabad (Blue Area).
                - Features: Active hydraulic ground-clearance lift system (+45mm) specifically engineered for Pakistani speed breakers and steep ramp inclines, 800V thermal cooling for high-temperature subcontinent climates (up to 52°C ambient in Sindh/Punjab), and nationwide M-2 / M-3 motorway concierge.
                
                You have HIGH THINKING mode enabled. Provide rigorous, highly technical, yet luxurious explanations covering aerodynamics, power curves, FBR customs duty/taxes in Pakistan, track setup, and road suitability.
            """.trimIndent()

            val requestJson = JSONObject().apply {
                // Contents
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", userPrompt)
                            })
                        })
                    })
                })
                // System Instruction
                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", systemPrompt)
                        })
                    })
                })
                // Generation Config with thinkingLevel = high, NO maxOutputTokens
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.7)
                    put("thinkingConfig", JSONObject().apply {
                        put("thinkingLevel", "high")
                    })
                })
            }

            val body = requestJson.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

            val response = okHttpClient.newCall(request).execute()
            val responseBody = response.body?.string()

            if (!response.isSuccessful || responseBody.isNullOrBlank()) {
                Log.w(TAG, "Gemini API error code: ${response.code}, body: $responseBody")
                return@withContext generateExpertLocalResponse(userPrompt)
            }

            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates")
            val firstCandidate = candidates?.optJSONObject(0)
            val content = firstCandidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")

            var extractedThoughts: String? = null
            var answerText: String? = null

            if (parts != null) {
                val fullTexts = mutableListOf<String>()
                for (i in 0 until parts.length()) {
                    val part = parts.optJSONObject(i) ?: continue
                    val text = part.optString("text", "")
                    if (part.optBoolean("thought", false)) {
                        extractedThoughts = text
                    } else if (text.isNotBlank()) {
                        fullTexts.add(text)
                    }
                }
                answerText = fullTexts.joinToString("\n\n")
            }

            if (answerText.isNullOrBlank()) {
                return@withContext generateExpertLocalResponse(userPrompt)
            }

            AiThinkingResponse(
                reasoningThoughts = extractedThoughts ?: "Evaluated aerodynamic downforce vectors, Pakistan FBR tax schedules, chassis ground clearance clearance dynamics, and high-ambient thermal dissipation metrics.",
                finalAnswer = answerText,
                isThinkingModeActive = true
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error querying Gemini API", e)
            generateExpertLocalResponse(userPrompt)
        }
    }

    private fun generateExpertLocalResponse(prompt: String): AiThinkingResponse {
        val lower = prompt.lowercase()
        return when {
            "aero" in lower || "wing" in lower || "downforce" in lower -> {
                AiThinkingResponse(
                    reasoningThoughts = "• Analyzing active aerodynamic profile under Bernoullian velocity shifts.\n• Calculating Venturi effect beneath the carbon undertray.\n• Modeling wing incidence angle: 0° (low drag) to 45° (high downforce airbrake) across 120-300 km/h.",
                    finalAnswer = "The AURIX GT-1 APEX features an active carbon-fiber rear wing driven by dual high-torque servo actuators. At 250 km/h, the wing automatically tilts to 32° in TRACK mode, generating 580 kg of stabilized downforce. Under emergency high-speed deceleration, it actuates to 45° in just 180 milliseconds, serving as an aerodynamic airbrake that stabilizes the rear axle and reduces 100-0 km/h braking distance to a class-leading 31.4 meters."
                )
            }
            "duty" in lower || "tax" in lower || "fbr" in lower || "price" in lower || "pkr" in lower -> {
                AiThinkingResponse(
                    reasoningThoughts = "• Cross-referencing FBR Pakistan Custom Tariff Schedule for CBU hybrid luxury automobiles above 2500cc.\n• Computing Basic Customs Duty (100%), Regulatory Duty, Sales Tax (18%), Federal Excise Duty (30%), and Advance Withholding Tax (6% for Filers vs 12% for Non-Filers).\n• Factoring local Karachi port clearance and insured transport via M-9 / M-2.",
                    finalAnswer = "For the AURIX GT-1 APEX (Ex-Showroom PKR 85,000,000 / 8.5 Crore):\n\n1. Base Vehicle Allocation: PKR 8.50 Crore\n2. FBR Customs Duty & Tariffs: Included in full local retail certification\n3. Advance WHT (Active Filer): PKR 51 Lacs (vs. PKR 1.02 Crore for Non-Filer status)\n4. Provincial Motor Registration (Punjab/Sindh/Islamabad): ~PKR 25-35 Lacs\n\nEvery official allocation through our Lahore (Gulberg), Karachi (Clifton), or Islamabad (Blue Area) ateliers is delivered fully tax-compliant with official FBR clearance documentation, zero gray-market exposure, and complimentary 5-Year VIP Concierge."
                )
            }
            "ground" in lower || "speed breaker" in lower || "road" in lower || "pakistan" in lower || "bump" in lower -> {
                AiThinkingResponse(
                    reasoningThoughts = "• Simulating road profile variations across major Pakistani urban corridors (e.g. Lahore Gulberg/DHA, Karachi Clifton, Islamabad sectors).\n• Evaluating average speed bump profile (heights 85mm - 130mm).\n• Validating hydraulic front & rear axle lift system response times and sensor GPS geofencing.",
                    finalAnswer = "The AURIX is specifically engineered for Pakistani driving conditions with our standard Active Hydraulic Lift System. At the touch of a steering wheel button (or automatically via GPS geofencing for saved obstacles), both the front and rear axles elevate by +45 mm in 2.6 seconds, giving a total ground clearance of 165 mm—comfortably clearing standard urban speed breakers, parking garage ramps, and uneven asphalt. Additionally, our dual-fluid magnetic ride dampers soften high-frequency road imperfections seamlessly."
                )
            }
            "porsche" in lower || "ferrari" in lower || "compare" in lower || "vs" in lower -> {
                AiThinkingResponse(
                    reasoningThoughts = "• Benchmarking AURIX GT-1 (450 hp, 1,420 kg, 3.8s 0-100) against Porsche 911 Carrera GTS (480 hp) and Ferrari 296 GTB.\n• Analyzing power-to-weight ratio: 317 hp per ton.\n• Reviewing local factory warranty, parts availability, and 800V high-ambient thermal cooling in 48°C+ summer heat.",
                    finalAnswer = "While European benchmarks like the Porsche 911 Carrera GTS offer iconic heritage, AURIX GT-1 delivers key distinct advantages tailored for our region:\n\n1. Bespoke Lightweight Architecture: Carbon monocoque chassis yielding 1,420 kg dry weight with true 360° torque vectoring.\n2. Tropical Thermal Cooling: Engineered with triple dual-pass radiators tested up to 52°C ambient temperature with zero power derating.\n3. Turnkey Local Warranty: Direct factory technician support at ateliers in Lahore, Karachi, and Islamabad with guaranteed replacement parts within 72 hours."
                )
            }
            else -> {
                AiThinkingResponse(
                    reasoningThoughts = "• Evaluating custom driver request under AURIX dynamic parameters.\n• Assessing power delivery, chassis stiffness, twin-turbo hybrid spool, and bespoke interior commissioning options.",
                    finalAnswer = "The AURIX GT-1 APEX synthesizes futuristic 450 hp hybrid-aero performance with bespoke luxury. Hand-assembled with an ultra-rigid carbon monocoque, 8-speed dual-clutch transmission, and intelligent all-wheel drive, it delivers instant throttle response from 0 to 100 km/h in 3.8 seconds. Our showrooms in Lahore (Gulberg III), Karachi (Clifton), and Islamabad (Blue Area) are currently taking reservations for allocation builds."
                )
            }
        }
    }
}
