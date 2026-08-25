<script setup>
import api from "@/api/axios"
import {reactive} from 'vue'

const customerLoginRequest = reactive({
    userId : "",
    password : ""
})

const requestAPI = async () => {
    const url = "/customer/login";

    const response = await api.post(url, customerLoginRequest);

    console.log("response 전체:", response);
    console.log("response.data:", response.data);

    const accessToken = response.data;

    localStorage.setItem("accessToken", accessToken);

    console.log("저장된 accessToken:", localStorage.getItem("accessToken"));
};



</script>

<template>
    <div>
        아이디 :
        <input type = "text" v-model="customerLoginRequest.userId" /><br/>
        비밀번호 :
        <input type = "password" v-model="customerLoginRequest.password" /> <br /> 
        <button @click="requestAPI">전송</button>
    </div>
</template>