<script setup>
import axios from 'axios';
import {reactive, ref} from 'vue';

const customerRequest = reactive({
    name: "",
    userId: "",
    password: "",
    balance: ""
});

const customerId = ref(0);

const requestAPI = async () =>{
    // 현재 로그인한 상태의 사용자 ID를 넘겨야함
    const url = `/api/customer/update/${customerId.value}`;

    await axios.post(url, customerRequest);
}

</script>

<template>
    <div>
        사용자 이름을 넣으세요.
        <input type = "text" v-model="customerRequest.name"/> <br/>
        사용자 ID를 넣으세요.
        <input type = "text" v-model="customerRequest.userId"><br/>
        사용자 PW를 넣으세요.
        <input type = "password" v-model="customerRequest.password"><br />
        사용자 잔여금액을 넣으세요.
        <input type = "number" v-model="customerRequest.balance"><br />
    </div>
</template>