<script setup>
import { ref, watch } from 'vue';
import MenuCard from './MenuCard.vue';

const count = ref(0);

const plus = () => {
    return count.value++;
}

const minus = () => {
    return count.value--;
}

const props = defineProps({
    menu : Object
})

const emit = defineEmits([
    "send"
])

const cart = () => {
    emit("send", {
        menu : props.menu,
        quantity : count.value
    })
}


</script>

<template>
    <MenuCard :menu="menu"/>
    <div @click="plus">
        +
    </div>
    <div>
        {{ count }}
    </div>
    <div @click="minus">
        -
    </div>
    <button @click="cart">담기</button>    
</template>