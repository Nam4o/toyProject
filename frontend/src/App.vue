<template>
  <div>
    <h1>hi!</h1>
    <RouterLink :to="{ name: 'signup' }">회원가입</RouterLink>
    <br>
    <RouterLink :to="{ name: 'login'}">로그인</RouterLink>
    <br>
    <RouterLink :to="{ name: 'main'}">토큰확인</RouterLink>
    <br>
    <RouterLink :to="{ name: 'main2'}">토큰확인2</RouterLink>
    <br>
    <RouterLink :to="{ name: 'profile'}">프로필</RouterLink>
    <br>
    <button @click=logout>로그아웃</button>
    <RouterView />
      
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
import { useRouter } from "vue-router";
import { useSignStore } from "@/stores/signs";
import instance from '@/utils/axiosInstance.js';

const API_URL = "http://127.0.0.1:8080/api";
const store = useSignStore();

// function logout() {
//   console.log("zzz")
//   store.logout
// }


const logout = () => {
        instance({
          method: "post",
          url: `${API_URL}/member/logout`,
        })
          .then((response) => {
            alert("로그아웃 되었습니다!");
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");
            // 로그아웃 시 로컬스토리지를 비우고 새로고침하여 사용자 인증상태 갱신
            // router.go(0);
            // router.push({ name: "main" });
          })
          .catch((error) => {
            console.log(error);
          });
      };

</script>

<style lang="scss" scoped>

</style>