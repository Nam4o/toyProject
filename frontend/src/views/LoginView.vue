<template>
    <h1>로그인</h1>
  <div class="form-container">
    <form class="login-form" @submit.prevent="logIn">
      <div>
        <label style="margin-bottom: 20px" for="email">아이디</label>
        <input
          class="form-control"
          type="text"
          name="email"
          id="email"
          v-model="email"
        />
      </div>
      <div>
        <label style="margin-bottom: 20px" for="password"> 비밀번호</label>
        <input
          class="form-control"
          type="password"
          name="password"
          id="password"
          v-model="password"
        />
      </div>
      <input class="btn btn-info" type="submit" value="로그인" />
    </form>
  </div>

</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { routerKey, useRouter } from "vue-router";
import { useSignStore } from "@/stores/signs";

const store = useSignStore();
const email = ref(null);
const password = ref(null);

const logIn = () => {
  const payload = {
    email: email.value,
    password: password.value,
  };
  store.logIn(payload);
};


</script>

<style lang="scss" scoped>

</style>