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
    <div v-if="customer">
        사용자 이름
        {{ customer.name }} <br />

        사용자 ID
        <input type="password" v-model="customerRequest.password" />
        <br />

        사용자 잔액
        <input type="number" v-model="customerRequest.balance" />
        <br />

        <div v-for="role in customer.roles" :key="role">
            <div>{{ role }}</div>
        </div>
    </div>

    <div>
        <button @click="send">수정</button>
    </div>
</template>