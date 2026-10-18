<script setup>
defineProps({
    customer: Object
})
</script>

<template>
    <div v-if="customer" class = "customer">
        <div class = "list">
            <div class = "field">
            사용자 이름
            </div>
            <div class = "field">
                사용자 ID
            </div>
            <div class = "field">
                사용자 잔액
            </div>
            <div class = "field">
                권한
            </div>
        </div>
        <div class = "list">
            <div class = "customer-info">
                {{ customer.name }}
            </div>
            <div class = "customer-info">
                {{ customer.userId }}
            </div>
            <div class = "customer-info">
                {{ customer.balance }}
            </div>

            <div v-for="role in customer.roles" :key = "role">
                <div class = "customer-info">{{ role }}</div>
            </div>
        </div>

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
</style>