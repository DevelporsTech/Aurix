package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.example.data.model.DriveMode
import com.example.data.model.ExteriorColor
import com.example.data.model.WheelStyle
import com.example.ui.theme.AurixCyanElectric
import com.example.ui.theme.AurixObsidianBlack
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * 3D Vector mathematical representation
 */
private data class Vec3(val x: Float, val y: Float, val z: Float) {
    fun rotateY(angleRad: Float): Vec3 {
        val cosA = cos(angleRad)
        val sinA = sin(angleRad)
        return Vec3(
            x = x * cosA + z * sinA,
            y = y,
            z = -x * sinA + z * cosA
        )
    }

    fun rotateX(angleRad: Float): Vec3 {
        val cosA = cos(angleRad)
        val sinA = sin(angleRad)
        return Vec3(
            x = x,
            y = y * cosA - z * sinA,
            z = y * sinA + z * cosA
        )
    }
}

private data class Face3D(
    val v1: Vec3,
    val v2: Vec3,
    val v3: Vec3,
    val v4: Vec3? = null,
    val baseColor: Color,
    val isGlass: Boolean = false,
    val isCarbon: Boolean = false,
    val isEmissive: Boolean = false
)

@Composable
fun Aurix3DVehicleCanvas(
    modifier: Modifier = Modifier,
    exteriorColor: ExteriorColor = ExteriorColor.OBSIDIAN_CARBON,
    wheelStyle: WheelStyle = WheelStyle.AERO_TURBINE_21,
    driveMode: DriveMode = DriveMode.COMFORT,
    isExplodedView: Boolean = false,
    showAerodynamics: Boolean = true,
    yawAngleDeg: Float? = null,
    onAngleChange: ((Float) -> Unit)? = null
) {
    var manualYaw by remember { mutableFloatStateOf(yawAngleDeg ?: 38f) }
    var manualPitch by remember { mutableFloatStateOf(16f) }

    // If external yaw is passed, synchronize
    val effectiveYaw = yawAngleDeg ?: manualYaw

    // Infinite animation for wheel rotation, wind airflow, and ambient glow
    val infiniteTransition = rememberInfiniteTransition(label = "vehicle_3d_dynamics")
    val flowProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "airflow_particles"
    )

    val wheelSpinRad by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wheel_spin"
    )

    Box(
        modifier = modifier
            .background(AurixObsidianBlack)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val newYaw = (manualYaw + dragAmount.x * 0.45f) % 360f
                    manualYaw = if (newYaw < 0) newYaw + 360f else newYaw
                    manualPitch = (manualPitch - dragAmount.y * 0.25f).coerceIn(4f, 48f)
                    onAngleChange?.invoke(manualYaw)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val centerX = canvasWidth / 2f
            val centerY = canvasHeight / 2f + 25f

            // Dynamic scaling based on viewport
            val scale = (canvasWidth / 5.2f).coerceIn(120f, 260f)
            val cameraDist = 8.5f

            val yawRad = (effectiveYaw * PI / 180f).toFloat()
            val pitchRad = (manualPitch * PI / 180f).toFloat()

            // 1. Draw 3D Ground Shadow & Studio Floor Reflection Grid
            drawStudioFloor(
                centerX = centerX,
                centerY = centerY,
                yawRad = yawRad,
                pitchRad = pitchRad,
                scale = scale,
                cameraDist = cameraDist
            )

            // 2. Compute 3D Mesh of AURIX Supercar
            val meshFaces = buildAurixSupercarMesh(
                bodyColor = exteriorColor.color,
                wingAngleDeg = driveMode.aeroWingAngleDeg,
                isExploded = isExplodedView,
                driveMode = driveMode
            )

            // 3. Project 3D Faces into 2D with depth sorting (Painter's Algorithm)
            renderMeshWithLighting(
                faces = meshFaces,
                centerX = centerX,
                centerY = centerY,
                yawRad = yawRad,
                pitchRad = pitchRad,
                scale = scale,
                cameraDist = cameraDist,
                bodyColor = exteriorColor.color,
                headlightColor = when (driveMode) {
                    DriveMode.TRACK -> Color(0xFFFF3344)
                    DriveMode.HYPERSPACE -> AurixCyanElectric
                    else -> Color(0xFFE0F7FF)
                }
            )

            // 4. Render 3D Wheels with Rim Style
            renderWheels(
                centerX = centerX,
                centerY = centerY,
                yawRad = yawRad,
                pitchRad = pitchRad,
                scale = scale,
                cameraDist = cameraDist,
                wheelSpin = wheelSpinRad,
                wheelStyle = wheelStyle,
                driveMode = driveMode
            )

            // 5. Draw Aerodynamic Wind Tunnel Particle Trails (if enabled)
            if (showAerodynamics) {
                drawAeroStreamlines(
                    centerX = centerX,
                    centerY = centerY,
                    yawRad = yawRad,
                    pitchRad = pitchRad,
                    scale = scale,
                    cameraDist = cameraDist,
                    progress = flowProgress,
                    driveMode = driveMode
                )
            }
        }
    }
}

/**
 * Renders the studio floor grid, circular reflective pedestal, and ambient soft contact shadows.
 */
private fun DrawScope.drawStudioFloor(
    centerX: Float,
    centerY: Float,
    yawRad: Float,
    pitchRad: Float,
    scale: Float,
    cameraDist: Float
) {
    // Contact Shadow oval beneath car
    drawOval(
        brush = Brush.radialGradient(
            colors = listOf(Color(0xCC000000), Color(0x66000000), Color.Transparent),
            center = Offset(centerX, centerY + scale * 0.42f),
            radius = scale * 1.55f
        ),
        topLeft = Offset(centerX - scale * 1.8f, centerY + scale * 0.15f),
        size = androidx.compose.ui.geometry.Size(scale * 3.6f, scale * 0.85f)
    )

    // Studio neon ambient circular ring on floor
    val ringPoints = 32
    val ringRadius = 2.4f
    val path = Path()
    var isFirst = true

    for (i in 0..ringPoints) {
        val angle = (i.toFloat() / ringPoints) * 2 * PI.toFloat()
        val p3d = Vec3(cos(angle) * ringRadius, 0.52f, sin(angle) * ringRadius)
            .rotateY(yawRad)
            .rotateX(pitchRad)

        val depth = cameraDist - p3d.z
        if (depth > 0.1f) {
            val projX = centerX + (p3d.x * scale / (depth * 0.35f))
            val projY = centerY + (p3d.y * scale / (depth * 0.35f))
            if (isFirst) {
                path.moveTo(projX, projY)
                isFirst = false
            } else {
                path.lineTo(projX, projY)
            }
        }
    }
    path.close()

    drawPath(
        path = path,
        color = Color(0x1F00E5FF),
        style = Stroke(width = 2.5f)
    )
}

/**
 * Builds the geometric vertices and polygonal faces for the AURIX Supercar.
 * Low-slung Le Mans hypercar proportions: long wheelbase, wide front splitter,
 * cab-forward canopy, active aero wing, and rear venturi tunnels.
 */
private fun buildAurixSupercarMesh(
    bodyColor: Color,
    wingAngleDeg: Int,
    isExploded: Boolean,
    driveMode: DriveMode
): List<Face3D> {
    val explodeOffset = if (isExploded) 0.4f else 0f
    val wingLift = (wingAngleDeg / 45f) * 0.22f

    val faces = mutableListOf<Face3D>()

    // 1. FRONT SPLITTER & NOSE CONE
    val vNoseTip = Vec3(0f, 0.46f, 2.2f + explodeOffset)
    val vNoseL = Vec3(-0.95f, 0.48f, 1.9f)
    val vNoseR = Vec3(0.95f, 0.48f, 1.9f)
    val vSplitterL = Vec3(-1.05f, 0.52f, 2.1f)
    val vSplitterR = Vec3(1.05f, 0.52f, 2.1f)

    faces.add(Face3D(vNoseL, vNoseTip, vNoseR, null, Color(0xFF0F141C), isCarbon = true))
    faces.add(Face3D(vSplitterL, vNoseTip, vSplitterR, null, AurixCyanElectric, isEmissive = true))

    // 2. SCULPTED HOOD & DUAL AIR EXTRACTORS
    val vHoodCenter = Vec3(0f, 0.28f, 1.35f)
    val vHoodCowlL = Vec3(-0.75f, 0.22f, 0.95f)
    val vHoodCowlR = Vec3(0.75f, 0.22f, 0.95f)

    faces.add(Face3D(vNoseL, vHoodCenter, vNoseTip, null, bodyColor))
    faces.add(Face3D(vNoseR, vNoseTip, vHoodCenter, null, bodyColor))
    faces.add(Face3D(vNoseL, vHoodCowlL, vHoodCenter, null, bodyColor))
    faces.add(Face3D(vNoseR, vHoodCenter, vHoodCowlR, null, bodyColor))

    // Hood Aerodynamic Center Air Scoop (Carbon Vent)
    val vScoopFront = Vec3(0f, 0.35f, 1.7f)
    faces.add(Face3D(Vec3(-0.25f, 0.32f, 1.4f), vScoopFront, Vec3(0.25f, 0.32f, 1.4f), null, Color(0xFF141922), isCarbon = true))

    // 3. FRONT WHEEL ARCHES & FENDERS
    val vArchFL_Top = Vec3(-0.98f, 0.16f, 1.25f)
    val vArchFR_Top = Vec3(0.98f, 0.16f, 1.25f)
    faces.add(Face3D(vNoseL, vArchFL_Top, vHoodCowlL, null, bodyColor))
    faces.add(Face3D(vNoseR, vHoodCowlR, vArchFR_Top, null, bodyColor))

    // 4. COCKPIT CANOPY (WINDSHIELD & ROOF)
    val vWindshieldTopL = Vec3(-0.52f, -0.32f, 0.22f)
    val vWindshieldTopR = Vec3(0.52f, -0.32f, 0.22f)
    val vRoofRearL = Vec3(-0.46f, -0.34f, -0.55f)
    val vRoofRearR = Vec3(0.46f, -0.34f, -0.55f)

    // Windshield (Dark reflective polarized glass)
    faces.add(Face3D(vHoodCowlL, vWindshieldTopL, vWindshieldTopR, vHoodCowlR, Color(0xFF1A2634), isGlass = true))
    // Roof Panel (Carbon lightweight bubble)
    faces.add(Face3D(vWindshieldTopL, vRoofRearL, vRoofRearR, vWindshieldTopR, Color(0xFF0F1520), isCarbon = true))

    // Side Windows
    val vBeltlineL = Vec3(-0.92f, 0.12f, -0.15f)
    val vBeltlineR = Vec3(0.92f, 0.12f, -0.15f)
    faces.add(Face3D(vHoodCowlL, vWindshieldTopL, vRoofRearL, vBeltlineL, Color(0xFF182432), isGlass = true))
    faces.add(Face3D(vHoodCowlR, vBeltlineR, vRoofRearR, vWindshieldTopR, Color(0xFF182432), isGlass = true))

    // 5. DOORS & SCULPTED SIDE PODS (Deep dihedral air channels)
    val vSillBottomL = Vec3(-0.96f, 0.48f, -0.15f)
    val vSillBottomR = Vec3(0.96f, 0.48f, -0.15f)
    faces.add(Face3D(vBeltlineL, vSillBottomL, Vec3(-0.92f, 0.48f, 0.85f), vHoodCowlL, bodyColor))
    faces.add(Face3D(vBeltlineR, vHoodCowlR, Vec3(0.92f, 0.48f, 0.85f), vSillBottomR, bodyColor))

    // Side Aero Strakes in Cyan
    faces.add(Face3D(vSillBottomL, Vec3(-0.96f, 0.48f, -0.95f), Vec3(-0.98f, 0.44f, -0.15f), null, AurixCyanElectric, isEmissive = true))
    faces.add(Face3D(vSillBottomR, Vec3(0.98f, 0.44f, -0.15f), Vec3(0.96f, 0.48f, -0.95f), null, AurixCyanElectric, isEmissive = true))

    // 6. REAR ENGINE DECK & HAUNCHES (Wide stance)
    val vRearHaunchL = Vec3(-1.02f, 0.12f, -1.25f)
    val vRearHaunchR = Vec3(1.02f, 0.12f, -1.25f)
    val vRearDeckCenter = Vec3(0f, 0.08f, -1.45f)

    faces.add(Face3D(vRoofRearL, vRearHaunchL, vRearDeckCenter, null, bodyColor))
    faces.add(Face3D(vRoofRearR, vRearDeckCenter, vRearHaunchR, null, bodyColor))
    faces.add(Face3D(vBeltlineL, vRearHaunchL, vSillBottomL, null, bodyColor))
    faces.add(Face3D(vBeltlineR, vSillBottomR, vRearHaunchR, null, bodyColor))

    // 7. ACTIVE REAR WING & ENDPLATES
    val wingY = -0.38f - wingLift - (explodeOffset * 0.5f)
    val vWingL = Vec3(-0.95f, wingY, -1.85f)
    val vWingR = Vec3(0.95f, wingY, -1.85f)
    val vWingTrailingL = Vec3(-0.95f, wingY + 0.08f, -2.15f)
    val vWingTrailingR = Vec3(0.95f, wingY + 0.08f, -2.15f)

    faces.add(Face3D(vWingL, vWingTrailingL, vWingTrailingR, vWingR, Color(0xFF111722), isCarbon = true))
    // Wing Pylons
    faces.add(Face3D(Vec3(-0.35f, 0.05f, -1.65f), Vec3(-0.35f, wingY, -1.95f), Vec3(-0.32f, wingY, -1.95f), Vec3(-0.32f, 0.05f, -1.65f), Color(0xFF222834), isCarbon = true))
    faces.add(Face3D(Vec3(0.32f, 0.05f, -1.65f), Vec3(0.32f, wingY, -1.95f), Vec3(0.35f, wingY, -1.95f), Vec3(0.35f, 0.05f, -1.65f), Color(0xFF222834), isCarbon = true))

    // 8. REAR FASIA, DIFFUSER & CYBER LIGHTBAR
    val vTailCenter = Vec3(0f, 0.32f, -1.98f)
    val vTailL = Vec3(-0.92f, 0.34f, -1.92f)
    val vTailR = Vec3(0.92f, 0.34f, -1.92f)

    faces.add(Face3D(vRearDeckCenter, vTailL, vTailCenter, null, Color(0xFF141A24), isCarbon = true))
    faces.add(Face3D(vRearDeckCenter, vTailCenter, vTailR, null, Color(0xFF141A24), isCarbon = true))

    // Continuous Full-Width LED Taillight Bar
    val vLightbarL = Vec3(-0.90f, 0.28f, -1.94f)
    val vLightbarR = Vec3(0.90f, 0.28f, -1.94f)
    faces.add(Face3D(vLightbarL, Vec3(-0.90f, 0.30f, -1.94f), Vec3(0.90f, 0.30f, -1.94f), vLightbarR, Color(0xFFFF2A4B), isEmissive = true))

    // Dual Venturi Diffuser Fins
    val vDiffuserBottom = Vec3(0f, 0.52f, -1.95f)
    faces.add(Face3D(vTailL, vDiffuserBottom, vTailR, null, Color(0xFF080C14), isCarbon = true))

    return faces
}

/**
 * Depth-sorts and renders the faces with metallic specular highlights and ambient shading.
 */
private fun DrawScope.renderMeshWithLighting(
    faces: List<Face3D>,
    centerX: Float,
    centerY: Float,
    yawRad: Float,
    pitchRad: Float,
    scale: Float,
    cameraDist: Float,
    bodyColor: Color,
    headlightColor: Color
) {
    // Light source vector in world space: high front-right studio key light
    val lightDir = Vec3(0.6f, -0.8f, 0.8f)

    // Sort by projected Z (average depth)
    val transformedFaces = faces.map { face ->
        val rotV1 = face.v1.rotateY(yawRad).rotateX(pitchRad)
        val rotV2 = face.v2.rotateY(yawRad).rotateX(pitchRad)
        val rotV3 = face.v3.rotateY(yawRad).rotateX(pitchRad)
        val rotV4 = face.v4?.rotateY(yawRad)?.rotateX(pitchRad)

        val avgZ = if (rotV4 != null) {
            (rotV1.z + rotV2.z + rotV3.z + rotV4.z) / 4f
        } else {
            (rotV1.z + rotV2.z + rotV3.z) / 3f
        }
        Triple(face, listOfNotNull(rotV1, rotV2, rotV3, rotV4), avgZ)
    }.sortedBy { it.third } // Draw back to front

    for ((face, vertices, _) in transformedFaces) {
        val path = Path()
        var valid = true

        for ((idx, v) in vertices.withIndex()) {
            val depth = cameraDist - v.z
            if (depth <= 0.2f) {
                valid = false
                break
            }
            val projX = centerX + (v.x * scale / (depth * 0.35f))
            val projY = centerY + (v.y * scale / (depth * 0.35f))

            if (idx == 0) path.moveTo(projX, projY) else path.lineTo(projX, projY)
        }

        if (!valid) continue
        path.close()

        // Calculate surface normal for realistic lighting
        val vA = vertices[1]
        val vB = vertices[0]
        val vC = vertices[2]
        val edge1 = Vec3(vA.x - vB.x, vA.y - vB.y, vA.z - vB.z)
        val edge2 = Vec3(vC.x - vB.x, vC.y - vB.y, vC.z - vB.z)

        // Cross product
        val normX = edge1.y * edge2.z - edge1.z * edge2.y
        val normY = edge1.z * edge2.x - edge1.x * edge2.z
        val normZ = edge1.x * edge2.y - edge1.y * edge2.x
        val normLen = kotlin.math.sqrt(normX * normX + normY * normY + normZ * normZ).coerceAtLeast(0.0001f)

        val nx = normX / normLen
        val ny = normY / normLen
        val nz = normZ / normLen

        // Diffuse Lambertian light factor
        val dot = (nx * lightDir.x + ny * lightDir.y + nz * lightDir.z).coerceIn(-1f, 1f)
        val lightFactor = ((dot + 1f) * 0.5f).coerceIn(0.25f, 1.0f)

        val renderColor = when {
            face.isEmissive -> face.baseColor
            face.isGlass -> Color(0x9925384D)
            face.isCarbon -> Color(
                red = (0.08f * lightFactor).coerceIn(0f, 1f),
                green = (0.11f * lightFactor).coerceIn(0f, 1f),
                blue = (0.16f * lightFactor).coerceIn(0f, 1f),
                alpha = 0.96f
            )
            else -> Color(
                red = (face.baseColor.red * lightFactor * 1.15f).coerceIn(0f, 1f),
                green = (face.baseColor.green * lightFactor * 1.15f).coerceIn(0f, 1f),
                blue = (face.baseColor.blue * lightFactor * 1.15f).coerceIn(0f, 1f),
                alpha = 1f
            )
        }

        // Draw solid polygon
        drawPath(path = path, color = renderColor, style = Fill)

        // Draw subtle metallic edge highlight
        val strokeColor = if (face.isEmissive) face.baseColor else Color(0x33FFFFFF)
        drawPath(path = path, color = strokeColor, style = Stroke(width = 1f))
    }

    // Draw Laser Headlight Glow Orbs on front bumper
    val hlPosL = Vec3(-0.75f, 0.40f, 1.82f).rotateY(yawRad).rotateX(pitchRad)
    val hlPosR = Vec3(0.75f, 0.40f, 1.82f).rotateY(yawRad).rotateX(pitchRad)

    listOf(hlPosL, hlPosR).forEach { hl ->
        val depth = cameraDist - hl.z
        if (depth > 0.5f && hl.z > -0.5f) { // Visible mostly from front
            val px = centerX + (hl.x * scale / (depth * 0.35f))
            val py = centerY + (hl.y * scale / (depth * 0.35f))
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(headlightColor, Color.Transparent),
                    center = Offset(px, py),
                    radius = scale * 0.16f
                ),
                radius = scale * 0.16f,
                center = Offset(px, py)
            )
        }
    }
}

/**
 * Renders 4 accurate 3D wheels with rotating multi-spoke rims and brake calipers.
 */
private fun DrawScope.renderWheels(
    centerX: Float,
    centerY: Float,
    yawRad: Float,
    pitchRad: Float,
    scale: Float,
    cameraDist: Float,
    wheelSpin: Float,
    wheelStyle: WheelStyle,
    driveMode: DriveMode
) {
    // Wheel hub center coordinates in 3D (FL, FR, RL, RR)
    val wheelPositions = listOf(
        Vec3(-0.96f, 0.42f, 1.25f),  // Front Left
        Vec3(0.96f, 0.42f, 1.25f),   // Front Right
        Vec3(-0.98f, 0.42f, -1.25f), // Rear Left
        Vec3(0.98f, 0.42f, -1.25f)   // Rear Right
    )

    // Sort wheels by depth
    val sortedWheels = wheelPositions.map { pos ->
        val rotated = pos.rotateY(yawRad).rotateX(pitchRad)
        Pair(pos, rotated)
    }.sortedBy { it.second.z }

    val spokeCount = when (wheelStyle) {
        WheelStyle.TRACK_V_SPOKE_20 -> 5
        WheelStyle.AERO_TURBINE_21 -> 7
        WheelStyle.DIAMOND_CUT_21 -> 6
    }

    for ((original, rotated) in sortedWheels) {
        val depth = cameraDist - rotated.z
        if (depth <= 0.2f) continue

        val px = centerX + (rotated.x * scale / (depth * 0.35f))
        val py = centerY + (rotated.y * scale / (depth * 0.35f))
        val wheelRadius = (0.32f * scale / (depth * 0.35f))

        // Outer rubber tire (matte black)
        drawCircle(
            color = Color(0xFF0F1217),
            radius = wheelRadius,
            center = Offset(px, py)
        )
        // Tire rim lip
        drawCircle(
            color = Color(0xFF283244),
            radius = wheelRadius,
            center = Offset(px, py),
            style = Stroke(width = 2.5f)
        )

        // Inner alloy barrel
        drawCircle(
            color = Color(0xFF1B2230),
            radius = wheelRadius * 0.72f,
            center = Offset(px, py)
        )

        // High-performance brake caliper (Cyan or Red in Track mode)
        val caliperColor = if (driveMode == DriveMode.TRACK) Color(0xFFFF2A4B) else AurixCyanElectric
        drawArc(
            color = caliperColor,
            startAngle = 35f,
            sweepAngle = 70f,
            useCenter = false,
            topLeft = Offset(px - wheelRadius * 0.65f, py - wheelRadius * 0.65f),
            size = androidx.compose.ui.geometry.Size(wheelRadius * 1.3f, wheelRadius * 1.3f),
            style = Stroke(width = 4f)
        )

        // Spokes rotated by wheelSpin
        for (s in 0 until spokeCount) {
            val spokeAngle = wheelSpin + (s.toFloat() / spokeCount) * 2 * PI.toFloat()
            val sx = px + cos(spokeAngle) * (wheelRadius * 0.68f)
            val sy = py + sin(spokeAngle) * (wheelRadius * 0.68f)

            drawLine(
                color = when (wheelStyle) {
                    WheelStyle.AERO_TURBINE_21 -> Color(0xFF88A4C4)
                    WheelStyle.TRACK_V_SPOKE_20 -> Color(0xFFCBD5E1)
                    WheelStyle.DIAMOND_CUT_21 -> Color(0xFFE2E8F0)
                },
                start = Offset(px, py),
                end = Offset(sx, sy),
                strokeWidth = 2.8f
            )
        }

        // Center hub cap with AURIX cyan dot
        drawCircle(
            color = AurixCyanElectric,
            radius = wheelRadius * 0.16f,
            center = Offset(px, py)
        )
    }
}

/**
 * Aerodynamic streamline wind tunnel vectors flowing seamlessly over the vehicle body.
 */
private fun DrawScope.drawAeroStreamlines(
    centerX: Float,
    centerY: Float,
    yawRad: Float,
    pitchRad: Float,
    scale: Float,
    cameraDist: Float,
    progress: Float,
    driveMode: DriveMode
) {
    val lineCount = 5
    val streamlineColor = when (driveMode) {
        DriveMode.TRACK -> Color(0x88FF3355)
        DriveMode.HYPERSPACE -> Color(0xAA00E5FF)
        else -> Color(0x6600E5FF)
    }

    for (i in 0 until lineCount) {
        val xOffset = ((i - 2) * 0.42f)
        // Flowing along Z from front nose (2.2f) to rear exhaust (-2.4f)
        val zStart = 2.4f
        val zEnd = -2.6f
        val totalLength = zStart - zEnd

        val segmentCount = 14
        val path = Path()
        var started = false

        for (s in 0..segmentCount) {
            val t = s.toFloat() / segmentCount
            val curZ = zStart - t * totalLength

            // Calculate height profile based on car roof curvature
            val curY = when {
                curZ > 1.2f -> 0.46f - (2.4f - curZ) * 0.15f
                curZ in -0.5f..1.2f -> -0.36f - (curZ * 0.05f)
                else -> -0.32f + (-0.5f - curZ) * 0.35f
            }

            val p3d = Vec3(xOffset * 0.85f, curY - 0.08f, curZ)
                .rotateY(yawRad)
                .rotateX(pitchRad)

            val depth = cameraDist - p3d.z
            if (depth > 0.2f) {
                val px = centerX + (p3d.x * scale / (depth * 0.35f))
                val py = centerY + (p3d.y * scale / (depth * 0.35f))

                if (!started) {
                    path.moveTo(px, py)
                    started = true
                } else {
                    path.lineTo(px, py)
                }
            }
        }

        drawPath(
            path = path,
            color = streamlineColor,
            style = Stroke(
                width = 2f,
                pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                    floatArrayOf(24f, 24f),
                    progress * 48f
                )
            )
        )
    }
}
