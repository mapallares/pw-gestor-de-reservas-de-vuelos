import Notify from "./notify.mjs"

class Requester {
    static async method(url, request) {
        try {
            const response = await fetch(url, request)

            if (!response.ok) {
                const errorMessage = `Error ${response.status}: ${response.statusText}`
                Notify.notice(errorMessage, 'error')
                return null
            }

            const contentType = response.headers.get('Content-Type') || ''
            if (contentType.includes('application/json')) {
                return await response.json()
            }

            if (response.status === 204 || !contentType) {
                return true
            }

            return await response.text()
        } catch (error) {
            Notify.notice(error.message || 'Ah ocurrido un error de comunicación con el servidor', 'error')
            return undefined
        }
    }

    static async get(url) {
        const request = {
            method: 'GET',
            headers: this.getHeaders()
        }
        return await this.method(url, request)
    }

    static async post(url, body) {
        const request = {
            method: 'POST',
            headers: this.getHeaders(),
            body: JSON.stringify(body)
        }
        return await this.method(url, request)
    }

    static async put(url, body) {
        const request = {
            method: 'PUT',
            headers: this.getHeaders(),
            body: JSON.stringify(body)
        }
        return await this.method(url, request)
    }

    static async delete(url) {
        const request = {
            method: 'DELETE',
            headers: this.getHeaders()
        }
        return await this.method(url, request)
    }

    static getHeaders() {
        return {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        }
    }
}

export default Requester
