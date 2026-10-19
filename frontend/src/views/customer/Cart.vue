<script setup>
import { computed, onMounted, ref, reactive } from 'vue';
import api from '@/api/axios';
import CheckCart from '@/components/CheckCart.vue';
import UpdateItem from '@/components/UpdateItem.vue';

const cartList = ref([]);

const selectList = ref([]);

const updateStauts = ref(false);

const updateCart = ref();

const openUpdate = (cart) => {
    updateCart.value = cart;
    updateStauts.value = true;
};

const updateItem = async (data) => {
    const cartItemRequest = reactive({
        cartItemId : updateCart.value.cartItemId,
        quantity : data
    })

    const url = "/cart/update/quantity"
    await api.put(url, cartItemRequest)
    updateStauts.value = false;
    requestCart();
}

const requestCart = async() => {
    const url = "/cart/getCart"
    const response = await api.get(url);
    cartList.value = response.data;

}

const totalPrice = computed(() => {
    return cartList.value.reduce(
        (total, cart) => total + cart.price * cart.quantity, 0
    )
})

onMounted(() => {
    requestCart();
})

const select = (value,cart) => {
    if(value){
        selectList.value.push(cart)
    }
    else{
        selectList.value = selectList.value.filter(s => 
            s.cartItemId !== cart.cartItemId
        )
    }

    console.log(selectList.value);
}

const deleteRequest = async () => {
    const deleteIds = ref([]);
    deleteIds.value = selectList.value.map(
        s => s.cartItemId
    );
    const url = "/cart/delete"
    await api.delete(url,{
        data:{
            cartItemId : deleteIds.value
        }
    })
    requestCart();
}

const order = async () => {

    const url = "/order/create"

    const orderItemRequest = selectList.value.map(
        cart => ({
            cartItemId : cart.cartItemId
        })
    )
    const orderItemListRequest = {
        orderItemRequestList: orderItemRequest,
        totalPrice: totalPrice.value
    };

    orderItemRequest.forEach(
        cart => console.log('카트의 ID 값 : '+cart.cartItemId)
    )

    await api.post(url, orderItemListRequest);

}
</script>

<template>
<div class = "cart">
    <div v-for="cart in cartList" :key="cart.cartItemId">
        <div class = "menu">
            <CheckCart 
            class = "select"
            @click="select($event, cart)"/>

            <div class = "menu-info">
                <div class="info-field">
                    메뉴 이름:
                </div>
                <div class="info-field">
                    가격:
                </div>
                <div class="info-field">
                    수량:
                </div>
            </div>

            <div class = "menu-info">
                <div class="field">
                    {{ cart.name }}
                </div>
                <div class="field">
                    {{ cart.price * cart.quantity }}
                </div>
                <div class="field">
                    {{ cart.quantity }}
                </div>
            </div>

            <div class = "action">
                <button @click="openUpdate(cart)" class = "update-button">수정</button>
            </div>
        </div>
    </div>

    <div class = "buttons" v-if = "selectList.length > 0">
        <button @click = "deleteRequest">삭제</button>
        <button @click = "order">주문</button>
    </div>

    <div class = "total">
        <br/>
        <h2>총 가격</h2>
        {{ totalPrice }}원
    </div>

    <div v-if="updateStauts">
        <UpdateItem @quantity="updateItem($event)"/>
    </div>
</div>
    
</template>

<style scoped>
.cart{
    display : flex;
    flex-direction: column;
    border : 2px solid black;
    align-items: center;
    margin : 20px auto;
    position: relative;
    width: fit-content;
    padding: 60px;
}

.menu{
    border : 2px solid rgb(190, 85, 147);
    position : relative;
    display: flex;
    margin : 10px auto;
    background-color: blanchedalmond;
}
.menu-info{

}

.field{
    margin : 40px 10px;
}

.info-field{
    margin : 40px 10px;
}

.update-button{
    position : absolute;
    right: 5px;
    bottom: 5px;
}

.select{
    position : absolute;
    top: 5px;
    left : 5px;
}

.bottom{
    
}
.total{
    /* position:absolute;
    left : 50%;
    transform: translateX(-50%); */
}

.buttons{
    margin-left: auto;
}
</style>