<script setup>
    import AppHeader from '@/components/common/AppHeader.vue';
    import LogOut from '@/components/common/LogOut.vue';
</script>

<template>
    <LogOut />
    <AppHeader />
    <div class = "createStore">
        <router-link to="/store">
            가게 추가하기
        </router-link>
    </div>

    <div class = "menu-setting">
        <router-link to="/menu/setting">
            메뉴 설정
        </router-link>
    </div>

    <div class = "store-setting">
        가게 설정
    </div>

    <div class = "total-sales">
        총 판매
    </div>
</template>