<script setup>
import { computed, onMounted, ref } from 'vue';
import api from '@/api/axios';

const cartList = ref([]);

const requestCart = async() => {
    const url = "/cart/getCart"
    const response = await api.get(url);
    cartList.value = response.data;

}

const totalPrice = computed(() => {
    return cartList.value.reduce(
        (total, cart) => total + cart.price * cart.quantity, 0
    )
})

onMounted(() => {
    requestCart();
})
</script>

<template>
    <div v-for="cart in cartList" :key="cart.cartItemId">
        <div class = name>
            메뉴 이름 : 
            {{ cart.name }}
        </div>
        <div class = price>
            가격 : 
            {{ cart.price * cart.quantity }}
        </div>
        <div class = "quantity">
            수량 : 
            {{ cart.quantity }}
        </div>
        <br/>
    </div>
    <div class = "total">
        <br/>
        <h2>총 가격</h2>
        {{ totalPrice }}원
    </div>
</template>