<script setup>
    const props = defineProps({
        order : Object
    })


</script>

<template>
    <div class = "userId">
        {{ order.userId }}
    </div>
    <div v-for="item in order.orderItemResponseList" :key="item.menuName">
        메뉴이름 : {{ item.menuName }} <br/>
        수량 : {{ item.quantity }} <br/>
        가격 : {{ item.price }} <br/>
    </div>

    <div>
        총 가격: {{ order.totalPrice }}
    </div>
    
</template>