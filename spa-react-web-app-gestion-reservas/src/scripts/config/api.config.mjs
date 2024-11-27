class EndpointGroup {
    constructor(base, paths) {
        this.BASE = base
        this.ENDPOINTS = {}
        Object.keys(paths).forEach(path => {
            this.ENDPOINTS[path] = this.BASE + `/${paths[path]}`
        })
    }
}

const URL_BASE = 'http://localhost:8080'

export const API = {
    AUTH: new EndpointGroup(`${URL_BASE}/api/v1/auth`, {
        SIGNIN: 'signin',
        SIGNUP: 'signup'
    }),
    CLIENTES: new EndpointGroup(`${URL_BASE}/api/v1/clientes`, {}),
    AEROPUERTOS: new EndpointGroup(`${URL_BASE}/api/v1/aeropuertos`, {}),
    AEROLINEAS: new EndpointGroup(`${URL_BASE}/api/v1/aerolineas`, {}),
    VUELOS: new EndpointGroup(`${URL_BASE}/api/v1/vuelos`, {}),
    RESERVAS: new EndpointGroup(`${URL_BASE}/api/v1/reservas`, {}),
    PASAJEROS: new EndpointGroup(`${URL_BASE}/api/v1/pasajeros`, {})
}

export default API