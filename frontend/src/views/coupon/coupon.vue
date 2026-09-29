<script setup>
import CouponAdd from '@/components/coupon/CouponAdd.vue';
import { ref, onMounted, watch } from 'vue';
import api from '@/api/axios';
import CouponList from '@/components/coupon/CouponList.vue';
import UpdateCoupon from '@/components/coupon/UpdateCoupon.vue';

const couponId = ref();
const couponList = ref([]);
const action = ref(false);

const couponResponse = async () => {
    const url = "/coupon/list"
    const response = await api.get(url);
    couponList.value = response.data;
}

const selectCoupon = (id) => {
    couponId.value = couponId.value === id ? null : id;
}

const update = ref(false);

const close = () => {
    update.value = false;
}

onMounted(() => {
    couponResponse();
})

watch(couponId, () => {
    if(couponId.value){
        action.value = true;
    }
    else{
        action.value = false;
    }
})

</script>

<template>
    <CouponAdd />
    <h1>현재 쿠폰 리스트</h1>
    <div v-for="coupon in couponList">
        <CouponList 
        :coupon = "coupon"
        :is-selected="couponId === coupon.couponId"
        @select="selectCoupon" />
    </div>
    <div v-if="action">
        <button @click="update = true">
            수정
        </button>
    </div>
    <div v-if="update">
        <UpdateCoupon 
        :coupon-id="couponId"
        @close="close"/>
    </div>
</template>