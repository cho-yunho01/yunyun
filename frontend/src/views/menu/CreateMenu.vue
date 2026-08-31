<script setup>
import axios from 'axios';
import {ref, reactive, computed} from 'vue'
import Confirm from '@/components/common/Confirm.vue';
import {useRouter} from 'vue-router'
import api from '@/api/axios';

const router = useRouter();

const isConfirm = ref(false);

const MenuRequest = reactive({
    name : "",
    price : null,
    introduction : "",
})

const message = computed(() => {
    return `${MenuRequest.name}을 추가하시겠습니까?`
})


const requestAPI = async (value) => {
    const url = "/menu/create"
    if(value){
        await api.post(url, MenuRequest);
        router.push("/owner/home");
        isConfirm = false;
    }
    else{
        isConfirm.value = false;
    }
}

</script>

<template>
    <div>
        상품명을 입력하세요.
        <input type = "text" v-model="MenuRequest.name"> <br />
        가격을 입력하세요.
        <input type = "number" v-model="MenuRequest.price"><br/>
        메뉴 설명을 입력하세요. 
        <input type = "text" v-model="MenuRequest.introduction"><br/>
        
        <button @click="isConfirm = true">
            전송
        </button>

        <div v-if="isConfirm">
            <Confirm :message="message" @result="requestAPI"/>
        </div>
    </div>

</template>