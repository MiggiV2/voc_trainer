<template>
    <Header />
    <MyDictionarysContent />
    <Footer />
</template>

<script setup>
import Header from '@/components/HeaderComp.vue';
import Footer from '@/components/FooterComp.vue';
import MyDictionarysContent from './components/MyDictionarysContent.vue';

document.title = 'Yours | Voc-Trainer';
</script>