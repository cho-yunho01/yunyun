<script setup>
import axios from 'axios';
import {reactive,ref} from 'vue';
import Confirm from './common/Confirm.vue';
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

const confirmView = ref(false);

const message = "생성하시겠습니까?";

</script>

<template>
    <div>
        사용자 아이디를 입력하세요.
        <input type = "text" v-model="owner.userId" /> <br />
        사용자 비밀번호를 입력하세요.
        <input type = "password" v-model="owner.password" /> <br />
        사용자 이름을 입력하세요.
        <input type = "text" v-model="owner.name" />
        <button @click="confirmView = true">생성</button>

        <div v-if="confirmView">
            <Confirm :message="message" @result="result"/>
        </div>
    </div>
</template>