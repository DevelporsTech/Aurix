package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.AurixCyanElectric

data class VehicleSpecs(
    val modelName: String = "AURIX GT-1 APEX",
    val tagline: String = "Precision engineering. Unmistakable presence.",
    val powerHp: Int = 450,
    val zeroToHundredSec: Double = 3.8,
    val topSpeedKmh: Int = 300,
    val drivetrain: String = "Intelligent Torque-Vectoring AWD",
    val engineType: String = "3.0L Twin-Turbo V6 Hybrid-Aero Dual-Motor",
    val torqueNm: Int = 620,
    val downforceKg: Int = 580,
    val curbWeightKg: Int = 1420,
    val pricePkrCrore: Double = 8.5,
    val pricePkrRaw: Long = 85_000_000L
)

enum class ExteriorColor(
    val displayName: String,
    val color: Color,
    val hexCode: String,
    val description: String
) {
    OBSIDIAN_CARBON(
        "Obsidian Carbon",
        Color(0xFF15181F),
        "#15181F",
        "Raw exposed carbon weave with ultra-deep metallic clear coat"
    ),
    CYAN_HYPERNOVA(
        "Cyber Cyan",
        AurixCyanElectric,
        "#00E5FF",
        "Multi-stage tri-coat iridescent electric cyan with pearl flake"
    ),
    LIQUID_PLATINUM(
        "Liquid Platinum",
        Color(0xFFD4DCDE),
        "#D4DCDE",
        "Mirrored aerospace-grade liquid metallic aluminum finish"
    ),
    ROSSO_SCUDERIA(
        "Crimson Velocity",
        Color(0xFFDC2626),
        "#DC2626",
        "Deep race-heritage high-saturation crimson red"
    ),
    MONACO_GOLD(
        "Monaco Bronze",
        Color(0xFFD97706),
        "#D97706",
        "Warm rich champagne metallic bronze inspired by Monaco harbor"
    )
}

enum class WheelStyle(
    val displayName: String,
    val sizeInches: Int,
    val description: String,
    val weightKg: Double
) {
    AERO_TURBINE_21(
        "21\" Forged Aero Turbine",
        21,
        "Directional carbon-fiber aero blades optimizing brake cooling",
        9.2
    ),
    TRACK_V_SPOKE_20(
        "20\" Ultra-Light Track V-Spoke",
        20,
        "CNC-milled monoblock forged titanium in satin anthracite",
        8.4
    ),
    DIAMOND_CUT_21(
        "21\" Diamond-Cut Bi-Color",
        21,
        "Polished face with gloss obsidian spoke pockets",
        9.8
    )
}

enum class DriveMode(
    val displayName: String,
    val subtitle: String,
    val color: Color,
    val throttleResponse: String,
    val suspensionStiffness: String,
    val aeroWingAngleDeg: Int,
    val exhaustValve: String
) {
    COMFORT(
        "COMFORT",
        "Adaptive Grand Touring",
        Color(0xFF38BDF8),
        "Progressive & Smooth",
        "Active Air Plush (-10mm)",
        0,
        "Quiet Baffled Mode"
    ),
    SPORT(
        "SPORT",
        "Dynamic Road Agility",
        Color(0xFFF59E0B),
        "Immediate Boost Mapping",
        "Dynamic Damping (-20mm)",
        15,
        "Bypass Valves 50% Open"
    ),
    TRACK(
        "TRACK",
        "Apex Lap Time Precision",
        Color(0xFFEF4444),
        "Razor Linear Telemetry",
        "Track Stiff (-30mm ground effect)",
        32,
        "Titanium Free-Flow Roar"
    ),
    HYPERSPACE(
        "AURIX HYPERSPACE",
        "Unrestricted Launch Performance",
        Color(0xFF00E5FF),
        "Dual Electric Overboost + Launch",
        "Lowest Drag Aero Pitch",
        45,
        "Full Acoustic Resonance"
    )
}

data class PakistanShowroom(
    val city: String,
    val name: String,
    val address: String,
    val phone: String,
    val hours: String,
    val hasTestTrack: Boolean
)

val PakistanShowrooms = listOf(
    PakistanShowroom(
        city = "Lahore",
        name = "AURIX Atelier Lahore",
        address = "Main Gulberg III, MM Alam Road (Adjacent to Xinhua Mall)",
        phone = "+92 (42) 3578-9000",
        hours = "Mon - Sat: 10:00 AM - 9:00 PM",
        hasTestTrack = true
    ),
    PakistanShowroom(
        city = "Karachi",
        name = "AURIX Flagship Karachi",
        address = "Block 4, Clifton (Ocean Towers Boulevard), Karachi",
        phone = "+92 (21) 3582-7700",
        hours = "Mon - Sat: 11:00 AM - 10:00 PM",
        hasTestTrack = true
    ),
    PakistanShowroom(
        city = "Islamabad",
        name = "AURIX Experience Center Islamabad",
        address = "Blue Area, Jinnah Avenue (Adjacent to Centaurus), Islamabad",
        phone = "+92 (51) 280-4400",
        hours = "Mon - Sat: 10:00 AM - 8:30 PM",
        hasTestTrack = false
    )
)

data class AurixCarModel(
    val id: String,
    val name: String,
    val badge: String,
    val basePricePkr: Long,
    val powerHp: Int,
    val zeroToHundred: Double,
    val topSpeedKmh: Int,
    val summary: String,
    val isElectric: Boolean = false
)

val AurixLineup = listOf(
    AurixCarModel(
        id = "aurix-gt-apex",
        name = "AURIX GT-1 APEX",
        badge = "HYBRID-AERO V6",
        basePricePkr = 85_000_000L,
        powerHp = 450,
        zeroToHundred = 3.8,
        topSpeedKmh = 300,
        summary = "The definitive benchmark. 450 hp twin-scroll hybrid powertrain, carbon monocoque, and active aerodynamics."
    ),
    AurixCarModel(
        id = "aurix-strada-gt",
        name = "AURIX STRADA VELOCE",
        badge = "GRAND TOURER",
        basePricePkr = 72_000_000L,
        powerHp = 420,
        zeroToHundred = 4.0,
        topSpeedKmh = 290,
        summary = "Bespoke long-distance grand touring luxury with active air suspension and Italian leather interior."
    ),
    AurixCarModel(
        id = "aurix-volt-hyper",
        name = "AURIX VOLT PURE ELECTRIC",
        badge = "DUAL AXIAL FLUX",
        basePricePkr = 96_000_000L,
        powerHp = 580,
        zeroToHundred = 3.2,
        topSpeedKmh = 310,
        summary = "Pure electric zero-emission hypercar with 800V silicon-carbide architecture and 600 km range.",
        isElectric = true
    )
)
