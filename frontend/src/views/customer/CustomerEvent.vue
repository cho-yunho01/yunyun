<script setup>
import { onMounted,ref } from 'vue';
import { useRoute } from 'vue-router';
import api from '@/api/axios';

const event = ref();

const coupon = ref();

const currentRoute = useRoute();

const evnetResponse = async() => {
    const url = `/event/${currentRoute.params.eventId}`
    const response = await api.get(url);
    event.value = response.data
}

const couponResponse = async() => {
    const url = `/coupon/${event.value.couponId}`
    const response = await api.get(url);
    coupon.value = response.data;
}

onMounted(async () => {
    await evnetResponse();
    await couponResponse();
})

</script>

<template>
    <div v-if="event && coupon" class="event-container">
        <div class="event-card">
            <div class="event-header">
                <span class="event-badge">SPECIAL EVENT</span>
                <h1>{{ event.name }}</h1>
                <p>{{ event.description }}</p>
            </div>

            <div class="coupon-section">
                <span class="coupon-label">EVENT COUPON</span>
                <div class="coupon-name">{{ coupon.name }}</div>
                <div class="discount">
                    {{ Number(coupon.discountPrice).toLocaleString() }}
                    <span>원 할인</span>
                </div>
            </div>

            <div class="event-info">
                <div class="info-item">
                    <span class="info-label">최대 발급 수량</span>
                    <strong>{{ event.maxCount }}개</strong>
                </div>

                <div class="info-divider"></div>

                <div class="info-item">
                    <span class="info-label">이벤트 기간</span>
                    <strong>{{ event.startAt }} ~ {{ event.endAt }}</strong>
                </div>
            </div>

            <button class="coupon-button">
                쿠폰 받기
            </button>
        </div>
    </div>

    <div v-else class="loading">
        데이터를 불러오는 중입니다.
    </div>
</template>

<style scoped>
.event-container {
    display: flex;
    justify-content: center;
    padding: 40px 20px;
}

.event-card {
    width: 100%;
    max-width: 600px;
    background: white;
    border: 1px solid #e5e7eb;
    border-radius: 20px;
    overflow: hidden;
    box-shadow: 0 10px 35px rgba(0, 0, 0, 0.08);
}

.event-header {
    padding: 40px 30px;
    text-align: center;
    background: linear-gradient(135deg, #d1fae5, #ecfdf5);
}

.event-badge {
    display: inline-block;
    padding: 7px 16px;
    border-radius: 20px;
    background: #059669;
    color: white;
    font-size: 12px;
    font-weight: 700;
    letter-spacing: 1px;
}

.event-header h1 {
    margin: 20px 0 10px;
    color: #064e3b;
    font-size: 28px;
    font-weight: 800;
    overflow-wrap: anywhere;
}

.event-header p {
    margin: 0;
    color: #047857;
    font-size: 15px;
    line-height: 1.6;
    white-space: pre-wrap;
}

.coupon-section {
    margin: 25px 30px;
    padding: 30px 20px;
    text-align: center;
    border: 2px dashed #6ee7b7;
    border-radius: 15px;
    background: #f0fdf4;
}

.coupon-label {
    color: #059669;
    font-size: 12px;
    font-weight: 800;
    letter-spacing: 2px;
}

.coupon-name {
    margin-top: 12px;
    color: #334155;
    font-size: 18px;
    font-weight: 600;
}

.discount {
    margin-top: 10px;
    color: #059669;
    font-size: 38px;
    font-weight: 800;
}

.discount span {
    font-size: 18px;
    font-weight: 600;
}

.event-info {
    margin: 0 30px;
    padding: 20px;
    display: flex;
    flex-direction: column;
    gap: 18px;
    background: #f8fafc;
    border-radius: 12px;
}

.info-item {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.info-label {
    color: #64748b;
    font-size: 13px;
    font-weight: 600;
}

.info-item strong {
    color: #1e293b;
    font-size: 15px;
    overflow-wrap: anywhere;
}

.info-divider {
    height: 1px;
    background: #e2e8f0;
}

.coupon-button {
    display: block;
    width: calc(100% - 60px);
    margin: 25px 30px 30px;
    padding: 16px;
    border: none;
    border-radius: 12px;
    background: #059669;
    color: white;
    font-size: 17px;
    font-weight: 700;
    cursor: pointer;
    transition: background 0.2s, transform 0.2s;
}

.coupon-button:hover {
    background: #047857;
    transform: translateY(-2px);
}

.coupon-button:active {
    transform: translateY(0);
}

.loading {
    padding: 80px 20px;
    text-align: center;
    color: #64748b;
    font-size: 16px;
}

@media (max-width: 600px) {
    .event-container {
        padding: 20px 12px;
    }

    .event-header {
        padding: 30px 20px;
    }

    .event-header h1 {
        font-size: 23px;
    }

    .coupon-section {
        margin: 20px;
    }

    .discount {
        font-size: 32px;
    }

    .event-info {
        margin: 0 20px;
    }

    .coupon-button {
        width: calc(100% - 40px);
        margin: 20px 20px 25px;
    }
}
</style>