<script setup>
defineProps({
    customer: Object
})
</script>

<template>
    <div v-if="customer">
        사용자 이름
        {{ customer.name }} <br/>
        사용자 ID
        {{ customer.userId }} <br />
        사용자 잔액
        {{ customer.balance }} <br />
        <div v-for="role in customer.roles" :key = "role">
            <div>{{ role }}</div>
        </div>
    </div>
</template>