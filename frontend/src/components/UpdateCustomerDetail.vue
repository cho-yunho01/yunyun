<script setup>
import { reactive } from 'vue';

const props = defineProps({
    customer: Object
})

const emit = defineEmits([
    'update'
])

const customerRequest = reactive({
    password: null,
    balance: null
})

const send = () => {
    emit('update',customerRequest)
}
</script>

<template>
    <div v-if="customer" class = "customer">
        <div class = "list">
            <div class = "field">
                사용자 이름
            </div>
            <div class="field">
                사용자 비밀번호
            </div>
            <div class="field">
                사용자 잔액
            </div>
            <div class = "field">
                권한
            </div>
        </div>
        <div class = "list">
            <div class="field">
                {{ customer.name }}
            </div>
            <div class="field">
                <input type="password" v-model="customerRequest.password" class = "input"/>
            </div>
            <div class="field">
                <input type="number" v-model="customerRequest.balance" class = "input"/>
            </div>
             <div class = "field" v-for="role in customer.roles" :key="role">
                <div>{{ role }}</div>
            </div>
            

        </div>
    </div>

    <div>
        <button class = "update-button" @click="send">수정</button>
    </div>
</template>

<style scpoed>
.customer{
    display : flex;
}

.list{
    flex-direction: column;
}
.field{
    margin : 24px;
}
.customer-info{
    border : 2px solid black;
    margin : 20px;
}
.update-button{
    position: absolute;
    right : 10px;
    bottom : 10px;
}
.input {
    width : 100px;
}
</style>