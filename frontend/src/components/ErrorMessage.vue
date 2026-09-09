<script setup>
const props = defineProps({
    message: String,
    subMessage: String,
    buttons: {
        type: String,
        default: 'ALERT'
    }
})

const emit = defineEmits(['confirm', 'cancel'])
</script>

<template>
    <!-- 전체 화면 덮는 배경 -->
    <div class="overlay">

        <!-- 에러창 -->
        <div class="error-box">

            <div class="message">
                <p class="main-message">
                    {{ props.message }}
                </p>

                <p
                    v-if="props.subMessage"
                    class="sub-message"
                >
                    {{ props.subMessage }}
                </p>
            </div>

            <div class="button-area">

                <button
                    v-if="props.buttons === 'CONFIRM'"
                    class="cancel-button"
                    @click="emit('cancel')"
                >
                    취소
                </button>

                <button
                    class="confirm-button"
                    @click="emit('confirm')"
                >
                    확인
                </button>

            </div>
        </div>
    </div>
</template>

<style scoped>

.overlay {
    position: fixed;
    top: 0;
    left: 0;

    width: 100vw;
    height: 100vh;

    background: rgba(0, 0, 0, 0.45);

    display: flex;
    justify-content: center;
    align-items: center;

    z-index: 9999;
}

.error-box {
    width: 360px;
    min-height: 180px;

    background: white;
    border-radius: 12px;

    padding: 30px;

    box-sizing: border-box;

    box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2);

    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.message {
    text-align: center;
}

.main-message {
    font-size: 18px;
    font-weight: 600;

    margin: 10px 0;
}

.sub-message {
    font-size: 14px;
    color: #666;

    margin-top: 10px;
}

.button-area {
    display: flex;
    justify-content: center;
    gap: 10px;

    margin-top: 25px;
}

.button-area button {
    width: 100px;
    height: 40px;

    border: none;
    border-radius: 6px;

    cursor: pointer;

    font-size: 14px;
    font-weight: 500;
}

.confirm-button {
    background: #222;
    color: white;
}

.cancel-button {
    background: #eee;
    color: #333;
}

</style>