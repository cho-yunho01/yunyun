<script setup>
import { watch,reactive } from 'vue';

    const emit = defineEmits([
        'customerRequest'
    ])

    const customerRequest = reactive({
        name: "",
        userId: "",
        password: "",
        balance: ""
    });

    watch(customerRequest, (customer)=>{
        emit('customerRequest', customer)
    })
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