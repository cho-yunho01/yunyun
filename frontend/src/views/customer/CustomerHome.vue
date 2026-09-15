<script setup>
import AppHeader from '@/components/common/AppHeader.vue';
import { onMounted, ref,computed,onUnmounted } from 'vue';
import { useRouter } from 'vue-router'
import api from '@/api/axios';
import LogOut from '@/components/common/LogOut.vue';
    import {Client} from '@stomp/stompjs';
    import SockJS from 'sockjs-client';

const storeName = ref("");
const router = useRouter();
const storeList = ref([]);
const id = localStorage.getItem("id");


let stompClient = null;

// const seartchStore = async () => {
//     const url = `/find/stores/${storeName.value}`
//     const response = await api.get(url);
//     storeList.value = response.data
// }

const searchStore = async () => {
    const url = "/find/stores"
    const response = await api.get(url);
    storeList.value = response.data
}

onMounted(() => {
    searchStore();
    stompClient = new Client({
        webSocketFactory: () => {
            return new SockJS('http://localhost:8080/ws/order')
        },
        onConnect: () => {
            console.log('WebSocket 연결 성공');

            stompClient.subscribe(`/topic/customer/order/${id}`, (message) => {
                console.log("주문 답변!");
                const order = JSON.parse(message.body)
                if(order.status === 'ACCEPT'){
                    acceptOrder(order.orderId)
                }
                else if(order.status === 'CANCEL'){
                    cancelOrder(order.orderId)
                }
            })
        }
    })
    stompClient.activate();
})

const acceptOrder = (orderId) => {
    alert(orderId+"번 주문 수락!");
}

const cancelOrder = (orderId) => {
    alert(orderId+"번 주문 취소!");
}

onUnmounted(() => {
    if(stompClient)
    stompClient.deactivate();
})

const stores = computed(() => {
    if(!storeName.value.trim()){
        return [];
    }

    return storeList.value.filter((s) => {
        return s.name.includes(storeName.value)
    })
})

const goStore = (storeId) => {
    router.push(`/store/${storeId}`)
}

</script>

<template>
    <LogOut />
    <AppHeader />
    <div class = "home">

        <div class="search">
            음식점 검색 창
            <input type = "text" v-model="storeName">
            <button @click="seartchStore">검색</button>
        </div>
        <div v-if="stores.length > 0">
            <div v-for="store in stores" :key="store.storeId"
            @click="goStore(store.storeId)">
                {{ store.name }}
            </div>
        </div>

        <div class = "event">
            이벤트 배너
        </div>

        <div class = "info">
            <div class = "info-image">
                사용자 이미지 정보
            </div>
            <router-link to="/customer/info">사용자 정보</router-link>
        </div>

        <div class = "category" v-for="menu in menus" :key = "menu.id">
            
        </div>

        <div class="delivery-status">
            <router-link to="/customer/order/status">
                배달 현황
            </router-link>
        </div>

        <div class = "cart">
            <router-link to ="/cart">
                장바구니
            </router-link>
        </div>


    </div>
</template>
