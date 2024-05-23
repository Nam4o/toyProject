import { createRouter, createWebHistory } from 'vue-router'
import SignupView from '../views/SignupView.vue'
import LoginView from '../views/LoginView.vue'
import MainView from '../views/MainView.vue'
import TmpView from '../views/TmpView.vue'
import ProfileView from '../views/ProfileView.vue'
import ProfileEditView from '../views/ProfileEditView.vue'
import ArticleView from '../views/ArticleView.vue'
import ArticleCreateView from '../views/ArticleCreateView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/signup',
      name:'signup',
      component: SignupView
    },
    {
      path:'/login',
      name:'login',
      component: LoginView
    },
    {
      path:'/main',
      name:'main',
      component: MainView
    },
    {
      path:'/main2',
      name:'main2',
      component: TmpView
    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView
    },
    {
      path: '/edit',
      name: 'edit',
      component: ProfileEditView
    },
    {
      path: '/articles',
      name: 'articles',
      component: ArticleView
    },
    {
      path: '/articles/create',
      name: 'articleCreate',
      component: ArticleCreateView
    },
    
  ]
})

export default router
