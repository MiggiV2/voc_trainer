<template>
    <Header />
    <TrainCheck />
    <Tainer />
    <Footer />
</template>

<script setup>
import TrainCheck from "@/components/TrainCheckComp.vue";
import Tainer from "@/components/TainerComp.vue";
import Header from '@/components/HeaderComp.vue';
import Footer from '@/components/FooterComp.vue';

document.title = 'Train | Voc-Trainer';
</script>