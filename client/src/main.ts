import 'vuetify/styles' // Global CSS has to be imported

import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import store from './store/store'
import router from './router'
import mitt from 'mitt'
import setup from './services/interceptor'

const emitter = mitt();
loadFonts()

const app = createApp(App)
app.config.globalProperties.emitter = emitter

setup(store);

app.use(store)
app.use(router)
app.use(vuetify)
app.mount('#app')

