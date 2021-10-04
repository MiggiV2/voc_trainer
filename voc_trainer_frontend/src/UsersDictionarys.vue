<template>
    <Header />
    <UsersDictionarysContent />
    <Footer />
</template>

<script setup>
import Header from './components/Header.vue';
import Footer from './components/Footer.vue';
import UsersDictionarysContent from "./components/UsersDictionarysContent";

document.title = 'Users | Voc-Trainer';
</script>