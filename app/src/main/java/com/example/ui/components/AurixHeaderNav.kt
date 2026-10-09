package com.example.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricCar
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AurixCyanElectric
import com.example.ui.theme.AurixGraphiteDark
import com.example.ui.theme.AurixObsidianBlack
import com.example.ui.theme.AurixTextPrimary
import com.example.ui.theme.AurixTextSecondary

enum class AurixNavTab(val title: String, val icon: ImageVector) {
    CINEMATIC_STORY("STORY", Icons.Default.Speed),
    STUDIO_3D("3D STUDIO", Icons.Default.ViewInAr),
    PERFORMANCE("PERFORMANCE", Icons.Default.LocalGasStation),
    COCKPIT("INTERIOR", Icons.Default.ElectricCar),
    PAKISTAN_BUY("PAKISTAN ATELIER", Icons.Default.LocationOn),
    AI_ADVISOR("AI ADVISOR", Icons.Default.Psychology)
}

@Composable
fun AurixHeaderNav(
    activeTab: AurixNavTab,
    onTabSelected: (AurixNavTab) -> Unit,
    onRequestTestDrive: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AurixObsidianBlack.copy(alpha = 0.95f))
            .statusBarsPadding()
            .border(width = 0.8.dp, color = Color(0x2200E5FF))
    ) {
        // Top Brand Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // AURIX Brand Identity
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onTabSelected(AurixNavTab.CINEMATIC_STORY) }
                    .testTag("brand_logo_home")
            ) {
                Image(
                    painter = painterResource(id = R.drawable.aurix_app_logo),
                    contentDescription = "AURIX Emblem",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "AURIX",
                        color = AurixTextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 4.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        text = "AUTOMOTIVE • PAKISTAN",
                        color = AurixCyanElectric,
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )
                }
            }

            // Quick CTAs: PKR Badge & Test Drive CTA
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF162130), RoundedCornerShape(6.dp))
                        .border(1.dp, Color(0x3300E5FF), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "PKR (Rs.)",
                        color = AurixCyanElectric,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onRequestTestDrive,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AurixCyanElectric,
                        contentColor = AurixObsidianBlack
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .height(36.dp)
                        .testTag("nav_test_drive_cta")
                ) {
                    Text(
                        text = "TEST DRIVE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        // Navigation Tabs Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AurixNavTab.values().forEach { tab ->
                val isSelected = tab == activeTab
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) AurixCyanElectric.copy(alpha = 0.15f) else Color.Transparent)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) AurixCyanElectric else Color(0x1AFFFFFF),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable { onTabSelected(tab) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("nav_tab_${tab.name.lowercase()}")
                ) {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title,
                        tint = if (isSelected) AurixCyanElectric else AurixTextSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = tab.title,
                        color = if (isSelected) AurixCyanElectric else AurixTextSecondary,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}
