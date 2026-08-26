<script setup>
import MenuCard from '@/components/MenuCard.vue';
import api from '@/api/axios';
import { ref,onMounted } from 'vue';

const menus = ref([]);

const selectMenus = ref([]);

const selectMenu = (menu, selected) => {
    if(selected){
        selectMenus.value.push(menu)
    }
    else{
        selectMenus.value = selectMenus.value.filter(
            item => item.menuId !== menu.menuId
        )
    }
}

const responseMenu = async () => {
    const url = `/store/1/menus`; // 임시로 storeId 1로 설정
    const response = await api.get(url);
    menus.value = response.data;
}

onMounted(() => {
    responseMenu()
})

</script>

<template>
    <MenuCard 
    v-for ="menu in menus" 
    :key ="menu.menuId"
    @select="selectMenu(menu, $event)"/>

</template>