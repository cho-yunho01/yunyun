<script setup>
import api from '@/api/axios';
import EventList from '@/components/event/EventList.vue';
import EventAdd from '@/components/EventAdd.vue';
import { ref,onMounted } from 'vue';

const eventList = ref([]);

const eventId = ref();

const selectEvent = (id) => {
    eventId.value = eventId.value === id ? null : id
}

onMounted(() => {
    eventResponse();
})

const eventResponse = async() => {
    const url = '/event'
    const response = await api.get(url);
    eventList.value = response.data;
}
</script>
<template>

<EventAdd />
<h1>이벤트 리스트</h1>
<div v-for="event in eventList">
    <EventList :event="event" :is-selected="event.eventId === eventId" @select="selectEvent" />
</div>
</template>