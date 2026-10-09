package com.example.ui.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ai.AiThinkingResponse
import com.example.data.ai.AurixAiService
import com.example.ui.theme.AurixCyanElectric
import com.example.ui.theme.AurixObsidianBlack
import com.example.ui.theme.AurixSilverMetallic
import com.example.ui.theme.AurixTextPrimary
import com.example.ui.theme.AurixTextSecondary
import kotlinx.coroutines.launch

@Composable
fun AurixAiAdvisorSection(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    var userQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var currentAiResponse by remember {
        mutableStateOf<AiThinkingResponse?>(
            AiThinkingResponse(
                reasoningThoughts = "• Evaluated active carbon wing incidence angles across 100-300 km/h.\n• Modeled high-ambient thermal dissipation metrics for 50°C+ summer heat in Pakistan.\n• Calculated hydraulic front axle ground clearance (+45mm) for urban speed breakers.",
                finalAnswer = "Welcome to AURIX Intelligence. Powered by Gemini High Thinking mode, I can analyze complex aerodynamics, calculate FBR import tariffs for Lahore/Karachi/Islamabad delivery, or simulate track telemetry for your bespoke hypercar allocation.",
                isThinkingModeActive = true
            )
        )
    }
    var isThinkingExpanded by remember { mutableStateOf(true) }

    fun sendPrompt(prompt: String) {
        if (prompt.isBlank() || isLoading) return
        isLoading = true
        coroutineScope.launch {
            val result = AurixAiService.consultAurixEngineer(prompt)
            currentAiResponse = result
            isLoading = false
            isThinkingExpanded = true
        }
    }

    val suggestedQuestions = listOf(
        "How does the active aero wing adapt at 250+ km/h?",
        "Explain the FBR import duty breakdown for Lahore delivery",
        "Will the suspension handle Pakistani road bumps and high ramps?",
        "Compare AURIX GT 450hp with Porsche 911 Carrera GTS"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AurixObsidianBlack)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // AI Header Badge
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = "AI Engineer",
                    tint = AurixCyanElectric,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "AURIX INTELLIGENCE",
                    color = AurixCyanElectric,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }

            // High Thinking Mode Indicator Badge
            Box(
                modifier = Modifier
                    .background(Color(0xFF003844), RoundedCornerShape(12.dp))
                    .border(1.dp, AurixCyanElectric, RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).background(AurixCyanElectric, CircleShape))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "HIGH THINKING ACTIVE",
                        color = AurixCyanElectric,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "DEEP AUTOMOTIVE\nENGINEERING ADVISOR.",
            color = AurixTextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 34.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.testTag("ai_advisor_headline")
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Consult our chief engineering neural core with deep reasoning. Analyzes aerodynamics, custom FBR tax schedules, suspension kinematics, and bespoke track setups.",
            color = AurixTextSecondary,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Suggested Prompt Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            suggestedQuestions.forEach { question ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF101726))
                        .border(1.dp, Color(0x3300E5FF), RoundedCornerShape(20.dp))
                        .clickable {
                            userQuery = question
                            sendPrompt(question)
                        }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .testTag("suggested_chip")
                ) {
                    Text(
                        text = question,
                        color = AurixSilverMetallic,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Prompt Input Box
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F1522), RoundedCornerShape(12.dp))
                .border(1.dp, Color(0x3300E5FF), RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = userQuery,
                onValueChange = { userQuery = it },
                placeholder = { Text("Ask engineering or Pakistan allocation queries...", fontSize = 11.5.sp, color = AurixTextSecondary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .testTag("ai_query_input")
            )

            IconButton(
                onClick = {
                    val prompt = userQuery
                    userQuery = ""
                    sendPrompt(prompt)
                },
                enabled = !isLoading && userQuery.isNotBlank(),
                modifier = Modifier.testTag("ai_send_query_btn")
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = AurixCyanElectric,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = if (userQuery.isNotBlank()) AurixCyanElectric else Color(0x66FFFFFF)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // AI Response Container
        if (currentAiResponse != null) {
            val response = currentAiResponse!!
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0C121D), RoundedCornerShape(14.dp))
                    .border(1.dp, Color(0x2288A0C0), RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                // Collapsible High Thinking Process Section
                if (!response.reasoningThoughts.isNullOrBlank()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF141F30))
                            .clickable { isThinkingExpanded = !isThinkingExpanded }
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Thinking Process",
                                tint = AurixCyanElectric,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "ENGINEERING THOUGHT PROCESS (HIGH THINKING)",
                                color = AurixCyanElectric,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Icon(
                            imageVector = if (isThinkingExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Toggle Thinking",
                            tint = AurixCyanElectric,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    AnimatedVisibility(visible = isThinkingExpanded) {
                        Text(
                            text = response.reasoningThoughts ?: "",
                            color = Color(0xFF90A4AE),
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 4.dp)
                                .fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Final Answer
                Text(
                    text = response.finalAnswer,
                    color = AurixTextPrimary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    modifier = Modifier.testTag("ai_final_answer_text")
                )
            }
        }
    }
}
