<script setup>
import api from '@/api/axios';
import Confirm from '@/components/common/Confirm.vue';
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

const confirmModal = () => {
    confirm = true;
}

const confirm = ref(false);

const message = "등록하시겠습니까?";

const reset = () => {
    storeCreateRequest.address = "",
    storeCreateRequest.storeName = "",
    storeCreateRequest.storeNumber = ""
}

const result = (value) =>{
    if(value){
        requestAPI();
        reset();
        confirm.value = false
    }
    else{
        confirm.value = false
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

            <button @click="confirmModal">확인</button>

            <div v-if="confirm">
                <Confirm :message="message" 
                @result="result($event)" />
            </div>
        </div>

    </div>

</template>