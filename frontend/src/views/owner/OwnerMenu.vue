<script setup>
import api from '@/api/axios';
import ButtonMenuCard from '@/components/ButtonMenuCard.vue';
import { ref,reactive,onMounted } from 'vue';

const menus = ref([]);
const requestAPI = async () => {
    const url = "/stores";
    const response = api.get(url);
    menus.value = response.data;
}

const menuRequest = reactive({
    name: "",
    price: null,
    introduction: "",
    storeId : null
})

const acceptRequest = async (menu) => {
    const menuId = menu.menuId;
    const url = `/update/${menuId}`;
    await api.put(url);
}


onMounted(() => requestAPI())
</script>

<template>

    <ButtonMenuCard :menus="menus"
     :accept-message="수정"
     :cancel-message="삭제"
     @accept="acceptRequest"
     @cancel="cancelRequest"/>
</template>