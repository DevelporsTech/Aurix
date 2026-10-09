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
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExteriorColor
import com.example.data.model.WheelStyle
import com.example.ui.components.Aurix3DVehicleCanvas
import com.example.ui.theme.AurixCyanElectric
import com.example.ui.theme.AurixObsidianBlack
import com.example.ui.theme.AurixSilverMetallic
import com.example.ui.theme.AurixTextPrimary
import com.example.ui.theme.AurixTextSecondary

@Composable
fun ExteriorRevealSection(
    selectedColor: ExteriorColor,
    onColorSelected: (ExteriorColor) -> Unit,
    selectedWheel: WheelStyle,
    onWheelSelected: (WheelStyle) -> Unit,
    modifier: Modifier = Modifier
) {
    var cameraAngleYaw by remember { mutableFloatStateOf(45f) }
    var showAeroTrails by remember { mutableStateOf(true) }
    var isExplodedView by remember { mutableStateOf(false) }

    // Perspective descriptions tied to angle
    val angleDescription = when {
        cameraAngleYaw in 15f..70f -> "Front 3/4 Stance: Dual carbon-composite vortex generators channeling high-pressure airflow directly toward cooling ducts."
        cameraAngleYaw in 71f..115f -> "Sculpted Silhouette: Drag coefficient of 0.28 Cd with an aggressive cab-forward teardrop canopy."
        cameraAngleYaw in 116f..170f -> "Rear 3/4 Stance: Twin Venturi underfloor tunnels generating 580 kg downforce without parasitic top-surface drag."
        cameraAngleYaw in 171f..225f -> "Rear Fascia: Titanium center-exit exhaust, aerodynamic carbon endplates, and full-width cyber LED lightbar."
        cameraAngleYaw in 226f..295f -> "Driver Profile: Dihedral synchro-helix doors with recessed aero touch sensors."
        else -> "Front Aerodynamic Splitter: Ground effect nose producing vacuum stabilization at speeds above 200 km/h."
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AurixObsidianBlack)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // Section B Tag
        Text(
            text = "SECTION B • AERODYNAMIC ARCHITECTURE",
            color = AurixCyanElectric,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Headline
        Text(
            text = "EVERY LINE\nHAS A PURPOSE.",
            color = AurixTextPrimary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 38.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.testTag("exterior_reveal_headline")
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "From raw carbon-fiber monocoque to millimeter-accurate wind tunnel surfaces. Drag is the enemy; downforce is our triumph.",
            color = AurixTextSecondary,
            fontSize = 13.5.sp,
            lineHeight = 19.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3D Canvas Box with interactive rotation slider & controls
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color(0x3300E5FF), RoundedCornerShape(16.dp))
                .background(Color(0xFF070A0F))
        ) {
            Aurix3DVehicleCanvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(310.dp)
                    .testTag("exterior_3d_canvas"),
                exteriorColor = selectedColor,
                wheelStyle = selectedWheel,
                isExplodedView = isExplodedView,
                showAerodynamics = showAeroTrails,
                yawAngleDeg = cameraAngleYaw,
                onAngleChange = { cameraAngleYaw = it }
            )

            // Dynamic camera presets row on top
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .background(Color(0xDD0B0E14), RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CameraPresetChip("FRONT 3/4", 45f, cameraAngleYaw) { cameraAngleYaw = it }
                    CameraPresetChip("SIDE", 90f, cameraAngleYaw) { cameraAngleYaw = it }
                    CameraPresetChip("REAR 3/4", 135f, cameraAngleYaw) { cameraAngleYaw = it }
                }

                // Toggles for Aero Wind & Exploded Panels
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(if (showAeroTrails) AurixCyanElectric else Color(0x99101622), CircleShape)
                            .border(1.dp, Color(0x4400E5FF), CircleShape)
                            .clickable { showAeroTrails = !showAeroTrails }
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Air,
                            contentDescription = "Toggle Wind Streamlines",
                            tint = if (showAeroTrails) AurixObsidianBlack else Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(if (isExplodedView) AurixCyanElectric else Color(0x99101622), CircleShape)
                            .border(1.dp, Color(0x4400E5FF), CircleShape)
                            .clickable { isExplodedView = !isExplodedView }
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Category,
                            contentDescription = "Toggle Exploded View",
                            tint = if (isExplodedView) AurixObsidianBlack else Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Controlled 360 Scroll/Slider Turntable
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F1420), RoundedCornerShape(12.dp))
                .border(1.dp, Color(0x2288A0C0), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Cached,
                        contentDescription = "Turntable",
                        tint = AurixCyanElectric,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "TURNTABLE ANGLE: ${cameraAngleYaw.toInt()}°",
                        color = AurixCyanElectric,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "DRAG OR SCRUB",
                    color = AurixTextSecondary,
                    fontSize = 10.sp
                )
            }

            Slider(
                value = cameraAngleYaw,
                onValueChange = { cameraAngleYaw = it },
                valueRange = 0f..360f,
                colors = SliderDefaults.colors(
                    thumbColor = AurixCyanElectric,
                    activeTrackColor = AurixCyanElectric,
                    inactiveTrackColor = Color(0xFF232D3F)
                ),
                modifier = Modifier.testTag("exterior_turntable_slider")
            )

            Text(
                text = angleDescription,
                color = AurixSilverMetallic,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Exterior Paint Customizer
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Palette,
                contentDescription = "Color Palette",
                tint = AurixCyanElectric,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "BESPOKE FINISH: ${selectedColor.displayName.uppercase()}",
                color = AurixTextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ExteriorColor.values().forEach { colorOption ->
                val isSelected = colorOption == selectedColor
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) Color(0xFF162030) else Color(0xFF0F1420))
                        .border(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) AurixCyanElectric else Color(0x22FFFFFF),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { onColorSelected(colorOption) }
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                        .testTag("color_chip_${colorOption.name.lowercase()}")
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(colorOption.color, CircleShape)
                            .border(1.dp, Color.White.copy(alpha = 0.4f), CircleShape)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = colorOption.displayName,
                        color = if (isSelected) AurixCyanElectric else AurixTextSecondary,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Wheel Rim Options
        Text(
            text = "WHEEL FORGING SELECTION",
            color = AurixTextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            WheelStyle.values().forEach { wheel ->
                val isSelected = wheel == selectedWheel
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) Color(0xFF162130) else Color(0xFF0F1420))
                        .border(
                            1.dp,
                            if (isSelected) AurixCyanElectric else Color(0x1AFFFFFF),
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onWheelSelected(wheel) }
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                        .testTag("wheel_option_${wheel.name.lowercase()}"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = wheel.displayName,
                            color = if (isSelected) AurixCyanElectric else AurixSilverMetallic,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = wheel.description,
                            color = AurixTextSecondary,
                            fontSize = 10.5.sp
                        )
                    }
                    Text(
                        text = "${wheel.weightKg} kg/ea",
                        color = AurixCyanElectric,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun CameraPresetChip(
    label: String,
    targetAngle: Float,
    currentAngle: Float,
    onSelect: (Float) -> Unit
) {
    val isNear = kotlin.math.abs(currentAngle - targetAngle) < 10f
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isNear) AurixCyanElectric else Color.Transparent)
            .clickable { onSelect(targetAngle) }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = if (isNear) AurixObsidianBlack else AurixTextSecondary,
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
