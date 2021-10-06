<template>
    <Header />
    <NoPageContent />
    <Footer />
</template>

<script setup>
import Header from './components/Header.vue';
import Footer from './components/Footer.vue';
import NoPageContent from './components/NoPageContent.vue'

document.title = '404 | Voc-Trainer';
</script>