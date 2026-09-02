<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import Action from './Action.vue';

const router = useRouter();

const message = "정말로 로그아웃 하시겠습니까?"
const resultTrue = "확인"
const resultFalse = "취소"

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
    <Action :message="message" 
    :resultTrue = "resultTrue"
    :resultFalse="resultFalse"
    @result="logOut" />
</div>
</template>