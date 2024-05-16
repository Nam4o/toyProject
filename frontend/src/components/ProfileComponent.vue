<template>
    <div>
        <p>{{ name }}</p>
        <p>{{ email }}</p>
        <p>{{ nickname }}</p>
        <p>{{ gender }}</p>
        <p>{{ address }}</p>
    </div>
</template>

<script setup>
import instance from '@/utils/axiosInstance';
import { ref, onMounted } from 'vue';

// ref 변수 선언
const name = ref(null);
const email = ref(null);
const nickname = ref(null);
const gender = ref(null);
const address = ref(null);

// API 호출
const fetchData = async () => {
    try {
        const response = await instance({
            method: "get",
            url: "http://127.0.0.1:8080/api/member",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("accessToken")}`, // 액세스 토큰
                RefreshToken: `Bearer ${localStorage.getItem("refreshToken")}` // 리프레시 토큰
            }
        });

        // 응답 데이터를 상태 변수에 저장
        console.log(response.data)
        const data = response.data.dataBody;
        console.log(data)

        name.value = data.name;
        email.value = data.email;
        nickname.value = data.nickname;
        gender.value = data.gender;
        address.value = data.address;
    } catch (error) {
        console.error("Error fetching data:", error);
    }
};

// 컴포넌트가 마운트될 때 데이터 가져오기
onMounted(() => {
    fetchData();
});
</script>

<style lang="scss" scoped>
/* 스타일 정의 */
</style>
