<template>
    <Header />
    <UsersDictionarysContent />
    <Footer />
</template>

<script setup>
import Header from '@/components/HeaderComp.vue';
import Footer from '@/components/FooterComp.vue';
import UsersDictionarysContent from "@/components/UsersDictionarysContent.vue";

document.title = 'Users | Voc-Trainer';
</script>