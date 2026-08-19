<script setup>
import axios from 'axios';
import {reactive} from 'vue';

const owner = reactive({
    userId : "",
    password : "",
    name : "",
})
const url = "/api/owner/create"
const requestAPI = async () => {
    await axios.post(url,owner);

}

</script>

<template>
    <div>
        사용자 아이디를 입력하세요.
        <input type = "text" v-model="customer.userId" /> <br />
        사용자 비밀번호를 입력하세요.
        <input type = "password" v-model="customer.password" /> <br />
        사용자 이름을 입력하세요.
        <input type = "text" v-model="customer.name" /> <br />
        <button @click="requestAPI">전송</button>
    </div>
</template>