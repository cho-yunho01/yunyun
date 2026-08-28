<script setup>
import MenuCard from '@/components/MenuCard.vue';
import api from '@/api/axios';
import { ref,onMounted } from 'vue';
import { useRoute } from 'vue-router';
import CheckMenuCard from '@/components/CheckMenuCard.vue';

const menus = ref([]);

const selectMenus = ref([]);

const route = useRoute();

const storeId = route.params.storeId;

// const selectMenu = (menu, selected) => {
//     if(selected){
//         selectMenus.value.push(menu)
//     }
//     else{
//         selectMenus.value = selectMenus.value.filter(
//             item => item.menuId !== menu.menuId
//         )
//     }
// }

const selectMenu = (value) =>{
    if(value.checked){
        selectMenus.value.push(value.menu)
    }
    else{
        selectMenus.value = selectMenus.value.filter(
            item => item.menuId !== value.menu.menuId
        )
    }
}

const responseMenu = async () => {
    const url = `/store/${storeId}/menus`;
    const response = await api.get(url);
    menus.value = response.data;
}

onMounted(() => {
    responseMenu()
})

</script>

<template>
<!--     
    <MenuCard 
    v-for ="menu in menus" 
    :key ="menu.menuId"
    @select="selectMenu(menu, $event)"/> 
-->

<CheckMenuCard :menus="menus" @select="selectMenu"/>

</template>