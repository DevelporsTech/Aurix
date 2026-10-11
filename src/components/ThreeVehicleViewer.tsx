import React, { useRef, useState, useEffect } from 'react';
import { Canvas, useFrame } from '@react-three/fiber';
import { OrbitControls, Float } from '@react-three/drei';
import * as THREE from 'three';

interface ModelProps {
  color: string;
  isSpinning?: boolean;
  wingAngle?: number;
  showAero?: boolean;
}

function SculptedSupercar({ color, isSpinning = true, wingAngle = 0, showAero = true }: ModelProps) {
  const groupRef = useRef<THREE.Group>(null);
  const wingRef = useRef<THREE.Group>(null);
  const wheelsRef = useRef<THREE.Group>(null);

  useFrame((_, delta) => {
    if (isSpinning && groupRef.current) {
      groupRef.current.rotation.y += delta * 0.3;
    }
    if (wingRef.current) {
      wingRef.current.rotation.x = (wingAngle * Math.PI) / 180;
    }
    if (wheelsRef.current && isSpinning) {
      wheelsRef.current.children.forEach((w) => {
        w.rotation.x += delta * 2.5;
      });
    }
  });

  return (
    <group ref={groupRef} position={[0, -0.35, 0]}>
      {/* 1. Low-Slung Aerodynamic Front Splitter */}
      <mesh position={[0, 0.18, 1.95]}>
        <boxGeometry args={[1.6, 0.08, 0.9]} />
        <meshStandardMaterial color="#0b1118" roughness={0.2} metalness={0.9} />
      </mesh>

      {/* 2. Sculpted Carbon Monocoque Lower Body */}
      <mesh position={[0, 0.32, 0.1]}>
        <boxGeometry args={[1.78, 0.28, 3.9]} />
        <meshStandardMaterial color={color} roughness={0.15} metalness={0.9} />
      </mesh>

      {/* 3. Sculpted Hood with Dual NACA Air Ducts */}
      <mesh position={[0, 0.42, 1.2]} rotation={[-0.12, 0, 0]}>
        <boxGeometry args={[1.42, 0.16, 1.4]} />
        <meshStandardMaterial color={color} roughness={0.15} metalness={0.9} />
      </mesh>

      {/* Hood Carbon Air Vent */}
      <mesh position={[0, 0.46, 1.3]}>
        <boxGeometry args={[0.5, 0.04, 0.6]} />
        <meshStandardMaterial color="#090d14" roughness={0.3} metalness={0.8} />
      </mesh>

      {/* 4. Cab-Forward Teardrop Cockpit Canopy */}
      <mesh position={[0, 0.72, -0.15]}>
        <boxGeometry args={[1.18, 0.38, 1.85]} />
        <meshPhysicalMaterial
          color="#08101a"
          roughness={0.04}
          metalness={0.85}
          transmission={0.88}
          thickness={0.5}
        />
      </mesh>

      {/* Cockpit Carbon Roof Cap */}
      <mesh position={[0, 0.92, -0.15]}>
        <boxGeometry args={[0.85, 0.05, 1.4]} />
        <meshStandardMaterial color="#0a0f18" roughness={0.2} metalness={0.9} />
      </mesh>

      {/* 5. Rear Muscular Haunches & Fastback Engine Cover */}
      <mesh position={[0, 0.48, -1.2]} rotation={[0.08, 0, 0]}>
        <boxGeometry args={[1.68, 0.22, 1.7]} />
        <meshStandardMaterial color={color} roughness={0.15} metalness={0.9} />
      </mesh>

      {/* 6. Active Aerodynamic Rear Wing & Dual Pylons */}
      <group ref={wingRef} position={[0, 0.88, -1.82]}>
        {/* Main Carbon Airfoil */}
        <mesh position={[0, 0, 0]}>
          <boxGeometry args={[1.75, 0.04, 0.38]} />
          <meshStandardMaterial color="#0f172a" roughness={0.2} metalness={0.95} />
        </mesh>
        {/* Aero Endplates */}
        {[-0.88, 0.88].map((x) => (
          <mesh key={x} position={[x, -0.02, 0]}>
            <boxGeometry args={[0.03, 0.14, 0.4]} />
            <meshStandardMaterial color="#00e5ff" roughness={0.2} metalness={0.8} />
          </mesh>
        ))}
        {/* Pylons */}
        {[-0.45, 0.45].map((x) => (
          <mesh key={x} position={[x, -0.22, 0]}>
            <cylinderGeometry args={[0.025, 0.025, 0.44, 12]} />
            <meshStandardMaterial color="#1e293b" metalness={0.9} />
          </mesh>
        ))}
      </group>

      {/* 7. Dual Laser Projector Headlights (Cyan Neon Glow) */}
      {[-0.64, 0.64].map((x) => (
        <group key={x} position={[x, 0.38, 1.95]}>
          <mesh>
            <sphereGeometry args={[0.07, 16, 16]} />
            <meshBasicMaterial color="#00e5ff" />
          </mesh>
          <pointLight color="#00e5ff" intensity={1.5} distance={3} />
        </group>
      ))}

      {/* 8. Full-Width Rear Cyber Taillight Strip */}
      <mesh position={[0, 0.46, -1.96]}>
        <boxGeometry args={[1.6, 0.04, 0.03]} />
        <meshBasicMaterial color="#ff2a4b" />
      </mesh>
      <mesh position={[0, 0.38, -1.96]}>
        <cylinderGeometry args={[0.07, 0.07, 0.08, 16]} rotation={[Math.PI / 2, 0, 0]} />
        <meshStandardMaterial color="#1e293b" metalness={0.95} />
      </mesh>

      {/* 9. 4 Detailed Multi-Spoke Wheels & Red Brake Calipers */}
      <group ref={wheelsRef}>
        {[-0.94, 0.94].map((x, i) =>
          [1.18, -1.22].map((z, j) => (
            <group key={`${i}-${j}`} position={[x, 0.18, z]}>
              {/* Outer Rubber Tire */}
              <mesh rotation={[0, 0, Math.PI / 2]}>
                <cylinderGeometry args={[0.35, 0.35, 0.22, 28]} />
                <meshStandardMaterial color="#0f1217" roughness={0.6} />
              </mesh>
              {/* Inner Alloy Rim */}
              <mesh rotation={[0, 0, Math.PI / 2]}>
                <cylinderGeometry args={[0.26, 0.26, 0.23, 20]} />
                <meshStandardMaterial color="#94a3b8" metalness={0.9} roughness={0.2} />
              </mesh>
              {/* Cyan Center Hub */}
              <mesh position={[x > 0 ? 0.12 : -0.12, 0, 0]}>
                <sphereGeometry args={[0.05, 12, 12]} />
                <meshBasicMaterial color="#00e5ff" />
              </mesh>
            </group>
          ))
        )}
      </group>

      {/* 10. Studio Ground Contact Shadow */}
      <mesh position={[0, 0.02, 0]} rotation={[-Math.PI / 2, 0, 0]}>
        <planeGeometry args={[2.5, 4.8]} />
        <meshBasicMaterial color="#000000" transparent opacity={0.65} />
      </mesh>
    </group>
  );
}

export default function ThreeVehicleViewer({
  color = '#0b0e14',
  isSpinning = true,
  wingAngle = 0
}: {
  color?: string;
  isSpinning?: boolean;
  wingAngle?: number;
}) {
  const [isMobile, setIsMobile] = useState(false);

  useEffect(() => {
    const checkMobile = () => {
      setIsMobile(window.innerWidth < 768);
    };
    checkMobile();
    window.addEventListener('resize', checkMobile);
    return () => window.removeEventListener('resize', checkMobile);
  }, []);

  return (
    <div style={{ width: '100%', height: '100%', position: 'relative' }}>
      <Canvas
        camera={{
          position: isMobile ? [3.8, 2.0, 4.8] : [3.2, 1.7, 4.0],
          fov: isMobile ? 48 : 40
        }}
        dpr={[1, 2]}
        style={{ width: '100%', height: '100%' }}
      >
        <ambientLight intensity={0.65} />
        <directionalLight position={[10, 14, 6]} intensity={2.0} color="#ffffff" />
        <directionalLight position={[-8, 6, -6]} intensity={1.2} color="#00e5ff" />
        <directionalLight position={[0, -4, 0]} intensity={0.4} color="#1e293b" />

        <SculptedSupercar color={color} isSpinning={isSpinning} wingAngle={wingAngle} />

        <OrbitControls
          enableZoom={false}
          enablePan={false}
          maxPolarAngle={Math.PI / 2.08}
          minPolarAngle={Math.PI / 6}
          rotateSpeed={0.7}
        />
      </Canvas>
    </div>
  );
}
