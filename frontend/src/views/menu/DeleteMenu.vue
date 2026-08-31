<script setup>
import Confirm from '@/components/common/Confirm.vue';
import axios from 'axios';
import {ref,computed} from 'vue';
import api from '@/api/axios';


const props = defineProps({
    menu : Object
})

const emit = defineEmits([
    'isTrue'
])

const isTrue = ref();

const menuId = ref();

console.log(props);

const message = computed(() => {
    return `${props.menu.menuName}을 삭제하시겠습니까?`
})



const accept = async (value) => {
    if(value){
        menuId.value = props.menu.menuId;
        const url = `/menu/delete/${menuId.value}`
        await api.delete(url);
        isTrue.value = false;
        emit('isTrue', false, "delete");
    }
    else{
        isTrue.value = false;
        emit('isTrue', false, "delete")
    }
}
</script>

<template>
    <Confirm :message="message" @result="accept" />
</template>