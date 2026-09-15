import {createRouter, createWebHistory} from 'vue-router'
import CustomerInfo from '@/views/customer/CustomerInfo.vue'
import CustomerHome from '@/views/customer/CustomerHome.vue'
import Login from '@/views/Login.vue'
import CustomerMenu from '@/views/customer/CustomerMenu.vue'
import StoreCreate from '@/views/owner/StoreCreate.vue'
import OwnerMenu from '@/views/owner/OwnerMenu.vue'
import UserCreate from '@/views/UserCreate.vue'
import OwnerHome from '@/views/owner/OwnerHome.vue'
import CreateMenu from '@/views/menu/CreateMenu.vue'
import StoreInfo from '@/views/store/StoreInfo.vue'
import Cart from '@/views/customer/Cart.vue'
import OrderList from '@/views/owner/OrderList.vue'
import CustomerOrderStatus from '@/views/customer/CustomerOrderStatus.vue'

const router = createRouter({
    history : createWebHistory(),
    routes:[
        {path:"/",component: Login},
        {path: "/api/customer/info", component: CustomerInfo},
        // 사용자 메뉴 화면
        {path: "/api/store/:storeId", component:CustomerMenu},

        // 로그인 화면
        {path : "/api/login", component:Login},

        // 사용자 홈화면
        {path: "/customer/home",component: CustomerHome},

        // 사용자 주문 현황
        {path: "/customer/order/status", component: CustomerOrderStatus},

        // 오너 홈화면
        {path: "/owner/home", component: OwnerHome},

        // 사용자 회원가입
        {path : "/api/create", component: UserCreate},

        // 점주 가게 추가
        {path: "/store", component: StoreCreate},
        
        // 점주 메뉴 설정
        {path: "/menu/setting", component:OwnerMenu},

        // 메뉴 추가
        {path: "/menu/create", component: CreateMenu},

        // 해당 매장 정보
        {path: "/store/:storeId", component: StoreInfo},

        // 장바구니
        {path: "/cart", component: Cart},

        // 주문 목록
        {path: "/order/list", component: OrderList},
    ]
})

// src/router/index.js (기존 코드의 맨 아래 export default router 바로 위에 추가하세요)

router.beforeEach((to, from, next) => {
    // 1. localStorage에 저장된 토큰이 있는지 확인
    const token = localStorage.getItem('accessToken');
    
    // 2. 로그인이 필요 없는 페이지(로그인 화면, 회원가입 등) 주소 목록
    const publicPages = ['/', '/customer/home','/owner/home', '/api/create']; 

    // 3. 지금 가려는 곳이 로그인이 필요한 페이지인지 확인
    const authRequired = !publicPages.includes(to.path);

    // 4. 로그인이 필요한 페이지인데 토큰이 없다면?
    if (authRequired && !token) {
        alert("로그인 후 이용하실 수 있습니다.");
        return next('/'); // 로그인 페이지로 강제 추방!
    }

    // 5. 토큰이 있거나 로그인이 필요 없는 페이지면 정상적으로 화면 띄워줌
    next();
});


export default router