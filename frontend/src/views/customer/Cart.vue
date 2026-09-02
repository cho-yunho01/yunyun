<script setup>
import { computed, onMounted, ref, reactive } from 'vue';
import api from '@/api/axios';
import CheckCart from '@/components/CheckCart.vue';
import UpdateItem from '@/components/UpdateItem.vue';

const cartList = ref([]);

const selectList = ref([]);

const updateStauts = ref(false);

const 

const openUpdate = ref()

const updateItem = async (data, cart) => {
    const cartItemRequest = reactive({
        cartItemId : cart.cartItemId,
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
    await api.delete(url,deleteIds)
}
</script>

<template>
    <div v-for="cart in cartList" :key="cart.cartItemId">
        <CheckCart @click="select($event, cart)"/>
        <div class = name>
            메뉴 이름 : 
            {{ cart.name }}
        </div>
        <div class = price>
            가격 : 
            {{ cart.price * cart.quantity }}
        </div>
        <div class = "quantity">
            수량 : 
            {{ cart.quantity }}
        </div>
        <br/>

        <div class = "action">
            <button @click="openUpdate(cart)">수정</button>
        </div>
    </div>

    <div v-if = "selectList.length > 0">
        <button @click = "deleteRequest">삭제</button>
    </div>

    <div class = "total">
        <br/>
        <h2>총 가격</h2>
        {{ totalPrice }}원
    </div>

    <div v-if="updateStauts">
        <UpdateItem @quantity="updateItem($event, cart)"/>
    </div>
    
</template>