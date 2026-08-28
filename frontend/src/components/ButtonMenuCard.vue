<script setup>
import MenuCard from './MenuCard.vue';

defineProps({
    menus : Array,
    acceptMessage: String,
    cancelMessage: String
})


const emit = defineEmits([
    'accept',
    'cancel'
])

const acceptButton= (menu) => {
    emit('accept', menu)
}

const cancelButton= (menu) => {
    emit('cancel', menu)
}

</script>

<template>
    <div v-for="menu in menus" :key="menu.menuId">
        <menu-card :menu="menu" />

        <button @click="acceptButton(menu)">
            {{ acceptMessage }}
        </button>

        <button @click="cancelButton(menu)">
            {{ cancelMessage }}
        </button>
    </div>
</template>