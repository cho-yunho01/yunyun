<script setup>
import {ref, onMounted} from 'vue'
import api from '@/api/axios';
import CustomerDetail from '@/components/CustomerDetail.vue';


const customer = ref(null);

const responseAPI = async () =>{
    const url = "/customer/info"
    const response = await api.get(url);
    customer.value = response.data;
}

onMounted( () =>{
    responseAPI()
})
</script>

<template>
    <h1>사용자 정보</h1>
    <CustomerDetail :customer = "customer" />
</template>