import API from './config/api.config.mjs'
import Requester from './utils/requester.mjs'

class Cliente extends Requester {

    static async getAll() {
        return await super.get(API.CLIENTES.BASE)
    }

    static async get(idCliente) {
        return await super.get(`${API.CLIENTES.BASE}/${idCliente}`)
    }
    
    static async post(cliente) {
        return await super.post(API.CLIENTES.BASE, cliente)
    }

    static async put(idCliente, cliente) {
        return await super.put(`${API.CLIENTES.BASE}/${idCliente}`, cliente)
    }

    static async delete(idCliente) {
        return await super.delete(`${API.CLIENTES.BASE}/${idCliente}`)
    }
    
}

export default Cliente