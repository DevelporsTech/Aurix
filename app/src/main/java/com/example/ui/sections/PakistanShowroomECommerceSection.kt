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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AurixCarModel
import com.example.data.model.AurixLineup
import com.example.data.model.PakistanShowroom
import com.example.data.model.PakistanShowrooms
import com.example.data.model.VehicleSpecs
import com.example.ui.theme.AurixCyanElectric
import com.example.ui.theme.AurixObsidianBlack
import com.example.ui.theme.AurixSilverMetallic
import com.example.ui.theme.AurixTextPrimary
import com.example.ui.theme.AurixTextSecondary

@Composable
fun PakistanShowroomECommerceSection(
    specs: VehicleSpecs,
    onRequestTestDrive: () -> Unit,
    onExploreSpecs: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedModel by remember { mutableStateOf(AurixLineup[0]) }
    var selectedShowroom by remember { mutableStateOf(PakistanShowrooms[0]) }

    // FBR Tax Calculator State
    var isFiler by remember { mutableStateOf(true) }
    var showTaxCalculator by remember { mutableStateOf(false) }

    // Test Drive Booking Modal State
    var showBookingForm by remember { mutableStateOf(false) }
    var clientName by remember { mutableStateOf("") }
    var clientPhone by remember { mutableStateOf("") }
    var preferredDate by remember { mutableStateOf("Tomorrow (VIP Evening Slot)") }
    var hasLicense by remember { mutableStateOf(true) }
    var bookingConfirmedVoucher by remember { mutableStateOf<String?>(null) }
    var formError by remember { mutableStateOf<String?>(null) }

    // Trade-In Estimator
    var showTradeIn by remember { mutableStateOf(false) }
    var tradeInCarName by remember { mutableStateOf("Toyota Land Cruiser LC300 ZX") }
    var tradeInYear by remember { mutableStateOf("2023") }
    var estimatedTradeInPkrCrore by remember { mutableDoubleStateOf(5.8) }

    // Computations for FBR duties
    val basePrice = selectedModel.basePricePkr
    val customsDuty = (basePrice * 0.40).toLong() // 40% effective
    val salesTax = (basePrice * 0.18).toLong() // 18% Sales Tax
    val advanceWht = if (isFiler) (basePrice * 0.06).toLong() else (basePrice * 0.12).toLong()
    val totalOnRoadPkr = basePrice + customsDuty + salesTax + advanceWht
    val totalOnRoadCrore = totalOnRoadPkr / 10_000_000.0

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AurixObsidianBlack)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // Section F Tag
        Text(
            text = "SECTION F • FINAL SHOWCASE & PAKISTAN ATELIER",
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
            modifier = Modifier.testTag("showroom_headline")
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Discover a new perspective on performance. Direct allocations available exclusively through AURIX flagship ateliers in Lahore, Karachi, and Islamabad.",
            color = AurixTextSecondary,
            fontSize = 13.5.sp,
            lineHeight = 19.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Model Lineup Selector Cards (PKR Pricing in Crores)
        Text(
            text = "AURIX OFFICIAL PAKISTAN LINEUP (PKR)",
            color = AurixTextPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            AurixLineup.forEach { car ->
                val isSelected = car.id == selectedModel.id
                val priceInCrores = car.basePricePkr / 10_000_000.0

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) Color(0xFF142032) else Color(0xFF0F1522)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.2.dp,
                            if (isSelected) AurixCyanElectric else Color(0x1AFFFFFF),
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { selectedModel = car }
                        .testTag("car_lineup_${car.id}")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = car.name,
                                        color = if (isSelected) AurixCyanElectric else AurixSilverMetallic,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .background(Color(0xFF223145), RoundedCornerShape(4.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = car.badge,
                                            color = AurixCyanElectric,
                                            fontSize = 8.5.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Text(
                                    text = car.summary,
                                    color = AurixTextSecondary,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Column {
                                    Text(text = "POWER", color = AurixTextSecondary, fontSize = 9.sp)
                                    Text(text = "${car.powerHp} hp", color = AurixSilverMetallic, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                                Column {
                                    Text(text = "0-100", color = AurixTextSecondary, fontSize = 9.sp)
                                    Text(text = "${car.zeroToHundred}s", color = AurixSilverMetallic, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                                Column {
                                    Text(text = "TOP SPEED", color = AurixTextSecondary, fontSize = 9.sp)
                                    Text(text = "${car.topSpeedKmh} km/h", color = AurixSilverMetallic, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(text = "EX-SHOWROOM", color = AurixTextSecondary, fontSize = 9.sp)
                                Text(
                                    text = "PKR $priceInCrores Cr",
                                    color = AurixCyanElectric,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // FBR Customs Duty & Tax Calculator Expander
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F1522), RoundedCornerShape(12.dp))
                .border(1.dp, Color(0x3300E5FF), RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showTaxCalculator = !showTaxCalculator },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = "Tax Calculator",
                        tint = AurixCyanElectric,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "PAKISTAN FBR TAX & IMPORT DUTY BREAKDOWN",
                            color = AurixCyanElectric,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (showTaxCalculator) "Hide tariff schedule" else "Tap to calculate On-Road costs with Filer status",
                            color = AurixTextSecondary,
                            fontSize = 10.sp
                        )
                    }
                }

                Text(
                    text = if (showTaxCalculator) "CLOSE" else "VIEW",
                    color = AurixCyanElectric,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            AnimatedVisibility(visible = showTaxCalculator) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    // Filer / Non-Filer Switch
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF162030), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isFiler) "Active Filer Status (6% WHT)" else "Non-Filer Status (12% WHT)",
                            color = if (isFiler) AurixCyanElectric else Color(0xFFFF7043),
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Switch(
                            checked = isFiler,
                            onCheckedChange = { isFiler = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = AurixCyanElectric,
                                checkedTrackColor = Color(0xFF005662)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TaxRow(label = "Ex-Showroom Allocation:", value = "PKR ${(basePrice / 10_000_000.0)} Crore")
                    TaxRow(label = "Basic Customs & Regulatory Tariff:", value = "PKR ${(customsDuty / 10_000_000.0)} Crore")
                    TaxRow(label = "General Sales Tax (18%):", value = "PKR ${(salesTax / 10_000_000.0)} Crore")
                    TaxRow(label = "Advance WHT (Sec 231B):", value = "PKR ${(advanceWht / 100_000.0)} Lacs")

                    Spacer(modifier = Modifier.height(6.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0x33FFFFFF)))
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "TOTAL ESTIMATED ON-ROAD:",
                            color = AurixSilverMetallic,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "PKR ${String.format("%.2f", totalOnRoadCrore)} Crore",
                            color = AurixCyanElectric,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Official Showrooms in Pakistan
        Text(
            text = "AURIX OFFICIAL PAKISTAN ATELIERS",
            color = AurixTextPrimary,
            fontSize = 11.sp,
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
            PakistanShowrooms.forEach { showroom ->
                val isSelected = showroom.city == selectedShowroom.city
                Column(
                    modifier = Modifier
                        .width(220.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) Color(0xFF162338) else Color(0xFF0F1522))
                        .border(
                            1.dp,
                            if (isSelected) AurixCyanElectric else Color(0x1AFFFFFF),
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { selectedShowroom = showroom }
                        .padding(12.dp)
                        .testTag("showroom_${showroom.city.lowercase()}")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = showroom.city.uppercase(),
                            color = if (isSelected) AurixCyanElectric else AurixSilverMetallic,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black
                        )
                        if (showroom.hasTestTrack) {
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFF003840), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text(text = "TEST TRACK", color = AurixCyanElectric, fontSize = 7.5.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Text(
                        text = showroom.name,
                        color = AurixTextPrimary,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Text(
                        text = showroom.address,
                        color = AurixTextSecondary,
                        fontSize = 9.5.sp,
                        lineHeight = 13.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = showroom.phone,
                        color = AurixCyanElectric,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // PRIMARY & SECONDARY ACTION BUTTONS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    showBookingForm = true
                    bookingConfirmedVoucher = null
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AurixCyanElectric,
                    contentColor = AurixObsidianBlack
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("request_test_drive_cta")
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsCar,
                    contentDescription = "Test Drive",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "REQUEST TEST DRIVE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black
                )
            }

            OutlinedButton(
                onClick = onExploreSpecs,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x4400E5FF)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("explore_specifications_cta")
            ) {
                Text(
                    text = "EXPLORE SPECS",
                    color = AurixSilverMetallic,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Functional Test Drive Booking Dialog / Form
        AnimatedVisibility(visible = showBookingForm) {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .background(Color(0xFF0F1624), RoundedCornerShape(14.dp))
                    .border(1.2.dp, AurixCyanElectric, RoundedCornerShape(14.dp))
                    .padding(16.dp)
            ) {
                if (bookingConfirmedVoucher != null) {
                    // Confirmation State with Voucher
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified",
                            tint = AurixCyanElectric,
                            modifier = Modifier.size(44.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "VIP TEST DRIVE RESERVED",
                            color = AurixCyanElectric,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Reference Voucher: $bookingConfirmedVoucher",
                            color = AurixSilverMetallic,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Your private allocation test drive for the ${selectedModel.name} has been logged at ${selectedShowroom.name} in ${selectedShowroom.city}.\nOur Senior Brand Executive will call $clientPhone within 2 hours to confirm your VIP private track slot.",
                            color = AurixTextSecondary,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = { showBookingForm = false },
                            colors = ButtonDefaults.buttonColors(containerColor = AurixCyanElectric),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "DONE", color = AurixObsidianBlack, fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    // Interactive Booking Form
                    Text(
                        text = "VIP TEST DRIVE & ALLOCATION FORM",
                        color = AurixCyanElectric,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Reserve a private track or closed circuit experience in ${selectedShowroom.city}.",
                        color = AurixTextSecondary,
                        fontSize = 10.5.sp,
                        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = clientName,
                        onValueChange = { clientName = it; formError = null },
                        label = { Text("Full Name (Driver)") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AurixCyanElectric,
                            unfocusedBorderColor = Color(0x33FFFFFF),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("input_driver_name")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = clientPhone,
                        onValueChange = { clientPhone = it; formError = null },
                        label = { Text("Mobile (+92 3XX XXXXXXX)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AurixCyanElectric,
                            unfocusedBorderColor = Color(0x33FFFFFF),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("input_driver_phone")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Driver's License verification toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Valid CNIC & Driving License Verified",
                            color = AurixTextSecondary,
                            fontSize = 11.sp
                        )
                        Switch(
                            checked = hasLicense,
                            onCheckedChange = { hasLicense = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = AurixCyanElectric,
                                checkedTrackColor = Color(0xFF005662)
                            )
                        )
                    }

                    if (formError != null) {
                        Text(
                            text = formError ?: "",
                            color = Color(0xFFFF5252),
                            fontSize = 11.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                if (clientName.trim().length < 3) {
                                    formError = "Please enter your valid full name."
                                } else if (clientPhone.trim().length < 10) {
                                    formError = "Please enter a valid Pakistani contact number (+92 3xx)."
                                } else {
                                    val refCode = "AURIX-PK-${(10000..99999).random()}"
                                    bookingConfirmedVoucher = refCode
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AurixCyanElectric),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).testTag("submit_booking_btn")
                        ) {
                            Text(text = "CONFIRM RESERVATION", color = AurixObsidianBlack, fontWeight = FontWeight.Bold, fontSize = 11.5.sp)
                        }

                        OutlinedButton(
                            onClick = { showBookingForm = false },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "CANCEL", color = AurixTextSecondary, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TaxRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = AurixTextSecondary, fontSize = 10.5.sp)
        Text(text = value, color = AurixSilverMetallic, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}
