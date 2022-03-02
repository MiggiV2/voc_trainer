<template>
    <Header />
    <SavedContent />
    <Footer />
</template>

<script setup>
import Header from './components/Header.vue';
import Footer from './components/Footer.vue';
import SavedContent from './components/SavedContent.vue';

document.title = 'Saved | Voc-Trainer';
</script>