<template>
    <div>
      <h1>📝 게시판</h1>
      <div class="article-list-header">
        <h2>글 목록</h2>
        <RouterLink class="btn" :to="{ name: 'articleCreate' }"
          >게시물 생성</RouterLink>
      </div>
      <div v-for="article in articles">
        <!-- <div @click="godetail(article)"> -->
        <div>
          <!-- <h5>{{ article.title }} [{{ article.comment_count }}]</h5> -->
          <h5 type="button">{{ article.title }}</h5>
          <p
            style="
              font-size: smaller;
              color: gray;
              font-weight: lighter;
              margin: 0px 0px;
            "
          >
            {{ article.createdAt.slice(0, 10) }} {{ article.writer }}
          </p>
          <hr />
        </div>
      </div>
      <span type="button" v-for="number in pageSize" @click="goPage(number)">{{ number }}</span>
    </div>
  </template>
  
  <script setup>
  import { useArticleStore } from "@/stores/articles";
  import { RouterLink, useRouter } from "vue-router";
  import { ref, onMounted, onUpdated } from "vue";
  import axios from "axios";
  import instance from "@/utils/axiosInstance";
  
  const store = useArticleStore();
  const articles = ref(null);
  let page = ref(1);
  let pageSize = ref(null);

  const router = useRouter();
//   const godetail = function (article) {
//     router.push(`/article/detail/${article.id}`);
//   };

  const goPage = (newPage) => {

    page.value = newPage;

    instance({
      method: "get",
      url: `${store.API_URL}?page=${page.value}`,
    })
      .then((response) => {
        articles.value = response.data.dataBody.articleList.content;
        console.log(articles.value);
        pageSize.value = response.data.dataBody.pageSize % 10 + 1;
      })
      .catch((err) => {
        console.log(err);
      });
  } 

  onMounted(() => {
    instance({
      method: "get",
      url: `${store.API_URL}?page=${page.value}`,
    })
      .then((response) => {
        articles.value = response.data.dataBody.articleList.content;
        console.log(articles.value);
        pageSize.value = response.data.dataBody.pageSize % 10 + 1;
      })
      .catch((err) => {
        console.log(err);
      });
  });
  </script>
  
  <style scoped>
  * {
    font-family: "IBM Plex Sans KR", sans-serif;
    font-family: "Orbit", sans-serif;
    font-weight: bolder;
  }
  h1 {
    padding-bottom: 20px;
    margin-bottom: 30px;
    border-bottom: 5px rgba(13, 172, 220, 0.7) solid;
  }
  .article-list-header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;
  }
  
  .btn {
    width: 150px;
    height: 50px;
    background-color: rgba(119, 185, 252, 0.6);
    color: rgb(80, 80, 80);
    font-size: 20px;
  }
  </style>