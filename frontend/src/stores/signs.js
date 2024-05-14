import { ref, computed, onUpdated, onMounted } from "vue";
import { defineStore } from "pinia";
import { useRouter } from "vue-router";
import axios from "axios";
import instance from '@/utils/axiosInstance.js';

export const useSignStore = defineStore(
    "sign",
    () => {
      const API_URL = "http://127.0.0.1:8080/api";
  
      const router = useRouter();
  
      const accessToken = ref(null);
      const refreshToken = ref(null);
      const name = ref(null);
      const password = ref(null);
      const email = ref(null);
      const user = ref(null);
  
      const saveInfo = () => {
        instance({
          method: "get",
          url: `${API_URL}/member/signup`,
          headers: {
            // Authorization: `Bearer ${token.value}`,
          },
        })
          .then((res) => {
            console.log(res.data);
            user.value = res.data;
          })
          .catch((err) => {
            console.log(err);
          });
      };
  
      const logIn = (payload) => {
        email.value = payload.email;
        password.value = payload.password;
  
        axios({
          method: "post",
          url: `${API_URL}/member/login`,
          data: {
            email: email.value,
            password: password.value,
          },
        })
          .then((response) => {
            accessToken.value = "Bearer " + response.data.dataBody.accessToken;
            refreshToken.value = "Bearer " + response.data.dataBody.refreshToken;
            localStorage.setItem('accessToken', response.data.dataBody.accessToken);
            localStorage.setItem('refreshToken', response.data.dataBody.refreshToken);
            
            console.log(response.data);
            // saveInfo();
            
            window.alert(`환영합니다. ${email.value} 님!`);
            // router.push({ name: "main" });
          })
          .catch((error) => {
            window.alert("잘못 된 접근 방식입니다.");
            email.value = null;
            password.value = null;
            console.log(error);
          });
      };
  
      const isLogin = computed(() => {
        if (accessToken.value === null) {
          return false;
        } else {
          return true;
        }
      });
  
      const logout = () => {
        instance({
          method: "post",
          url: `${API_URL}/member/logout/`,
          data: {
            // username: username.value,
            // password: password.value,
          },
          // headers: {
          //   // Authorization: `Token ${token.value}`,
          // },
        })
          .then((response) => {
            alert("로그아웃 되었습니다!");
            localStorage.removeItem("Authorization");
            localStorage.removeItem("RefreshToken");
            // 로그아웃 시 로컬스토리지를 비우고 새로고침하여 사용자 인증상태 갱신
            // router.go(0);
            // router.push({ name: "main" });
          })
          .catch((error) => {
            console.log(error);
          });
      };
  
      return {
        accessToken,
        refreshToken,
        logIn,
        isLogin,
        logout,
        name,
        password,
        API_URL,
        user,
        saveInfo,
      };
    },
    { persist: true }
  );
  