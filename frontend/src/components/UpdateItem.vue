<script setup>
import { ref } from 'vue';
import api from '@/api/axios';


const emit = defineEmits([
    'quantity'
])

const size = ref(null);

const quantity = () => {
    emit('quantity', size.value)
}

// 나중가서 더 확장
</script>

<template>
    바꾸실 수량을 입력하세요.
    <input type="text" v-model="size">    
    <button @click="quantity">확인</button>
</template>