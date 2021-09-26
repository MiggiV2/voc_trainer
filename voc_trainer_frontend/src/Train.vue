<template>
    <Header />
    <Trainer />
    <Footer />
</template>

<script setup>
import Trainer from "./components/Trainer.vue";
import Header from './components/Header.vue';
import Footer from './components/Footer.vue';

document.title = 'Train | Voc-Trainer';
</script>