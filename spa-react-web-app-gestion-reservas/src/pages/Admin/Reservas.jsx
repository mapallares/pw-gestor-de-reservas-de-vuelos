import { useEffect, useState } from "react"
import "./Reservas.css"
import Reserva from "../../scripts/Reserva.mjs"
import Table from "../../components/Table/Table.jsx"
import Button from "../../components/Button/Button.jsx"
import Notify from "../../scripts/utils/notify.mjs"
import VueloCard from "../../components/VueloCard/VueloCard.jsx"

function Reservas () {
  const [reservas, setReservas] = useState([])
  const [showModal, setShowModal] = useState(false)
  const [idReserva, setIdReserva] = useState(null)

  const loadReservas = async () => {
    const reservas = await Reserva.getAll()
    setReservas(reservas)
    setIdReserva(null)
    setShowModal(false)
  }

  const toggleModal = () => {
    if(showModal) {
      setIdReserva(null)
    }
    setShowModal(!showModal)
  }

  const deleteReserva = async (id) => {
    const deleted = await Reserva.delete(id)
    if(deleted) {
      Notify.notice('Se ha eliminado la reserva con éxito')
      loadReservas()
    }
    else {
      Notify.notice('No se ha podido eliminar la reserva', 'error')
    }
  }

  useEffect(() => {
    loadReservas()
  }, [])

  return (
    <>
      <header className="pw-header">
        <div className="pw-header-path">
          <h2 className="pw-header-path-name">Reservas</h2>
        </div>
      </header>
      <div className="pw-content">
        <div className="pw-content-reservas">
          <div className="pw-content-reservas-table">
            {reservas && (
              <Table
                dataObjects={reservas.map(reserva => ({...reserva, 
                vuelos: <div className="pw-content-reservas-vuelos">{reserva.vuelos.map(vuelo => <VueloCard vuelo={{...vuelo}} key={`vueloReserva_${vuelo.id}`}></VueloCard>)}</div>,
                pasajeros: reserva.pasajeros.map(pasajero => <p key={`pasajeroReservado_${pasajero.id}`}>{pasajero.email}</p>),
                cliente: reserva.cliente.username,
                acciones: <div className="pw-layout-table-actions">
                  <Button variant="dimed" color="danger" size="xs" icon onClick={async () => await deleteReserva(reserva.id)}><span className="material-symbols-outlined">delete</span></Button>
                </div>
                }))}
                id="reservasTable"
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
          </div>
        </div>
      )}
    </>
  )
}

export default Reservas
