import './SignUp.css'
import Logo from '../../components/Logo/Logo.jsx'
import Button from '../../components/Button/Button.jsx'
import { useEffect, useState } from 'react'
import Auth from '../../scripts/Auth.mjs'
import { useNavigate } from 'react-router-dom'
import Validator from '../../scripts/utils/validator.mjs'
import { $ } from '../../scripts/utils/selectors.mjs'
import Notify from '../../scripts/utils/notify.mjs'

function SignUp() {
  const navigate = useNavigate()
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

  useEffect(() => {
    Auth.session(session => {
      console.log(session)
      if(session) {
        navigate('/')
      }
    })
  }, [])

  const handleChange = (event) => {
    setForm({
      ...form,
      [event.target.name]: event.target.value
    })
  }

  const submit = async (event) => {
    event.preventDefault()

    const isValid = Validator.valid($('signUpNombre'), () => {
      const nombre = { nombre: form.nombre }
      Validator.required(nombre)
      Validator.length(nombre, 2, 100)
    })
    && Validator.valid($('signUpApellido'), () => {
      const apellido = { apellido: form.apellido }
      Validator.required(apellido)
      Validator.length(apellido, 2, 100)
    })
    && Validator.valid($('signUpUsername'), () => {
      const username = { username: form.username }
      Validator.required(username)
      Validator.length(username, 2, 100)
    })
    && Validator.valid($('signUpFechaNacimiento'), () => {
      const fechaNacimiento = { fechaNacimiento: form.fechaNacimiento }
      Validator.required(fechaNacimiento)
      Validator.isDate(fechaNacimiento)
    })
    && Validator.valid($('signUpDireccion'), () => {
      const direccion = { direccion: form.direccion }
      Validator.required(direccion)
    })
    && Validator.valid($('signUpTelefono'), () => {
      const telefono = { telefono: form.telefono }
      Validator.required(telefono)
      Validator.length(telefono, 5, 100)
    })
    && Validator.valid($('signUpEmail'), () => {
      const email = { email: form.email }
      Validator.required(email)
      Validator.email(email)
    })
    && Validator.valid($('signUpPassword'), () => {
      const password = { password: form.password }
      Validator.required(password)
      Validator.length(password, 8, 500)
    })

    if(!isValid) return false

    const registered = await Auth.signUp(form)
    if(registered) {
      Notify.notice('Se ha registrado correctamente, puede iniciar sesión')
      navigate('/signin')
    }
  }

  const toggleShowPassword = (event) => {
    event.target.type = event.target.type === 'password' ? 'text' : 'password'
  }

  return (
  <div className="pw-signup">
      <div className="pw-signup-banner">
        <video autoPlay loop src="https://cdn.dribbble.com/userupload/16879833/file/original-446b1a1b2f5d72d214e14b36ca3d16ec.mp4"></video>
      </div>
      <div className="pw-signup-content">
        <Logo size="m" color="black"/>
        <form className="pw-signup-form">
          <h2>SignUp <span className="h1-weight-300">| Next Bookings</span></h2>
          <br/>
          <div className="pw-forms-inputs">
            <div className="pw-forms-input-group-double">
              <div className="pw-forms-input-group">
                <input className="pw-forms-input" onChange={handleChange} id="signUpNombre" minLength={2} maxLength={100} name="nombre" type="text" autoComplete="off" required="required"/>
                <label className="pw-forms-label" htmlFor="signUpNombre">Nombre</label>
              </div>
              <div className="pw-forms-input-group">
                <input className="pw-forms-input" onChange={handleChange} id="signUpApellido" minLength={2} maxLength={100} name="apellido" type="text" autoComplete="off" required="required"/>
                <label className="pw-forms-label" htmlFor="signUpApellido">Apellido</label>
              </div>
            </div>
            <div className="pw-forms-input-group">
              <input className="pw-forms-input" onChange={handleChange} id="signUpUsername" minLength={2} maxLength={100} name="username" type="text" autoComplete="off" required="required"/>
              <label className="pw-forms-label" htmlFor="signUpUsername">Username</label>
            </div>
            <div className="pw-forms-input-group-triple">
              <div className="pw-forms-input-group">
                <input className="pw-forms-input" onChange={handleChange} id="signUpFechaNacimiento" name="fechaNacimiento" type="date" autoComplete="off" required="required"/>
                <label className="pw-forms-label" htmlFor="signUpNombre">Cumpleaños</label>
              </div>
              <div className="pw-forms-input-group">
                <input className="pw-forms-input" onChange={handleChange} id="signUpDireccion" name="direccion" type="text" autoComplete="off" required="required"/>
                <label className="pw-forms-label" htmlFor="signUpDireccion">Dirección</label>
              </div>
              <div className="pw-forms-input-group">
                <input className="pw-forms-input" onChange={handleChange} id="signUpTelefono" minLength={5} maxLength={100} name="telefono" type="text" autoComplete="off" required="required"/>
                <label className="pw-forms-label" htmlFor="signUpTelefono">Teléfono</label>
              </div>
            </div>
            <div className="pw-forms-input-group">
              <input className="pw-forms-input" onChange={handleChange} id="signUpEmail" name="email" type="email" autoComplete="off" required="required"/>
              <label className="pw-forms-label" htmlFor="signUpEmail">Email</label>
            </div>
            <div className="pw-forms-input-group">
              <input className="pw-forms-input" onChange={handleChange} id="signUpPassword" onDoubleClick={toggleShowPassword} name="password" type="password" autoComplete="off" minLength={8} maxLength={500} required="required"/>
              <label className="pw-forms-label" htmlFor="signUpPassword">Password</label>
            </div>
          </div>
          <br/>
          <Button size="m" width="full" color="black" onClick={submit}>Registrarse</Button>
        </form>
        <div className="pw-signup-login">¿Ya tienes una cuenta? <a href="/signin">Iniciar Sesión</a></div>
      </div>
    </div>
  )
}

export default SignUp
