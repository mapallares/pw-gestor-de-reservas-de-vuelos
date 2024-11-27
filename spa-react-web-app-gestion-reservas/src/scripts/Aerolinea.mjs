import API from './config/api.config.mjs'
import Requester from './utils/requester.mjs'

class Aerolinea extends Requester {

    static async getAll() {
        return await super.get(API.AEROLINEAS.BASE)
    }

    static async get(idAerolinea) {
        return await super.get(`${API.AEROLINEAS.BASE}/${idAerolinea}`)
    }
    
    static async post(aerolinea) {
        return await super.post(API.AEROLINEAS.BASE, aerolinea)
    }

    static async put(idAerolinea, aerolinea) {
        return await super.put(`${API.AEROLINEAS.BASE}/${idAerolinea}`, aerolinea)
    }

    static async delete(idAerolinea) {
        return await super.delete(`${API.AEROLINEAS.BASE}/${idAerolinea}`)
    }
    
}

export default Aerolinea