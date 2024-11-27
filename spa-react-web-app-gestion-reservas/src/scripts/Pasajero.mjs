import API from './config/api.config.mjs'
import Requester from './utils/requester.mjs'

class Pasajero extends Requester {

    static async getAll() {
        return await super.get(API.PASAJEROS.BASE)
    }

    static async get(idPasajero) {
        return await super.get(`${API.PASAJEROS.BASE}/${idPasajero}`)
    }
    
    static async post(pasajero) {
        return await super.post(API.PASAJEROS.BASE, pasajero)
    }

    static async put(idPasajero, pasajero) {
        return await super.put(`${API.PASAJEROS.BASE}/${idPasajero}`, pasajero)
    }

    static async delete(idPasajero) {
        return await super.delete(`${API.PASAJEROS.BASE}/${idPasajero}`)
    }
    
}

export default Pasajero