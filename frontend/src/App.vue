<template>
  <div>
    <h1>hi!</h1>
    <RouterLink :to="{ name: 'signup' }">회원가입</RouterLink>
    <br>
    <RouterLink :to="{ name: 'login'}">로그인</RouterLink>
    <br>
    <RouterLink :to="{ name: 'main'}">토큰확인</RouterLink>
    <RouterView />
      
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
import { useRouter } from "vue-router";
</script>

<style lang="scss" scoped>

</style>