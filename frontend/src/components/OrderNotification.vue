```vue
<script setup>
const props = defineProps({
    order: Object
})

const emit = defineEmits([
    'accept',
    'cancel',
    'close'
])

const accept = (orderId) => {
    emit('accept', orderId)
}

const cancel = (orderId) => {
    emit('cancel', orderId)
}

const close = (orderId) => {
    emit('close', orderId)
}
</script>

<template>
    <div class="overlay">

        <div class="order-modal">

            <!-- X 버튼 -->
            <button class="close-button" @click="close(order.orderId)">
                X
            </button>

            <h2>새 주문이 들어왔습니다!</h2>
            <h3>주문 ID {{ order.orderId }}</h3>

            <div>
                사용자 ID : {{ order.userId }}
            </div>

            <div
                v-for="item in order.orderItemResponseList"
                :key="item.menuName"
            >
                메뉴이름 : {{ item.menuName }} <br>
                수량 : {{ item.quantity }} <br>
                가격 : {{ item.price }} <br>
            </div>

            <div>
                총 가격 : {{ order.totalPrice }}
            </div>

            <div>
                <button @click="accept(order.orderId)">
                    수락
                </button>

                <button @click="cancel(order.orderId)">
                    거절
                </button>
            </div>

        </div>

    </div>
</template>

<style scoped>

.overlay {
    position: fixed;

    top: 0;
    left: 0;

    width: 100vw;
    height: 100vh;

    background: rgba(0, 0, 0, 0.5);

    display: flex;
    justify-content: center;
    align-items: center;

    z-index: 9999;
}

.order-modal {
    position: relative;

    width: 400px;

    padding: 30px;

    background: white;

    border-radius: 15px;

    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
}

.close-button {
    position: absolute;

    top: 10px;
    right: 10px;

    width: auto !important;

    padding: 5px 10px !important;

    margin: 0 !important;

    background: none !important;

    font-size: 20px;
    font-weight: bold;

    cursor: pointer;
}

.order-modal h2 {
    margin-top: 0;
    margin-bottom: 20px;
}

.order-modal button {
    width: 48%;

    padding: 12px;

    margin-top: 20px;
    margin-right: 2%;

    border: none;
    border-radius: 8px;

    cursor: pointer;
}

</style>