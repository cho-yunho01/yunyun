<script setup>
import { ref, computed } from 'vue';

const props = defineProps({
    events: {
        type: Array,
        default: () => []
    }
});

const emit = defineEmits([
    'clickEvent'
])
const currentIndex = ref(0);

const currentEvent = computed(() => {
    return props.events[currentIndex.value];
});

const prev = () => {
    currentIndex.value =
        (currentIndex.value - 1 + props.events.length)
        % props.events.length;
};

const next = () => {
    currentIndex.value =
        (currentIndex.value + 1) % props.events.length;
};

const clickEvent = () => {
    emit("clickEvent", props.events[currentIndex.value].eventId)
}

</script>

<template>
    <div v-if="events.length > 0" class="carousel">
        <button
            class="arrow"
            @click="prev"
            :disabled="events.length <= 1"
        >
            &#10094;
        </button>

        <div class="event-card" @click="clickEvent">
            <div class="event-header">
                <span class="event-badge">SPECIAL EVENT</span>
                <h1>{{ currentEvent.name }}</h1>
                <p>특별한 혜택을 지금 바로 만나보세요!</p>
            </div>

            <div class="event-content">
                <div class="event-info">
                    <span class="info-label">선착순</span>
                    <strong>{{ currentEvent.maxCount }}명</strong>
                </div>

                <div class="event-info">
                    <span class="info-label">이벤트 기간</span>
                    <strong>
                        {{ currentEvent.startAt }} ~
                        {{ currentEvent.endAt }}
                    </strong>
                </div>
            </div>

            <div class="page-number">
                {{ currentIndex + 1 }} / {{ events.length }}
            </div>
        </div>

        <button
            class="arrow"
            @click="next"
            :disabled="events.length <= 1"
        >
            &#10095;
        </button>
    </div>
</template>

<style scoped>
.carousel {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 20px;
    width: 100%;
    max-width: 850px;
    margin: 30px auto;
}

.event-card {
    flex: 1;
    min-width: 0;
    background: white;
    border-radius: 20px;
    overflow: hidden;
    box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
    border: 1px solid #e5e7eb;
}

.event-header {
    padding: 40px 25px;
    background: linear-gradient(135deg, #d1fae5, #ecfdf5);
    text-align: center;
}

.event-badge {
    display: inline-block;
    padding: 7px 15px;
    background: #059669;
    color: white;
    font-size: 12px;
    font-weight: bold;
    border-radius: 20px;
    margin-bottom: 15px;
}

.event-header h1 {
    margin: 0;
    font-size: 28px;
    color: #064e3b;
    word-break: break-word;
}

.event-header p {
    margin: 12px 0 0;
    color: #047857;
}

.event-content {
    display: flex;
    gap: 20px;
    padding: 30px;
}

.event-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 12px;
    padding: 25px 10px;
    background: #f8fafc;
    border-radius: 12px;
    text-align: center;
    min-width: 0;
}

.info-label {
    color: #64748b;
    font-size: 14px;
}

.event-info strong {
    color: #0f172a;
    font-size: 16px;
    overflow-wrap: anywhere;
}

.arrow {
    flex-shrink: 0;
    width: 45px;
    height: 45px;
    border: none;
    border-radius: 50%;
    background: #059669;
    color: white;
    font-size: 20px;
    cursor: pointer;
    transition: background 0.2s;
}

.arrow:hover:not(:disabled) {
    background: #047857;
}

.arrow:disabled {
    background: #cbd5e1;
    cursor: not-allowed;
}

.page-number {
    text-align: center;
    padding: 0 0 20px;
    color: #64748b;
    font-size: 13px;
}

@media (max-width: 600px) {
    .carousel {
        gap: 8px;
    }

    .arrow {
        width: 35px;
        height: 35px;
        font-size: 16px;
    }

    .event-content {
        flex-direction: column;
        padding: 20px;
    }

    .event-header h1 {
        font-size: 22px;
    }
}
</style>