<script setup>
import { ref } from 'vue';


const emit = defineEmits([
    'click'
])


const click = (click) => {
    emit('click',click)
}
</script>

<template>
    <input type = "checkbox" @change="click($event.target.checked)"/>
</template>





