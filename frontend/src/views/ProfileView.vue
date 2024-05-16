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
    </div>
</template>

<script setup>
import ProfileComponent from '@/components/ProfileComponent.vue';

</script>

<style lang="scss" scoped>

</style>