<template>
    <div>
        <h1>프로필</h1>
        <!-- <ProfileComponent></ProfileComponent> -->
        <Suspense>
        <template #default>
            <ProfileComponent />
            
        </template>
        <template #fallback>
            <div>Loading...</div>
        </template>
    </Suspense>
    <RouterLink class="btn" :to="{name : 'edit'}">수정하기</RouterLink>
    <!-- <button class="btn btn-primary" @click="edit">수정하기</button> -->
    </div>
</template>

<script setup>
import ProfileComponent from '@/components/ProfileComponent.vue';
import { useRoute, useRouter } from "vue-router";
import { RouterLink } from 'vue-router';

const router = useRouter();

const edit = function () {
  router.push(`/edit`);
}
</script>

<style lang="scss" scoped>
.btn {
  width: 150px;
  height: 50px;
  background-color: rgba(119, 185, 252, 0.6);
  color: rgb(80, 80, 80);
  font-size: 20px;
}

</style>