<script setup>
import axios from 'axios';
import {reactive,ref} from 'vue';
import Action from './common/Action.vue';
import { useRouter } from 'vue-router';

const customer = reactive({
    userId : "",
    password : "",
    name : "",
    balance : null
})
const url = "/api/customer/create"

const result = async (data) => {
    if(data){
        await axios.post(url,customer);
        router.push("/")
    }
    else{
        reset();
    }

}

const router = useRouter();

const reset = () => {
    customer.userId = "",
    customer.password = "",
    customer.name = "",
    customer.balance = null
}

const ActionView = ref(false);

const message = "생성하시겠습니까?";
const resultTrue = "확인";
const resultFalse = "취소";

</script>

<template>
    <div>
        사용자 아이디를 입력하세요.
        <input type = "text" v-model="customer.userId" /> <br />
        사용자 비밀번호를 입력하세요.
        <input type = "password" v-model="customer.password" /> <br />
        사용자 이름을 입력하세요.
        <input type = "text" v-model="customer.name" /> <br />
        사용자 잔액을 입력하세요.
        <input type = "number" v-model="customer.balance" />
        <button @click="ActionView = true">생성</button>

        <div v-if="ActionView">
            <Action :message="message"
            :resultTrue = "resultTrue"
            :resultFalse="resultFalse"
            @result="result"/>
        </div>
    </div>
</template>