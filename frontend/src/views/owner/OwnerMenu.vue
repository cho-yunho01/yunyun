<script setup>
import api from '@/api/axios';
import ButtonMenuCard from '@/components/ButtonMenuCard.vue';
import { ref,onMounted } from 'vue';
import { useRouter } from 'vue-router';
import UpdateMenu from '../menu/UpdateMenu.vue';
import DeleteMenu from '../menu/DeleteMenu.vue';

const router = useRouter();

const menus = ref([]);

const requestAPI = async () => {
    const url = "/stores/menus";
    const response = await api.get(url);
    menus.value = response.data;
    console.log("menu의 값");
    console.log(response.data);
}

const acceptMessage = ref("수정");

const cancelMessage = ref("삭제");

// 수정
const isUpdate = ref(false);

// 삭제
const isDelete = ref(false);

// 선택된 메뉴
const selectMenu = ref("");

const accept = (menu) => {
    isUpdate.value = true;
    selectMenu.value = menu;
}

const close = (value,type) => {
    if(type === "update"){
        isUpdate.value = value;
    }
    else{
        isDelete.value = value;
    }

}

const cancel= (menu) => {
    isDelete.value = true;
    selectMenu.value = menu;
}

onMounted(() => requestAPI())


</script>

<template>
    <router-link to = "/menu/create">
        메뉴 추가
    </router-link>

    <ButtonMenuCard :menus="menus"
     :acceptMessage= acceptMessage
     :cancelMessage= cancelMessage
     @accept="accept"
     @cancel="cancel"/>


     <div v-if = "isUpdate">
        <UpdateMenu :menu = "selectMenu" @isTrue="close" />
     </div>

     <div v-if = "isDelete">
        <DeleteMenu :menu = "selectMenu" @isTrue="close"/>
     </div>
</template>