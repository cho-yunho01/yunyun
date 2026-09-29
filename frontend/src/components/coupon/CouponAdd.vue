<script setup>
import api from '@/api/axios';
import Action from '../common/Action.vue';
import { reactive, ref } from 'vue';

const action = ref(false);

const couponRequest = reactive({
    couponName : null,
    discountPrice : null
})

const request = async () => {
    const url = '/coupon/add'
    await api.post(url, couponRequest);
}

const result = (value) => {
    if(value){
        request();
        couponRequest.couponName = null;
        couponRequest.discountPrice = null;
    }
    else{
        action.value = false;
    }
}
</script>

<template>
    <h1>쿠폰 생성하기</h1>
    <div>
        생성할 쿠폰 이름을 입력하세요.
        <input type = "text" v-model="couponRequest.couponName" />
    </div>
    <div>
        할인 할 가격을 입력하세요.
        <input type = "text" v-model="couponRequest.discountPrice" />
    </div>
    
    <button @click="action = true">생성</button>
    <div v-if="action">
        <Action message="생성하시겠습니까?"
        result-true="생성"
        result-false="취소"
        @result="result"/>
    </div>

</template>