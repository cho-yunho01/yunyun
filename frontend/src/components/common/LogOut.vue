<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import Confirm from './Confirm.vue';

const router = useRouter();

const message = "정말로 로그아웃 하시겠습니까?"

const isLogOut = ref(false);

const logOut = (isTrue) => {
    if(isTrue){
        localStorage.removeItem('accessToken');
        router.push("/");
    }
    else{
        isLogOut.value = false;
    }
}

</script>

<template>
<button @click="isLogOut = true">
    로그아웃
</button>

<div v-if="isLogOut">
    <Confirm :message="message" @result="logOut" />
</div>
</template>