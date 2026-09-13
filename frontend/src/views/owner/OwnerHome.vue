<script setup>
    import api from '@/api/axios';
    import AppHeader from '@/components/common/AppHeader.vue';
    import LogOut from '@/components/common/LogOut.vue';
import OrderInfo from '@/components/OrderInfo.vue';
    import {Client} from '@stomp/stompjs';
    import SockJS from 'sockjs-client';
    import { onMounted, onUnmounted } from 'vue';
    import { ref } from 'vue';

    const id = localStorage.getItem("id");
    const url = `/order/${id}`
    const orderResponse = ref();

    let stompClient = null;

    onMounted( () => {
        stompClient = new Client({
            webSocketFactory: () => {
                return new SockJS('http://localhost:8080/ws/order')
            },
            onConnect: () => {
                console.log('WebSocket 연결 성공');

                stompClient.subscribe(`/topic/order/${id}`, (message) => {
                    console.log("새 주문 도착!");
                    const orderId = JSON.parse(message.body)

                    getNotifications(orderId);
                })
            }
        

        })

        stompClient.activate();
    })

    onUnmounted( () => {
        if(stompClient)
        stompClient.deactivate();
    })
    
    const getNotifications = async (orderId) => {
        const url = `/order/get/${orderId}`;
        const response = await api.get(url);
        orderResponse.value = response.data;
    }

</script>

<template>
    <LogOut />
    <AppHeader />
    <div class = "createStore">
        <router-link to="/store">
            가게 추가하기
        </router-link>
    </div>

    <div class = "menu-setting">
        <router-link to="/menu/setting">
            메뉴 설정
        </router-link>
    </div>

    <div class = "store-setting">
        가게 설정
    </div>

    <div class = "total-sales">
        총 판매
    </div>

    <div class = "order-list">
        <router-link :to = "url">
            주문 상태
        </router-link>
    </div>

    <div v-if="orderResponse">
        <OrderInfo :order="orderResponse"/>
    </div>
</template>