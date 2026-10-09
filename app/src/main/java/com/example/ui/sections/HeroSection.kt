package com.example.ui.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.data.model.VehicleSpecs
import com.example.ui.components.Aurix3DVehicleCanvas
import com.example.ui.theme.AurixCyanElectric
import com.example.ui.theme.AurixGraphiteDark
import com.example.ui.theme.AurixObsidianBlack
import com.example.ui.theme.AurixSilverMetallic
import com.example.ui.theme.AurixTextPrimary
import com.example.ui.theme.AurixTextSecondary

@Composable
fun HeroSection(
    specs: VehicleSpecs,
    onExploreMachine: () -> Unit,
    onDiscoverDesign: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isEngineRunning by remember { mutableStateOf(false) }
    var interactive3DActive by remember { mutableStateOf(false) }

    val rpmProgress by animateFloatAsState(
        targetValue = if (isEngineRunning) 0.85f else 0f,
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
        label = "rpm_anim"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AurixObsidianBlack)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        // Subtle Brand Label
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(AurixCyanElectric, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "FLAGSHIP SERIES • PAKISTAN LAUNCH",
                color = AurixCyanElectric,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }

        // Section A Headline
        Text(
            text = "ENGINEERED\nTO MOVE YOU.",
            color = AurixTextPrimary,
            fontSize = 34.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 40.sp,
            letterSpacing = (-0.5).sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.testTag("hero_headline")
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Precision engineering. Unmistakable presence. The pinnacle of aerodynamic performance, now commissioned for Pakistan.",
            color = AurixTextSecondary,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Center Stage: Dual Mode Display (Cinematic Photorealism / Interactive 3D Canvas)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(290.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color(0x3300E5FF), RoundedCornerShape(16.dp))
                .background(Color(0xFF07090E))
        ) {
            if (interactive3DActive) {
                // Interactive 3D Real-time Render
                Aurix3DVehicleCanvas(
                    modifier = Modifier.fillMaxWidth().height(290.dp),
                    yawAngleDeg = 35f,
                    showAerodynamics = true
                )
            } else {
                // High-End Cinematic Studio Photograph
                Image(
                    painter = painterResource(id = R.drawable.aurix_hypercar_hero),
                    contentDescription = "AURIX Hypercar in Cinematic Studio",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(290.dp)
                )

                // Dark gradient overlay to preserve contrast
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(290.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0x22000000),
                                    Color(0x99000000)
                                )
                            )
                        )
                )
            }

            // Mode Switcher Badge (Photorealistic Studio vs Real-Time 3D)
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .background(Color(0xDD0B0E14), RoundedCornerShape(20.dp))
                    .border(1.dp, Color(0x4400E5FF), RoundedCornerShape(20.dp))
                    .clickable { interactive3DActive = !interactive3DActive }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .testTag("toggle_3d_mode_button"),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = "Toggle 3D View",
                    tint = AurixCyanElectric,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (interactive3DActive) "SWITCH TO STUDIO PHOTO" else "INTERACTIVE 3D MESH",
                    color = AurixCyanElectric,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }

            // Start Engine / Acoustics Simulator Button on Bottom Left
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .background(Color(0xDD0D111A), RoundedCornerShape(12.dp))
                    .border(1.dp, if (isEngineRunning) AurixCyanElectric else Color(0x33FFFFFF), RoundedCornerShape(12.dp))
                    .clickable { isEngineRunning = !isEngineRunning }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .testTag("engine_start_button"),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(if (isEngineRunning) AurixCyanElectric else Color(0xFF243042), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isEngineRunning) Icons.Default.Stop else Icons.Default.PlayArrow,
                        contentDescription = "Start Engine",
                        tint = if (isEngineRunning) AurixObsidianBlack else Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = if (isEngineRunning) "V6 HYBRID RUNNING" else "IGNITE ENGINE",
                        color = if (isEngineRunning) AurixCyanElectric else AurixSilverMetallic,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isEngineRunning) "Idle: 1,800 RPM • Valves Open" else "Tap to hear acoustic roar",
                        color = AurixTextSecondary,
                        fontSize = 9.5.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Quick Specifications Grid (4 core indicators)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F141F), RoundedCornerShape(12.dp))
                .border(1.dp, Color(0x2288A0C0), RoundedCornerShape(12.dp))
                .padding(vertical = 12.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            HeroStatItem(label = "POWER", value = "${specs.powerHp} hp", accent = AurixCyanElectric)
            HeroStatItem(label = "0-100 KM/H", value = "${specs.zeroToHundredSec}s", accent = AurixSilverMetallic)
            HeroStatItem(label = "TOP SPEED", value = "${specs.topSpeedKmh} km/h", accent = AurixSilverMetallic)
            HeroStatItem(label = "PRICE", value = "PKR 8.5 Cr", accent = AurixCyanElectric)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons: Primary & Secondary
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onExploreMachine,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AurixCyanElectric,
                    contentColor = AurixObsidianBlack
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("hero_explore_button")
            ) {
                Text(
                    text = "EXPLORE THE MACHINE",
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }

            OutlinedButton(
                onClick = onDiscoverDesign,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x4400E5FF)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("hero_discover_button")
            ) {
                Text(
                    text = "DISCOVER DESIGN",
                    color = AurixSilverMetallic,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Scroll Indicator
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onDiscoverDesign() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDownward,
                contentDescription = "Scroll down",
                tint = AurixCyanElectric,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "SCROLL TO REVEAL EXTERIOR & SPECS",
                color = AurixTextSecondary,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
private fun HeroStatItem(label: String, value: String, accent: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            color = accent,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = label,
            color = AurixTextSecondary,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
        )
    }
}
