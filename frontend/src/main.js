import Vue, { createApp} from '@vue/compat';
import router from './router'
import App from './App.vue'
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';



Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)
const app = createApp(App)
app.use(router)
router.isReady().then(()=> app.mount('#app'))