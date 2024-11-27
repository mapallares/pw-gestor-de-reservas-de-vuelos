import Button from '../components/Button/Button.jsx'
import DynamicMessage from '../components/DynamicMessage/DynamicMessage.jsx'
import Logo from '../components/Logo/Logo.jsx'
import Auth from '../scripts/Auth.mjs'
import './AppLayout.css'
import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import Vuelo from '../scripts/Vuelo.mjs'
import VueloCard from '../components/VueloCard/VueloCard.jsx'
import Aeropuerto from '../scripts/Aeropuerto.mjs'
import Reserva from '../scripts/Reserva.mjs'
import Notify from '../scripts/utils/notify.mjs'
import Table from '../components/Table/Table.jsx'
import Pasajero from '../scripts/Pasajero.mjs'

function AppLayout() {
  const navigate = useNavigate()
  const [session, setSession] = useState(null)
  const [aeropuertos, setAeropuertos] = useState([])
  const [vuelos, setVuelos] = useState([])
  const [rutas, setRutas] = useState([])
  const [reservas, setReservas] = useState(null)
  const [reserva, setReserva] = useState(null)
  const [form, setForm] = useState({
    origen: '',
    destino: '',
    fechaSalida: '',
    pasajeros: 1
  })
  const [pasajero, setPasajero] = useState({
    nombre: '',
    apellido: '',
    identificacion: '',
    fechaNacimiento: '',
    email: '',
    reservaId: ''
  })


  const handleChange = (event) => {
    setForm({
      ...form,
      [event.target.name]: event.target.value
    })
  }

  const handleChangePasajero = (event) => {
    setPasajero({
      ...pasajero,
      [event.target.name]: event.target.value
    })
  }


  const loadVuelos = async () => {
    const vuelos = await Vuelo.getAll()
    setVuelos(vuelos)
  }

  const loadAeropuertos = async () => {
    const aeropuertos = await Aeropuerto.getAll()
    setAeropuertos(aeropuertos)
  }

  const loadReservas = async () => {
    const reservas = await Reserva.getAll() 
    setReservas(reservas.filter(reserva => reserva.cliente.id === session.cliente.id))
  }

  const loadReserva = async (id) => {
    const reserva = await Reserva.get(id) 
    setReserva(reserva)
  }

  const findRutasPosibles = (form, vuelos) => {
    const { origen, destino, fechaSalida } = form;
    const rutas = findRutas(origen, destino, vuelos)
    const rutasFiltradas = rutas.filter(ruta => {
      if (ruta[0].fechaSalida === fechaSalida) {
        for (let i = 1; i < ruta.length; i++) {
          if (new Date(ruta[i].fechaSalida) <= new Date(ruta[i - 1].fechaSalida)) {
            return false;
          }
        }
        return true;
      }
      return false;
    })
    return rutasFiltradas
  };

  const findRutas = (origen, destino, vuelos, rutaActual = [], rutasEncontradas = [], ciudadesVisitadas = new Set()) => {
    if (ciudadesVisitadas.has(origen)) {
      return;
    }
    ciudadesVisitadas.add(origen);
    const vuelosDesdeOrigen = vuelos.filter((vuelo) => vuelo.origen.ciudad === origen);

    vuelosDesdeOrigen.forEach((vuelo) => {
      if (vuelo.destino.ciudad === destino) {
        rutasEncontradas.push([...rutaActual, vuelo]);
      } else {
        findRutas(vuelo.destino.ciudad, destino, vuelos, [...rutaActual, vuelo], rutasEncontradas, new Set(ciudadesVisitadas));
      }
    });
  
    return rutasEncontradas;
  };

  useEffect(() => {
    Auth.session(session => {
      if (!session) return
      if (!session.roles.includes('ROLE_USER')) navigate('/admin')
      loadVuelos()
      loadAeropuertos()
      setSession(session)
    })
  }, [])

  const createReserva = async (ruta) => {
    const reserva = await Reserva.post({
      clienteId: session.cliente.id,
      fechaReserva: new Date(),
      numeroPasajeros: form.pasajeros,
      vuelosIds: ruta.map(vuelo => vuelo.id)
    })
    if(reserva) {
      Notify.notice('Se ha creado la reserva con éxito')
      loadReserva(reserva.id)
    }
    else {
      Notify.notice('No se ha podido crear la reserva', 'error')
    }
  }

  const createPasajero = async (event) => {
    event.preventDefault()
    console.log({...pasajero, reservaId: reserva.id})
    const pasaj = await Pasajero.post({...pasajero, reservaId: reserva.id})
    if(pasaj) {
      Notify.notice('Se ha creado el pasajero con éxito')
      loadReserva(reserva.id)
      setPasajero({
        nombre: '',
        apellido: '',
        identificacion: '',
        fechaNacimiento: '',
        telefono: '',
        email: '',
        reservaId: ''
      })
    }
    else {
      Notify.notice('No se ha podido crear el pasajero', 'error')
    }
  }

  const toggleReservas = async () => {
    if(!reserva) {
      loadReservas()
    }
    else {
      setReservas(null)
    }
  }

  return (session
    ?
    <>

      {reservas ? <div className="pw-layout-modal-overlay-fixed">
        <div className="pw-app-reservas-modal">
          <h2><Button size="s" color="danger" variant="dimed"><span className="material-symbols-outlined" onClick={() => setReservas(null)}>arrow_back</span> Salir</Button> ({reservas.length}) Reservas</h2>
          <br></br>
          {reservas.length === 0 ? <p>No ha realizado reservas</p> : reservas.map(reserva => 
            <div className="pw-app-reservas-reserva-card" onClick={() => setReserva(reserva)}>
              <h3 className="pw-app-reservas-reserva-card-id">Reserva #{reserva.id}</h3>
              <p className="pw-app-reservas-reserva-card-info"><strong>Fecha de reserva:</strong> {reserva.fechaReserva}</p>
              <p className="pw-app-reservas-reserva-card-info"><strong>Vuelos:</strong> {reserva.vuelos.length}</p>
              <p className="pw-app-reservas-reserva-card-info"><strong>Pasajeros:</strong> {reserva.pasajeros.length}</p>
            </div>
          )}
        </div>
      </div> : ''}

      {reserva ? <div className="pw-layout-modal-overlay-fixed">
        <div className="pw-app-reserva-modal">
          <h2><Button size="s" color="danger" variant="dimed"><span className="material-symbols-outlined" onClick={() => setReserva(null)}>arrow_back</span> Salir</Button> Reserva #{reserva.id}</h2>
          <br></br>
          <h3>({reserva.vuelos.length}) Vuelos</h3>
          <br></br>
          <div className="pw-app-reserva-modal-vuelos">
            {reserva.vuelos.map(vuelo => <VueloCard vuelo={{...vuelo}} key={`vueloReservado_${vuelo.id}`}></VueloCard>)}
          </div>
          <br></br>
          <h3>({reserva.pasajeros.length}) Pasajeros</h3>
          <br></br>
          <Table dataObjects={reserva.pasajeros}></Table>
          <br></br>
          <form className="pw-pasajeros-form">
              <center><h2>Pasajero</h2></center>
              <br></br>
              <div className="pw-forms-inputs">
                <div className="pw-forms-input-group-double">
                  <div className="pw-forms-input-group">
                    <input className="pw-forms-input" onChange={handleChangePasajero} id="pasajerosNombre" minLength={2} maxLength={100} name="nombre" type="text" autoComplete="off" required="required" value={pasajero.nombre}/>
                    <label className="pw-forms-label" htmlFor="pasajerosNombre">Nombre</label>
                  </div>
                  <div className="pw-forms-input-group">
                    <input className="pw-forms-input" onChange={handleChangePasajero} id="pasajerosApellido" minLength={2} maxLength={100} name="apellido" type="text" autoComplete="off" required="required" value={pasajero.apellido}/>
                    <label className="pw-forms-label" htmlFor="pasajerosApellido">Apellido</label>
                  </div>
                </div>
                <div className="pw-forms-input-group-double">
                  <div className="pw-forms-input-group">
                    <input className="pw-forms-input" onChange={handleChangePasajero} id="pasajerosFechaNacimiento" name="fechaNacimiento" type="date" autoComplete="off" required="required" value={pasajero.fechaNacimiento}/>
                    <label className="pw-forms-label" htmlFor="pasajerosNombre">Cumpleaños</label>
                  </div>
                  <div className="pw-forms-input-group">
                    <input className="pw-forms-input" onChange={handleChangePasajero} id="pasajerosIdentificacion" minLength={2} maxLength={100} name="identificacion" type="text" autoComplete="off" required="required" value={pasajero.identificacion}/>
                    <label className="pw-forms-label" htmlFor="pasajerosApellido">Identificacion</label>
                  </div>
                </div>
                <div className="pw-forms-input-group">
                  <input className="pw-forms-input" onChange={handleChangePasajero} id="pasajerosEmail" name="email" type="email" autoComplete="off" required="required" value={pasajero.email}/>
                  <label className="pw-forms-label" htmlFor="pasajerosEmail">Email</label>
                </div>
              </div>
              <Button size="m" width="full" onClick={createPasajero}>Guardar</Button>
            </form>
        </div>
      </div> : ''}

      <main className="pw-app-main">

        <section className="pw-app-main-principal">

          <div className="pw-app-main-principal-container">

            <header className="pw-app-header">
              <div className="pw-app-header-logo">
                <Logo color="white" size="s"></Logo>
                <h2>| Bookings</h2>
              </div>
              <nav></nav>
              <div className="pw-app-header-session">
                <div className="pw-app-header-session-user">
                  <div className="pw-app-header-session-user-avatar">
                    {session.cliente.nombre[0]}
                  </div>
                  <div className="pw-app-header-session-user-info">
                    <strong>{session.cliente.nombre} {session.cliente.apellido}</strong>
                    <p>@{session.cliente.username}</p>
                  </div>
                </div>
                <Button radius="full" size="s" variant="dimed" color="success" onClick={toggleReservas}>
                  <span className="material-symbols-outlined">flight</span>
                  <span className="material-symbols-outlined">today</span>
                </Button>
                <Button radius="full" size="s" variant="dimed" color="danger" onClick={Auth.signOut}>
                  <span className="material-symbols-outlined">logout</span>
                </Button>
              </div>
            </header>

            <div className="pw-app-main-principal-content">
              <DynamicMessage />
              <br></br>
            </div>

            <div className="pw-app-main-search">
              <h2>Busca un vuelo</h2>

              <br></br>

              <div className="pw-app-main-search-pickers">
                <div className="pw-app-main-search-picker">

                  <div className="pw-app-main-search-picker-item">
                    <div className="pw-app-main-search-picker-item-icon">
                      <span className="material-symbols-outlined">flight_takeoff</span>
                    </div>
                    <div className="pw-app-main-search-picker-item-info">
                      <label htmlFor="consultaOrigen">Ciudad origen</label>
                      <select className="pw-app-main-search-picker-item-select" onChange={handleChange} id="consultaOrigen" name="origen" type="numeric" autoComplete="off" required="required" value={form.origen}>
                        <option value=''>Ninguno</option>
                        {[...new Set(aeropuertos.map(item => item.ciudad))].map(ciudad => <option key={`origen_${ciudad}`} value={ciudad}>{ciudad}</option>)}
                      </select>
                    </div>
                  </div>

                  <div className="pw-app-main-search-picker-item-swap">
                    <Button color="white" size="s" icon><span className="material-symbols-outlined" onClick={() => {
                      setForm({...form, origen: form.destino, destino: form.origen})
                    }}>swap_horiz</span></Button>
                  </div>

                  <div className="pw-app-main-search-picker-item">
                    <div className="pw-app-main-search-picker-item-icon">
                      <span className="material-symbols-outlined">flight_land</span>
                    </div>
                    <div className="pw-app-main-search-picker-item-info">
                      <label htmlFor="consultaDestino">Ciudad destino</label>
                      <select className="pw-app-main-search-picker-item-select" onChange={handleChange} id="consultaDestino" name="destino" type="numeric" autoComplete="off" required="required" value={form.destino}>
                        <option value=''>Ninguno</option>
                        {[...new Set(aeropuertos.map(item => item.ciudad))].map(ciudad => <option key={`destino_${ciudad}`} value={ciudad}>{ciudad}</option>)}
                      </select>
                    </div>
                  </div>

                </div>

                <div className="pw-app-main-search-picker">

                  <div className="pw-app-main-search-picker-item">
                    <div className="pw-app-main-search-picker-item-icon pw-app-main-search-picker-item-icon-variant">
                      <span className="material-symbols-outlined">event</span>
                    </div>
                    <div className="pw-app-main-search-picker-item-info">
                      <label htmlFor="consultaFechaSalida">Fecha de vuelo</label>
                      <input id="consultaFechaSalida" onChange={handleChange} name="fechaSalida" type="date" autoComplete="off" required="required"/>
                    </div>
                  </div>

                  <div className="pw-app-main-search-picker-item">
                    <div className="pw-app-main-search-picker-item-icon pw-app-main-search-picker-item-icon-variant">
                      <span className="material-symbols-outlined">account_circle</span>
                    </div>
                    <div className="pw-app-main-search-picker-item-info">
                      <label htmlFor="consultaPasajeros">Cantidad de pasajeros</label>
                      <input id="consultaPasajeros" onChange={handleChange} name="pasajeros" type="number" autoComplete="off" required="required" min={1} defaultValue={1}/>
                    </div>
                  </div>

                </div>
              </div>

              <br></br>

              <div className="pw-app-main-search-btn" onClick={() => { 
                setRutas(findRutasPosibles(form, vuelos))
                }}>
              <Button><span className="material-symbols-outlined">search</span> Buscar</Button>
              </div>

            </div>

          </div>
        </section>

        <section className="pw-app-section-search">
          <section className="pw-app-section-results">
            {rutas.length > 0 ? <>
            <h2>({rutas.length}) Resultados</h2>
            {rutas.map((r, i) => <div className="pw-app-result" key={`result_${i}`}>
                {r.length === 1 ? <h2>Vuelo directo</h2> : <h2>Vuelo con {r.length} escalas</h2>}
                <div className="pw-app-result-vuelos">{r.map(vuelo => <VueloCard vuelo={{...vuelo}} key={`vueloCard_${vuelo.id}`}></VueloCard>)}</div>
              <div onClick={async () => await createReserva(r)}><Button size="s" color="black"><span className="material-symbols-outlined">add</span> Reservar</Button></div>
              </div>)}
            </> : <div className="pw-app-section-results-empty">Sin resultados</div>}
          </section>
        </section>

      </main>
    </>
    :
    <>
      <main className="pw-app-landing-main">

        <section className="pw-app-landing-main-principal">

          <div className="pw-app-landing-main-principal-container">

            <header className="pw-app-landing-header">
              <div className="pw-app-landing-header-logo">
                <Logo color="white" size="s"></Logo>
                <h2>| Bookings</h2>
              </div>
              <nav></nav>
              <div className="pw-app-landing-header-session">
                <Button radius="full" size="s" variant="bordered" onClick={() => navigate('/signup')}>Sign Up</Button>
                <Button radius="full" size="s" color="white" onClick={() => navigate('/signin')}>Sign In</Button>
              </div>
            </header>

            <div className="pw-app-landing-main-principal-content">
              <DynamicMessage />
              <br></br>
            </div>

            <div className="pw-app-landing-main-search">

              <div className="pw-content-vuelos-vuelo"><div className="pw-content-vuelos-vuelo-top"><div className="pw-content-vuelos-vuelo-top-origen"><h2>BAR</h2><p>Colombia</p></div><div className="pw-content-vuelos-vuelo-top-divider"><div className="pw-content-vuelos-vuelo-top-divider-plane"><div className="pw-content-vuelos-vuelo-divider-line"></div><span className="material-symbols-outlined">flight</span></div><div className="pw-content-vuelos-vuelo-top-divider-duracion"><span className="material-symbols-outlined">schedule</span><span className="pw-content-vuelos-vuelo-top-divider-duracion-time">16 horas</span></div></div><div className="pw-content-vuelos-vuelo-top-destino"><h2>COR</h2><p>Corea del Sur</p></div></div><div className="pw-content-vuelos-vuelo-divider"><div className="pw-content-vuelos-vuelo-divider-line"></div></div><div className="pw-content-vuelos-vuelo-bottom"><div className="pw-content-vuelos-vuelo-bottom-detail"><strong>Fecha</strong><p>2024-11-26</p></div><div className="pw-content-vuelos-vuelo-bottom-detail"><strong>Hora</strong><p>12:12:00</p></div><div className="pw-content-vuelos-vuelo-bottom-detail"><strong>Aerolínea</strong><p>Avianca</p></div></div></div>

            </div>

          </div>
        </section>

      </main>
    </>
  )
}

export default AppLayout
