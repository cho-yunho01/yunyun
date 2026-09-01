<script setup>
import MenuCard from './MenuCard.vue';

const props = defineProps({
    menus : Array
})

const emit = defineEmits([
    'select',
])

const select = (menu, check) => {
    emit('select', {
        menu: menu,
        checked : check
    })
}

</script>

<template>
    <div v-for="menu in props.menus">
        <MenuCard :menu="menu" />
        <input type = "checkbox" @change="select(menu, $event.target.checked)">
    </div>

</template>