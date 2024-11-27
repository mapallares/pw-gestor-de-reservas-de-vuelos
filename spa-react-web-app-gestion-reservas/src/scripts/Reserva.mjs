import API from './config/api.config.mjs'
import Requester from './utils/requester.mjs'

class Reserva extends Requester {

    static async getAll() {
        return await super.get(API.RESERVAS.BASE)
    }

    static async get(idReserva) {
        return await super.get(`${API.RESERVAS.BASE}/${idReserva}`)
    }
    
    static async post(reserva) {
        return await super.post(API.RESERVAS.BASE, reserva)
    }

    static async put(idReserva, reserva) {
        return await super.put(`${API.RESERVAS.BASE}/${idReserva}`, reserva)
    }

    static async delete(idReserva) {
        return await super.delete(`${API.RESERVAS.BASE}/${idReserva}`)
    }
    
}

export default Reserva