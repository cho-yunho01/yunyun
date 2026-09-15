<script setup>
    const props = defineProps({
        order : Object,
        button: {
            type : String,
            default : "PENDING"
        }
    })

    const emit = defineEmits([
        'accept',
        'cancel'
    ])

    const accept = (orderId) => {
        emit('accept',orderId)
    }

    const cancel = (orderId) => {
        emit('cancel',orderId)
    }

</script>

<template>
    <div class = "userId" v-if="button">
        사용자 ID : {{ order.userId }}
    </div>
    <div v-for="item in order.orderItemResponseList" :key="item.menuName">
        메뉴이름 : {{ item.menuName }} <br/>
        수량 : {{ item.quantity }} <br/>
        가격 : {{ item.price }} <br/>
    </div>

    <div>
        주문 상태 : {{ order.orderStatus }} <br/>
        총 가격: {{ order.totalPrice }}
    </div>

    <div v-if="props.button === 'PENDING'">
        <button @click="accept(props.order.orderId)">
            수락
        </button>
        <button @click="cancel(props.order.orderId)">
            거절
        </button>
    </div>

    <div v-else-if="props.button === 'CUSTOMER'">
        <button @click="cancel(props.order.orderId)">
            주문 취소
        </button>
    </div>
</template>