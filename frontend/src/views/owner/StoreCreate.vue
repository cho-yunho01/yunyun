<script setup>
import api from '@/api/axios';
import Action from '@/components/common/Action.vue';
import { ref,reactive } from 'vue';

const storeCreateRequest = reactive({
    storeName : "",
    address : "",
    storeNumber : "",
})

const requestAPI = async () => {
    const url = "/stores"
    await api.post(url, storeCreateRequest)
}

const ActionModal = () => {
    isTrue = true;
}

const isTrue = ref(false);

const message = "등록하시겠습니까?";
const resultTrue = "확인"
const resultFalse = "취소"

const reset = () => {
    storeCreateRequest.address = "",
    storeCreateRequest.storeName = "",
    storeCreateRequest.storeNumber = ""
}

const result = (value) =>{
    if(value){
        requestAPI();
        reset();
        isTrue.value = false
    }
    else{
        isTrue.value = false
    }
}


</script>

<template>
    <div class = "storeCard">
        <div class = "sub">가게 등록</div>

        <div class = "info">
            가게 이름
            <input type = "text" v-model="storeCreateRequest.storeName"/>
            <br/>

            주소
            <input type = "text" v-model="storeCreateRequest.address"/>
            <br/>

            전화번호
            <input type = "text" v-model="storeCreateRequest.storeNumber"/>
            <br/>

            <button @click="isTrue = true">확인</button>

            <div v-if="isTrue">
                <Action :message="message"
                :resultTrue = "resultTrue"
                :resultFalse = "resultFalse" 
                @result="result($event)" />
            </div>
        </div>

    </div>

</template>