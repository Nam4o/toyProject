<template>
    <div>
        <h1>메인페이지</h1>
        <p>토큰 확인</p>
    </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { routerKey, useRouter } from "vue-router";
import { useSignStore } from "@/stores/signs";

const store = useSignStore();

axios({
        method: "get",
        url: "http://127.0.0.1:8080/api/server/test",
        headers: {
          Authorization: store.accessToken,
          Refreshtoken: store.refreshToken
        }

        // data: data,
    })
        .then((response) => {
        // store.logIn(payload);
        // router.push({ name: "login" });
        console.log(response.data)
        console.log("check")
        })
        .catch((error) => {
        console.log(error);
        });

</script>

<style lang="scss" scoped>

</style>