package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.data.model.DriveMode
import com.example.data.model.ExteriorColor
import com.example.data.model.VehicleSpecs
import com.example.data.model.WheelStyle
import com.example.ui.components.AurixHeaderNav
import com.example.ui.components.AurixNavTab
import com.example.ui.sections.AurixAiAdvisorSection
import com.example.ui.sections.AurixFooter
import com.example.ui.sections.DrivingEnvironmentSection
import com.example.ui.sections.ExteriorRevealSection
import com.example.ui.sections.HeroSection
import com.example.ui.sections.InteriorExperienceSection
import com.example.ui.sections.PakistanShowroomECommerceSection
import com.example.ui.sections.PerformanceEngineeringSection
import com.example.ui.theme.AurixObsidianBlack
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AurixAutomotiveApp()
            }
        }
    }
}

@Composable
fun AurixAutomotiveApp() {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    // Centralized Vehicle Specs Configuration
    val vehicleSpecs = remember { VehicleSpecs() }

    // User Customization State
    var selectedExteriorColor by remember { mutableStateOf(ExteriorColor.OBSIDIAN_CARBON) }
    var selectedWheelStyle by remember { mutableStateOf(WheelStyle.AERO_TURBINE_21) }
    var activeDriveMode by remember { mutableStateOf(DriveMode.COMFORT) }
    var activeNavTab by remember { mutableStateOf(AurixNavTab.CINEMATIC_STORY) }

    // Navigation indices in LazyColumn:
    // 0: HeroSection (Section A)
    // 1: ExteriorRevealSection (Section B)
    // 2: PerformanceEngineeringSection (Section C)
    // 3: InteriorExperienceSection (Section D)
    // 4: DrivingEnvironmentSection (Section E)
    // 5: PakistanShowroomECommerceSection (Section F)
    // 6: AurixAiAdvisorSection (Gemini High Thinking)
    // 7: AurixFooter (Section G)
    fun navigateToTab(tab: AurixNavTab) {
        activeNavTab = tab
        coroutineScope.launch {
            val targetIndex = when (tab) {
                AurixNavTab.CINEMATIC_STORY -> 0
                AurixNavTab.STUDIO_3D -> 1
                AurixNavTab.PERFORMANCE -> 2
                AurixNavTab.COCKPIT -> 3
                AurixNavTab.PAKISTAN_BUY -> 5
                AurixNavTab.AI_ADVISOR -> 6
            }
            listState.animateScrollToItem(targetIndex)
        }
    }

    // Back handling: if not at top, return to top
    BackHandler(enabled = listState.firstVisibleItemIndex > 0) {
        navigateToTab(AurixNavTab.CINEMATIC_STORY)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(AurixObsidianBlack),
        topBar = {
            AurixHeaderNav(
                activeTab = activeNavTab,
                onTabSelected = { navigateToTab(it) },
                onRequestTestDrive = { navigateToTab(AurixNavTab.PAKISTAN_BUY) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(AurixObsidianBlack)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                // SECTION A: Cinematic Hero
                item(key = "section_hero") {
                    HeroSection(
                        specs = vehicleSpecs,
                        onExploreMachine = { navigateToTab(AurixNavTab.PERFORMANCE) },
                        onDiscoverDesign = { navigateToTab(AurixNavTab.STUDIO_3D) }
                    )
                }

                // SECTION B: Exterior Design Reveal & 3D Turntable
                item(key = "section_exterior") {
                    ExteriorRevealSection(
                        selectedColor = selectedExteriorColor,
                        onColorSelected = { selectedExteriorColor = it },
                        selectedWheel = selectedWheelStyle,
                        onWheelSelected = { selectedWheelStyle = it }
                    )
                }

                // SECTION C: Performance and Engineering Dyno
                item(key = "section_performance") {
                    PerformanceEngineeringSection(
                        specs = vehicleSpecs,
                        activeDriveMode = activeDriveMode,
                        onDriveModeChanged = { activeDriveMode = it }
                    )
                }

                // SECTION D: Interior Cockpit Experience
                item(key = "section_interior") {
                    InteriorExperienceSection()
                }

                // SECTION E: Cinematic Driving Environment & Launch Control
                item(key = "section_driving") {
                    DrivingEnvironmentSection()
                }

                // SECTION F: Final Showcase & Pakistan Atelier E-Commerce (PKR)
                item(key = "section_pakistan_ecommerce") {
                    PakistanShowroomECommerceSection(
                        specs = vehicleSpecs,
                        onRequestTestDrive = { /* Handled in section form */ },
                        onExploreSpecs = { navigateToTab(AurixNavTab.PERFORMANCE) }
                    )
                }

                // SECTION: AURIX AI Intelligence (Gemini High Thinking)
                item(key = "section_ai_advisor") {
                    AurixAiAdvisorSection()
                }

                // SECTION G: Refined Footer
                item(key = "section_footer") {
                    AurixFooter(
                        onNavigate = { navigateToTab(it) }
                    )
                }
            }
        }
    }
}
