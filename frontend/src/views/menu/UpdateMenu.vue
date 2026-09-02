<script setup>
import Action from '@/components/common/Action.vue';
import axios from 'axios';
import {ref, reactive, computed} from 'vue'
import api from '@/api/axios';

const menuId = ref(null);

const props = defineProps({
    menu : Object
})

const MenuRequest = reactive({
    name : null,
    price : null,
    introduction : null,
})

const isTrue = ref(false);

const message = computed(()=> {
    return `${props.menu.menuName}을 수정하시겠습니까?`
})

const resultTrue = "확인"
const resultFalse = "취소"

const emit = defineEmits([
    'isTrue'
])

const accept = async (value) => {
    if(value){
        menuId.value = props.menu.menuId;

        const url = `/menu/update/${menuId.value}`
        await api.put(url, MenuRequest);
        isTrue.value = false;
        emit('isTrue', false, "update");
    }
    else{
        isTrue.value = false;
        emit('isTrue', false, "update")
    }
}

const cancle = () => {
    emit('isTrue', false, "update")
}

</script>

<template>
    <div>
        <div class = "name">
            <h2>{{ props.menu.menuName }}</h2>
        </div>
        상품명을 입력하세요.
        <input type = "text" v-model="MenuRequest.name"> <br />
        가격을 입력하세요.
        <input type = "number" v-model="MenuRequest.price"><br/>
        메뉴 설명을 입력하세요. 
        <input type = "text" v-model="MenuRequest.introduction"><br/>
        
        <button @click="isTrue = true">전송</button>
        <button @click="cancle">취소</button>

        <div v-if="isTrue">
            <Action :message="message" 
            :resultTrue = "resultTrue"
            :resultFalse="resultFalse"
            @result="accept"/>
        </div>
    </div>

</template>