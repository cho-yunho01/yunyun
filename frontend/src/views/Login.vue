<script setup>
import api from "@/api/axios"
import { reactive, ref } from 'vue'
import { useRouter } from "vue-router";

const router = useRouter();

const loginRequest = reactive({
    userId: "",
    password: ""
})

const isConvert = ref(true);

const convertAPI = () => {
    isConvert.value = !isConvert.value
}

const resetLogin = () => {
    loginRequest.userId = "";
    loginRequest.password = "";
}

const showModal = ref(false)

const requestAPI = async () => {
    let url;

    if (isConvert.value) {
        url = "/customer/login";
    } else {
        url = "/owner/login";
    }

    const response = await api.post(url, loginRequest);

    console.log("response 전체:", response);
    console.log("response.data:", response.data);

    const accessToken = response.data;

    if (accessToken === null || !accessToken) {
        showModal.value = true;
        resetLogin();
        return;
    }

    localStorage.setItem("accessToken", accessToken);

    console.log(
        "저장된 accessToken:",
        localStorage.getItem("accessToken")
    );

    resetLogin();


    if (isConvert.value) {
        router.push("/api/customer/home")
    } else {
        router.push("/api/owner/home")
    }
    
};
</script>

<template>
    <div class="login-page">

        <!-- 로그인 카드 -->
        <div class="login-card">

            <!-- 로고 / 제목 -->
            <div class="logo-area">
                <div class="logo-icon">🍕</div>
                <h1>YUN YUN</h1>
                <p>맛있는 한 끼, 간편하게 배달하세요</p>
            </div>

            <!-- 사용자 / 가맹점 전환 -->
            <div class="login-type">
                <button
                    :class="{ active: isConvert }"
                    @click="isConvert = true"
                >
                    사용자
                </button>

                <button
                    :class="{ active: !isConvert }"
                    @click="isConvert = false"
                >
                    가맹점
                </button>
            </div>

            <div class="login-title">
                <h2>
                    {{ isConvert ? '사용자 로그인' : '가맹점 로그인' }}
                </h2>

                <p>
                    {{ isConvert
                        ? 'YUN YUN에서 맛있는 음식을 만나보세요.'
                        : '가맹점 관리 페이지에 로그인해주세요.'
                    }}
                </p>
            </div>

            <!-- 로그인 입력 -->
            <div class="input-area">

                <div class="input-group">
                    <label>아이디</label>

                    <div class="input-box">
                        <span>👤</span>
                        <input
                            type="text"
                            v-model="loginRequest.userId"
                            placeholder="아이디를 입력하세요"
                        />
                    </div>
                </div>

                <div class="input-group">
                    <label>비밀번호</label>

                    <div class="input-box">
                        <span>🔒</span>
                        <input
                            type="password"
                            v-model="loginRequest.password"
                            placeholder="비밀번호를 입력하세요"
                        />
                    </div>
                </div>

            </div>

            <!-- 로그인 버튼 -->
            <button class="login-button" @click="requestAPI">
                {{ isConvert ? '사용자 로그인' : '가맹점 로그인' }}
            </button>

            <div class="login-footer">
                <span>아직 회원이 아니신가요?</span>
                <router-link to ="/api/create" class = "signup">회원가입</router-link>
            </div>

        </div>

        <!-- 로그인 실패 모달 -->
        <div v-if="showModal" class="modal-overlay">

            <div class="modal">

                <div class="modal-icon">
                    ⚠️
                </div>

                <h3>로그인 실패</h3>

                <p>
                    아이디 또는 비밀번호를<br />
                    다시 확인해주세요.
                </p>

                <button
                    class="modal-button"
                    @click="showModal = false"
                >
                    확인
                </button>

            </div>

        </div>

    </div>
</template>

<style scoped>

* {
    box-sizing: border-box;
}

.login-page {
    min-height: 100vh;

    display: flex;
    justify-content: center;
    align-items: center;

    background:
        linear-gradient(
            135deg,
            #fff7ed 0%,
            #ffffff 50%,
            #fff1e8 100%
        );

    padding: 30px;
}

/* 로그인 카드 */

.login-card {
    width: 430px;

    padding: 42px 40px;

    background: white;

    border-radius: 24px;

    box-shadow:
        0 20px 60px rgba(0, 0, 0, 0.08);

    border: 1px solid #f3f3f3;
}

/* 로고 */

.logo-area {
    text-align: center;
    margin-bottom: 28px;
}

.logo-icon {
    width: 64px;
    height: 64px;

    margin: 0 auto 12px;

    display: flex;
    align-items: center;
    justify-content: center;

    background: #fff0e6;

    border-radius: 20px;

    font-size: 30px;
}

.logo-area h1 {
    margin: 0;

    font-size: 27px;
    font-weight: 800;

    letter-spacing: -1px;

    color: #ff6b35;
}

.logo-area p {
    margin-top: 8px;

    font-size: 13px;

    color: #999;
}

/* 로그인 타입 */

.login-type {
    display: flex;

    padding: 4px;

    margin-bottom: 30px;

    background: #f5f5f5;

    border-radius: 12px;
}

.login-type button {
    flex: 1;

    padding: 11px 0;

    border: none;

    background: transparent;

    border-radius: 9px;

    font-size: 14px;

    color: #888;

    cursor: pointer;

    transition: 0.2s;
}

.login-type button.active {
    background: white;

    color: #ff6b35;

    font-weight: 700;

    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

/* 제목 */

.login-title {
    margin-bottom: 25px;
}

.login-title h2 {
    margin: 0;

    font-size: 22px;

    color: #222;

    letter-spacing: -0.5px;
}

.login-title p {
    margin-top: 7px;

    font-size: 13px;

    color: #999;

    line-height: 1.5;
}

/* 입력 */

.input-area {
    display: flex;

    flex-direction: column;

    gap: 17px;
}

.input-group label {
    display: block;

    margin-bottom: 8px;

    font-size: 13px;

    font-weight: 600;

    color: #444;
}

.input-box {
    display: flex;

    align-items: center;

    height: 50px;

    padding: 0 15px;

    border: 1px solid #e8e8e8;

    border-radius: 12px;

    background: #fafafa;

    transition: 0.2s;
}

.input-box:focus-within {
    border-color: #ff8a5b;

    background: white;

    box-shadow: 0 0 0 3px rgba(255, 107, 53, 0.08);
}

.input-box span {
    margin-right: 10px;

    font-size: 16px;
}

.input-box input {
    width: 100%;

    border: none;

    outline: none;

    background: transparent;

    font-size: 14px;

    color: #333;
}

.input-box input::placeholder {
    color: #bbb;
}

/* 로그인 버튼 */

.login-button {
    width: 100%;

    height: 52px;

    margin-top: 25px;

    border: none;

    border-radius: 13px;

    background: #ff6b35;

    color: white;

    font-size: 15px;

    font-weight: 700;

    cursor: pointer;

    box-shadow: 0 7px 18px rgba(255, 107, 53, 0.25);

    transition: 0.2s;
}

.login-button:hover {
    background: #f45c27;

    transform: translateY(-1px);
}

.login-button:active {
    transform: translateY(0);
}

/* 하단 */

.login-footer {
    display: flex;

    justify-content: center;

    gap: 7px;

    margin-top: 22px;

    font-size: 12px;

    color: #aaa;
}

.signup {
    color: #ff6b35;

    font-weight: 700;

    cursor: pointer;
}

/* 모달 배경 */

.modal-overlay {
    position: fixed;

    inset: 0;

    display: flex;

    justify-content: center;

    align-items: center;

    background: rgba(0, 0, 0, 0.45);

    backdrop-filter: blur(3px);

    z-index: 1000;
}

/* 모달 */

.modal {
    width: 330px;

    padding: 32px 28px;

    background: white;

    border-radius: 20px;

    text-align: center;

    box-shadow:
        0 20px 60px rgba(0, 0, 0, 0.18);

    animation: modalOpen 0.2s ease-out;
}

@keyframes modalOpen {
    from {
        opacity: 0;
        transform: translateY(10px) scale(0.97);
    }

    to {
        opacity: 1;
        transform: translateY(0) scale(1);
    }
}

.modal-icon {
    width: 55px;
    height: 55px;

    margin: 0 auto 15px;

    display: flex;

    align-items: center;

    justify-content: center;

    background: #fff3ed;

    border-radius: 50%;

    font-size: 25px;
}

.modal h3 {
    margin: 0;

    font-size: 19px;

    color: #222;
}

.modal p {
    margin: 10px 0 22px;

    color: #888;

    font-size: 13px;

    line-height: 1.6;
}

.modal-button {
    width: 100%;

    height: 45px;

    border: none;

    border-radius: 10px;

    background: #ff6b35;

    color: white;

    font-size: 14px;

    font-weight: 700;

    cursor: pointer;
}

.modal-button:hover {
    background: #f45c27;
}

</style>