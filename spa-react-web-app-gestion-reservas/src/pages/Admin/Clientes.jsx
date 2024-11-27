import { useEffect, useState } from "react"
import "./Clientes.css"
import Cliente from "../../scripts/Cliente.mjs"
import Table from "../../components/Table/Table.jsx"
import Button from "../../components/Button/Button.jsx"
import Validator from "../../scripts/utils/validator.mjs"
import Notify from "../../scripts/utils/notify.mjs"
import { $ } from "../../scripts/utils/selectors.mjs"
import Auth from "../../scripts/Auth.mjs"

function Clientes () {
  const [clientes, setClientes] = useState([])
  const [showModal, setShowModal] = useState(false)
  const [idCliente, setIdCliente] = useState(null)
  const [form, setForm] = useState({
    nombre: '',
    apellido: '',
    fechaNacimiento: '',
    direccion: '',
    telefono: '',
    username: '',
    password: '',
    email: ''
  })

  const loadClientes = async () => {
    const clientes = await Cliente.getAll()
    setClientes(clientes)
    setForm({
      nombre: '',
      apellido: '',
      fechaNacimiento: '',
      direccion: '',
      telefono: '',
      username: '',
      password: '',
      email: ''
    })
    setIdCliente(null)
    setShowModal(false)
  }

  const toggleModal = () => {
    if(showModal) {
      setForm({
        nombre: '',
        apellido: '',
        fechaNacimiento: '',
        direccion: '',
        telefono: '',
        username: '',
        password: '',
        email: ''
      })
      setIdCliente(null)
    }
    setShowModal(!showModal)
  }

  const handleChange = (event) => {
    setForm({
      ...form,
      [event.target.name]: event.target.value
    })
  }

  const saveCliente = async (event) => {
    event.preventDefault()

    const isValid = Validator.valid($('clientesNombre'), () => {
      const nombre = { nombre: form.nombre }
      Validator.required(nombre)
      Validator.length(nombre, 2, 100)
    })
    && Validator.valid($('clientesApellido'), () => {
      const apellido = { apellido: form.apellido }
      Validator.required(apellido)
      Validator.length(apellido, 2, 100)
    })
    && Validator.valid($('clientesUsername'), () => {
      const username = { username: form.username }
      Validator.required(username)
      Validator.length(username, 2, 100)
    })
    && Validator.valid($('clientesFechaNacimiento'), () => {
      const fechaNacimiento = { fechaNacimiento: form.fechaNacimiento }
      Validator.required(fechaNacimiento)
      Validator.isDate(fechaNacimiento)
    })
    && Validator.valid($('clientesDireccion'), () => {
      const direccion = { direccion: form.direccion }
      Validator.required(direccion)
    })
    && Validator.valid($('clientesTelefono'), () => {
      const telefono = { telefono: form.telefono }
      Validator.required(telefono)
      Validator.length(telefono, 5, 100)
    })
    && Validator.valid($('clientesEmail'), () => {
      const email = { email: form.email }
      Validator.required(email)
      Validator.email(email)
    })
    && Validator.valid($('clientesPassword'), () => {
      const password = { password: form.password }
      Validator.required(password)
      Validator.length(password, 8, 500)
    })

    if(!isValid) return false

    const cliente = await Auth.signUp(form)

    if(cliente) {
      Notify.notice('Se ha creado el cliente con éxito')
      loadClientes()
    }
    else {
      Notify.notice('No se ha podido crear el cliente', 'error')
    }

  }

  const toggleShowPassword = (event) => {
    event.target.type = event.target.type === 'password' ? 'text' : 'password'
  }

  useEffect(() => {
    loadClientes()
  }, [])

  return (
    <>
      <header className="pw-header">
        <div className="pw-header-path">
          <h2 className="pw-header-path-name">Clientes</h2>
        </div>
      </header>
      <div className="pw-content">
        <div className="pw-content-clientes">
          <Button size="m" onClick={toggleModal}>
            <span className="material-symbols-outlined">add</span> Nuevo
          </Button>
          <div className="pw-content-clientes-table">
            {clientes && (
              <Table
                dataObjects={clientes.map(cliente => ({...cliente,
                edad: <strong><i>{new Date().getFullYear() - new Date(cliente.fechaNacimiento).getFullYear()}</i></strong>,
                tipo: new Date().getFullYear() - new Date(cliente.fechaNacimiento).getFullYear() > 18 ? <span className="pw-content-clientes-table-row-adulto">Adulto</span> : <span className="pw-content-clientes-table-row-ninno">Niño</span>,
                fechaNacimiento: <span className="pw-content-clientes-table-row-fechaNacimiento"><u>{cliente.fechaNacimiento}</u></span>,
                telefono: <span className="pw-content-clientes-table-row-telefono">{cliente.telefono}</span>,
                email: <span className="pw-content-clientes-table-row-email">{cliente.email}</span>,
                password: 'Segura',
                username: <strong>{cliente.username}</strong>,
                acciones: <div className="pw-layout-table-actions">
                  <Button variant="dimed" color="success" size="xs" icon onClick={async () => {
                    setForm({
                      ...cliente,
                      id: undefined
                    })
                    setIdCliente(cliente.id)
                    toggleModal()
                  }}><span className="material-symbols-outlined">visibility</span></Button>
                </div>
                }))}
                id="clientesTable"
                translate={{
                  id: "#",
                  edad: 'Edad',
                  tipo: 'Tipo',
                  nombre: 'Nombre',
                  apellido: 'Apellido',
                  fechaNacimiento: 'Cumpleaños',
                  direccion: 'Dirección',
                  telefono: 'Teléfono',
                  username: 'Nombre de usario',
                  password: 'Contraseña',
                  email: 'Correo Electrónico',
                  acciones: 'Acciones'
                }}
              ></Table>
            )}
          </div>
        </div>
      </div>
      {showModal && (
        <div className="pw-layout-modal-overlay" onClick={toggleModal}>
          <div className="pw-layout-modal" onClick={(e) => e.stopPropagation()}>
            <form className="pw-clientes-form">
              <center><h2>{idCliente ? "Ver" : "Nuevo"} Cliente</h2></center>
              <br></br>
              <div className="pw-forms-inputs">
                <div className="pw-forms-input-group-double">
                  <div className="pw-forms-input-group">
                    <input className="pw-forms-input" onChange={handleChange} id="clientesNombre" minLength={2} maxLength={100} name="nombre" type="text" autoComplete="off" required="required" value={form.nombre}/>
                    <label className="pw-forms-label" htmlFor="clientesNombre">Nombre</label>
                  </div>
                  <div className="pw-forms-input-group">
                    <input className="pw-forms-input" onChange={handleChange} id="clientesApellido" minLength={2} maxLength={100} name="apellido" type="text" autoComplete="off" required="required" value={form.apellido}/>
                    <label className="pw-forms-label" htmlFor="clientesApellido">Apellido</label>
                  </div>
                </div>
                <div className="pw-forms-input-group">
                  <input className="pw-forms-input" onChange={handleChange} id="clientesUsername" minLength={2} maxLength={100} name="username" type="text" autoComplete="off" required="required" value={form.username}/>
                  <label className="pw-forms-label" htmlFor="clientesUsername">Username</label>
                </div>
                <div className="pw-forms-input-group-triple">
                  <div className="pw-forms-input-group">
                    <input className="pw-forms-input" onChange={handleChange} id="clientesFechaNacimiento" name="fechaNacimiento" type="date" autoComplete="off" required="required" value={form.fechaNacimiento}/>
                    <label className="pw-forms-label" htmlFor="clientesNombre">Cumpleaños</label>
                  </div>
                  <div className="pw-forms-input-group">
                    <input className="pw-forms-input" onChange={handleChange} id="clientesDireccion" name="direccion" type="text" autoComplete="off" required="required" value={form.direccion}/>
                    <label className="pw-forms-label" htmlFor="clientesDireccion">Dirección</label>
                  </div>
                  <div className="pw-forms-input-group">
                    <input className="pw-forms-input" onChange={handleChange} id="clientesTelefono" minLength={5} maxLength={100} name="telefono" type="text" autoComplete="off" required="required" value={form.telefono}/>
                    <label className="pw-forms-label" htmlFor="clientesTelefono">Teléfono</label>
                  </div>
                </div>
                <div className="pw-forms-input-group">
                  <input className="pw-forms-input" onChange={handleChange} id="clientesEmail" name="email" type="email" autoComplete="off" required="required" value={form.email}/>
                  <label className="pw-forms-label" htmlFor="clientesEmail">Email</label>
                </div>
                {idCliente ? '' :<div className="pw-forms-input-group">
                  <input className="pw-forms-input" onChange={handleChange} id="clientesPassword" onDoubleClick={toggleShowPassword} name="password" type="password" autoComplete="off" minLength={8} maxLength={500} required="required"/>
                  <label className="pw-forms-label" htmlFor="clientesPassword">Password</label>
                </div>}
              </div>
              {idCliente ? '' : <><br/><Button size="m" width="full" onClick={saveCliente}>Guardar</Button></>}
            </form>
          </div>
        </div>
      )}
    </>
  )
}

export default Clientes
