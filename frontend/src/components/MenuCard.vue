<script setup>
import { ref } from 'vue';

defineProps({
    menu : Object
})

</script>


<template>
    <div class = "menu" v-if = "menu">
        <div class = "menu-picture">

        </div>

        <div class = "menu-name">
            {{ menu.menuName }}
        </div>
        <div class = "price">
            {{ menu.price }}
        </div>

        <div class = "introduction">
            {{ menu.introduction }}
        </div>

    </div>
</template>