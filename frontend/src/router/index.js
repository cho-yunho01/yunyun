import {createRouter, createWebHistory} from 'vue-router'
import CustomerCreate from '@/views/customer/CustomerCreate.vue'
import CustomerInfo from '@/views/customer/CustomerInfo.vue'
import CustomerLogin from '@/views/customer/CustomerLogin.vue'
import CustomerHome from '@/views/customer/CustomerHome.vue'
import OwnerHome from '@/views/menu/OwnerHome.vue'
import Login from '@/views/Login.vue'
import CustomerMenu from '@/views/customer/CustomerMenu.vue'
import StoreCreate from '@/views/owner/StoreCreate.vue'
import OwnerMenu from '@/views/owner/OwnerMenu.vue'

const router = createRouter({
    history : createWebHistory(),
    routes:[
        {path:"/",component: Login},
        {path: "/api/customer/home", component: CustomerHome},
        {path: "/api/owner/home", component: OwnerHome},
        {path: "/api/customer/create", component: CustomerCreate},
        {path: "/api/customer/login", component: CustomerLogin},
        {path: "/api/customer/info", component: CustomerInfo},
        // 사용자 메뉴 화면
        {path: "/api/store/:storeId", component:CustomerMenu},

        // 점주 가게 추가
        {path: "/api/store", component: StoreCreate},
        
        // 점주 메뉴 설정
        {path: "/api/menu/setting", component:OwnerMenu}
    ]
})

export default router