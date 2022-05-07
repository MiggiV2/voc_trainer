<template>
    <Header />
    <NoPageContent />
    <Footer />
</template>

<script setup>
import Header from '@/components/HeaderComp.vue';
import Footer from '@/components/FooterComp.vue';
import NoPageContent from '@/components/NoPageContent.vue'

document.title = '404 | Voc-Trainer';
</script>