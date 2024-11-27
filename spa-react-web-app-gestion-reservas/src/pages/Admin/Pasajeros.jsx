import { useEffect, useState } from "react"
import "./Pasajeros.css"
import Pasajero from "../../scripts/Pasajero.mjs"
import Table from "../../components/Table/Table.jsx"
import Button from "../../components/Button/Button.jsx"
import Notify from "../../scripts/utils/notify.mjs"

function Pasajeros () {
  const [pasajeros, setPasajeros] = useState([])
  const [showModal, setShowModal] = useState(false)
  const [idPasajero, setIdPasajero] = useState(null)

  const loadPasajeros = async () => {
    const pasajeros = await Pasajero.getAll()
    setPasajeros(pasajeros)
    setIdPasajero(null)
    setShowModal(false)
  }

  const toggleModal = () => {
    if(showModal) {
      setIdPasajero(null)
    }
    setShowModal(!showModal)
  }

  const deletePasajero = async (id) => {
    const deleted = await Pasajero.delete(id)
    if(deleted) {
      Notify.notice('Se ha eliminado el pasajero con éxito')
      loadPasajeros()
    }
    else {
      Notify.notice('No se ha podido eliminar el pasajero', 'error')
    }
  }

  useEffect(() => {
    loadPasajeros()
  }, [])

  return (
    <>
      <header className="pw-header">
        <div className="pw-header-path">
          <h2 className="pw-header-path-name">Pasajeros</h2>
        </div>
      </header>
      <div className="pw-content">
        <div className="pw-content-pasajeros">
          <div className="pw-content-pasajeros-table">
            {pasajeros && (
              <Table
                dataObjects={pasajeros.map(pasajero => ({...pasajero, 
                acciones: <div className="pw-layout-table-actions">
                  <Button variant="dimed" color="danger" size="xs" icon onClick={async () => await deletePasajero(pasajero.id)}><span className="material-symbols-outlined">delete</span></Button>
                </div>
                }))}
                id="pasajerosTable"
                translate={{
                  id: "#",
                  nombre: 'Nombre',
                  apellido: 'Apellido',
                  fechaNacimiento: 'Cumpleaños',
                  telefono: 'Teléfono',
                  email: 'Correo Electrónico'
                }}
              ></Table>
            )}
          </div>
        </div>
      </div>
      {showModal && (
        <div className="pw-layout-modal-overlay" onClick={toggleModal}>
          <div className="pw-layout-modal" onClick={(e) => e.stopPropagation()}>
          </div>
        </div>
      )}
    </>
  )
}

export default Pasajeros
