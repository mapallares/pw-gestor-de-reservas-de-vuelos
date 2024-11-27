import API from './config/api.config.mjs'
import Notify from './utils/notify.mjs'

class Auth {

    static async signIn(credentials) {
        const { username, password } = credentials
        try {
            const response = await fetch(API.AUTH.ENDPOINTS['SIGNIN'], {
                method: 'POST',
                mode: 'cors',
                headers: {
                    'Content-Type': 'application/json'
                },
                redirect: 'follow',
                body: JSON.stringify({
                    username,
                    password
                })
            })

            if(response.ok) {
                const session = await response.json()
                window.localStorage.setItem('session', JSON.stringify(session))
                window.localStorage.setItem('token', session.token || '/')
                return session
            }
            else {
                Notify.notice('Usuario o constraseña incorrectos', 'error')
                return null
            }
        
        } catch (error) {
            Notify.notice('Ah ocurrido un error de comunicación con el servidor', 'error')
        }
    }

    static async signUp(user) {
        const { nombre, apellido, fechaNacimiento, direccion, telefono, email, username, password } = user
        try {
            const response = await fetch(API.AUTH.ENDPOINTS['SIGNUP'], {
                method: 'POST',
                mode: 'cors',
                headers: {
                    'Content-Type': 'application/json'
                },
                redirect: 'follow',
                body: JSON.stringify({ 
                    nombre, 
                    apellido, 
                    fechaNacimiento, 
                    direccion, 
                    telefono, 
                    email, 
                    username, 
                    password 
                })
            })

            const result = await response.json()

            if(response.ok) {
                return true
            }
            else {
                Notify.notice(JSON.stringify(result.message || result), 'error')
                return false
            }
        
        } catch (error) {
            Notify.notice('Ah ocurrido un error de comunicación con el servidor', 'error')
        }
    }

    static async signOut() {
        try {
            window.localStorage.removeItem('session')
            window.localStorage.removeItem('token')
            window.location.reload()
        }
        catch(error) {
            Notify.notice('Ah ocurrido un error al cerrar sesión', 'error')
        }
    }

    static async session(callback) {
        try {
            const session =  window.localStorage.getItem('session') 
            ? JSON.parse( window.localStorage.getItem('session')) 
            : null
            if(session) {
                const response = await fetch(API.CLIENTES.BASE + `/${session.username}`, {
                    method: 'GET',
                    mode: 'cors',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': session.type + ' ' + session.token
                    },
                    redirect: 'follow'
                })
                const cliente = await response.json()
                if(response.ok) {
                    session.cliente = cliente
                }
                else {
                    session.cliente = null
                    Notify.notice(JSON.stringify(cliente), 'error')
                }
            }
            return callback(session) || session
        } catch (error) {
            this.signOut()
            return callback(undefined) || undefined
        }
    }

}

export default Auth