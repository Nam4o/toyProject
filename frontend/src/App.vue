<template>
  <h1>hi!</h1>
    <nav class="container-fluid">
      <nav class="nav-container">
      <div>
        <RouterLink :to="{ name: 'signup' }">회원가입</RouterLink>
        <span> | </span>
        <RouterLink :to="{ name: 'login'}">로그인</RouterLink>
        <span> | </span>
        <RouterLink :to="{ name: 'main'}">토큰확인</RouterLink>
        <span> | </span>
        <RouterLink :to="{ name: 'main2'}">토큰확인2</RouterLink>
        <span> | </span>
        <RouterLink :to="{ name: 'profile'}">프로필</RouterLink>
        <span> | </span>
        <RouterLink :to="{ name: 'articles'}">게시판</RouterLink>
        <span> | </span>
      </div>
        <button @click=logout>로그아웃</button>
      </nav>
    </nav>
  <div class="base">
    <RouterView />
      
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from "vue-router";
import { useRouter } from "vue-router";
import { useSignStore } from "@/stores/signs";
import instance from '@/utils/axiosInstance.js';

const store = useSignStore();


const logout = () => {
        instance({
          method: "post",
          url: `${store.API_URL}/logout`,
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
.base {
  margin: 5% ;
  // display: flex;
  // flex-direction: column;
  // align-items: center;
  // width: 90%;
  justify-content: center;
}
.container-fluid {
  background-color: rgb(119, 185, 252);
  color: rgba(255, 255, 255);
  font-weight: bold;
  font-family: "Orbit", sans-serif;
}
.nav-auth {
  height: 40px;
  font-family: "IBM Plex Sans KR", sans-serif;
  font-family: "Orbit", sans-serif;
  margin: 0px 14%;
}
.logined {
  display: flex;
  justify-content: space-between;
  margin: 0px 10px;
}
.greeting {
  display: flex;
  align-items: center;
  margin: 0px 20px;
  font-size: larger;
  font-weight: bolder;
}

.sign {
  display: flex;
  justify-content: end;
  margin: 0px 20px;
}
.sign a {
  margin: 8px;
  font-size: 15px;
}
.nav-container {
  display: flex;
  justify-content: space-between;
  margin: 0px 14%;
  height: 80px;
  padding: 0px;
}

.navbar-brand {
  display: flex;
  margin: 10px 30px;
  font-size: 30px;
  text-align: center;
  align-items: center;
}

.navbar-list {
  display: flex;
  margin: 0px 30px 0px 0px;
}

.navbar-list * {
  display: flex;
  margin: 10px 5px;
  font-size: 20px;
  align-items: center;
}

.router-view {
  margin: 50px 15% 80px;

  /* display: flex; */
  /* align-items: center; */
}
.back {
  font-family: "IBM Plex Sans KR", sans-serif;
  padding: 4px 0px 0px 0px;
  margin-right: 5px;
  font-weight: bolder;
}
</style>