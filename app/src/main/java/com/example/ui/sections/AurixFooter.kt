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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.AurixNavTab
import com.example.ui.theme.AurixCyanElectric
import com.example.ui.theme.AurixGraphiteDark
import com.example.ui.theme.AurixObsidianBlack
import com.example.ui.theme.AurixSilverMetallic
import com.example.ui.theme.AurixTextPrimary
import com.example.ui.theme.AurixTextSecondary

@Composable
fun AurixFooter(
    onNavigate: (AurixNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF06080D))
            .border(width = 1.dp, color = Color(0x1A00E5FF))
            .padding(horizontal = 20.dp, vertical = 28.dp)
            .navigationBarsPadding()
    ) {
        // Brand Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.aurix_app_logo),
                contentDescription = "AURIX Emblem",
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "AURIX",
                    color = AurixTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 4.sp,
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    text = "AUTOMOTIVE EXPERIENCE • PAKISTAN",
                    color = AurixCyanElectric,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
            }
        }

        Text(
            text = "Engineered at the boundary of aerodynamic science and luxury grand touring. Official flagship distribution and certified factory maintenance across Pakistan.",
            color = AurixTextSecondary,
            fontSize = 12.sp,
            lineHeight = 17.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Navigation Links
        Text(
            text = "EXPLORE EXPERIENCE",
            color = AurixCyanElectric,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                FooterLink("Cinematic Storyline") { onNavigate(AurixNavTab.CINEMATIC_STORY) }
                FooterLink("3D Real-Time Configurator") { onNavigate(AurixNavTab.STUDIO_3D) }
                FooterLink("Performance Dyno & Telemetry") { onNavigate(AurixNavTab.PERFORMANCE) }
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                FooterLink("Curved OLED Cockpit") { onNavigate(AurixNavTab.COCKPIT) }
                FooterLink("Pakistan Showrooms & Pricing") { onNavigate(AurixNavTab.PAKISTAN_BUY) }
                FooterLink("AI Engineering Intelligence") { onNavigate(AurixNavTab.AI_ADVISOR) }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Pakistan Concierge & Showroom Contact
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0B101A), RoundedCornerShape(10.dp))
                .border(1.dp, Color(0x1AFFFFFF), RoundedCornerShape(10.dp))
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Phone",
                    tint = AurixCyanElectric,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "24/7 NATIONWIDE CONCIERGE HOTLINE",
                    color = AurixCyanElectric,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "+92 (42) 3578-9000 (Lahore) • +92 (21) 3582-7700 (Karachi) • +92 (51) 280-4400 (Islamabad)",
                color = AurixSilverMetallic,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = AurixTextSecondary,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "vip-allocations@aurix-automotive.pk",
                    color = AurixTextSecondary,
                    fontSize = 10.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Warranty & Certified Badge
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = "Certified",
                tint = AurixCyanElectric,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "5-Year / 100,000 KM Comprehensive Warranty • M-2 / M-3 Motorway Roadside Support Included",
                color = AurixTextSecondary,
                fontSize = 10.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Copyright & Legal Notice
        Text(
            text = "© 2026 AURIX Automotive Pakistan. All rights reserved.\nSpecifications and performance metrics are engineered concept benchmarks. All prices are in PKR and subject to prevailing FBR statutory regulatory duties.",
            color = Color(0xFF64748B),
            fontSize = 9.5.sp,
            lineHeight = 14.sp
        )
    }
}

@Composable
private fun FooterLink(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        color = AurixSilverMetallic,
        fontSize = 11.5.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .clickable { onClick() }
            .testTag("footer_link_${text.lowercase().replace(" ", "_")}")
    )
}
