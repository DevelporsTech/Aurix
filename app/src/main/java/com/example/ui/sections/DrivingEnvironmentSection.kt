package com.example.ui.sections

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AurixCyanElectric
import com.example.ui.theme.AurixObsidianBlack
import com.example.ui.theme.AurixSilverMetallic
import com.example.ui.theme.AurixTextPrimary
import com.example.ui.theme.AurixTextSecondary
import kotlinx.coroutines.launch

@Composable
fun DrivingEnvironmentSection(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    var isSimulatingLaunch by remember { mutableStateOf(false) }
    val currentSpeedAnim = remember { Animatable(120f) }
    var measuredTimeSec by remember { mutableDoubleStateOf(0.0) }
    var launchCompleted by remember { mutableStateOf(false) }

    fun runLaunchTest() {
        if (isSimulatingLaunch) return
        isSimulatingLaunch = true
        launchCompleted = false
        measuredTimeSec = 0.0

        coroutineScope.launch {
            // Drop to 0 km/h first
            currentSpeedAnim.snapTo(0f)
            val startTime = System.currentTimeMillis()

            // Sprint to 100 km/h in exactly 3.8s
            currentSpeedAnim.animateTo(
                targetValue = 100f,
                animationSpec = tween(durationMillis = 3800, easing = LinearEasing)
            )

            measuredTimeSec = 3.80
            launchCompleted = true
            isSimulatingLaunch = false

            // Cruise up to 180 km/h
            currentSpeedAnim.animateTo(
                targetValue = 180f,
                animationSpec = tween(durationMillis = 2200, easing = LinearEasing)
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AurixObsidianBlack)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // Section E Tag
        Text(
            text = "SECTION E • DYNAMIC DRIVING ENVIRONMENT",
            color = AurixCyanElectric,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Headline
        Text(
            text = "THE ROAD\nIS YOURS.",
            color = AurixTextPrimary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 38.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.testTag("driving_headline")
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Carving the winding curves of the Margalla Hills or cruising at 120 km/h along the M-2 Motorway Salt Range. Active torque vectoring calculates grip 1,000 times per second.",
            color = AurixTextSecondary,
            fontSize = 13.5.sp,
            lineHeight = 19.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // High-Speed Cinematic Frame
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color(0x3300E5FF), RoundedCornerShape(16.dp))
                .background(Color(0xFF07090F))
        ) {
            Image(
                painter = painterResource(id = R.drawable.aurix_mountain_drive),
                contentDescription = "AURIX Mountain Driving Environment",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            // Vignette gradient
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x33000000),
                                Color.Transparent,
                                Color(0x99000000)
                            )
                        )
                    )
            )

            // Live Speedometer HUD overlay on top left
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
                    .background(Color(0xDD0B0E14), RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0x4400E5FF), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = "Speed HUD",
                        tint = AurixCyanElectric,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LIVE TELEMETRY HUD",
                        color = AurixCyanElectric,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${currentSpeedAnim.value.toInt()}",
                        color = AurixSilverMetallic,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "KM/H",
                        color = AurixCyanElectric,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }

            // Route Tag on Top Right
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .background(Color(0xDD0B0E14), RoundedCornerShape(10.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Terrain,
                    contentDescription = "Location",
                    tint = Color(0xFFFFB300),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "M-2 SALT RANGE • 780M ELEV",
                    color = AurixSilverMetallic,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 0-100 km/h Live Launch Control Action on bottom
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(Color(0xEE0D121C), RoundedCornerShape(10.dp))
                    .border(1.dp, Color(0x3300E5FF), RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = if (isSimulatingLaunch) "ACCELERATING 0-100..." else if (launchCompleted) "0-100 SPRINT VERIFIED: 3.80s" else "0-100 KM/H BENCHMARK",
                        color = if (launchCompleted) AurixCyanElectric else AurixSilverMetallic,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Launch control with dual-motor overboost",
                        color = AurixTextSecondary,
                        fontSize = 9.sp
                    )
                }

                Button(
                    onClick = { runLaunchTest() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSimulatingLaunch) Color(0xFF243040) else AurixCyanElectric,
                        contentColor = AurixObsidianBlack
                    ),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .height(32.dp)
                        .testTag("launch_control_btn")
                ) {
                    Icon(
                        imageVector = if (launchCompleted) Icons.Default.Refresh else Icons.Default.PlayArrow,
                        contentDescription = "Launch",
                        tint = AurixObsidianBlack,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isSimulatingLaunch) "TESTING..." else if (launchCompleted) "RE-RUN" else "LAUNCH",
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Dynamic Telemetry Grid: G-Force, Aero Drag, Tire Temp
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TelemetryBox(label = "LATERAL GRIP", value = "1.38 G", status = "Peak Cornering", modifier = Modifier.weight(1f))
            TelemetryBox(label = "AERO BALANCE", value = "42:58", status = "Front/Rear Split", modifier = Modifier.weight(1f))
            TelemetryBox(label = "TIRE TEMP", value = "78° C", status = "Optimal Compound", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun TelemetryBox(label: String, value: String, status: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color(0xFF0F1522), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0x1AFFFFFF), RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Text(text = label, color = AurixTextSecondary, fontSize = 8.5.sp, fontWeight = FontWeight.Bold)
        Text(text = value, color = AurixCyanElectric, fontSize = 15.sp, fontWeight = FontWeight.Black)
        Text(text = status, color = AurixTextSecondary, fontSize = 8.5.sp)
    }
}
