<script setup>
import {ref} from 'vue'
import api from '@/api/axios';



const id = ref(0);

const responseAPI = async () => {
    const url = "/customer/id"
    const response = await api.get(url);
    id.value = response.data;
}

</script>

<template>
    현재 ID의 값
    {{ id }}
    <button @click="responseAPI">전송</button>
</template>