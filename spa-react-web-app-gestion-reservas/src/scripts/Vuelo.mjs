import API from './config/api.config.mjs'
import Requester from './utils/requester.mjs'

class Vuelo extends Requester {

    static async getAll() {
        return await super.get(API.VUELOS.BASE)
    }

    static async get(idVuelo) {
        return await super.get(`${API.VUELOS.BASE}/${idVuelo}`)
    }
    
    static async post(vuelo) {
        return await super.post(API.VUELOS.BASE, vuelo)
    }

    static async put(idVuelo, vuelo) {
        return await super.put(`${API.VUELOS.BASE}/${idVuelo}`, vuelo)
    }

    static async delete(idVuelo) {
        return await super.delete(`${API.VUELOS.BASE}/${idVuelo}`)
    }
    
}

export default Vuelo