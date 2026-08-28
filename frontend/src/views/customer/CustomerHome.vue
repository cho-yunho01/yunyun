<script setup>
import AppHeader from '@/components/common/AppHeader.vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router'
import api from '@/api/axios';

const storeName = ref("");
const router = useRouter();
const seartchStore = async () => {
    const url = `/api/find/stores/${storeName.value}`
    const response = await api.get(url);
    router.push(`/api/store/${response.data}`)
}

</script>

<template>
    <AppHeader />
    <div class = "home">

        <div class="search">
            음식점 검색 창
            <input type = "text" v-model="storeName">
            <button @click="seartchStore">검색</button>

        </div>

        <div class = "event">
            이벤트 배너
        </div>

        <div class = "info">
            <div class = "info-image">
                사용자 이미지 정보
            </div>
            <router-link to="/api/customer/info">사용자 정보</router-link>
        </div>

        <div class = "category" v-for="menu in menus" :key = "menu.id">
            
        </div>

        <div class="delivery-status">
            <router-link to="/api/customer/delivery_status">
                배달 현황
            </router-link>
        </div>


    </div>
</template>
