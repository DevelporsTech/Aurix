package com.example.ui.sections

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
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SurroundSound
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.ui.theme.AurixCyanElectric
import com.example.ui.theme.AurixObsidianBlack
import com.example.ui.theme.AurixSilverMetallic
import com.example.ui.theme.AurixTextPrimary
import com.example.ui.theme.AurixTextSecondary

enum class AmbientHue(val label: String, val color: Color) {
    CYBER_CYAN("Cyan Pulse", AurixCyanElectric),
    AZURE_HYPER("Neon Azure", Color(0xFF2979FF)),
    CRIMSON_APEX("Crimson Apex", Color(0xFFFF2A4B)),
    MONACO_GOLD("Tuscan Gold", Color(0xFFFFB300)),
    EMERALD_GT("Emerald GT", Color(0xFF00E676))
}

enum class CockpitDisplayMode(val label: String, val subtitle: String) {
    TELEMETRY("RACE TELEMETRY", "Lap timer, tire pressures & G-force"),
    NAVIGATION("M-2 MOTORWAY NAV", "Satellite HUD & topographic curves"),
    ACOUSTIC("BESPOKE AUDIO", "18-Speaker Bang & Olufsen 3D Sound")
}

@Composable
fun InteriorExperienceSection(modifier: Modifier = Modifier) {
    var selectedHue by remember { mutableStateOf(AmbientHue.CYBER_CYAN) }
    var selectedDisplayMode by remember { mutableStateOf(CockpitDisplayMode.TELEMETRY) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AurixObsidianBlack)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // Section D Tag
        Text(
            text = "SECTION D • INTERIOR ARCHITECTURE",
            color = AurixCyanElectric,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Headline
        Text(
            text = "EVERY DETAIL.\nEVERY MOMENT.",
            color = AurixTextPrimary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 38.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.testTag("interior_headline")
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "An uncompromising driver-centric sanctum. Matte carbon-fiber steering wheel with embedded shift lights, tactile knurled aluminum rotary switches, and handcrafted Italian Alcantara.",
            color = AurixTextSecondary,
            fontSize = 13.5.sp,
            lineHeight = 19.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Interior Cockpit Visual Frame with dynamic ambient glow overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, selectedHue.color.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                .background(Color(0xFF05070A))
        ) {
            Image(
                painter = painterResource(id = R.drawable.aurix_interior_cockpit),
                contentDescription = "AURIX Cockpit Interior",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            // Dynamic ambient lighting gradient tint
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                selectedHue.color.copy(alpha = 0.18f),
                                Color.Transparent,
                                Color(0x66000000)
                            ),
                            radius = 600f
                        )
                    )
            )

            // Display Mode Badge on top right
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .background(Color(0xDD0B0E14), RoundedCornerShape(20.dp))
                    .border(1.dp, selectedHue.color, RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = selectedDisplayMode.label,
                    color = selectedHue.color,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }

            // HUD Telemetry overlay badge at bottom
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .background(Color(0xDD0C111A), RoundedCornerShape(10.dp))
                    .border(1.dp, Color(0x33FFFFFF), RoundedCornerShape(10.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(selectedHue.color, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "AMBIENT OPTIC: ${selectedHue.label.uppercase()}",
                    color = AurixSilverMetallic,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ambient Fiber-Optic Color Selector
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LightMode,
                contentDescription = "Ambient Lighting",
                tint = AurixCyanElectric,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "CABIN FIBER-OPTIC AMBIENT HUE",
                color = AurixTextPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AmbientHue.values().forEach { hue ->
                val isSelected = hue == selectedHue
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) Color(0xFF162130) else Color(0xFF0F1420))
                        .border(
                            1.2.dp,
                            if (isSelected) hue.color else Color(0x1AFFFFFF),
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { selectedHue = hue }
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .testTag("ambient_hue_${hue.name.lowercase()}")
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(hue.color, CircleShape)
                            .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = hue.label.substringBefore(" "),
                        color = if (isSelected) hue.color else AurixTextSecondary,
                        fontSize = 9.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Central Curved OLED Display Mode Switcher
        Text(
            text = "CURVED OLED TELEMETRY SCREEN",
            color = AurixTextPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            CockpitDisplayMode.values().forEach { mode ->
                val isSelected = mode == selectedDisplayMode
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) Color(0xFF152236) else Color(0xFF0F1420))
                        .border(
                            1.dp,
                            if (isSelected) AurixCyanElectric else Color(0x1AFFFFFF),
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { selectedDisplayMode = mode }
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                        .testTag("cockpit_mode_${mode.name.lowercase()}"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = when (mode) {
                                CockpitDisplayMode.TELEMETRY -> Icons.Default.Speed
                                CockpitDisplayMode.NAVIGATION -> Icons.Default.Navigation
                                CockpitDisplayMode.ACOUSTIC -> Icons.Default.SurroundSound
                            },
                            contentDescription = mode.label,
                            tint = if (isSelected) AurixCyanElectric else AurixTextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = mode.label,
                                color = if (isSelected) AurixCyanElectric else AurixSilverMetallic,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = mode.subtitle,
                                color = AurixTextSecondary,
                                fontSize = 9.5.sp
                            )
                        }
                    }

                    if (isSelected) {
                        Text(
                            text = "ACTIVE",
                            color = AurixCyanElectric,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }
    }
}
