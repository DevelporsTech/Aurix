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
  VolumeX
} from 'lucide-react';

export default function App() {
  const [carColor, setCarColor] = useState('#0b0e14');
  const [isSpinning, setIsSpinning] = useState(true);
  const [driveMode, setDriveMode] = useState<'comfort' | 'sport' | 'track' | 'hyperspace'>('comfort');
  const [rpm, setRpm] = useState(6500);
  const [isEngineOn, setIsEngineOn] = useState(false);
  const [launchTesting, setLaunchTesting] = useState(false);
  const [launchDone, setLaunchDone] = useState(false);
  const [speed, setSpeed] = useState(120);

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
    { name: 'Obsidian Carbon', hex: '#0b0e14' },
    { name: 'Cyber Cyan', hex: '#00e5ff' },
    { name: 'Liquid Platinum', hex: '#d1d5db' },
    { name: 'Crimson Velocity', hex: '#dc2626' },
    { name: 'Monaco Bronze', hex: '#d97706' }
  ];

  // Drive Mode configs
  const driveModes = {
    comfort: { name: 'COMFORT', wing: 0, color: '#38bdf8', desc: 'Plush adaptive air ride' },
    sport: { name: 'SPORT', wing: 15, color: '#f59e0b', desc: 'Dynamic boost damping' },
    track: { name: 'TRACK', wing: 32, color: '#ef4444', desc: '580 kg downforce attack' },
    hyperspace: { name: 'HYPERSPACE', wing: 45, color: '#00e5ff', desc: 'Unrestricted dual electric boost' }
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
    'gt-apex': { name: 'AURIX GT-1 APEX', hp: 450, zero100: 3.8, topSpeed: 300, baseCrore: 8.5 },
    strada: { name: 'AURIX STRADA VELOCE', hp: 420, zero100: 4.0, topSpeed: 290, baseCrore: 7.2 },
    volt: { name: 'AURIX VOLT PURE ELECTRIC', hp: 580, zero100: 3.2, topSpeed: 310, baseCrore: 9.6 }
  };

  // Tax calculations
  const curModel = models[selectedModel];
  const basePkr = curModel.baseCrore * 10000000;
  const customs = basePkr * 0.4;
  const salesTax = basePkr * 0.18;
  const advanceWht = isFiler ? basePkr * 0.06 : basePkr * 0.12;
  const totalOnRoadCrore = (basePkr + customs + salesTax + advanceWht) / 10000000;

  // Launch test simulation
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

  // Submit test drive
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
          background: 'rgba(8, 10, 15, 0.9)',
          backdropFilter: 'blur(16px)',
          borderBottom: '1px solid rgba(0, 229, 255, 0.15)',
          padding: '0.85rem 1.5rem',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between'
        }}
      >
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
          <div
            style={{
              width: '36px',
              height: '36px',
              borderRadius: '50%',
              background: '#00E5FF',
              color: '#080A0F',
              fontWeight: 900,
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              fontSize: '1.1rem'
            }}
          >
            A
          </div>
          <div>
            <div style={{ fontSize: '1.25rem', fontWeight: 900, letterSpacing: '0.25em' }}>AURIX</div>
            <div style={{ fontSize: '0.65rem', fontWeight: 700, color: '#00E5FF', letterSpacing: '0.1em' }}>
              AUTOMOTIVE • PAKISTAN
            </div>
          </div>
        </div>

        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
          <div
            style={{
              padding: '0.35rem 0.75rem',
              borderRadius: '20px',
              border: '1px solid rgba(0, 229, 255, 0.3)',
              background: 'rgba(0, 229, 255, 0.1)',
              fontSize: '0.75rem',
              fontWeight: 800,
              color: '#00E5FF'
            }}
          >
            PKR (Rs.)
          </div>
          <a href="#booking-section" className="btn-primary" style={{ padding: '0.5rem 1rem', textDecoration: 'none' }}>
            REQUEST TEST DRIVE
          </a>
        </div>
      </header>

      {/* 2. Section A: Cinematic Hero */}
      <section style={{ paddingTop: '6.5rem', paddingBottom: '3rem', paddingLeft: '1.5rem', paddingRight: '1.5rem' }}>
        <div style={{ maxWidth: '1200px', margin: '0 auto', display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '2.5rem', alignItems: 'center' }}>
          <div>
            <div style={{ display: 'inline-flex', alignItems: 'center', gap: '0.5rem', color: '#00E5FF', fontSize: '0.75rem', fontWeight: 800, letterSpacing: '0.15em', marginBottom: '0.75rem' }}>
              <span style={{ width: '8px', height: '8px', borderRadius: '50%', background: '#00E5FF' }}></span>
              FLAGSHIP PERFORMANCE ALLOCATION
            </div>
            <h1 style={{ fontSize: 'clamp(2.5rem, 5vw, 4.2rem)', fontWeight: 900, lineHeight: 1.05, letterSpacing: '-0.02em', marginBottom: '1rem' }}>
              ENGINEERED<br />TO MOVE YOU.
            </h1>
            <p style={{ color: '#94A3B8', fontSize: '1rem', lineHeight: 1.6, maxWidth: '480px', marginBottom: '1.5rem' }}>
              Precision engineering. Unmistakable presence. 450 hp hybrid-aero twin-turbo V6 with intelligent AWD, hand-crafted carbon monocoque, and active downforce aerodynamics.
            </p>

            {/* Quick Specs Cards */}
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(4, 1fr)', gap: '0.75rem', background: '#101622', padding: '1rem', borderRadius: '14px', border: '1px solid rgba(255,255,255,0.08)', marginBottom: '1.5rem' }}>
              <div>
                <div style={{ color: '#00E5FF', fontWeight: 900, fontSize: '1.15rem' }}>450 hp</div>
                <div style={{ color: '#64748B', fontSize: '0.65rem', fontWeight: 700 }}>POWER</div>
              </div>
              <div>
                <div style={{ color: '#F8FAFC', fontWeight: 900, fontSize: '1.15rem' }}>3.8s</div>
                <div style={{ color: '#64748B', fontSize: '0.65rem', fontWeight: 700 }}>0–100</div>
              </div>
              <div>
                <div style={{ color: '#F8FAFC', fontWeight: 900, fontSize: '1.15rem' }}>300</div>
                <div style={{ color: '#64748B', fontSize: '0.65rem', fontWeight: 700 }}>KM/H</div>
              </div>
              <div>
                <div style={{ color: '#00E5FF', fontWeight: 900, fontSize: '1.15rem' }}>8.5 Cr</div>
                <div style={{ color: '#64748B', fontSize: '0.65rem', fontWeight: 700 }}>PKR BASE</div>
              </div>
            </div>

            <div style={{ display: 'flex', gap: '0.75rem', flexWrap: 'wrap' }}>
              <a href="#booking-section" className="btn-primary" style={{ textDecoration: 'none' }}>
                EXPLORE MACHINE
              </a>
              <button
                onClick={() => setIsEngineOn(!isEngineOn)}
                className="btn-outline"
                style={{ borderColor: isEngineOn ? '#00E5FF' : 'rgba(255,255,255,0.2)' }}
              >
                {isEngineOn ? <Volume2 size={16} color="#00E5FF" /> : <VolumeX size={16} />}
                {isEngineOn ? 'V6 HYBRID RUNNING (1,800 RPM)' : 'IGNITE ENGINE AUDIO'}
              </button>
            </div>
          </div>

          {/* Real-Time 3D Vehicle Canvas Box */}
          <div
            className="aurix-card"
            style={{
              height: '420px',
              position: 'relative',
              overflow: 'hidden',
              background: 'radial-gradient(circle at center, #131c2d 0%, #07090e 80%)'
            }}
          >
            <ThreeVehicleViewer
              color={carColor}
              isSpinning={isSpinning}
              wingAngle={driveModes[driveMode].wing}
            />

            {/* Turntable Pause/Play */}
            <button
              onClick={() => setIsSpinning(!isSpinning)}
              style={{
                position: 'absolute',
                top: '12px',
                right: '12px',
                padding: '0.4rem 0.8rem',
                borderRadius: '20px',
                background: 'rgba(8,10,15,0.7)',
                border: '1px solid rgba(0,229,255,0.3)',
                color: '#00E5FF',
                fontSize: '0.7rem',
                fontWeight: 700,
                cursor: 'pointer'
              }}
            >
              {isSpinning ? 'PAUSE 3D SPIN' : 'RESUME 3D SPIN'}
            </button>

            {/* Color Customizer Pill */}
            <div
              style={{
                position: 'absolute',
                bottom: '12px',
                left: '12px',
                right: '12px',
                padding: '0.65rem 1rem',
                borderRadius: '12px',
                background: 'rgba(8,10,15,0.85)',
                backdropFilter: 'blur(10px)',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between',
                border: '1px solid rgba(255,255,255,0.1)'
              }}
            >
              <span style={{ fontSize: '0.75rem', color: '#94A3B8', fontWeight: 700 }}>BESPOKE FINISH:</span>
              <div style={{ display: 'flex', gap: '0.5rem' }}>
                {colors.map((c) => (
                  <button
                    key={c.name}
                    onClick={() => setCarColor(c.hex)}
                    style={{
                      width: '24px',
                      height: '24px',
                      borderRadius: '50%',
                      background: c.hex,
                      border: carColor === c.hex ? '2px solid #00E5FF' : '1px solid rgba(255,255,255,0.3)',
                      cursor: 'pointer',
                      transform: carColor === c.hex ? 'scale(1.15)' : 'scale(1)',
                      transition: 'transform 0.2s'
                    }}
                    title={c.name}
                  />
                ))}
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* 3. Section C: Performance & Virtual Dyno */}
      <section style={{ padding: '3.5rem 1.5rem', background: '#0a0d14', borderTop: '1px solid rgba(255,255,255,0.05)' }}>
        <div style={{ maxWidth: '1200px', margin: '0 auto' }}>
          <div style={{ color: '#00E5FF', fontSize: '0.75rem', fontWeight: 800, letterSpacing: '0.15em', marginBottom: '0.5rem' }}>
            SECTION C • TECHNICAL DYNO & TELEMETRY
          </div>
          <h2 style={{ fontSize: '2.5rem', fontWeight: 900, marginBottom: '1.5rem' }}>BUILT BEYOND EXPECTATIONS.</h2>

          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: '1.5rem' }}>
            {/* Interactive Dyno */}
            <div className="aurix-card" style={{ padding: '1.5rem' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '1rem' }}>
                <span style={{ fontSize: '0.85rem', fontWeight: 800, color: '#00E5FF' }}>VIRTUAL RPM DYNO</span>
                <span style={{ fontSize: '1rem', fontWeight: 900 }}>{rpm} RPM</span>
              </div>
              <input
                type="range"
                min="1000"
                max="8000"
                value={rpm}
                onChange={(e) => setRpm(Number(e.target.value))}
                style={{ width: '100%', accentColor: '#00E5FF', marginBottom: '1rem', cursor: 'pointer' }}
              />
              <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.85rem', fontWeight: 800 }}>
                <span style={{ color: '#00E5FF' }}>HP: {Math.round((rpm / 7800) * 450)} hp</span>
                <span style={{ color: '#f59e0b' }}>TORQUE: {Math.round(620 * (1 - Math.abs(rpm - 4500) / 9000))} Nm</span>
              </div>
            </div>

            {/* Drive Mode Selector */}
            <div className="aurix-card" style={{ padding: '1.5rem' }}>
              <div style={{ fontSize: '0.85rem', fontWeight: 800, color: '#00E5FF', marginBottom: '1rem' }}>
                INTELLIGENT DYNAMICS MODE
              </div>
              <div style={{ display: 'grid', gridTemplateColumns: 'repeat(2, 1fr)', gap: '0.5rem', marginBottom: '1rem' }}>
                {(Object.keys(driveModes) as (keyof typeof driveModes)[]).map((mode) => (
                  <button
                    key={mode}
                    onClick={() => setDriveMode(mode)}
                    style={{
                      padding: '0.65rem',
                      borderRadius: '8px',
                      border: driveMode === mode ? `2px solid ${driveModes[mode].color}` : '1px solid rgba(255,255,255,0.1)',
                      background: driveMode === mode ? `${driveModes[mode].color}22` : '#101622',
                      color: driveMode === mode ? driveModes[mode].color : '#94A3B8',
                      fontWeight: 800,
                      fontSize: '0.75rem',
                      cursor: 'pointer'
                    }}
                  >
                    {driveModes[mode].name}
                  </button>
                ))}
              </div>
              <div style={{ fontSize: '0.75rem', color: '#94A3B8' }}>
                Active Rear Wing Attack Angle: <strong style={{ color: driveModes[driveMode].color }}>{driveModes[driveMode].wing}°</strong>
              </div>
            </div>

            {/* Launch Control Sprint Test */}
            <div className="aurix-card" style={{ padding: '1.5rem', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
              <div>
                <div style={{ fontSize: '0.85rem', fontWeight: 800, color: '#00E5FF', marginBottom: '0.5rem' }}>
                  0–100 KM/H BENCHMARK SPRINT
                </div>
                <div style={{ fontSize: '2.5rem', fontWeight: 900, color: '#F8FAFC' }}>
                  {speed} <span style={{ fontSize: '1rem', color: '#00E5FF' }}>KM/H</span>
                </div>
                <div style={{ fontSize: '0.75rem', color: '#94A3B8' }}>
                  {launchDone ? 'Verified: 3.80s Flat (Launch Control Active)' : 'Dual axial-flux overboost'}
                </div>
              </div>
              <button
                onClick={handleLaunchSprint}
                className="btn-primary"
                style={{ width: '100%', marginTop: '1rem' }}
                disabled={launchTesting}
              >
                {launchTesting ? 'ACCELERATING...' : launchDone ? 'RE-RUN SPRINT' : 'TEST 0-100 LAUNCH'}
              </button>
            </div>
          </div>
        </div>
      </section>

      {/* 4. Section F: Pakistan Luxury Atelier & FBR Customs Tax Calculator */}
      <section id="booking-section" style={{ padding: '4rem 1.5rem', maxWidth: '1200px', margin: '0 auto' }}>
        <div style={{ color: '#00E5FF', fontSize: '0.75rem', fontWeight: 800, letterSpacing: '0.15em', marginBottom: '0.5rem' }}>
          SECTION F • PAKISTAN ATELIER & ALLOCATION (PKR)
        </div>
        <h2 style={{ fontSize: '2.5rem', fontWeight: 900, marginBottom: '2rem' }}>THE ROAD IS YOURS.</h2>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '2rem' }}>
          {/* Tax Calculator & Models */}
          <div className="aurix-card" style={{ padding: '1.75rem' }}>
            <h3 style={{ fontSize: '1.15rem', fontWeight: 900, marginBottom: '1rem' }}>OFFICIAL ALLOCATION SCHEDULE</h3>

            {/* Model Select */}
            <div style={{ display: 'flex', gap: '0.5rem', marginBottom: '1.25rem' }}>
              {(Object.keys(models) as (keyof typeof models)[]).map((key) => (
                <button
                  key={key}
                  onClick={() => setSelectedModel(key)}
                  style={{
                    flex: 1,
                    padding: '0.5rem',
                    borderRadius: '8px',
                    border: selectedModel === key ? '2px solid #00E5FF' : '1px solid rgba(255,255,255,0.1)',
                    background: selectedModel === key ? 'rgba(0,229,255,0.1)' : '#101622',
                    color: selectedModel === key ? '#00E5FF' : '#94A3B8',
                    fontSize: '0.7rem',
                    fontWeight: 800,
                    cursor: 'pointer'
                  }}
                >
                  {models[key].name.replace('AURIX ', '')}
                </button>
              ))}
            </div>

            {/* Filer Toggle */}
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem', background: '#0e1420', padding: '0.75rem', borderRadius: '8px' }}>
              <span style={{ fontSize: '0.8rem', color: '#94A3B8' }}>Tax Filer Status (Sec 231B):</span>
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

            {/* Costs Table */}
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
                <span style={{ fontWeight: 800 }}>ESTIMATED TOTAL ON-ROAD:</span>
                <span style={{ fontSize: '1.25rem', fontWeight: 900, color: '#00E5FF' }}>PKR {totalOnRoadCrore.toFixed(2)} Crore</span>
              </div>
            </div>

            {/* Showroom Contacts */}
            <div style={{ marginTop: '1.5rem' }}>
              <div style={{ fontSize: '0.75rem', color: '#94A3B8', fontWeight: 800, marginBottom: '0.5rem' }}>ATELIER NETWORK:</div>
              <div style={{ display: 'flex', gap: '0.5rem', marginBottom: '0.75rem' }}>
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
              <div style={{ background: '#0e1420', padding: '0.85rem', borderRadius: '8px', fontSize: '0.8rem' }}>
                <div style={{ fontWeight: 800, color: '#F8FAFC' }}>{showrooms[activeShowroom].name}</div>
                <div style={{ color: '#94A3B8', margin: '0.25rem 0' }}>{showrooms[activeShowroom].address}</div>
                <div style={{ color: '#00E5FF', fontWeight: 800 }}>{showrooms[activeShowroom].phone}</div>
              </div>
            </div>
          </div>

          {/* Test Drive Form */}
          <div className="aurix-card" style={{ padding: '1.75rem' }}>
            {bookingConfirmed ? (
              <div style={{ textAlign: 'center', padding: '2rem 1rem' }}>
                <CheckCircle2 size={54} color="#00E5FF" style={{ margin: '0 auto 1rem' }} />
                <h3 style={{ fontSize: '1.5rem', fontWeight: 900, marginBottom: '0.5rem' }}>VIP ALLOCATION RESERVED</h3>
                <p style={{ color: '#94A3B8', fontSize: '0.85rem', lineHeight: 1.6, marginBottom: '1rem' }}>
                  Confirmation Reference: <strong style={{ color: '#00E5FF' }}>{bookingConfirmed}</strong><br />
                  Our Senior Specialist in {activeShowroom.toUpperCase()} will call <strong>{clientPhone}</strong> to schedule your closed-circuit track allocation.
                </p>
                <button onClick={() => setBookingConfirmed(null)} className="btn-outline">
                  BOOK ANOTHER SESSION
                </button>
              </div>
            ) : (
              <form onSubmit={handleBookingSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                <h3 style={{ fontSize: '1.25rem', fontWeight: 900 }}>VIP TEST DRIVE & ALLOCATION</h3>
                <p style={{ color: '#94A3B8', fontSize: '0.8rem' }}>
                  Reserve private track experience in Lahore, Karachi, or Islamabad.
                </p>

                <div>
                  <label style={{ fontSize: '0.75rem', color: '#94A3B8', display: 'block', marginBottom: '0.35rem' }}>Client Full Name</label>
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
                  <label style={{ fontSize: '0.75rem', color: '#94A3B8', display: 'block', marginBottom: '0.35rem' }}>Mobile (+92 3XX XXXXXXX)</label>
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

                <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', fontSize: '0.8rem', color: '#94A3B8' }}>
                  <input
                    type="checkbox"
                    checked={hasLicense}
                    onChange={(e) => setHasLicense(e.target.checked)}
                    id="license"
                    style={{ accentColor: '#00E5FF' }}
                  />
                  <label htmlFor="license">I hold a valid driving license & CNIC.</label>
                </div>

                <button type="submit" className="btn-primary" style={{ marginTop: '0.5rem', padding: '0.85rem' }}>
                  CONFIRM PRIVATE RESERVATION
                </button>
              </form>
            )}
          </div>
        </div>
      </section>

      {/* 5. Footer */}
      <footer style={{ borderTop: '1px solid rgba(255,255,255,0.08)', padding: '2.5rem 1.5rem', textAlign: 'center', fontSize: '0.8rem', color: '#64748B' }}>
        <div style={{ maxWidth: '1200px', margin: '0 auto', display: 'flex', flexDirection: 'column', gap: '0.75rem' }}>
          <div style={{ fontWeight: 800, color: '#F8FAFC', letterSpacing: '0.1em' }}>AURIX AUTOMOTIVE PAKISTAN</div>
          <div>5-Year / 100,000 KM Certified Factory Warranty • 24/7 Roadside Concierge across M-2, M-3 & M-9 Motorways</div>
          <div>© 2026 AURIX. All rights reserved. Prices in PKR subject to prevailing statutory FBR customs tariffs.</div>
        </div>
      </footer>
    </div>
  );
}
