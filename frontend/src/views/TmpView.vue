<template>
    <div>
        <h1>메인페이지</h1>
        <p>토큰 확인2</p>
    </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { routerKey, useRouter } from "vue-router";
import { useSignStore } from "@/stores/signs";
import instance from '@/utils/axiosInstance.js';

// import axiosInstance from "@/utils/axiosInstance";
const store = useSignStore();

// axiosInstance({
//     method: "get",
//     url: "http://127.0.0.1:8080/api/server/test"
// }).then((response) => {
//     console.log(response.data);
//     console.log("check");
// }).catch((error) => {
//     console.log(error)
// })
instance({
        method: "get",
        url: "http://127.0.0.1:8080/api/server/test",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`, // 액세스 토큰
            RefreshToken: `Bearer ${localStorage.getItem("refreshToken")}` // 리프레시 토큰
        }
        // headers: {
        //   Authorization: store.accessToken,
        //   Refreshtoken: store.refreshToken
        // }

        // data: data,
    })
        .then((response) => {
        // store.logIn(payload);
        // router.push({ name: "login" });
        console.log(response.data + "2")
        console.log("check2")
        })
        .catch((error) => {
        // const idx = error.response.data.indexOf(" ")
        // console.log(error.response.data.substring(0, idx))
        // console.log(error.response.data.substring(idx + 1))
        // store.accessToken = "Bearer " + error.response.data.substring(0, idx)
        // store.refreshToken = "Bearer " + error.response.data.substring(idx + 1);
        // store.accessToken = error.response.data;
        console.log(error);
        });

</script>

<style lang="scss" scoped>

</style>