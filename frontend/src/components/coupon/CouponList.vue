
<script setup>
import { ref } from 'vue';

const props = defineProps({
    coupon: Object,
    isSelected : Boolean
});

const emit = defineEmits(['select']);


const selectCoupon = () => {
    emit('select', props.coupon.couponId);
};
</script>

<template>
    <div
        class="coupon"
        :class="{ selected: isSelected }"
        @click="selectCoupon"
    >
        쿠폰 이름 : {{ props.coupon.couponName }}
        <br>
        쿠폰 할인 가격 : {{ props.coupon.discountPrice }}
    </div>
</template>

<style scoped>
.coupon {
    background-color: white;
    padding: 16px;
    border: 1px solid #ddd;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.2s;
}

.coupon.selected {
    background-color: #ddd;
}
</style>