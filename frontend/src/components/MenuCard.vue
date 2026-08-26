<script setup>
import { ref } from 'vue';

defineProps({
    menu : Object
})

const emit = defineEmits([
    'select'
])

const isSelected = ref(false)

const selectMenu = () => {
    emit('select', isSelected.value)
}
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
            {{ menu.introcution }}
        </div>

        <input type = "checkbox" v-model="isSelected" @change="selectMenu" />
    </div>
</template>