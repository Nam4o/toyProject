<template>
    <div>
        <form @submit.prevent="signUp" class="signup-form">
      <div class="info-list">
        <label for="name">이름 : </label>
        <input
          type="text"
          name="name"
          id="name"
          v-model.trim="name"
          class="form-control required"
          placeholder="(필수)"
          onfocus="this.placeholder=''"
          onblur="this.placeholder='(필수)'"
        />
      </div>
      <div class="info-list">
        <label for="email">email : </label>
        <input
          type="text"
          name="email"
          id="email"
          v-model.trim="email"
          class="form-control required"
          placeholder="(필수)"
          onfocus="this.placeholder=''"
          onblur="this.placeholder='(필수)'"
        />
      </div>
      <div class="info-list">
        <label for="password1">password1 : </label>
        <input
          type="text"
          name="password1"
          id="password1"
          v-model.trim="password1"
          class="form-control required"
          placeholder="(필수)"
          onfocus="this.placeholder=''"
          onblur="this.placeholder='(필수)'"
        />
      </div>
      <div class="info-list">
        <label for="password2">password2 : </label>
        <input
          type="text"
          name="password2"
          id="password2"
          v-model.trim="password2"
          class="form-control required"
          placeholder="(필수)"
          onfocus="this.placeholder=''"
          onblur="this.placeholder='(필수)'"
        />
      </div>
      <div class="info-list">
        <label for="nickname">nickname : </label>
        <input
          type="text"
          name="nickname"
          id="nickname"
          v-model.trim="nickname"
          class="form-control required"
          placeholder="(필수)"
          onfocus="this.placeholder=''"
          onblur="this.placeholder='(필수)'"
        />
        <button id="btn" style="width: 100px; margin: 0px  0px 0px 20px; border-radius: 10%;">중복확인</button>
      </div>
      <div class="info-list">
        <label for="address">address : </label>
        <input
          type="text"
          name="address"
          id="address"
          v-model.trim="address"
          class="form-control required"
          placeholder="(필수)"
          onfocus="this.placeholder=''"
          onblur="this.placeholder='(필수)'"
        />
      </div>
      <div class="info-list">
        <label for="gender">gender : </label>
        <input
          type="text"
          name="gender"
          id="gender"
          v-model.trim="gender"
          class="form-control required"
          placeholder="(필수)"
          onfocus="this.placeholder=''"
          onblur="this.placeholder='(필수)'"
        />
      </div>
      <div class="info-list">
        <label for="phone">phone : </label>
        <input
          type="text"
          name="phone"
          id="phone"
          v-model.trim="phone"
          class="form-control required"
          placeholder="(필수)"
          onfocus="this.placeholder=''"
          onblur="this.placeholder='(필수)'"
        />
      </div>
      <div class="info-list">
        <input id="btn" class="btn btn-info" type="submit" value="회원 가입" />
      </div>

      </form>



    </div>
</template>

<script setup>
import axios from "axios";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useSignStore } from "@/stores/signs";

const router = useRouter();
const store = useSignStore();

const name = ref(null);
const email = ref(null);
const password1 = ref(null);
const password2 = ref(null);
const nickname = ref(null);
const gender = ref(null);
const address = ref(null);
const phone = ref(null);


const signUp = () => {
    const data = {
        name: name.value,
        password: password1.value,
        // password2: password2.value,
        email: email.value,
        nickname: nickname.value,
        gender: gender.value,
        address: address.value,
        phone: phone.value
    };
    



    const payload = {
        email: email.value,
        password: password1.value,
    };


    axios({
        method: "post",
        url: "http://127.0.0.1:8080/api/member/signup",
        data: data,
    })
        .then((response) => {
        store.logIn(payload);
        router.push({ name: "login" });
        })
        .catch((error) => {
        console.log(error);
        });
}
</script>

<style lang="scss" scoped>
* {
  font-family: "IBM Plex Sans KR", sans-serif;
  font-family: "Orbit", sans-serif;
}
h1 {
  padding-bottom: 20px;
  margin-bottom: 30px;
  border-bottom: 5px rgba(13, 172, 220, 0.7) solid;
}
.signup-form {
  display: flex;
  flex-direction: column;
  font-weight: bolder;
  font-size: 20px;
}
.signup-form div {
  margin: 10px 0px;
}

.signup-form label {
  width: 150px;
}
.signup-form input {
  width: 300px;
  border-top: 0px;
  border-right: 0px;
  border-left: 0px;
  border-bottom: lightgray solid 1px;
  font-size: 20px;
  font-weight: bolder;
}

.signup-form input {
  width: 300px;
  border-top: 0px;
  border-right: 0px;
  border-left: 0px;
  border-bottom-left-radius: 0%;
  border-bottom-right-radius: 0%;
  border-bottom: lightgray solid 1px;
  border-color: rgb(119, 185, 252);
  font-size: 20px;
  font-weight: bolder;
}

.info-list {
  display: flex;
}

.required::placeholder {
  color: crimson;
}

#btn {
  border: 1px solid rgba(119, 185, 252, 0.1);
  background-color: rgba(119, 185, 252, 0.6);
  color: rgb(60, 60, 60);
  font-size: 17px;
  font-weight: bolder;
  margin-top: 15px;
  width: 450px;
  height: 50px;
}


</style>