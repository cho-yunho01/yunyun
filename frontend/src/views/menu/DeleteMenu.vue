<script setup>
import axios from 'axios';
import {ref} from 'vue';

const menuId = ref(0);

const requestAPI = async () => {
    const url = `/api/menu/delete/${menuId.value}`
    await axios.delete(url);
}
</script>

<template>
    <div>
        삭제할 메뉴 ID를 입력하세요.
        <input type = "number" v-model="menuId"/>
        <button @click="requestAPI">전송</button>
    </div>
</template>