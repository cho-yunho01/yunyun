<script setup>

</script>

<template>
  <nav>
    <router-link to = "/api/customer/create">사용자 회원가입</router-link><br />
    <router-link to = "/api/customer/login">사용자 로그인</router-link><br />
    <router-link to = "/api/customer/id">사용자 ID</router-link>
  </nav>
  <div>
    <router-view />
  </div>
</template>