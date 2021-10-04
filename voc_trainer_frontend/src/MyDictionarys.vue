<template>
    <Header />
    <MyDictionarysContent />
    <Footer />
</template>

<script setup>
import Header from './components/Header.vue';
import Footer from './components/Footer.vue';
import MyDictionarysContent from './components/MyDictionarysContent.vue';

document.title = 'Yours | Voc-Trainer';
</script>