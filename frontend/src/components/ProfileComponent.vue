<template>
    <div class="container mt-5">
        <div class="card p-3">
            <div class="card-body">
                <h5 class="card-title">Member Information</h5>
                <p class="card-text"><strong>Name:</strong> {{ name }}</p>
                <p class="card-text"><strong>Email:</strong> {{ email }}</p>
                <p class="card-text"><strong>Nickname:</strong> {{ nickname }}</p>
                <p class="card-text"><strong>Gender:</strong> {{ gender }}</p>
                <p class="card-text"><strong>Address:</strong> {{ address }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
import instance from '@/utils/axiosInstance';
import { ref, onMounted } from 'vue';
import { useSignStore } from '@/stores/signs';

// ref 변수 선언
const name = ref(null);
const email = ref(null);
const nickname = ref(null);
const gender = ref(null);
const address = ref(null);

const store = useSignStore();

// API 호출
const fetchData = async () => {
    try {
        const response = await instance({
            method: "get",
            url: store.API_URL,
            headers: {
                Authorization: `Bearer ${localStorage.getItem("accessToken")}`, // 액세스 토큰
                RefreshToken: `Bearer ${localStorage.getItem("refreshToken")}` // 리프레시 토큰
            }
        });

        // 응답 데이터를 상태 변수에 저장
        console.log(response.data);
        const data = response.data.dataBody;
        console.log(data);

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
.container {
    max-width: 600px;
    margin: 0 auto;
}

.card {
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card-title {
    font-size: 1.5rem;
    margin-bottom: 1rem;
}

.card-text {
    font-size: 1rem;
    margin-bottom: 0.5rem;
}
</style>
