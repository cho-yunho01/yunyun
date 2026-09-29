<script setup>

const props = defineProps({
    event : Object,
    isSelected : Boolean
})

const emit = defineEmits([
    'select'
])

const selectEvent = () => {
    emit('select', props.event.eventId)
}

</script>

<template>
    <div
        class="event"
        :class="{ selected: isSelected }"
        @click="selectEvent"
    >
        이벤트 이름 : {{ props.event.name }}
        <br>
        이벤트 설명 : {{ props.event.description }}
        <br />
        쿠폰 ID : {{ props.event.couponId }}
        <br />
        최대 수량 : {{ props.event.maxCount }}
        <br />
        시작일 : {{ props.event.startAt }}
        <br />
        종료일 : {{ props.event.endAt }}
    </div>
</template>

<style scoped>
.event {
    background-color: white;
    padding: 16px;
    border: 1px solid #ddd;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.2s;
}

.event.selected {
    background-color: #ddd;
}
</style>