<script setup>
import AppHeader from '@/components/common/AppHeader.vue';
import { onMounted, ref,computed } from 'vue';
import { useRouter } from 'vue-router'
import api from '@/api/axios';
import LogOut from '@/components/common/LogOut.vue';

const storeName = ref("");
const router = useRouter();
const storeList = ref([]);

// const seartchStore = async () => {
//     const url = `/find/stores/${storeName.value}`
//     const response = await api.get(url);
//     storeList.value = response.data
// }

const searchStore = async () => {
    const url = "/find/stores"
    const response = await api.get(url);
    storeList.value = response.data
}

onMounted(() => {
    searchStore();
})

const stores = computed(() => {
    if(!storeName.value.trim()){
        return [];
    }

    return storeList.value.filter((s) => {
        return s.name.includes(storeName.value)
    })
})

const goStore = (storeId) => {
    router.push(`/store/${storeId}`)
}

</script>

<template>
    <LogOut />
    <AppHeader />
    <div class = "home">

        <div class="search">
            음식점 검색 창
            <input type = "text" v-model="storeName">
            <button @click="seartchStore">검색</button>
        </div>
        <div v-if="stores.length > 0">
            <div v-for="store in stores" :key="store.storeId"
            @click="goStore(store.storeId)">
                {{ store.name }}
            </div>
        </div>

        <div class = "event">
            이벤트 배너
        </div>

        <div class = "info">
            <div class = "info-image">
                사용자 이미지 정보
            </div>
            <router-link to="/customer/info">사용자 정보</router-link>
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
