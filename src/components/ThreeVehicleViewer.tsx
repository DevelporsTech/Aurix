import React, { useRef, useState, useEffect } from 'react';
import { Canvas, useFrame } from '@react-three/fiber';
import { OrbitControls } from '@react-three/drei';
import * as THREE from 'three';

interface ModelProps {
  color: string;
  isSpinning?: boolean;
  wingAngle?: number;
}

function ProceduralSupercar({ color, isSpinning = true, wingAngle = 0 }: ModelProps) {
  const groupRef = useRef<THREE.Group>(null);
  const wingRef = useRef<THREE.Mesh>(null);

  useFrame((_, delta) => {
    if (isSpinning && groupRef.current) {
      groupRef.current.rotation.y += delta * 0.35;
    }
    if (wingRef.current) {
      wingRef.current.rotation.x = (wingAngle * Math.PI) / 180;
    }
  });

  return (
    <group ref={groupRef} position={[0, -0.3, 0]}>
      {/* Front Nose & Splitter */}
      <mesh position={[0, 0.28, 1.85]}>
        <boxGeometry args={[1.5, 0.12, 0.8]} />
        <meshStandardMaterial color="#0b1017" roughness={0.2} metalness={0.9} />
      </mesh>

      {/* Main Carbon Monocoque Body */}
      <mesh position={[0, 0.38, 0]}>
        <boxGeometry args={[1.75, 0.38, 3.8]} />
        <meshStandardMaterial color={color} roughness={0.18} metalness={0.88} />
      </mesh>

      {/* Cockpit Greenhouse Canopy (Tinted Mirror Glass) */}
      <mesh position={[0, 0.72, -0.2]}>
        <boxGeometry args={[1.15, 0.36, 1.8]} />
        <meshPhysicalMaterial
          color="#0c131c"
          roughness={0.05}
          metalness={0.9}
          transmission={0.85}
          thickness={0.6}
        />
      </mesh>

      {/* Active Rear Wing */}
      <mesh ref={wingRef} position={[0, 0.92, -1.75]}>
        <boxGeometry args={[1.65, 0.05, 0.35]} />
        <meshStandardMaterial color="#0f172a" roughness={0.2} metalness={0.95} />
      </mesh>

      {/* Dual Laser Headlights (Cyan Emissive) */}
      <mesh position={[-0.65, 0.38, 1.91]}>
        <sphereGeometry args={[0.07, 16, 16]} />
        <meshBasicMaterial color="#00e5ff" />
      </mesh>
      <mesh position={[0.65, 0.38, 1.91]}>
        <sphereGeometry args={[0.07, 16, 16]} />
        <meshBasicMaterial color="#00e5ff" />
      </mesh>

      {/* Continuous Rear Cyber Lightbar (Red) */}
      <mesh position={[0, 0.45, -1.91]}>
        <boxGeometry args={[1.55, 0.04, 0.04]} />
        <meshBasicMaterial color="#ff2a4b" />
      </mesh>

      {/* 4 Multi-Spoke Wheels */}
      {[-0.92, 0.92].map((x, i) =>
        [1.15, -1.15].map((z, j) => (
          <mesh key={`${i}-${j}`} position={[x, 0.15, z]} rotation={[0, 0, Math.PI / 2]}>
            <cylinderGeometry args={[0.34, 0.34, 0.2, 28]} />
            <meshStandardMaterial color="#1e293b" roughness={0.5} metalness={0.85} />
          </mesh>
        ))
      )}
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
    <div style={{ width: '100%', height: '100%', touchAction: 'pan-y' }}>
      <Canvas
        camera={{
          position: isMobile ? [3.8, 2.0, 5.2] : [3.2, 1.8, 4.2],
          fov: isMobile ? 48 : 42
        }}
        dpr={[1, 2]}
        style={{ width: '100%', height: '100%', touchAction: 'pan-y' }}
      >
        <ambientLight intensity={0.7} />
        <directionalLight position={[10, 12, 6]} intensity={1.8} />
        <directionalLight position={[-8, 6, -6]} intensity={0.8} color="#00e5ff" />
        <directionalLight position={[0, -5, 0]} intensity={0.3} color="#ffffff" />

        <ProceduralSupercar color={color} isSpinning={isSpinning} wingAngle={wingAngle} />

        <OrbitControls
          enableZoom={false}
          enablePan={false}
          maxPolarAngle={Math.PI / 2.05}
          minPolarAngle={Math.PI / 6}
          rotateSpeed={0.8}
        />
      </Canvas>
    </div>
  );
}
