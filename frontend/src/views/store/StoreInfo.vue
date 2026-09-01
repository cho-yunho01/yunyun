<script setup>
import { onMounted,reactive,ref } from 'vue';
import { useRoute } from 'vue-router';
import api from '@/api/axios';
import MenuCart from '@/components/MenuCart.vue';

const route = useRoute();

const menuList = ref([]);

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
    api.post(url,itemAddReuqest);
}


</script>

<template>
    <div v-for = "menu in menuList">
        <MenuCart :menu = "menu" @send = "cart"/>
    </div>
</template>