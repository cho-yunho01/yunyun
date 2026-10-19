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
    <div class = "order">
        <div class = "order-item">
            <div class = "userId">
                사용자 ID : {{ order.userId }}
            </div>
            
            <div class="menu" v-for="item in order.orderItemResponseList" :key="item.menuName">
                <div class="menu-info">
                    <div>메뉴이름 :</div>
                    <div>수량 :</div>
                    <div>가격 :</div>
                </div>

                <div class="menu-info">
                    <div>{{ item.menuName }}</div>
                    <div>{{ item.quantity }}</div>
                    <div>{{ item.price }}</div>
                </div>
            </div>


            <div>
                주문 상태 : {{ order.orderStatus }} <br/>
                총 가격: {{ order.totalPrice }}
            </div>

            <div 
            class = "button"
            v-if="props.button === 'PENDING'">
                <button @click="accept(props.order.orderId)">
                    수락
                </button>
                <button @click="cancel(props.order.orderId)">
                    거절
                </button>
            </div>

            <div class = "button" v-else-if="props.button === 'CUSTOMER'">
                <button @click="cancel(props.order.orderId)">
                    주문 취소
                </button>
            </div>
        </div>
    </div>
</template>

<style scoped>
.order{
    display : flex;
    flex-direction: column;
    margin: 20px auto;
    align-items: center;
    background-color: oldlace;
    border-radius: 20px;
    width : fit-content;
    position : relative;
}
.order-item{
    margin: 20px;
}

.menu{
    display : flex;
    margin-top: 20px;
    margin-bottom: 20px;
    border-radius: 20px;
    padding: 20px;
    background-color: white;
}
.menu-info{
    margin: 5px;
}

.button{
    position: absolute;
    right: 5px;
    bottom : 5px;
}

</style>