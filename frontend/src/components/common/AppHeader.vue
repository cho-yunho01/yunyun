<template>
  <!-- 전체 네비게이션 바 영역 -->
  <nav class="nav-bar">

    <!--
      1. v-for로 대단위 메뉴들을 그립니다.
      2. @mouseenter: 마우스가 영역에 들어오면 현재 메뉴의 id를 저장합니다.
      3. @mouseleave: 마우스가 영역을 나가면 저장된 id를 지웁니다 (null).
    -->
    <div
      v-for="menu in menus"
      :key="menu.id"
      class="menu-item"
      @mouseenter="hoveredMenuId = menu.id"
      @mouseleave="hoveredMenuId = null"
    >
      <!-- 대단위 메뉴 텍스트 -->
      <span class="main-title">{{ menu.title }}</span>

      <!--
        세부단위 메뉴 영역
        v-show: 내가 마우스를 올린 메뉴의 id와 이 서브메뉴의 id가 같을 때만 보여줍니다!
      -->
      <div
        v-show="hoveredMenuId === menu.id"
        class="sub-menu"
      >
        <!--
          세부 메뉴들을 반복해서 출력합니다.
          RouterLink를 사용하면 페이지 전체를 새로고침하지 않고
          Vue Router를 통해 해당 경로로 이동합니다.
        -->
        <RouterLink
          v-for="subMenu in menu.children"
          :key="subMenu.name"
          :to="subMenu.link"
        >
          {{ subMenu.name }}
        </RouterLink>
      </div>

    </div>

  </nav>
</template>

<script setup>
import { ref } from 'vue'

// 어떤 메뉴 위에 마우스가 올라가 있는지 기억하는 변수
const hoveredMenuId = ref(null)

// 네비게이션에 띄울 데이터 목록
const menus = [
  {
    id: 1,
    title: '주문 관리',
    children: [
      { name: '새로운 주문', link: '/order/new' },
      { name: '배달 중', link: '/order/delivery' }
    ]
  },
  {
    id: 2,
    title: '메뉴 관리',
    children: [
      { name: '메뉴 추가', link: '/menu/add' },
      { name: '품절 처리', link: '/menu/soldout' }
    ]
  }
]
</script>

<style scoped>
/* 
  여기가 기능 동작을 위한 필수 CSS입니다!
  디자인은 빼고 '위치'를 잡기 위한 뼈대만 있습니다.
*/

/* 메뉴들을 가로로 나란히 배치 */
.nav-bar {
  display: flex;
  gap: 30px; 
  padding: 20px;
  background-color: #f0f0f0;
}

/* 대단위 메뉴 한 칸 (기준점 역할) */
.menu-item {
  position: relative; /* 서브메뉴가 튀어나올 기준점이 됨 (매우 중요) */
  cursor: pointer;
}

/* 마우스 올릴 영역 확인을 위해 살짝 굵게 */
.main-title {
  font-weight: bold;
  padding: 10px 0;
}

/* 세부단위 메뉴 (둥둥 떠야 함) */
.sub-menu {
  position: absolute; /* 주변 글씨를 밀어내지 않고 공중에 뜸 (매우 중요) */
  top: 100%; /* 대단위 글씨 바로 밑에 위치하도록 */
  left: 0;
  
  display: flex;
  flex-direction: column; /* 세부 메뉴들을 세로로 나열 */
  
  background-color: white;
  border: 1px solid #ccc;
  width: 120px;
  z-index: 999; /* 화면의 다른 요소들보다 무조건 맨 위에 오도록 */
}

/* 세부단위 안의 링크들 */
.sub-menu a {
  padding: 10px;
  text-decoration: none;
  color: black;
}
.sub-menu a:hover {
  background-color: #eee;
}
</style>