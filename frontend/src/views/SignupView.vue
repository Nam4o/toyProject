<template>
    <h1>회원가입</h1>
    <SignupComponent></SignupComponent>
</template>

<script setup>
import axios from "axios";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useSignStore } from "@/stores/signs";

import SignupComponent from "@/components/SignupComponent.vue";

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

</style>