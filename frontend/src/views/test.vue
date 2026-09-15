<script setup>
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';


const stompClient = new Client({
    webSocketFactory : () => {
        new SockJS("http://localhost:8080/order/")
    },

    onConnect : () => {
        stompClient.subscribe("/topic/order/owner", (message) => {
            const id = JSON.parse(message.body)
        })
    }
})

</script>

<template>

</template>