import {createRouter, createWebHistory} from 'vue-router'
import CustomerCreate from '@/views/customer/CustomerCreate.vue'
import CustomerInfo from '@/views/customer/CustomerInfo.vue'
import CustomerLogin from '@/views/customer/CustomerLogin.vue'

const router = createRouter({
    history : createWebHistory(),
    routes:[
        {path: "/api/customer/create", component: CustomerCreate},
        {path: "/api/customer/login", component: CustomerLogin},
        {path: "/api/customer/info", component: CustomerInfo},
    ]
})

export default router