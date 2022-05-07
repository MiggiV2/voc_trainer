<template>
    <Header />
    <SavedContent />
    <Footer />
</template>

<script setup>
import Header from '@/components/HeaderComp.vue';
import Footer from '@/components/FooterComp.vue';
import SavedContent from '@/components/SavedContent.vue';

document.title = 'Saved | Voc-Trainer';
</script>