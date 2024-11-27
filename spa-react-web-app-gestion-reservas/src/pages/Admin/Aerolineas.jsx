import { useEffect, useState } from "react"
import "./Aerolineas.css"
import Aerolinea from "../../scripts/Aerolinea.mjs"
import Table from "../../components/Table/Table.jsx"
import Button from "../../components/Button/Button.jsx"
import Validator from "../../scripts/utils/validator.mjs"
import Notify from "../../scripts/utils/notify.mjs"
import { $ } from "../../scripts/utils/selectors.mjs"

function Aerolineas () {
  const [aerolineas, setAerolineas] = useState([])
  const [showModal, setShowModal] = useState(false)
  const [idAerolinea, setIdAerolinea] = useState(null)
  const [form, setForm] = useState({
    nombre: '',
    codigoAerolinea: '',
    paisOrigen: ''
  })

  const loadAerolineas = async () => {
    const aerolineas = await Aerolinea.getAll()
    setAerolineas(aerolineas)
    setForm({
      nombre: '',
      codigoAerolinea: '',
      paisOrigen: ''
    })
    setIdAerolinea(null)
    setShowModal(false)
  }

  const toggleModal = () => {
    if(showModal) {
      setForm({
        nombre: '',
        codigoAerolinea: '',
        paisOrigen: ''
      })
      setIdAerolinea(null)
    }
    setShowModal(!showModal)
  }

  const handleChange = (event) => {
    setForm({
      ...form,
      [event.target.name]: event.target.value
    })
  }

  const saveAerolinea = async (event) => {
    event.preventDefault()

    const isValid = Validator.valid($('aerolineasNombre'), () => {
      const nombre = { nombre: form.nombre }
      Validator.required(nombre)
      Validator.length(nombre, 2, 100)
    })
    && Validator.valid($('aerolineasCodigo'), () => {
      const codigoAerolinea = { codigoAerolinea: form.codigoAerolinea }
      Validator.required(codigoAerolinea)
      Validator.length(codigoAerolinea, 3, 100)
    })
    && Validator.valid($('aerolineasPaisOrigen'), () => {
      const paisOrigen = { paisOrigen: form.paisOrigen }
      Validator.required(paisOrigen)
      Validator.length(paisOrigen, 2, 100)
    })

    if(!isValid) return false

    if(idAerolinea) {
      const aerolinea = await Aerolinea.put(idAerolinea, form)
      if(aerolinea) {
        Notify.notice('Se ha modificado la aerolinea con éxito')
        loadAerolineas()
      }
      else {
        Notify.notice('No se ha podido modificar la aerolinea', 'error')
      }
    }
    else {
      const aerolinea = await Aerolinea.post(form)
      if(aerolinea) {
        Notify.notice('Se ha creado la aerolinea con éxito')
        loadAerolineas()
      }
      else {
        Notify.notice('No se ha podido crear la aerolinea', 'error')
      }
    }

  }

  const deleteAerolinea = async (id) => {
    const deleted = await Aerolinea.delete(id)
    if(deleted) {
      Notify.notice('Se ha eliminado la aerolinea con éxito')
      loadAerolineas()
    }
    else {
      Notify.notice('No se ha podido eliminar la aerolinea', 'error')
    }
  }

  useEffect(() => {
    loadAerolineas()
  }, [])

  return (
    <>
      <header className="pw-header">
        <div className="pw-header-path">
          <h2 className="pw-header-path-name">Aerolíneas</h2>
        </div>
      </header>
      <div className="pw-content">
        <div className="pw-content-aerolineas">
          <Button size="m" onClick={toggleModal}>
            <span className="material-symbols-outlined">add</span> Nueva Aerolínea
          </Button>
          <div className="pw-content-aerolineas-table">
            {aerolineas && (
              <Table
                dataObjects={aerolineas.map(aerolinea => ({...aerolinea,
                codigoAerolinea: <span className="pw-aerolineas-table-row-codigo">{aerolinea.codigoAerolinea}</span>, 
                paisOrigen: <strong>{aerolinea.paisOrigen}</strong>,
                acciones: <div className="pw-layout-table-actions">
                  <Button variant="dimed" color="success" size="xs" icon onClick={async () => {
                    setForm({
                      ...aerolinea,
                      id: undefined
                    })
                    setIdAerolinea(aerolinea.id)
                    toggleModal()
                  }}><span className="material-symbols-outlined">edit</span></Button>
                  <Button variant="dimed" color="danger" size="xs" icon onClick={async () => await deleteAerolinea(aerolinea.id)}><span className="material-symbols-outlined">delete</span></Button>
                </div>
                }))}
                id="aerolineasTable"
                translate={{
                  id: "#",
                  nombre: "Nombre",
                  codigoAerolinea: "Código",
                  paisOrigen: "País",
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
            <form className="pw-aerolineas-form">
              <center><h2>{idAerolinea ? "Editar" : "Nueva"} Aerolínea</h2></center>
              <br></br>
              <div className="pw-forms-inputs">
                <div className="pw-forms-input-group">
                  <input className="pw-forms-input" onChange={handleChange} id="aerolineasNombre" name="nombre" type="text" autoComplete="off" minLength={2} maxLength={100} required="required" value={form.nombre}/>
                  <label className="pw-forms-label" htmlFor="aerolineasNombre">Nombre</label>
                </div>
                <div className="pw-forms-input-group">
                  <input className="pw-forms-input" onChange={handleChange} id="aerolineasCodigo" name="codigoAerolinea" type="text" autoComplete="off" minLength={3} maxLength={100} required="required" value={form.codigoAerolinea}/>
                  <label className="pw-forms-label" htmlFor="aerolineasCodigo">Código</label>
                </div>
                <div className="pw-forms-input-group">
                  <input className="pw-forms-input" onChange={handleChange} id="aerolineasPaisOrigen" name="paisOrigen" type="text" autoComplete="off" minLength={2} maxLength={100} required="required" value={form.paisOrigen}/>
                  <label className="pw-forms-label" htmlFor="aerolineasPaisOrigen">Pais de Origen</label>
                </div>
              </div>
              <br/>
              <Button size="m" width="full" onClick={saveAerolinea}>Guardar</Button>
            </form>
          </div>
        </div>
      )}
    </>
  )
}

export default Aerolineas
