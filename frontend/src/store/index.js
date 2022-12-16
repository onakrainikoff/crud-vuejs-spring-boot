import { createStore } from 'vuex'

export default createStore({
    state: {
        alerts: []
      },
    mutations: {
        UPDATE_ALERTS(state, alerts) {
            state.alerts = alerts
            for (const alert of state.alerts) {
                if(alert.timer === undefined) {
                    alert.timer = setTimeout(()=> {
                        this.dispatch('removeAlert', alert.id)
                    }, alert.timeout * 1000)
                }
            }
        }
    },
    actions: {
        addSuccessAlert(context, {message, timeout=5}){
            const alerts = context.state.alerts
            alerts.push({message, timeout, id: Date.now(), variant: "success"})
            context.commit('UPDATE_ALERTS', alerts)
        },

        addErrorAlert(context, {message, timeout=5}){
            const alerts = context.state.alerts
            alerts.push({message, timeout, id: Date.now(), variant: "danger"})
            context.commit('UPDATE_ALERTS', alerts)
        },

        removeAlert(context, id) {
            const alerts = context.state.alerts
            for (let i = 0; i < alerts.length; i++) {
                const alert = alerts[i];
                if(alert.id === id) {
                    if(alert.timer) {
                        clearTimeout(alert.timer)
                    }
                    alerts.splice(i, 1);
                }
            }
            context.commit('UPDATE_ALERTS', alerts)
        }
    },

})