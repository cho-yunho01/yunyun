<script setup>
import Update from '@/components/Update.vue';
import axios from 'axios';
import {ref} from 'vue';


const customerRequest = ref(null);
const customerId = ref(0);

const responseCustomer = (customer) => {
    customerRequest.value = customer;
}

const requestAPI = async () =>{
    // 현재 로그인한 상태의 사용자 ID를 넘겨야함
    const url = `/api/customer/update/${customerId.value}`;

    await axios.post(url, customerRequest);
}

</script>

<template>
    <Update @customerRequest="responseCustomer" />
    <button @click="requestAPI">수정</button>
</template>