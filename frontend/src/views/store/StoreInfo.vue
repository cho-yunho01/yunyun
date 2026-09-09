<script setup>
import { onMounted,reactive,ref } from 'vue';
import { useRoute } from 'vue-router';
import api from '@/api/axios';
import MenuCart from '@/components/MenuCart.vue';
import ErrorMessage from '@/components/ErrorMessage.vue';

const route = useRoute();

const menuList = ref([]);

const errorMessage = ref();

const errorCheck = ref(false);

const menuListRequest = async () => {
    const id = route.params.storeId;
    const url = `/stores/menus/${id}`
    const response = await api(url);
    console.log(response.data);
    menuList.value = response.data;

}


onMounted(()=> {
    menuListRequest();
})

const cart = async(data) => {
    const url = "/cart/add";
    const itemAddReuqest = reactive({
        menuId : data.menu.menuId,
        quantity: data.quantity
    })

    console.log(itemAddReuqest);
    try{
        await api.post(url,itemAddReuqest);
    }
    catch(error){
        errorMessage.value = error.response.data;
        errorCheck.value = true;
    }
}

const deleteCart = async () => {
    const url = "/cart/delete/all"
    await api.delete(url);
    errorCheck.value = false;
}

const cancel = async () => {
    errorCheck.value = false;
}

</script>

<template>
    <div v-for = "menu in menuList">
        <MenuCart :menu = "menu" @send = "cart"/>
    </div>
    <div v-if = errorCheck>
        <ErrorMessage 
        :message="errorMessage.message" 
        subMessage = "현재 장바구니에 있는 상품을 삭제하시겠습니까?" 
        buttons="CONFIRM"
        @confirm="deleteCart"
        @cancel="cancel"
        />
    </div>
</template>