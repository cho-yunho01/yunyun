import {createRouter, createWebHistory} from 'vue-router'
import CustomerCreate from '@/components/CustomerCreate.vue'

const router = createRouter({
    history : createWebHistory(),
    routes:[
        {path: "/api/customer/create", component: CustomerCreate},
        
    ]
})

export default router