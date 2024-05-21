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
    <button class="btn btn-primary" @click="edit">수정하기</button>
    </div>
</template>

<script setup>
import ProfileComponent from '@/components/ProfileComponent.vue';
import { useRoute, useRouter } from "vue-router";

const router = useRouter();

const edit = function () {
  router.push(`/edit`);
}
</script>

<style lang="scss" scoped>

</style>