<template>
    <div>
        <h1>프로필 수정</h1>
        <Suspense>
        <template #default>
            <ProfileUpdateComponent />
        </template>
        <template #fallback>
            <div>Loading...</div>
        </template>
    </Suspense>
    <!-- <button class="btn">확인</button> -->
    </div>
</template>

<script setup>
import ProfileUpdateComponent from '@/components/ProfileUpdateComponent.vue';

</script>

<style lang="scss" scoped>

</style>