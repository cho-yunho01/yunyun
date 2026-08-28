<script setup>
import CustomerCreate from '@/components/CustomerCreate.vue';
import OwnerCreate from '@/components/OwnerCreate.vue';
import { ref } from 'vue';

const isCustomer = ref(true);

</script>

<template>
    <div class = 'user-type'>
        <div>
            <button @click="isCustomer = true">
                사용자
            </button>

            <button @click="isCustomer = false">
                가맹점
            </button>
        </div>
    </div>

    <div v-if="isCustomer">
        <CustomerCreate />
    </div>

    <div v-else>
        <OwnerCreate />
    </div>


</template>