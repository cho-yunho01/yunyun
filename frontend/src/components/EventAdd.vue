<script setup>
import api from '@/api/axios';
import { reactive,ref, onMounted } from 'vue';
import CouponList from './coupon/CouponList.vue';
import Action from './common/Action.vue';

const eventRequest = reactive({
    name : null,
    description : null,
    maxCount : null,
    couponId : null,
    startAt : null,
    endAt : null
})

const action = ref(false);

const couponList = ref([
    // {
    //     couponId: 1,
    //     couponName: "신규 가입 쿠폰",
    //     discountPrice: 3000
    // },
    // {
    //     couponId: 2,
    //     couponName: "첫 주문 할인 쿠폰",
    //     discountPrice: 5000
    // },
    // {
    //     couponId: 3,
    //     couponName: "단골 고객 쿠폰",
    //     discountPrice: 2000
    // },
    // {
    //     couponId: 4,
    //     couponName: "특별 할인 쿠폰",
    //     discountPrice: 10000
    // }
]);

const result = (value) => {
    if(value){
        request();
        action.value = false;
    }
    else{
        action.value = false;
    }
}

const request = async () => {
    const url = "/event/add";
    await api.post(url, eventRequest)
}

const couponResponse = async () => {
    const url = "/coupon/list"
    const response = await api.get(url);
    couponList.value = response.data;
}

onMounted(() => {
    couponResponse();
})

const selectCoupon = (id) => {
    eventRequest.couponId = eventRequest.couponId === id ? null : id
}

</script>

<template>
    <div name = 'modal'>
        <h1>이벤트 생성</h1>
        <div>
            이벤트 이름을 입력하세요.
            <input type = "text" v-model="eventRequest.name"/>
        </div>
        <div>
            이벤트 설명을 입력하세요.
            <input type = "text" v-model="eventRequest.description"/>
        </div>
        <div>
            적용시킬 쿠폰을 클릭하세요.
            <div v-for="coupon in couponList" :key="coupon.couponId">
                <CouponList :coupon="coupon" :is-selected="eventRequest.couponId === coupon.couponId" @select="selectCoupon"/>
            </div>
        </div>
        <div>
            쿠폰 수량을 입력하세요.
            <input type = "number" v-model="eventRequest.maxCount"/>
        </div>
        <div>
            적용 시작일을 입력하세요.
            <input type = "datetime-local" v-model="eventRequest.startAt"/>
        </div>
        <div>
            종료일을 입력하세요.
            <input type = "datetime-local" v-model="eventRequest.endAt"/>
        </div>
    </div>

    <button @click="action = true">전송</button>
    <div v-if="action">
        <Action message="생성하시겠습니까?" result-true="생성" result-false="취소"
        @result="result" />
    </div>
</template>