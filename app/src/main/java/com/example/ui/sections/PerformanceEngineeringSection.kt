package com.example.ui.sections

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DriveMode
import com.example.data.model.VehicleSpecs
import com.example.ui.theme.AurixCyanElectric
import com.example.ui.theme.AurixObsidianBlack
import com.example.ui.theme.AurixSilverMetallic
import com.example.ui.theme.AurixTextPrimary
import com.example.ui.theme.AurixTextSecondary

@Composable
fun PerformanceEngineeringSection(
    specs: VehicleSpecs,
    activeDriveMode: DriveMode,
    onDriveModeChanged: (DriveMode) -> Unit,
    modifier: Modifier = Modifier
) {
    var rpmSlider by remember { mutableFloatStateOf(6500f) }

    // Calculate dynamic HP and Torque based on RPM
    val currentHp = ((rpmSlider / 7800f) * specs.powerHp).toInt().coerceIn(60, specs.powerHp)
    val currentTorqueNm = (specs.torqueNm * (1.0f - (kotlin.math.abs(rpmSlider - 4500f) / 9000f))).toInt().coerceIn(240, specs.torqueNm)

    val modeColor by animateColorAsState(
        targetValue = activeDriveMode.color,
        label = "mode_color"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AurixObsidianBlack)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // Section C Tag
        Text(
            text = "SECTION C • PERFORMANCE & ENGINEERING",
            color = AurixCyanElectric,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Headline
        Text(
            text = "BUILT BEYOND\nEXPECTATIONS.",
            color = AurixTextPrimary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 38.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.testTag("performance_headline")
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "An all-aluminum 3.0L twin-turbo hybrid V6 paired with dual axial-flux motors. Instant electric torque off the line, sustained atmospheric boost at the top end.",
            color = AurixTextSecondary,
            fontSize = 13.5.sp,
            lineHeight = 19.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        // 4 Editable Core Specs from VehicleSpecs
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0D121C), RoundedCornerShape(14.dp))
                .border(1.dp, Color(0x2288A0C0), RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {
            Text(
                text = "FACTORY BENCHMARK METRICS",
                color = AurixCyanElectric,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SpecCard(title = "POWER OUTPUT", value = "${specs.powerHp} hp", subtitle = "Twin-Turbo Hybrid V6", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                SpecCard(title = "0–100 KM/H", value = "${specs.zeroToHundredSec}s", subtitle = "Launch Control AWD", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SpecCard(title = "TOP SPEED", value = "${specs.topSpeedKmh} km/h", subtitle = "Electronically Limited", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                SpecCard(title = "DRIVETRAIN", value = "AWD", subtitle = specs.drivetrain, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SpecCard(title = "AERO DOWNFORCE", value = "${specs.downforceKg} kg", subtitle = "@ 250 km/h Track Aero", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                SpecCard(title = "DRY WEIGHT", value = "${specs.curbWeightKg} kg", subtitle = "Carbon Monocoque", modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Interactive Dyno Power Band Curve
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F1522), RoundedCornerShape(14.dp))
                .border(1.dp, Color(0x3300E5FF), RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = "Dyno",
                        tint = AurixCyanElectric,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "VIRTUAL DYNO TELEMETRY",
                        color = AurixCyanElectric,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "${rpmSlider.toInt()} RPM",
                    color = AurixSilverMetallic,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Canvas Line Chart for Power and Torque Curves
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val w = size.width
                    val h = size.height

                    // Background grid lines
                    for (i in 1..4) {
                        val y = h * (i / 5f)
                        drawLine(
                            color = Color(0x1AFFFFFF),
                            start = Offset(0f, y),
                            end = Offset(w, y),
                            strokeWidth = 1f
                        )
                    }

                    // Horsepower Curve (Cyan)
                    val hpPath = Path()
                    val torquePath = Path()
                    val steps = 20

                    for (s in 0..steps) {
                        val t = s.toFloat() / steps
                        val x = t * w
                        // Quadratic power curve peaking near redline
                        val normHp = 0.2f + 0.8f * (t * t)
                        val yHp = h - (normHp * (h - 12f))

                        // Torque curve plateauing in mid-range
                        val normTq = 0.5f + 0.5f * kotlin.math.sin(t * kotlin.math.PI.toFloat())
                        val yTq = h - (normTq * (h - 12f))

                        if (s == 0) {
                            hpPath.moveTo(x, yHp)
                            torquePath.moveTo(x, yTq)
                        } else {
                            hpPath.lineTo(x, yHp)
                            torquePath.lineTo(x, yTq)
                        }
                    }

                    // Draw Torque (Gold/Amber)
                    drawPath(path = torquePath, color = Color(0xFFF59E0B), style = Stroke(width = 2.2f))
                    // Draw HP (Cyan)
                    drawPath(path = hpPath, color = AurixCyanElectric, style = Stroke(width = 2.8f))

                    // Draw current RPM cursor line
                    val currentX = ((rpmSlider - 1000f) / 7000f).coerceIn(0f, 1f) * w
                    drawLine(
                        color = Color.White,
                        start = Offset(currentX, 0f),
                        end = Offset(currentX, h),
                        strokeWidth = 2f
                    )
                }
            }

            Slider(
                value = rpmSlider,
                onValueChange = { rpmSlider = it },
                valueRange = 1000f..8000f,
                colors = SliderDefaults.colors(
                    thumbColor = AurixCyanElectric,
                    activeTrackColor = AurixCyanElectric,
                    inactiveTrackColor = Color(0xFF1B2332)
                ),
                modifier = Modifier.testTag("dyno_rpm_slider")
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "POWER: $currentHp HP (Cyan)",
                    color = AurixCyanElectric,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "TORQUE: $currentTorqueNm NM (Amber)",
                    color = Color(0xFFF59E0B),
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Drive Mode Calibration Switcher
        Text(
            text = "INTELLIGENT DYNAMICS MODE",
            color = AurixTextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DriveMode.values().forEach { mode ->
                val isSelected = mode == activeDriveMode
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) mode.color.copy(alpha = 0.16f) else Color(0xFF0F1420))
                        .border(
                            1.2.dp,
                            if (isSelected) mode.color else Color(0x22FFFFFF),
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onDriveModeChanged(mode) }
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                        .testTag("drive_mode_${mode.name.lowercase()}"),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = mode.displayName,
                        color = if (isSelected) mode.color else AurixSilverMetallic,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = mode.subtitle,
                        color = AurixTextSecondary,
                        fontSize = 9.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Drive Mode Telemetry Specs
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0A0F17), RoundedCornerShape(10.dp))
                .border(1.dp, modeColor.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Active Aero Wing:", color = AurixTextSecondary, fontSize = 11.sp)
                Text(text = "${activeDriveMode.aeroWingAngleDeg}° Attack Angle", color = modeColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Chassis Suspension:", color = AurixTextSecondary, fontSize = 11.sp)
                Text(text = activeDriveMode.suspensionStiffness, color = AurixSilverMetallic, fontSize = 11.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Titanium Exhaust Valve:", color = AurixTextSecondary, fontSize = 11.sp)
                Text(text = activeDriveMode.exhaustValve, color = AurixSilverMetallic, fontSize = 11.sp)
            }
        }
    }
}

@Composable
private fun SpecCard(title: String, value: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color(0xFF131A26), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0x1AFFFFFF), RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Text(
            text = title,
            color = AurixTextSecondary,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
        )
        Text(
            text = value,
            color = AurixCyanElectric,
            fontSize = 17.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = subtitle,
            color = AurixTextSecondary,
            fontSize = 9.sp
        )
    }
}
