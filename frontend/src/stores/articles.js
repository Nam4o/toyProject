import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useArticleStore = defineStore('article', () => {

    const API_URL = "http://127.0.0.1:8080/api/article";

  return { API_URL }
})
