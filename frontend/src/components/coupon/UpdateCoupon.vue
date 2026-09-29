<script setup>
import api from '@/api/axios.js';
import Action from '../common/Action.vue';
import { reactive, ref } from 'vue';

const action = ref(false);

const props = defineProps({
    couponId : Number
})

const emit = defineEmits([
    'close'
])

const couponRequest = reactive({
    couponId: props.couponId,
    couponName : null,
    discountPrice : null
})

const request = async () => {
    const url = '/coupon/update'
    await api.patch(url, couponRequest);
}

const result = (value) => {
    if(value){
        request();
        couponRequest.couponName = null;
        couponRequest.discountPrice = null;
        emit('close');
    }
    else{
        action.value = false;
        emit('close');
    }
}
</script>

<template>
    <h1>쿠폰 수정하기</h1>
    <div>
        수정할 쿠폰 이름을 입력하세요.
        <input type = "text" v-model="couponRequest.couponName" />
    </div>
    <div>
        수정 할 가격을 입력하세요.
        <input type = "text" v-model="couponRequest.discountPrice" />
    </div>
    
    <button @click="action = true">수정</button>
    <div v-if="action">
        <Action message="수정하시겠습니까?"
        result-true="수정"
        result-false="취소"
        @result="result"/>
    </div>

</template>