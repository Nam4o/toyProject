import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
// import axiosInstance from '@/utils/axiosInstance.js';

const app = createApp(App)

app.use(createPinia())
app.use(router)
// app.config.globalProperties.$axios = axiosInstance;
app.mount('#app')
