<script setup>
import api from '@/api/axios';
import OrderInfo from '@/components/OrderInfo.vue';
import { onMounted, onUnmounted, ref } from 'vue';
import OrderNotification from '@/components/OrderNotification.vue';
import {Client} from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const orderList = ref([]);

const pendingOrderList = ref([]);

const newOrderResponse = ref([]);

const id = localStorage.getItem('id');


const orderRequest = async () => {
    const url = `/order/info/list`;
    const response = await api.get(url);
    orderList.value = response.data
    console.log(orderList.value);
    console.log('response의 값 : '+response.data)
}

const pendingOrderRequest = async () => {
    const url = `/order/info/pending/list`;
    const response = await api.get(url);
    pendingOrderList.value = response.data;
    
}
let stompClient = null;

onMounted(() => {
    orderRequest();
    pendingOrderRequest();

        stompClient = new Client({
            webSocketFactory: () => {
                return new SockJS('http://localhost:8080/ws/order')
            },
            onConnect: () => {
                console.log('WebSocket 연결 성공');

                stompClient.subscribe(`/topic/owner/order/${id}`, (message) => {
                    console.log("새 주문 도착!");
                    const orderId = JSON.parse(message.body)
                    orderRequest();
                    pendingOrderRequest();
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
        orderRequest();
        pendingOrderRequest();
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
        orderRequest();
        pendingOrderRequest();
    }

    const close = (orderId) => {
        newOrderResponse.value = newOrderResponse.value.filter(
            order => order.orderId !== orderId
        )
    }
</script>

<template>
    <section class="order-section">
        <h1>확인 안 된 주문들</h1>

        <div v-for="orders in pendingOrderList" :key="orders.orderId" class="order-item">
            주문 ID : {{ orders.orderId }}

            <OrderInfo
                :order="orders"
                @accept="acceptOrder"
                @cancel="cancelOrder"
            />
        </div>
    </section>

    <section class="order-section confirmed">
        <h1>확인이 된 주문들</h1>

        <div v-for="orders in orderList" :key="orders.orderId" class="order-item">
            주문 ID : {{ orders.orderId }}

            <OrderInfo
                :order="orders"
                button="ACCEPTED"
                @accept="acceptOrder"
                @cancel="cancelOrder"
            />
        </div>
    </section>

    <div v-if="newOrderResponse.length > 0">
        <OrderNotification
            v-for="order in newOrderResponse"
            :key="order.orderId"
            :order="order"
            @accept="acceptOrder"
            @cancel="cancelOrder"
            @close="close"
        />
    </div>
</template>

<style scoped>
.order-section {
    margin-bottom: 80px;
}

.confirmed {
    margin-top: 80px;
}

.order-item {
    margin-bottom: 30px;
}
</style>