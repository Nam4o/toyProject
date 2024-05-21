<template>
    <div class="container mt-5">
        <div class="card p-3">
            <div class="card-body">
                <!-- <form @submit="updateProfile"> -->
                    <h5 class="card-title">Member Information</h5>
                    <p class="card-text"><strong>Name:</strong> {{ name }}</p>
                    <p class="card-text"><strong>Email:</strong> {{ email }}</p>
                    <p class="card-text"><strong>Nickname:</strong>
                        <input type="text" name="nicknameFrom" id="" v-model.trim="nickname">
                        <button class="btn btn-primary" @click="nicknameCheck()">중복확인</button>
                    </p>            
                    <p class="card-text"><strong>Gender:</strong> {{ gender }}</p>
                    <p class="card-text"><strong>Address:</strong>
                        <input type="text" name="" id="" v-model.trim="address">
                    </p>
                    <button class="btn btn-primary" @click="updateProfile">수정하기</button>
                <!-- </form> -->
            </div>
        </div>
    </div>
</template>

<script setup>
import instance from '@/utils/axiosInstance';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

// ref 변수 선언

const router = useRouter();

const name = ref(null);
const email = ref(null);
const nickname = ref(null);
const gender = ref(null);
const address = ref(null);

const oldNickname = ref(null);

let isNicknameValid = ref(false);

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
        console.log(response.data);
        const data = response.data.dataBody;
        console.log(data);

        name.value = data.name;
        email.value = data.email;
        nickname.value = data.nickname;
        gender.value = data.gender;
        address.value = data.address;
        oldNickname.value = data.nickname;
    } catch (error) {
        console.log(error)
    }
};

const nicknameCheck = () => {
  const data = {
    email: email.value,
    nickname: nickname.value
  }
  console.log(data.nickname)
  console.log(nickname)
  axios({
    method: "post",
    url: "http://127.0.0.1:8080/api/member/nickname",
    data: data,
  })
  .then((response) => {
    isNicknameValid = response.data.dataBody === true?true : false;
    isNicknameValid === true?window.alert("사용 가능한 닉네임 입니다.") : window.alert("사용 불가능한 닉네임 입니다.");
  })
  .catch((error) => 
    console.log(error)
  )
}

const updateProfile = () => {
    const data = {
        nickname: nickname.value,
        address: address.value,
        // profileImage: profileImage.value
    }
    if(oldNickname.value === nickname.value || isNicknameValid === true) {
        instance({
        method: "patch",
        url: "http://127.0.0.1:8080/api/member",
        data: data
        })
        .then((response) => {
            console.log(response.data.dataBody)
            router.push({name : "profile"})
        })
        .catch((error) => {
            console.log(error)
        })
    } else {
        window.alert("회원 정보를 변경할 수 없습니다.")
    }
    
}



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
