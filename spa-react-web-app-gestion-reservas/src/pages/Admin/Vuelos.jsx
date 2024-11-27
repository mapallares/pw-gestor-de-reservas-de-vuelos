import { useEffect, useState } from "react"
import "./Vuelos.css"
import Vuelo from "../../scripts/Vuelo.mjs"
import Aeropuerto from "../../scripts/Aeropuerto.mjs"
import Aerolinea from "../../scripts/Aerolinea.mjs"
import Table from "../../components/Table/Table.jsx"
import Button from "../../components/Button/Button.jsx"
import Validator from "../../scripts/utils/validator.mjs"
import Notify from "../../scripts/utils/notify.mjs"
import VueloCard from "../../components/VueloCard/VueloCard.jsx"

function Vuelos () {
  const [vuelos, setVuelos] = useState([])
  const [aeropuertos, setAeropuertos] = useState([])
  const [aerolineas, setAerolineas] = useState([])
  const [showModal, setShowModal] = useState(false)
  const [idVuelo, setIdVuelo] = useState(null)
  const [form, setForm] = useState({
    origenId: '',
    destinoId: '',
    aerolineaId: '',
    fechaSalida: '',
    horaSalida: '',
    duracion: '',
    capacidad: ''
  })

  const loadVuelos = async () => {
    const vuelos = await Vuelo.getAll()
    setVuelos(vuelos)
    setForm({
      origenId: '',
      destinoId: '',
      aerolineaId: '',
      fechaSalida: '',
      horaSalida: '',
      duracion: '',
      capacidad: ''
    })
    setIdVuelo(null)
    setShowModal(false)
  }

  const loadAeropuertos = async () => {
    const aeropuertos = await Aeropuerto.getAll()
    setAeropuertos(aeropuertos)
  }

  const loadAerolineas = async () => {
    const aerolineas = await Aerolinea.getAll()
    setAerolineas(aerolineas)
  }

  const toggleModal = () => {
    if(showModal) {
      setForm({
        origenId: '',
        destinoId: '',
        aerolineaId: '',
        fechaSalida: '',
        horaSalida: '',
        duracion: '',
        capacidad: ''
      })
      setIdVuelo(null)
    }
    setShowModal(!showModal)
  }

  const handleChange = (event) => {
    setForm({
      ...form,
      [event.target.name]: event.target.value
    })
  }

  const saveVuelo = async (event) => {
    event.preventDefault()

    const isValid = true

    if(!isValid) return false

    if(idVuelo) {
      const vuelo = await Vuelo.put(idVuelo, form)
      if(vuelo) {
        Notify.notice('Se ha modificado el vuelo con éxito')
        loadVuelos()
      }
      else {
        Notify.notice('No se ha podido modificar el vuelo', 'error')
      }
    }
    else {
      const vuelo = await Vuelo.post(form)
      if(vuelo) {
        Notify.notice('Se ha creado el vuelo con éxito')
        loadVuelos()
      }
      else {
        Notify.notice('No se ha podido crear el vuelo', 'error')
      }
    }

  }

  const deleteVuelo = async (id) => {
    const deleted = await Vuelo.delete(id)
    if(deleted) {
      Notify.notice('Se ha eliminado el vuelo con éxito')
      loadVuelos()
    }
    else {
      Notify.notice('No se ha podido eliminar el vuelo', 'error')
    }
  }

  useEffect(() => {
    loadVuelos()
    loadAeropuertos()
    loadAerolineas()
  }, [])

  return (
    <>
      <header className="pw-header">
        <div className="pw-header-path">
          <h2 className="pw-header-path-name">Vuelos</h2>
        </div>
      </header>
      <div className="pw-content">
        <div className="pw-content-vuelos">
          <div className="pw-content-vuelos-head">
          <Button size="m" onClick={toggleModal}>
            <span className="material-symbols-outlined">add</span> Nuevo Vuelo
          </Button>
          </div>

          <div className="pw-content-vuelos-vuelos">
            {vuelos ? vuelos.map(vuelo => <VueloCard vuelo={{...vuelo}} key={`vueloCard_${vuelo.id}`}></VueloCard>
            ) : <div className="pw-content-vuelos-vuelos-empty">No hay vuelos por mostrar</div>}
          </div>
          
          <div className="pw-content-vuelos-table-container">
          <div className="pw-content-vuelos-table">
            {vuelos && (
              <Table
                dataObjects={vuelos.map(vuelo => ({...vuelo,
                origen: `${vuelo.origen.ciudad} (${vuelo.origen.pais})`,
                destino: `${vuelo.destino.ciudad} (${vuelo.destino.pais})`,
                aerolinea: vuelo.aerolinea.nombre, 
                acciones: <div className="pw-layout-table-actions">
                  <Button variant="dimed" color="success" size="xs" icon onClick={async () => {
                    setForm({
                      ...vuelo,
                      id: undefined,
                      origenId: vuelo.origen.id,
                      destinoId: vuelo.destino.id,
                      aerolineaId: vuelo.aerolinea.id
                    })
                    setIdVuelo(vuelo.id)
                    toggleModal()
                  }}><span className="material-symbols-outlined">edit</span></Button>
                  <Button variant="dimed" color="danger" size="xs" icon onClick={async () => await deleteVuelo(vuelo.id)}><span className="material-symbols-outlined">delete</span></Button>
                </div>
                }))}
                id="vuelosTable"
                translate={{
                  origen: 'Sale desde',
                  destino: 'Llega hasta',
                  aerolinea: 'Aerolinea',
                  fechaSalida: 'Fecha',
                  horaSalida: 'Hora',
                  duracion: 'Duración',
                  capacidad: 'Capacidad',
                  acciones: 'Acciones'
                }}
              ></Table>
            )}
          </div>
          </div>
        </div>
      </div>
      {showModal && (
        <div className="pw-layout-modal-overlay" onClick={toggleModal}>
          <div className="pw-content-vuelos-modal" onClick={(e) => e.stopPropagation()}>
            <form className="pw-vuelos-form" id="form">
              <center><h2>{idVuelo ? "Editar" : "Nuevo"} Vuelo</h2></center>
              <br></br>
              <div className="pw-forms-inputs">
                <div className="pw-forms-input-group-double">
                  <div className="pw-forms-input-group">
                    <select className="pw-forms-input" onChange={handleChange} id="vuelosOrigenId" name="origenId" type="number" autoComplete="off" required="required" value={form.origenId}>
                      <option value=''>Ninguno</option>
                      {aeropuertos.map(aeropuerto => <option key={`origen_${aeropuerto.id}`} value={aeropuerto.id}>{`${aeropuerto.nombre}, ${aeropuerto.ciudad} (${aeropuerto.pais})`}</option>)}
                    </select>
                    <label className="pw-forms-label" htmlFor="vuelosOrigenId">Origen</label>
                  </div>
                  <div className="pw-forms-input-group">
                    <select className="pw-forms-input" onChange={handleChange} id="vuelosDestinoId" name="destinoId" type="number" autoComplete="off" required="required" value={form.destinoId}>
                    <option value=''>Ninguno</option>
                      {aeropuertos.map(aeropuerto => <option key={`destino_${aeropuerto.id}`} value={aeropuerto.id}>{`${aeropuerto.nombre}, ${aeropuerto.ciudad} (${aeropuerto.pais})`}</option>)}
                    </select>
                    <label className="pw-forms-label" htmlFor="vuelosDestinoId">Destino</label>
                  </div>
                </div>
                <div className="pw-forms-input-group">
                  <select className="pw-forms-input" onChange={handleChange} id="vuelosAerolineaId" name="aerolineaId" type="number" autoComplete="off" required="required" value={form.aerolineaId}>
                    <option value=''>Ninguna</option>
                    {aerolineas.map(aerolinea => <option key={`aerolinea_${aerolinea.id}`} value={aerolinea.id}>{`${aerolinea.nombre} [${aerolinea.codigoAerolinea}] (${aerolinea.paisOrigen})`}</option>)}
                  </select>
                  <label className="pw-forms-label" htmlFor="vuelosAerolineaId">Aerolínea</label>
                </div>
                <div className="pw-forms-input-group-double">
                  <div className="pw-forms-input-group">
                    <input className="pw-forms-input" onChange={handleChange} id="vuelosFechaSalida" name="fechaSalida" type="date" autoComplete="off" required="required" value={form.fechaSalida}/>
                    <label className="pw-forms-label" htmlFor="vuelosFechaSalida">Fecha</label>
                  </div>
                  <div className="pw-forms-input-group">
                    <input className="pw-forms-input" onChange={handleChange} id="vuelosHoraSalida" name="horaSalida" type="time" autoComplete="off" required="required" value={form.horaSalida}/>
                    <label className="pw-forms-label" htmlFor="vuelosHoraSalida">Hora</label>
                  </div>
                </div>
                <div className="pw-forms-input-group-double">
                  <div className="pw-forms-input-group">
                    <input className="pw-forms-input" onChange={handleChange} id="vuelosDuracion" name="duracion" type="number" autoComplete="off" required="required" value={form.duracion}/>
                    <label className="pw-forms-label" htmlFor="vuelosDuracion">Duración</label>
                  </div>
                  <div className="pw-forms-input-group">
                    <input className="pw-forms-input" onChange={handleChange} id="vuelosCapacidad" name="capacidad" type="number" autoComplete="off" required="required" value={form.capacidad}/>
                    <label className="pw-forms-label" htmlFor="vuelosCapacidad">Capacidad</label>
                  </div>
                </div>
              </div>
              <br/>
              <Button size="m" width="full" onClick={saveVuelo}>Guardar</Button>
            </form>
          </div>
        </div>
      )}
    </>
  )
}

export default Vuelos
