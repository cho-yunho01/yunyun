<script setup>
import axios from 'axios';
import {reactive,ref} from 'vue';
import Action from './common/Action.vue';
import { useRouter } from 'vue-router';

const owner = reactive({
    userId : "",
    password : "",
    name : "",
})
const url = "/api/owner/create"
const result = async (data) => {
    if(data){
        await axios.post(url,owner);
        router.push("/")
    }
    else{
        reset();
    }

}

const router = useRouter();

const reset = () => {
    owner.userId = "",
    owner.password = "",
    owner.name = ""
}

const ActionView = ref(false);

const message = "생성하시겠습니까?";
const resultTrue = "확인";
const resultFalse = "취소";

</script>

<template>
    <div>
        사용자 아이디를 입력하세요.
        <input type = "text" v-model="owner.userId" /> <br />
        사용자 비밀번호를 입력하세요.
        <input type = "password" v-model="owner.password" /> <br />
        사용자 이름을 입력하세요.
        <input type = "text" v-model="owner.name" />
        <button @click="ActionView = true">생성</button>

        <div v-if="ActionView">
            <Action :message="message" 
            :resultTrue = "resultTrue" 
            :resultFalse = "resultFalse"
            @result="result"/>
        </div>
    </div>
</template>