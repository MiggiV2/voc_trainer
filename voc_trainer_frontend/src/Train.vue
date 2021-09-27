<template>
    <Header />
    <TrainCheck />
    <Tainer />
    <Footer />
</template>

<script setup>
import TrainCheck from "./components/TrainCheck.vue";
import Tainer from "./components/Tainer.vue";
import Header from './components/Header.vue';
import Footer from './components/Footer.vue';

document.title = 'Train | Voc-Trainer';
</script>