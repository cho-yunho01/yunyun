<script setup>
import Action from '@/components/common/Action.vue';
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

const resultTrue = "확인"
const resultFalse = "취소"


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
    <Action :message="message" 
    :resultTrue = "resultTrue"
    :resultFalse="resultFalse"
    @result="accept" />
</template>