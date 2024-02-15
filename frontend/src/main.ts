import { createApp } from 'vue'
import CoreuiVue from '@coreui/vue';
import '@coreui/coreui/dist/css/coreui.min.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import App from './App.vue'


const app = createApp(App)
app.mount('#app')
app.use(CoreuiVue)