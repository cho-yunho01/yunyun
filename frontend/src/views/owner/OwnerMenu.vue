<script setup>
import api from '@/api/axios';
import ButtonMenuCard from '@/components/ButtonMenuCard.vue';
import { ref,onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const menus = ref([]);

const requestAPI = async () => {
    const url = "/stores";
    const response = api.get(url);
    menus.value = response.data;
}


const acceptRequest = async (menu) => {
    const menuId = menu.menuId;
    const url = `/update/${menuId}`;
    await api.put(url);
}


onMounted(() => requestAPI())
</script>

<template>
    <router-link to = "/menu/create">
        메뉴 추가
    </router-link>
    <ButtonMenuCard :menus="menus"
     :accept-message="수정"
     :cancel-message="삭제"
     @accept="acceptRequest"
     @cancel="cancelRequest"/>
</template>