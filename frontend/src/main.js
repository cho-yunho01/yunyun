
import { createApp } from 'vue'
import router from './router/index.js'
import App from './App.vue'

window.global = window;

createApp(App)
.use(router)
.mount('#app')
