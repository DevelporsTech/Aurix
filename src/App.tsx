import React, { useState } from 'react';
import ThreeVehicleViewer from './components/ThreeVehicleViewer';
import {
  Gauge,
  Phone,
  MapPin,
  ShieldCheck,
  CheckCircle2,
  Play,
  RotateCcw,
  Sparkles,
  Sliders,
  ChevronRight,
  Zap,
  Volume2,
  VolumeX,
  Eye,
  Camera,
  Activity,
  Award,
  Layers
} from 'lucide-react';

export default function App() {
  const [carColor, setCarColor] = useState('#0b0e14');
  const [isSpinning, setIsSpinning] = useState(true);
  const [heroViewMode, setHeroViewMode] = useState<'3d' | 'photo'>('photo');
  const [driveMode, setDriveMode] = useState<'comfort' | 'sport' | 'track' | 'hyperspace'>('comfort');
  const [rpm, setRpm] = useState(6500);
  const [isEngineOn, setIsEngineOn] = useState(false);
  const [launchTesting, setLaunchTesting] = useState(false);
  const [launchDone, setLaunchDone] = useState(false);
  const [speed, setSpeed] = useState(120);

  // Interior Ambient Lighting Hue
  const [interiorHue, setInteriorHue] = useState<'cyan' | 'azure' | 'crimson' | 'gold' | 'emerald'>('cyan');
  const [cockpitScreen, setCockpitScreen] = useState<'telemetry' | 'nav' | 'audio'>('telemetry');

  // FBR Pakistan Tax State
  const [isFiler, setIsFiler] = useState(true);
  const [activeShowroom, setActiveShowroom] = useState<'lahore' | 'karachi' | 'islamabad'>('lahore');
  const [selectedModel, setSelectedModel] = useState<'gt-apex' | 'strada' | 'volt'>('gt-apex');

  // Booking Form State
  const [clientName, setClientName] = useState('');
  const [clientPhone, setClientPhone] = useState('');
  const [hasLicense, setHasLicense] = useState(true);
  const [bookingConfirmed, setBookingConfirmed] = useState<string | null>(null);

  // Exterior Colors
  const colors = [
    { name: 'Obsidian Carbon', hex: '#0b0e14', desc: 'Exposed carbon weave with deep lacquer' },
    { name: 'Cyber Cyan', hex: '#00e5ff', desc: 'Electric tri-coat iridescent cyan pearl' },
    { name: 'Liquid Platinum', hex: '#d1d5db', desc: 'Mirrored aerospace aluminum sheen' },
    { name: 'Crimson Velocity', hex: '#dc2626', desc: 'Deep race-heritage high-chroma red' },
    { name: 'Monaco Bronze', hex: '#d97706', desc: 'Warm champagne metallic bronze' }
  ];

  // Drive Mode configs
  const driveModes = {
    comfort: { name: 'COMFORT', wing: 0, color: '#38bdf8', desc: 'Plush adaptive air ride (-10mm)' },
    sport: { name: 'SPORT', wing: 15, color: '#f59e0b', desc: 'Dynamic boost damping (-20mm)' },
    track: { name: 'TRACK', wing: 32, color: '#ef4444', desc: '580 kg downforce attack (-30mm)' },
    hyperspace: { name: 'HYPERSPACE', wing: 45, color: '#00e5ff', desc: 'Unrestricted dual electric overboost' }
  };

  const interiorHues = {
    cyan: { name: 'Cyan Pulse', hex: '#00e5ff' },
    azure: { name: 'Neon Azure', hex: '#2979ff' },
    crimson: { name: 'Apex Crimson', hex: '#ef4444' },
    gold: { name: 'Tuscan Gold', hex: '#f59e0b' },
    emerald: { name: 'Emerald GT', hex: '#10b981' }
  };

  // Pakistan Showrooms
  const showrooms = {
    lahore: {
      name: 'AURIX Atelier Lahore',
      address: 'Main Gulberg III, MM Alam Road (Adjacent to Xinhua Mall)',
      phone: '+92 (42) 3578-9000',
      track: true
    },
    karachi: {
      name: 'AURIX Flagship Karachi',
      address: 'Ocean Towers Boulevard, Block 4, Clifton',
      phone: '+92 (21) 3582-7700',
      track: true
    },
    islamabad: {
      name: 'AURIX Experience Islamabad',
      address: 'Jinnah Avenue, Blue Area (Adjacent to Centaurus)',
      phone: '+92 (51) 280-4400',
      track: false
    }
  };

  // Models
  const models = {
    'gt-apex': { name: 'AURIX GT-1 APEX', badge: 'HYBRID-AERO V6', hp: 450, zero100: 3.8, topSpeed: 300, baseCrore: 8.5 },
    strada: { name: 'AURIX STRADA GT', badge: 'GRAND TOURER', hp: 420, zero100: 4.0, topSpeed: 290, baseCrore: 7.2 },
    volt: { name: 'AURIX VOLT EV', badge: 'DUAL AXIAL FLUX', hp: 580, zero100: 3.2, topSpeed: 310, baseCrore: 9.6 }
  };

  const curModel = models[selectedModel];
  const basePkr = curModel.baseCrore * 10000000;
  const customs = basePkr * 0.4;
  const salesTax = basePkr * 0.18;
  const advanceWht = isFiler ? basePkr * 0.06 : basePkr * 0.12;
  const totalOnRoadCrore = (basePkr + customs + salesTax + advanceWht) / 10000000;

  const handleLaunchSprint = () => {
    if (launchTesting) return;
    setLaunchTesting(true);
    setLaunchDone(false);
    setSpeed(0);

    const interval = setInterval(() => {
      setSpeed((prev) => {
        if (prev >= 100) {
          clearInterval(interval);
          setLaunchDone(true);
          setLaunchTesting(false);
          return 100;
        }
        return prev + 10;
      });
    }, 380);
  };

  const handleBookingSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (clientName.length >= 3 && clientPhone.length >= 10) {
      setBookingConfirmed(`AURIX-PK-${Math.floor(10000 + Math.random() * 90000)}`);
    }
  };

  return (
    <div style={{ minHeight: '100vh', background: '#080A0F', color: '#F8FAFC' }}>
      {/* 1. Header Navigation */}
      <header
        style={{
          position: 'fixed',
          top: 0,
          left: 0,
          right: 0,
          zIndex: 100,
          background: 'rgba(8, 10, 15, 0.94)',
          backdropFilter: 'blur(20px)',
          WebkitBackdropFilter: 'blur(20px)',
          borderBottom: '1px solid rgba(0, 229, 255, 0.18)',
          padding: '0.85rem 1.25rem'
        }}
      >
        <div className="container" style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', padding: 0 }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
            <img
              src="/images/aurix_app_logo.jpg"
              alt="AURIX Emblem"
              style={{ width: '38px', height: '38px', borderRadius: '50%', border: '1px solid #00E5FF', objectFit: 'cover' }}
            />
            <div>
              <div style={{ fontSize: '1.25rem', fontWeight: 900, letterSpacing: '0.25em', lineHeight: 1.1 }}>AURIX</div>
              <div style={{ fontSize: '0.62rem', fontWeight: 800, color: '#00E5FF', letterSpacing: '0.12em' }}>
                AUTOMOTIVE • PAKISTAN
              </div>
            </div>
          </div>

          <div style={{ display: 'flex', alignItems: 'center', gap: '0.65rem' }}>
            <div
              style={{
                padding: '0.35rem 0.75rem',
                borderRadius: '20px',
                border: '1px solid rgba(0, 229, 255, 0.35)',
                background: 'rgba(0, 229, 255, 0.12)',
                fontSize: '0.72rem',
                fontWeight: 800,
                color: '#00E5FF'
              }}
            >
              PKR (Rs.)
            </div>
            <a href="#booking-section" className="btn-primary" style={{ padding: '0.5rem 1rem', fontSize: '0.75rem', textDecoration: 'none' }}>
              REQUEST TEST DRIVE
            </a>
          </div>
        </div>
      </header>

      {/* 2. Section A: Hero */}
      <section style={{ paddingTop: '6rem', paddingBottom: '3rem' }}>
        <div className="container">
          <div className="grid-hero">
            <div>
              <div style={{ display: 'inline-flex', alignItems: 'center', gap: '0.5rem', color: '#00E5FF', fontSize: '0.72rem', fontWeight: 800, letterSpacing: '0.15em', marginBottom: '0.75rem' }}>
                <span style={{ width: '8px', height: '8px', borderRadius: '50%', background: '#00E5FF', display: 'inline-block' }}></span>
                FLAGSHIP 450 HP HYBRID-AERO • OFFICIAL ALLOCATION
              </div>
              <h1 style={{ fontSize: 'clamp(2.4rem, 6.5vw, 4.4rem)', fontWeight: 900, lineHeight: 1.02, letterSpacing: '-0.025em', marginBottom: '1rem' }}>
                ENGINEERED<br />TO MOVE YOU.
              </h1>
              <p style={{ color: '#94A3B8', fontSize: '0.95rem', lineHeight: 1.6, maxWidth: '480px', marginBottom: '1.5rem' }}>
                Precision engineering. Unmistakable presence. 450 hp twin-turbo hybrid V6 with intelligent AWD, hand-laid carbon monocoque, and active aerodynamics.
              </p>

              {/* Key Specs Row */}
              <div className="grid-specs" style={{ background: '#101622', padding: '1rem', borderRadius: '14px', border: '1px solid rgba(255,255,255,0.08)', marginBottom: '1.5rem' }}>
                <div>
                  <div style={{ color: '#00E5FF', fontWeight: 900, fontSize: '1.25rem' }}>450 hp</div>
                  <div style={{ color: '#64748B', fontSize: '0.65rem', fontWeight: 700 }}>POWER</div>
                </div>
                <div>
                  <div style={{ color: '#F8FAFC', fontWeight: 900, fontSize: '1.25rem' }}>3.8s</div>
                  <div style={{ color: '#64748B', fontSize: '0.65rem', fontWeight: 700 }}>0–100 KM/H</div>
                </div>
                <div>
                  <div style={{ color: '#F8FAFC', fontWeight: 900, fontSize: '1.25rem' }}>300</div>
                  <div style={{ color: '#64748B', fontSize: '0.65rem', fontWeight: 700 }}>TOP SPEED</div>
                </div>
                <div>
                  <div style={{ color: '#00E5FF', fontWeight: 900, fontSize: '1.25rem' }}>8.5 Cr</div>
                  <div style={{ color: '#64748B', fontSize: '0.65rem', fontWeight: 700 }}>PKR BASE</div>
                </div>
              </div>

              <div style={{ display: 'flex', gap: '0.75rem', flexWrap: 'wrap' }}>
                <a href="#booking-section" className="btn-primary" style={{ textDecoration: 'none', flex: '1 1 auto', textAlign: 'center' }}>
                  EXPLORE ALLOCATION
                </a>
                <button
                  onClick={() => setIsEngineOn(!isEngineOn)}
                  className="btn-outline"
                  style={{ flex: '1 1 auto', borderColor: isEngineOn ? '#00E5FF' : 'rgba(255,255,255,0.2)' }}
                >
                  {isEngineOn ? <Volume2 size={16} color="#00E5FF" /> : <VolumeX size={16} />}
                  {isEngineOn ? 'V6 RUNNING (1,800 RPM)' : 'IGNITE ENGINE AUDIO'}
                </button>
              </div>
            </div>

            {/* Hero Media Container with 3D / 8K Photo Switcher */}
            <div className="viewer-3d-box aurix-card" style={{ position: 'relative' }}>
              {heroViewMode === 'photo' ? (
                <div style={{ width: '100%', height: '100%', position: 'relative' }}>
                  <img
                    src="/images/aurix_hypercar_hero.jpg"
                    alt="AURIX Hypercar Studio Render"
                    style={{ width: '100%', height: '100%', objectFit: 'cover' }}
                  />
                  <div
                    style={{
                      position: 'absolute',
                      inset: 0,
                      background: 'linear-gradient(to top, rgba(8,10,15,0.7) 0%, transparent 60%)'
                    }}
                  />
                </div>
              ) : (
                <ThreeVehicleViewer
                  color={carColor}
                  isSpinning={isSpinning}
                  wingAngle={driveModes[driveMode].wing}
                />
              )}

              {/* View Mode Toggle Pill (Top Left) */}
              <div
                style={{
                  position: 'absolute',
                  top: '12px',
                  left: '12px',
                  background: 'rgba(8,10,15,0.88)',
                  backdropFilter: 'blur(10px)',
                  borderRadius: '20px',
                  border: '1px solid rgba(0,229,255,0.3)',
                  padding: '0.25rem 0.5rem',
                  display: 'flex',
                  gap: '0.25rem'
                }}
              >
                <button
                  onClick={() => setHeroViewMode('photo')}
                  style={{
                    background: heroViewMode === 'photo' ? '#00E5FF' : 'transparent',
                    color: heroViewMode === 'photo' ? '#080A0F' : '#94A3B8',
                    border: 'none',
                    borderRadius: '16px',
                    padding: '0.25rem 0.65rem',
                    fontSize: '0.65rem',
                    fontWeight: 800,
                    cursor: 'pointer',
                    display: 'flex',
                    alignItems: 'center',
                    gap: '0.35rem'
                  }}
                >
                  <Camera size={12} /> 8K STUDIO
                </button>
                <button
                  onClick={() => setHeroViewMode('3d')}
                  style={{
                    background: heroViewMode === '3d' ? '#00E5FF' : 'transparent',
                    color: heroViewMode === '3d' ? '#080A0F' : '#94A3B8',
                    border: 'none',
                    borderRadius: '16px',
                    padding: '0.25rem 0.65rem',
                    fontSize: '0.65rem',
                    fontWeight: 800,
                    cursor: 'pointer',
                    display: 'flex',
                    alignItems: 'center',
                    gap: '0.35rem'
                  }}
                >
                  <Eye size={12} /> 3D TURNTABLE
                </button>
              </div>

              {/* Color Customizer Bar */}
              <div
                style={{
                  position: 'absolute',
                  bottom: '12px',
                  left: '12px',
                  right: '12px',
                  padding: '0.65rem 1rem',
                  borderRadius: '12px',
                  background: 'rgba(8,10,15,0.92)',
                  backdropFilter: 'blur(12px)',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'space-between',
                  border: '1px solid rgba(255,255,255,0.1)'
                }}
              >
                <span style={{ fontSize: '0.72rem', color: '#94A3B8', fontWeight: 800 }}>BESPOKE FINISH:</span>
                <div style={{ display: 'flex', gap: '0.5rem' }}>
                  {colors.map((c) => (
                    <button
                      key={c.name}
                      onClick={() => {
                        setCarColor(c.hex);
                        if (heroViewMode !== '3d') setHeroViewMode('3d');
                      }}
                      style={{
                        width: '24px',
                        height: '24px',
                        borderRadius: '50%',
                        background: c.hex,
                        border: carColor === c.hex ? '2px solid #00E5FF' : '1px solid rgba(255,255,255,0.3)',
                        cursor: 'pointer',
                        transform: carColor === c.hex ? 'scale(1.2)' : 'scale(1)',
                        transition: 'transform 0.2s'
                      }}
                      title={c.name}
                    />
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* 3. Section B: Exterior Design Showcase */}
      <section style={{ padding: '3.5rem 0', background: '#0a0e17', borderTop: '1px solid rgba(255,255,255,0.06)' }}>
        <div className="container">
          <div style={{ color: '#00E5FF', fontSize: '0.72rem', fontWeight: 800, letterSpacing: '0.15em', marginBottom: '0.4rem' }}>
            SECTION B • EXTERIOR AERODYNAMIC REVEAL
          </div>
          <h2 style={{ fontSize: 'clamp(2rem, 4.5vw, 3rem)', fontWeight: 900, marginBottom: '1rem' }}>
            EVERY LINE HAS A PURPOSE.
          </h2>
          <p style={{ color: '#94A3B8', maxWidth: '600px', fontSize: '0.9rem', marginBottom: '2rem' }}>
            From the front splitter ground effects to the active carbon-fiber rear airfoil. Drag coefficient of 0.28 Cd generating 580 kg of stabilized downforce at 250 km/h.
          </p>

          <div className="grid-3col">
            <div className="aurix-card" style={{ padding: '1.5rem' }}>
              <div style={{ fontSize: '0.85rem', fontWeight: 900, color: '#00E5FF', marginBottom: '0.5rem' }}>01 / FRONT SPLITTER</div>
              <div style={{ fontSize: '0.9rem', fontWeight: 800, marginBottom: '0.5rem' }}>Vortex Channeling Induction</div>
              <div style={{ fontSize: '0.8rem', color: '#94A3B8', lineHeight: 1.6 }}>
                Directs high-pressure air through twin radiators, cooling the hybrid battery architecture during high-heat 50°C+ summer drives.
              </div>
            </div>

            <div className="aurix-card" style={{ padding: '1.5rem' }}>
              <div style={{ fontSize: '0.85rem', fontWeight: 900, color: '#00E5FF', marginBottom: '0.5rem' }}>02 / CARBON MONOCOQUE</div>
              <div style={{ fontSize: '0.9rem', fontWeight: 800, marginBottom: '0.5rem' }}>1,420 kg Dry Chassis</div>
              <div style={{ fontSize: '0.8rem', color: '#94A3B8', lineHeight: 1.6 }}>
                Aerospace-grade T1000 carbon weave offering 45,000 Nm/degree of torsional rigidity for apex cornering precision.
              </div>
            </div>

            <div className="aurix-card" style={{ padding: '1.5rem' }}>
              <div style={{ fontSize: '0.85rem', fontWeight: 900, color: '#00E5FF', marginBottom: '0.5rem' }}>03 / ACTIVE AIRBRAKE</div>
              <div style={{ fontSize: '0.9rem', fontWeight: 800, marginBottom: '0.5rem' }}>45° High-Speed Deployment</div>
              <div style={{ fontSize: '0.8rem', color: '#94A3B8', lineHeight: 1.6 }}>
                Under emergency high-speed deceleration, the rear wing deploys to 45° in 180ms, stabilizing the rear axle from 100 to 0 km/h in 31.4m.
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* 4. Section C: Technical Dyno & Dynamics */}
      <section style={{ padding: '3.5rem 0', background: '#080A0F', borderTop: '1px solid rgba(255,255,255,0.06)' }}>
        <div className="container">
          <div style={{ color: '#00E5FF', fontSize: '0.72rem', fontWeight: 800, letterSpacing: '0.15em', marginBottom: '0.4rem' }}>
            SECTION C • TECHNICAL DYNO & TELEMETRY
          </div>
          <h2 style={{ fontSize: 'clamp(2rem, 4.5vw, 3rem)', fontWeight: 900, marginBottom: '2rem' }}>
            BUILT BEYOND EXPECTATIONS.
          </h2>

          <div className="grid-3col">
            {/* Dyno Card */}
            <div className="aurix-card" style={{ padding: '1.5rem' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.85rem' }}>
                <span style={{ fontSize: '0.85rem', fontWeight: 800, color: '#00E5FF' }}>VIRTUAL RPM DYNO</span>
                <span style={{ fontSize: '1rem', fontWeight: 900 }}>{rpm} RPM</span>
              </div>
              <input
                type="range"
                min="1000"
                max="8000"
                value={rpm}
                onChange={(e) => setRpm(Number(e.target.value))}
                style={{ width: '100%', accentColor: '#00E5FF', marginBottom: '1rem', cursor: 'pointer', height: '6px' }}
              />
              <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.85rem', fontWeight: 800 }}>
                <span style={{ color: '#00E5FF' }}>HP: {Math.round((rpm / 7800) * 450)} hp</span>
                <span style={{ color: '#f59e0b' }}>TORQUE: {Math.round(620 * (1 - Math.abs(rpm - 4500) / 9000))} Nm</span>
              </div>
            </div>

            {/* Dynamics Mode */}
            <div className="aurix-card" style={{ padding: '1.5rem' }}>
              <div style={{ fontSize: '0.85rem', fontWeight: 800, color: '#00E5FF', marginBottom: '0.85rem' }}>
                INTELLIGENT DYNAMICS MODE
              </div>
              <div style={{ display: 'grid', gridTemplateColumns: 'repeat(2, 1fr)', gap: '0.5rem', marginBottom: '1rem' }}>
                {(Object.keys(driveModes) as (keyof typeof driveModes)[]).map((mode) => (
                  <button
                    key={mode}
                    onClick={() => setDriveMode(mode)}
                    style={{
                      padding: '0.55rem',
                      borderRadius: '8px',
                      border: driveMode === mode ? `2px solid ${driveModes[mode].color}` : '1px solid rgba(255,255,255,0.1)',
                      background: driveMode === mode ? `${driveModes[mode].color}22` : '#101622',
                      color: driveMode === mode ? driveModes[mode].color : '#94A3B8',
                      fontWeight: 800,
                      fontSize: '0.72rem',
                      cursor: 'pointer'
                    }}
                  >
                    {driveModes[mode].name}
                  </button>
                ))}
              </div>
              <div style={{ fontSize: '0.78rem', color: '#94A3B8' }}>
                Active Aero Wing: <strong style={{ color: driveModes[driveMode].color }}>{driveModes[driveMode].wing}°</strong> • {driveModes[driveMode].desc}
              </div>
            </div>

            {/* 0-100 Test */}
            <div className="aurix-card" style={{ padding: '1.5rem', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
              <div>
                <div style={{ fontSize: '0.85rem', fontWeight: 800, color: '#00E5FF', marginBottom: '0.35rem' }}>
                  0–100 KM/H BENCHMARK SPRINT
                </div>
                <div style={{ fontSize: '2.4rem', fontWeight: 900, color: '#F8FAFC', lineHeight: 1.1 }}>
                  {speed} <span style={{ fontSize: '1rem', color: '#00E5FF' }}>KM/H</span>
                </div>
                <div style={{ fontSize: '0.75rem', color: '#94A3B8', marginTop: '0.25rem' }}>
                  {launchDone ? 'Verified: 3.80s Flat (Launch Control Active)' : 'Dual axial-flux electric overboost'}
                </div>
              </div>
              <button
                onClick={handleLaunchSprint}
                className="btn-primary"
                style={{ width: '100%', marginTop: '1rem', padding: '0.75rem' }}
                disabled={launchTesting}
              >
                {launchTesting ? 'ACCELERATING...' : launchDone ? 'RE-RUN LAUNCH TEST' : 'TEST 0-100 LAUNCH'}
              </button>
            </div>
          </div>
        </div>
      </section>

      {/* 5. Section D: Cockpit Interior Sanctum */}
      <section style={{ padding: '3.5rem 0', background: '#0a0d14', borderTop: '1px solid rgba(255,255,255,0.06)' }}>
        <div className="container">
          <div style={{ color: '#00E5FF', fontSize: '0.72rem', fontWeight: 800, letterSpacing: '0.15em', marginBottom: '0.4rem' }}>
            SECTION D • CURVED OLED COCKPIT
          </div>
          <h2 style={{ fontSize: 'clamp(2rem, 4.5vw, 3rem)', fontWeight: 900, marginBottom: '2rem' }}>
            EVERY DETAIL. EVERY MOMENT.
          </h2>

          <div className="grid-2col" style={{ alignItems: 'center' }}>
            {/* Visual Cockpit Frame */}
            <div className="aurix-card" style={{ position: 'relative', overflow: 'hidden', height: '360px' }}>
              <img
                src="/images/aurix_interior_cockpit.jpg"
                alt="AURIX Cockpit Interior"
                style={{ width: '100%', height: '100%', objectFit: 'cover' }}
              />
              {/* Dynamic Ambient Optic Glow Overlay */}
              <div
                style={{
                  position: 'absolute',
                  inset: 0,
                  background: `radial-gradient(circle at 50% 50%, ${interiorHues[interiorHue].hex}33 0%, transparent 70%)`,
                  mixBlendMode: 'screen',
                  pointerEvents: 'none'
                }}
              />
              <div
                style={{
                  position: 'absolute',
                  bottom: '12px',
                  left: '12px',
                  padding: '0.4rem 0.8rem',
                  borderRadius: '20px',
                  background: 'rgba(8,10,15,0.85)',
                  border: `1px solid ${interiorHues[interiorHue].hex}`,
                  color: interiorHues[interiorHue].hex,
                  fontSize: '0.7rem',
                  fontWeight: 800
                }}
              >
                AMBIENT: {interiorHues[interiorHue].name.toUpperCase()}
              </div>
            </div>

            {/* Controls */}
            <div style={{ display: 'flex', flexDirection: 'column', gap: '1.25rem' }}>
              <div className="aurix-card" style={{ padding: '1.25rem' }}>
                <div style={{ fontSize: '0.85rem', fontWeight: 800, color: '#00E5FF', marginBottom: '0.75rem' }}>
                  FIBER-OPTIC AMBIENT HUE
                </div>
                <div style={{ display: 'flex', gap: '0.65rem', flexWrap: 'wrap' }}>
                  {(Object.keys(interiorHues) as (keyof typeof interiorHues)[]).map((key) => (
                    <button
                      key={key}
                      onClick={() => setInteriorHue(key)}
                      style={{
                        padding: '0.45rem 0.85rem',
                        borderRadius: '20px',
                        border: interiorHue === key ? `2px solid ${interiorHues[key].hex}` : '1px solid rgba(255,255,255,0.15)',
                        background: interiorHue === key ? `${interiorHues[key].hex}22` : '#101622',
                        color: interiorHue === key ? interiorHues[key].hex : '#94A3B8',
                        fontWeight: 800,
                        fontSize: '0.75rem',
                        cursor: 'pointer',
                        display: 'flex',
                        alignItems: 'center',
                        gap: '0.4rem'
                      }}
                    >
                      <span style={{ width: '8px', height: '8px', borderRadius: '50%', background: interiorHues[key].hex }} />
                      {interiorHues[key].name}
                    </button>
                  ))}
                </div>
              </div>

              <div className="aurix-card" style={{ padding: '1.25rem' }}>
                <div style={{ fontSize: '0.85rem', fontWeight: 800, color: '#00E5FF', marginBottom: '0.75rem' }}>
                  CURVED OLED INSTRUMENT CLUSTER
                </div>
                <div style={{ display: 'flex', gap: '0.5rem' }}>
                  {[
                    { id: 'telemetry', label: 'RACE TELEMETRY' },
                    { id: 'nav', label: 'M-2 MOTORWAY NAV' },
                    { id: 'audio', label: '3D AUDIO MATRIX' }
                  ].map((mode) => (
                    <button
                      key={mode.id}
                      onClick={() => setCockpitScreen(mode.id as any)}
                      style={{
                        flex: 1,
                        padding: '0.55rem',
                        borderRadius: '8px',
                        border: cockpitScreen === mode.id ? '2px solid #00E5FF' : '1px solid rgba(255,255,255,0.1)',
                        background: cockpitScreen === mode.id ? 'rgba(0,229,255,0.15)' : '#101622',
                        color: cockpitScreen === mode.id ? '#00E5FF' : '#94A3B8',
                        fontSize: '0.7rem',
                        fontWeight: 800,
                        cursor: 'pointer'
                      }}
                    >
                      {mode.label}
                    </button>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* 6. Section E: Mountain Driving Environment */}
      <section style={{ padding: '3.5rem 0', background: '#080A0F', borderTop: '1px solid rgba(255,255,255,0.06)' }}>
        <div className="container">
          <div style={{ color: '#00E5FF', fontSize: '0.72rem', fontWeight: 800, letterSpacing: '0.15em', marginBottom: '0.4rem' }}>
            SECTION E • MOUNTAIN DRIVING PASS
          </div>
          <h2 style={{ fontSize: 'clamp(2rem, 4.5vw, 3rem)', fontWeight: 900, marginBottom: '2rem' }}>
            THE SPIRIT OF PERFORMANCE.
          </h2>

          <div className="aurix-card" style={{ position: 'relative', overflow: 'hidden', height: '380px' }}>
            <img
              src="/images/aurix_mountain_drive.jpg"
              alt="AURIX Mountain Driving"
              style={{ width: '100%', height: '100%', objectFit: 'cover' }}
            />
            <div
              style={{
                position: 'absolute',
                inset: 0,
                background: 'linear-gradient(to top, rgba(8,10,15,0.85) 0%, transparent 60%)'
              }}
            />
            {/* Route HUD overlay */}
            <div
              style={{
                position: 'absolute',
                top: '16px',
                left: '16px',
                padding: '0.5rem 1rem',
                borderRadius: '12px',
                background: 'rgba(8,10,15,0.85)',
                border: '1px solid rgba(0,229,255,0.3)',
                display: 'flex',
                alignItems: 'center',
                gap: '0.65rem'
              }}
            >
              <div style={{ width: '8px', height: '8px', borderRadius: '50%', background: '#00E5FF' }} />
              <div>
                <div style={{ fontSize: '0.65rem', color: '#94A3B8', fontWeight: 700 }}>TELEMETRY TRACKER</div>
                <div style={{ fontSize: '0.85rem', fontWeight: 900, color: '#F8FAFC' }}>M-2 SALT RANGE • 780M ELEVATION</div>
              </div>
            </div>

            <div
              style={{
                position: 'absolute',
                bottom: '16px',
                left: '16px',
                right: '16px',
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'flex-end',
                flexWrap: 'wrap',
                gap: '0.75rem'
              }}
            >
              <div>
                <div style={{ fontSize: '0.75rem', color: '#00E5FF', fontWeight: 800 }}>INTELLIGENT TORQUE-VECTORING AWD</div>
                <div style={{ fontSize: '0.85rem', color: '#94A3B8' }}>1,000 sensor calculations per second for maximum grip</div>
              </div>
              <div style={{ display: 'flex', gap: '0.75rem' }}>
                <div style={{ background: 'rgba(16,21,32,0.9)', padding: '0.5rem 0.85rem', borderRadius: '8px', border: '1px solid rgba(255,255,255,0.1)' }}>
                  <div style={{ fontSize: '0.65rem', color: '#64748B' }}>LATERAL G</div>
                  <div style={{ fontSize: '1.1rem', fontWeight: 900, color: '#00E5FF' }}>1.38 G</div>
                </div>
                <div style={{ background: 'rgba(16,21,32,0.9)', padding: '0.5rem 0.85rem', borderRadius: '8px', border: '1px solid rgba(255,255,255,0.1)' }}>
                  <div style={{ fontSize: '0.65rem', color: '#64748B' }}>TIRE TEMP</div>
                  <div style={{ fontSize: '1.1rem', fontWeight: 900, color: '#F8FAFC' }}>78° C</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* 7. Section F: Pakistan Luxury Atelier & FBR Calculator */}
      <section id="booking-section" style={{ padding: '3.5rem 0', background: '#0a0e17', borderTop: '1px solid rgba(255,255,255,0.06)' }}>
        <div className="container">
          <div style={{ color: '#00E5FF', fontSize: '0.72rem', fontWeight: 800, letterSpacing: '0.15em', marginBottom: '0.4rem' }}>
            SECTION F • PAKISTAN ATELIER & ALLOCATION (PKR)
          </div>
          <h2 style={{ fontSize: 'clamp(2rem, 4.5vw, 3rem)', fontWeight: 900, marginBottom: '2rem' }}>
            THE ROAD IS YOURS.
          </h2>

          <div className="grid-2col">
            {/* Tax Schedule Card */}
            <div className="aurix-card" style={{ padding: '1.5rem' }}>
              <h3 style={{ fontSize: '1.15rem', fontWeight: 900, marginBottom: '1rem' }}>OFFICIAL ALLOCATION SCHEDULE</h3>

              {/* Model Switcher */}
              <div style={{ display: 'flex', gap: '0.5rem', marginBottom: '1.25rem' }}>
                {(Object.keys(models) as (keyof typeof models)[]).map((key) => (
                  <button
                    key={key}
                    onClick={() => setSelectedModel(key)}
                    style={{
                      flex: 1,
                      padding: '0.55rem 0.35rem',
                      borderRadius: '8px',
                      border: selectedModel === key ? '2px solid #00E5FF' : '1px solid rgba(255,255,255,0.1)',
                      background: selectedModel === key ? 'rgba(0,229,255,0.15)' : '#101622',
                      color: selectedModel === key ? '#00E5FF' : '#94A3B8',
                      fontSize: '0.72rem',
                      fontWeight: 800,
                      cursor: 'pointer'
                    }}
                  >
                    {models[key].name.replace('AURIX ', '')}
                  </button>
                ))}
              </div>

              {/* Filer Toggle */}
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem', background: '#0e1420', padding: '0.75rem 1rem', borderRadius: '10px' }}>
                <span style={{ fontSize: '0.8rem', color: '#94A3B8' }}>FBR Tax Filer Status (Sec 231B):</span>
                <button
                  onClick={() => setIsFiler(!isFiler)}
                  style={{
                    padding: '0.35rem 0.75rem',
                    borderRadius: '6px',
                    border: '1px solid #00E5FF',
                    background: isFiler ? '#00E5FF' : 'transparent',
                    color: isFiler ? '#080A0F' : '#00E5FF',
                    fontWeight: 800,
                    fontSize: '0.7rem',
                    cursor: 'pointer'
                  }}
                >
                  {isFiler ? 'ACTIVE FILER (6% WHT)' : 'NON-FILER (12% WHT)'}
                </button>
              </div>

              {/* Costs Breakdown */}
              <div style={{ display: 'flex', flexDirection: 'column', gap: '0.6rem', fontSize: '0.85rem' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                  <span style={{ color: '#94A3B8' }}>Base Ex-Showroom:</span>
                  <strong>PKR {curModel.baseCrore} Crore</strong>
                </div>
                <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                  <span style={{ color: '#94A3B8' }}>Customs & Regulatory Tariff:</span>
                  <strong>PKR {(customs / 10000000).toFixed(2)} Crore</strong>
                </div>
                <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                  <span style={{ color: '#94A3B8' }}>General Sales Tax (18%):</span>
                  <strong>PKR {(salesTax / 10000000).toFixed(2)} Crore</strong>
                </div>
                <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                  <span style={{ color: '#94A3B8' }}>Advance Withholding Tax:</span>
                  <strong style={{ color: '#00E5FF' }}>PKR {(advanceWht / 100000).toFixed(0)} Lacs</strong>
                </div>
                <div style={{ borderTop: '1px solid rgba(255,255,255,0.1)', paddingTop: '0.75rem', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <span style={{ fontWeight: 800, fontSize: '0.9rem' }}>ESTIMATED TOTAL ON-ROAD:</span>
                  <span style={{ fontSize: '1.25rem', fontWeight: 900, color: '#00E5FF' }}>PKR {totalOnRoadCrore.toFixed(2)} Crore</span>
                </div>
              </div>

              {/* Showroom Contacts */}
              <div style={{ marginTop: '1.5rem' }}>
                <div style={{ fontSize: '0.72rem', color: '#94A3B8', fontWeight: 800, marginBottom: '0.5rem' }}>SHOWROOM ATELIERS:</div>
                <div style={{ display: 'flex', gap: '0.5rem', marginBottom: '0.65rem' }}>
                  {(['lahore', 'karachi', 'islamabad'] as const).map((city) => (
                    <button
                      key={city}
                      onClick={() => setActiveShowroom(city)}
                      style={{
                        flex: 1,
                        padding: '0.5rem',
                        borderRadius: '8px',
                        border: activeShowroom === city ? '2px solid #00E5FF' : '1px solid rgba(255,255,255,0.1)',
                        background: activeShowroom === city ? 'rgba(0,229,255,0.15)' : '#101622',
                        color: activeShowroom === city ? '#00E5FF' : '#94A3B8',
                        fontSize: '0.7rem',
                        fontWeight: 800,
                        cursor: 'pointer',
                        textTransform: 'uppercase'
                      }}
                    >
                      {city}
                    </button>
                  ))}
                </div>
                <div style={{ background: '#0e1420', padding: '0.85rem', borderRadius: '10px', fontSize: '0.8rem' }}>
                  <div style={{ fontWeight: 800, color: '#F8FAFC' }}>{showrooms[activeShowroom].name}</div>
                  <div style={{ color: '#94A3B8', margin: '0.25rem 0' }}>{showrooms[activeShowroom].address}</div>
                  <div style={{ color: '#00E5FF', fontWeight: 800 }}>{showrooms[activeShowroom].phone}</div>
                </div>
              </div>
            </div>

            {/* Test Drive Form Card */}
            <div className="aurix-card" style={{ padding: '1.5rem' }}>
              {bookingConfirmed ? (
                <div style={{ textAlign: 'center', padding: '2rem 0.5rem' }}>
                  <CheckCircle2 size={54} color="#00E5FF" style={{ margin: '0 auto 1rem' }} />
                  <h3 style={{ fontSize: '1.5rem', fontWeight: 900, marginBottom: '0.5rem' }}>VIP ALLOCATION RESERVED</h3>
                  <p style={{ color: '#94A3B8', fontSize: '0.85rem', lineHeight: 1.6, marginBottom: '1.25rem' }}>
                    Reference Code: <strong style={{ color: '#00E5FF' }}>{bookingConfirmed}</strong><br />
                    Our Senior Specialist in {activeShowroom.toUpperCase()} will contact <strong>{clientPhone}</strong> within 2 hours to confirm your private track slot.
                  </p>
                  <button onClick={() => setBookingConfirmed(null)} className="btn-outline" style={{ width: '100%' }}>
                    BOOK ANOTHER SESSION
                  </button>
                </div>
              ) : (
                <form onSubmit={handleBookingSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '0.95rem' }}>
                  <h3 style={{ fontSize: '1.25rem', fontWeight: 900 }}>VIP TEST DRIVE & ALLOCATION</h3>
                  <p style={{ color: '#94A3B8', fontSize: '0.8rem' }}>
                    Reserve closed-circuit session in Lahore, Karachi, or Islamabad.
                  </p>

                  <div>
                    <label style={{ fontSize: '0.72rem', color: '#94A3B8', display: 'block', marginBottom: '0.35rem' }}>Full Name</label>
                    <input
                      type="text"
                      required
                      value={clientName}
                      onChange={(e) => setClientName(e.target.value)}
                      placeholder="e.g. Tariq Mansoor"
                      style={{
                        width: '100%',
                        padding: '0.75rem',
                        borderRadius: '8px',
                        background: '#090d14',
                        border: '1px solid rgba(255,255,255,0.15)',
                        color: '#F8FAFC',
                        fontSize: '0.85rem'
                      }}
                    />
                  </div>

                  <div>
                    <label style={{ fontSize: '0.72rem', color: '#94A3B8', display: 'block', marginBottom: '0.35rem' }}>Mobile Contact (+92 3XX XXXXXXX)</label>
                    <input
                      type="tel"
                      required
                      value={clientPhone}
                      onChange={(e) => setClientPhone(e.target.value)}
                      placeholder="+92 300 1234567"
                      style={{
                        width: '100%',
                        padding: '0.75rem',
                        borderRadius: '8px',
                        background: '#090d14',
                        border: '1px solid rgba(255,255,255,0.15)',
                        color: '#F8FAFC',
                        fontSize: '0.85rem'
                      }}
                    />
                  </div>

                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', fontSize: '0.78rem', color: '#94A3B8' }}>
                    <input
                      type="checkbox"
                      checked={hasLicense}
                      onChange={(e) => setHasLicense(e.target.checked)}
                      id="license"
                      style={{ accentColor: '#00E5FF' }}
                    />
                    <label htmlFor="license">I hold a valid Pakistani driving license & CNIC.</label>
                  </div>

                  <button type="submit" className="btn-primary" style={{ width: '100%', padding: '0.85rem', marginTop: '0.5rem' }}>
                    CONFIRM PRIVATE ALLOCATION
                  </button>
                </form>
              )}
            </div>
          </div>
        </div>
      </section>

      {/* 8. Mobile Sticky Bottom Action Bar */}
      <div className="mobile-sticky-bar">
        <div>
          <div style={{ fontSize: '0.65rem', color: '#94A3B8', fontWeight: 700 }}>AURIX GT-1 APEX</div>
          <div style={{ fontSize: '1rem', fontWeight: 900, color: '#00E5FF' }}>PKR 8.5 Crore</div>
        </div>
        <a href="#booking-section" className="btn-primary" style={{ padding: '0.5rem 1rem', fontSize: '0.75rem', textDecoration: 'none' }}>
          REQUEST TEST DRIVE
        </a>
      </div>

      {/* 9. Footer */}
      <footer style={{ borderTop: '1px solid rgba(255,255,255,0.08)', padding: '2.5rem 1.25rem', textAlign: 'center', fontSize: '0.75rem', color: '#64748B' }}>
        <div className="container" style={{ display: 'flex', flexDirection: 'column', gap: '0.65rem' }}>
          <div style={{ fontWeight: 800, color: '#F8FAFC', letterSpacing: '0.1em' }}>AURIX AUTOMOTIVE PAKISTAN</div>
          <div>5-Year / 100,000 KM Certified Factory Warranty • 24/7 Roadside Concierge across M-2, M-3 & M-9 Motorways</div>
          <div>© 2026 AURIX. All rights reserved. Official allocations subject to statutory FBR customs schedules.</div>
        </div>
      </footer>
    </div>
  );
}
