import { $ } from './selectors.mjs'
import * as novato from './novato.mjs'

export class Notify {

    static notifies = []

    static async notice(message, type = 'info', duration = 6000) {
        let notify = {
            "id": `notify-${novato.UUID()}`,
            "type": ['info', 'sucess', 'warning', 'error'].includes(type) ? type : 'info',
            "message": message,
            "height": 60
        }
        let notifyCard = document.createElement("div")
        notifyCard.classList.add("notify-card")
        notifyCard.classList.add(`notify-card-${notify.type}`)
        notifyCard.setAttribute("id", notify.id)
        notifyCard.innerHTML = `<div><span class="material-symbols-outlined">${{info: 'info', warning: 'warning', sucess: 'check', error: 'close'}[notify.type]}</span></div>`
        notifyCard.innerHTML += `<p>${notify.message}</p>`
        notifyCard.style.top = this.notifies.reduce((first, second) => first + second.height + 20, 0) + 20 + "px"
        notifyCard.addEventListener("click", () => {
            this.closeNotify(notify.id)
        })
        document.body.appendChild(notifyCard)
        notify.height = $(notify.id).offsetHeight
        this.notifies.push(notify)
        new Promise((resolve) =>
        setTimeout(() => {
            this.closeNotify(notify.id)
            resolve()
        }, duration))
        return notify
    }
    
    static async closeNotify(id) {
        let notifyCard = null
        if(this.notifies.length > 0) {
            this.notifies.forEach(function(notify) {
                if(id == notify.id) {
                    if($(notify.id) != null) {
                        notifyCard = $(id)
                        notifyCard.classList.add("notify-card-out")
                    }
                }
            })
            await new Promise((resolve) =>
            setTimeout(() => {
                resolve()
            }, 1000))
            if(notifyCard != null) {
                notifyCard.remove()
            }
            this.notifies = this.notifies.filter(fil => fil.id != id)
            this.resizeNotifies()
        }
    }
    
    static async resizeNotifies() {
        if (this.notifies.length > 0) {
            this.notifies.forEach((notify, index) => {
                const notifyElement = document.getElementById(notify.id);
                if (notifyElement != null) {
                    const newTop = this.notifies.slice(0, index).reduce((acc, prevNotify) => acc + prevNotify.height + 20, 20);
                    notifyElement.style.top = `${newTop}px`;
                }
            });
        }
    }

}

export default Notify