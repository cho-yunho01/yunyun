<script setup>
import {ref, onMounted, reactive} from 'vue'
import api from '@/api/axios';
import CustomerDetail from '@/components/CustomerDetail.vue';
import UpdateCustomerDetail from '@/components/UpdateCustomerDetail.vue';


const customer = ref(null);

const update = ref(false);

const responseAPI = async () =>{
    const url = "/customer/info"
    const response = await api.get(url);
    customer.value = response.data;
}

const updateMode = () => {
    update.value = false;
}

const updateRequest = async (data) => {
    const url = "/customer/update"
    const customerRequest = reactive({
        password : data.password,
        balance : data.balance
    })

    await api.put(url, customerRequest);
    update.value = true;
    
}


onMounted( () =>{
    responseAPI(),
    update.value = true;
})
</script>

<template>
    <div class ="center">
        <h1>사용자 정보</h1>
        <div v-if = "update" class = "user-info">
            <CustomerDetail :customer = "customer" />
            <div v-if ="update">
            <button @click="updateMode">
                수정하기
            </button>
        </div>
        </div>
        <div v-else>
            <UpdateCustomerDetail 
            :customer = "customer"
            @update="updateRequest" />
        </div>
    </div>
</template>

<style scoped>

.center {
    display : flex;
    flex-direction : column;
    justify-content: center;
    align-items: center;
}
.user-info {
    border : 2px solid rgb(35, 145, 112);
    margin : 20px;
}
</style>