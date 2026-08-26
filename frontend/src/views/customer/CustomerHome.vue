<script setup>
import AppHeader from '@/components/common/AppHeader.vue';
import { ref } from 'vue';
import api from '@/api/axios';

const storeName = ref("");

const seartchStore = async () => {
    
}

</script>

<template>
    <AppHeader />
    <div class = "home">

        <div class="search">
            음식점 검색 창
            <input type = "text" v-model="storeName">
            <button @click="seartchStore">검색</button>
            <!-- 해당 음식점 페이지로 이동 -->

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
