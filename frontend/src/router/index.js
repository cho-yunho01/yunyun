import {createRouter, createWebHistory} from 'vue-router'
import CustomerCreate from '@/components/customer/CustomerCreate.vue'
import CustomerLogin from '@/components/customer/CustomerLogin.vue'
import Test from '@/components/test.vue'

const router = createRouter({
    history : createWebHistory(),
    routes:[
        {path: "/api/customer/create", component: CustomerCreate},
        {path: "/api/customer/login", component: CustomerLogin},
        {path: "/api/customer/id", component: Test},
    ]
})

export default router