<script setup>
import MenuCard from './MenuCard.vue';
import { ref } from 'vue';

defineProps({
    menus : Array
})

const emit = defineEmits([
    'select'
])

const select = (menu, check) => {
    emit('select', {
        menu: menu,
        checked : check
    })
}


</script>

<template>
    <div v-for="menu in menus">
        <MenuCard :menu="menus" />
        <input type = "checkbox" @change="select(menu, $event.target.checked)"
    </div>

</template>