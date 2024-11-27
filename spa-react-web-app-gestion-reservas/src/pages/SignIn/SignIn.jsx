import './SignIn.css'
import Logo from '../../components/Logo/Logo.jsx'
import Button from '../../components/Button/Button.jsx'
import { useEffect, useState } from 'react'
import { $ } from '../../scripts/utils/selectors.mjs'
import Validator from '../../scripts/utils/validator.mjs'
import Auth from '../../scripts/Auth.mjs'
import Notify from '../../scripts/utils/notify.mjs'
import { useNavigate } from 'react-router-dom'

function SignIn() {
  const navigate = useNavigate()
  const [form, setForm] = useState({
    username: '',
    password: ''
  })

  useEffect(() => {
    Auth.session(session => {
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

    const isValid = Validator.valid($('signInUsername'), () => {
      const username = { username: form.username }
      Validator.required(username)
      Validator.length(username, 2, 100)
    })
    && Validator.valid($('signInPassword'), () => {
      const password = { password: form.password }
      Validator.required(password)
      Validator.length(password, 8, 100)
    })

    if(!isValid) return false

    const session = await Auth.signIn(form)
    if(session) navigate('/')
  }

  const toggleShowPassword = (event) => {
    event.target.type = event.target.type === 'password' ? 'text' : 'password'
  }

  return (
    <div className="pw-signin">
      <div className="pw-signin-content">
        <Logo size="m" color="black"/>
        <form className="pw-signin-form">
          <h2>SignIn <span className="h1-weight-300">| Next Bookings</span></h2>
          <br/>
          <div className="pw-forms-inputs">
            <div className="pw-forms-input-group">
              <input className="pw-forms-input" onChange={handleChange} id="signInUsername" name="username" type="text" autoComplete="off" minLength={2} maxLength={100} required="required"/>
              <label className="pw-forms-label" htmlFor="signInUsername">Username</label>
            </div>
            <div className="pw-forms-input-group">
              <input className="pw-forms-input" onChange={handleChange} id="signInPassword" onDoubleClick={toggleShowPassword} name="password" type="password" autoComplete="off" minLength={8} maxLength={500} required="required"/>
              <label className="pw-forms-label" htmlFor="signInPassword">Password</label>
            </div>
          </div>
          <br/>
          <Button size="m" width="full" color="black" onClick={submit}>Iniciar Sesión</Button>
        </form>
        <div className="pw-signin-register">¿No tienes una cuenta? <a href="/signup">Registrarse</a></div>
        </div>
        <div className="pw-signin-banner">
          <img src="https://www.muyinteresante.com/wp-content/uploads/sites/5/2023/08/07/64d0c0b2583a6.jpeg"/>
        </div>
    </div>
  )
}

export default SignIn
