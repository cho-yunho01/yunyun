<script setup>
import axios from 'axios';
import {ref, reactive} from 'vue'

const MenuRequest = reactive({
    name : "",
    price : 0,
    introduction : "",
    storeId : 0
})

const requestAPI = async () => {
    const url = "/api/menu/create"
    await axios.post(url, MenuRequest);
}

</script>

<template>
    <div>
        상품명을 입력하세요.
        <input type = "text" v-model="MenuRequest.name"> <br />
        가격을 입력하세요.
        <input type = "number" v-model="MenuRequest.price"><br/>
        메뉴 설명을 입력하세요. 
        <input type = "text" v-memo="MenuRequest.introduction"><br/>
        <button @click="requestAPI">전송</button>
    </div>

</template>