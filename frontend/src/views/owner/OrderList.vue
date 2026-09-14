<script setup>
import api from '@/api/axios';
import OrderInfo from '@/components/OrderInfo.vue';
import { onMounted, onUnmounted, ref } from 'vue';
import OrderNotification from '@/components/OrderNotification.vue';
import {Client} from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const orderList = ref([]);

const id = localStorage.getItem('id');

const newOrderResponse = ref([]);

const orderRequest = async () => {
    const url = `/order/info/list`;
    const response = await api.get(url);
    orderList.value = response.data
    console.log(orderList.value);
    console.log('response의 값 : '+response.data)
}

let stompClient = null;

onMounted(() => {
    orderRequest();

        stompClient = new Client({
            webSocketFactory: () => {
                return new SockJS('http://localhost:8080/ws/order')
            },
            onConnect: () => {
                console.log('WebSocket 연결 성공');

                stompClient.subscribe(`/topic/owner/order/${id}`, (message) => {
                    console.log("새 주문 도착!");
                    const orderId = JSON.parse(message.body)

                    getNotifications(orderId);
                })
            }

        })

        stompClient.activate();
})

onUnmounted(() => {
    if(stompClient)
    stompClient.deactivate();
})

    const getNotifications = async (orderId) => {
        const url = `/order/get/${orderId}`;
        const response = await api.get(url);
        orderList.value.push(response.data);
        newOrderResponse.value.push(response.data);
    }

    const acceptOrder = async(orderId) => {
        const url = `/order/${orderId}/accept`
        await api.patch(url);
        orderList.value = orderList.value.filter(
            order => order.orderId !== orderId
        )

        newOrderResponse.value = newOrderResponse.value.filter(
            order => order.orderId !== orderId
        )
    }

    
    const cancelOrder = async(orderId) => {
        const url = `/order/${orderId}/cancel`
        await api.delete(url)
        orderList.value = orderList.value.filter(
            order => order.orderId !== orderId
        )


        newOrderResponse.value = newOrderResponse.value.filter(
            order => order.orderId !== orderId
        )
    }
</script>

<template>
    <div v-for="orders in orderList" :key="orders.orderId">
        주문 ID : {{ orders.orderId }}
        <OrderInfo :order="orders" 
        @accept="acceptOrder"
        @cancel="cancelOrder"/>
    </div>

    <div v-if="newOrderResponse.length > 0">
            <OrderNotification 
            v-for="order in newOrderResponse"
            :key = "order.orderId"
            :order="order"
            @accept="acceptOrder"
            @cancel="cancelOrder"/>
    </div>
</template>