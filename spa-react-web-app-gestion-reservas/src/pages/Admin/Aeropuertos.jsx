import { useEffect, useState } from "react"
import "./Aeropuertos.css"
import Aeropuerto from "../../scripts/Aeropuerto.mjs"
import Table from "../../components/Table/Table.jsx"
import Button from "../../components/Button/Button.jsx"
import Validator from "../../scripts/utils/validator.mjs"
import Notify from "../../scripts/utils/notify.mjs"
import { $ } from "../../scripts/utils/selectors.mjs"

function Aeropuertos () {
  const [aeropuertos, setAeropuertos] = useState([])
  const [showModal, setShowModal] = useState(false)
  const [idAeropuerto, setIdAeropuerto] = useState(null)
  const [form, setForm] = useState({
    nombre: '',
    ciudad: '',
    pais: ''
  })

  const loadAeropuertos = async () => {
    const aeropuertos = await Aeropuerto.getAll()
    setAeropuertos(aeropuertos)
    setForm({
      nombre: '',
      ciudad: '',
      pais: ''
    })
    setIdAeropuerto(null)
    setShowModal(false)
  }

  const toggleModal = () => {
    if(showModal) {
      setForm({
        nombre: '',
        ciudad: '',
        pais: ''
      })
      setIdAeropuerto(null)
    }
    setShowModal(!showModal)
  }

  const handleChange = (event) => {
    setForm({
      ...form,
      [event.target.name]: event.target.value
    })
  }

  const saveAeropuerto = async (event) => {
    event.preventDefault()

    const isValid = Validator.valid($('aeropuertosNombre'), () => {
      const nombre = { nombre: form.nombre }
      Validator.required(nombre)
      Validator.length(nombre, 2, 100)
    })
    && Validator.valid($('aeropuertosCiudad'), () => {
      const ciudad = { ciudad: form.ciudad }
      Validator.required(ciudad)
      Validator.length(ciudad, 3, 100)
    })
    && Validator.valid($('aeropuertosPais'), () => {
      const pais = { pais: form.pais }
      Validator.required(pais)
      Validator.length(pais, 2, 100)
    })

    if(!isValid) return false

    if(idAeropuerto) {
      const aeropuerto = await Aeropuerto.put(idAeropuerto, form)
      if(aeropuerto) {
        Notify.notice('Se ha modificado el aeropuerto con éxito')
        loadAeropuertos()
      }
      else {
        Notify.notice('No se ha podido modificar el aeropuerto', 'error')
      }
    }
    else {
      const aeropuerto = await Aeropuerto.post(form)
      if(aeropuerto) {
        Notify.notice('Se ha creado el aeropuerto con éxito')
        loadAeropuertos()
      }
      else {
        Notify.notice('No se ha podido crear el aeropuerto', 'error')
      }
    }

  }

  const deleteAeropuerto = async (id) => {
    const deleted = await Aeropuerto.delete(id)
    if(deleted) {
      Notify.notice('Se ha eliminado el aeropuerto con éxito')
      loadAeropuertos()
    }
    else {
      Notify.notice('No se ha podido eliminar el aeropuerto', 'error')
    }
  }

  useEffect(() => {
    loadAeropuertos()
  }, [])

  return (
    <>
      <header className="pw-header">
        <div className="pw-header-path">
          <h2 className="pw-header-path-name">Aeropuertos</h2>
        </div>
      </header>
      <div className="pw-content">
        <div className="pw-content-aeropuertos">
          <Button size="m" onClick={toggleModal}>
            <span className="material-symbols-outlined">add</span> Nuevo Aeropuerto
          </Button>
          <div className="pw-content-aeropuertos-table">
            {aeropuertos && (
              <Table
                dataObjects={aeropuertos.map(aeropuerto => ({...aeropuerto, 
                pais: <strong>{aeropuerto.pais}</strong>,
                ciudad: <span className="pw-content-aeropuertos-table-row-ciudad">{aeropuerto.ciudad}</span>,
                acciones: <div className="pw-layout-table-actions">
                  <Button variant="dimed" color="success" size="xs" icon onClick={async () => {
                    setForm({
                      ...aeropuerto,
                      id: undefined
                    })
                    setIdAeropuerto(aeropuerto.id)
                    toggleModal()
                  }}><span className="material-symbols-outlined">edit</span></Button>
                  <Button variant="dimed" color="danger" size="xs" icon onClick={async () => await deleteAeropuerto(aeropuerto.id)}><span className="material-symbols-outlined">delete</span></Button>
                </div>
                }))}
                id="aeropuertosTable"
                translate={{
                  id: "#",
                  nombre: "Nombre",
                  ciudad: "Ciudad",
                  pais: "País",
                  acciones: "Acciones"
                }}
              ></Table>
            )}
          </div>
        </div>
      </div>
      {showModal && (
        <div className="pw-layout-modal-overlay" onClick={toggleModal}>
          <div className="pw-layout-modal" onClick={(e) => e.stopPropagation()}>
            <form className="pw-aeropuertos-form">
              <center><h2>{idAeropuerto ? "Editar" : "Nuevo"} Aeropuerto</h2></center>
              <br></br>
              <div className="pw-forms-inputs">
                <div className="pw-forms-input-group">
                  <input className="pw-forms-input" onChange={handleChange} id="aeropuertosNombre" name="nombre" type="text" autoComplete="off" minLength={2} maxLength={100} required="required" value={form.nombre}/>
                  <label className="pw-forms-label" htmlFor="aeropuertosNombre">Nombre</label>
                </div>
                <div className="pw-forms-input-group">
                  <input className="pw-forms-input" onChange={handleChange} id="aeropuertosCiudad" name="ciudad" type="text" autoComplete="off" minLength={3} maxLength={100} required="required" value={form.ciudad}/>
                  <label className="pw-forms-label" htmlFor="aeropuertosCiudad">Ciudad</label>
                </div>
                <div className="pw-forms-input-group">
                  <input className="pw-forms-input" onChange={handleChange} id="aeropuertosPais" name="pais" type="text" autoComplete="off" minLength={2} maxLength={100} required="required" value={form.pais}/>
                  <label className="pw-forms-label" htmlFor="aeropuertosPais">Pais</label>
                </div>
              </div>
              <br/>
              <Button size="m" width="full" onClick={saveAeropuerto}>Guardar</Button>
            </form>
          </div>
        </div>
      )}
    </>
  )
}

export default Aeropuertos
