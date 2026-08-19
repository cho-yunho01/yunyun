<script setup>
import axios from 'axios';
import {ref, reactive} from 'vue'

const menuId = ref(0);

const MenuRequest = reactive({
    name : "",
    price : 0,
    introduction : "",
    storeId : 0
})

const putAPI = async () => {
    const url = `/api/menu/update/{menuId.value}`
    await axios.put(url, MenuRequest);
}

</script>

<template>
    <div>
        상품명을 입력하세요.
        <input type = "text" v-model="MenuRequest.name"> <br />
        가격을 입력하세요.
        <input type = "number" v-model="MenuRequest.price"><br/>
        메뉴 설명을 입력하세요. 
        <input type = "text" v-memel="MenuRequest.introduction"><br/>
        수정할 메뉴 ID를 입력하세요.
        <input type = "number" v-model="menuId"/>
        <button @click="putAPI">전송</button>
    </div>

</template>