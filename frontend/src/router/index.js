import {createRouter, createWebHistory} from 'vue-router'
import CustomerCreate from '@/views/customer/CustomerCreate.vue'
import CustomerInfo from '@/views/customer/CustomerInfo.vue'
import CustomerLogin from '@/views/customer/CustomerLogin.vue'
import CustomerHome from '@/views/customer/CustomerHome.vue'
import OwnerHome from '@/views/menu/OwnerHome.vue'
import Login from '@/views/Login.vue'

const router = createRouter({
    history : createWebHistory(),
    routes:[
        {path:"/",component: Login},
        {path: "/api/customer/home", component: CustomerHome},
        {path: "/api/owner/home", component: OwnerHome},
        {path: "/api/customer/create", component: CustomerCreate},
        {path: "/api/customer/login", component: CustomerLogin},
        {path: "/api/customer/info", component: CustomerInfo},
    ]
})

export default router