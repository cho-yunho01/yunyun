<script setup>
import { onMounted,ref } from 'vue';
import api from '@/api/axios';
import OrderInfo from '@/components/OrderInfo.vue';

const orderList = ref([]);

const orderRequest = async () => {
    const url = "/order/customer/info"
    const response = await api.get(url);
    orderList.value = response.data;

    console.log("OrderList의 값");
    console.log(response.data)
}

onMounted(() => {
    orderRequest();
})

const cancel = () => {
    // 주문 취소
}
</script>

<template>
    <div v-for="order in orderList">
        <OrderInfo :order="order" 
        button="CUSTOMER" 
        @cancel="cancel"/>
    </div>
</template>