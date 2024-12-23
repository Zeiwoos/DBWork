import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// 创建 Vue 实例
const app = createApp(App)

// 使用 Vue Router 和 Element Plus
app.use(router)
app.use(ElementPlus)

// 挂载应用
app.mount('#app')
