<template>
  <Header />
  <!--<div :class="{ 'dark-theme': theme == 'dark' }">-->
    <SearchForm />
    <Preview />
  <!--</div>-->
  <Footer />
</template>

<script setup>
import Header from "./components/Header.vue";
import SearchForm from "./components/SearchForm.vue";
import Preview from "./components/Preview.vue";
import Footer from "./components/Footer.vue";

//import { theme } from "./tools/user";

document.title = "Welcome | Voc-Trainer";
</script>

<style>
#app {
  color: #2c3e50;
}
</style>
