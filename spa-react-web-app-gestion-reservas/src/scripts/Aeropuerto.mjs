import API from './config/api.config.mjs'
import Requester from './utils/requester.mjs'

class Aeropuerto extends Requester {

    static async getAll() {
        return await super.get(API.AEROPUERTOS.BASE)
    }

    static async get(idAeropuerto) {
        return await super.get(`${API.AEROPUERTOS.BASE}/${idAeropuerto}`)
    }
    
    static async post(aeropuerto) {
        return await super.post(API.AEROPUERTOS.BASE, aeropuerto)
    }

    static async put(idAeropuerto, aeropuerto) {
        return await super.put(`${API.AEROPUERTOS.BASE}/${idAeropuerto}`, aeropuerto)
    }

    static async delete(idAeropuerto) {
        return await super.delete(`${API.AEROPUERTOS.BASE}/${idAeropuerto}`)
    }
    
}

export default Aeropuerto