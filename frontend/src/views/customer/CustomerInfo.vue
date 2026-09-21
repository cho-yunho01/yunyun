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
    update.value = true;
}

const updateRequest = async (data) => {
    const url = "/customer/update"
    const customerRequest = reactive({
        name : null,
        userId : null,
        password : data.password,
        balance : data.balance
    })

    await api.put(url, customerRequest);
    
}


onMounted( () =>{
    responseAPI(),
    update.value = false;
})
</script>

<template>
    <h1>사용자 정보</h1>
    <div v-if = "update">
        <CustomerDetail :customer = "customer" />
    </div>
    <div v-else>
        <UpdateCustomerDetail 
        :customer = "customer"
        @update="updateRequest" />
    </div>
    <div v-if ="!update">
        <button @click="updateMode">
            수정하기
        </button>
    </div>
</template>