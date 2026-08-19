<script setup>
import axios from 'axios';
import {ref} from 'vue';

const owerId = ref(0);

// userDetail에서 자동으로 ID를 가져와서 삭제시키기
const deleteAPI = async () => {
    const url = `/api/owner/delete/${owerId.value}`
    await axios.delete(url);
}

</script>

<template>
<div>
    삭제할 ID를 입력하세요.
    <input type = "number" v-model="owerId"/>
    <button @click="deleteAPI">전송</button>
</div>
</template>