<template>
  <Header />
  <!--<div :class="{ 'dark-theme': theme == 'dark' }">-->
    <SearchForm />
    <Preview />
  <!--</div>-->
  <Footer />
</template>

<script setup>
import Header from "@/components/HeaderComp.vue";
import SearchForm from "@/components/SearchFormComp.vue";
import Preview from "@/components/PreviewComp.vue";
import Footer from "@/components/FooterComp.vue";

//import { theme } from "./tools/user";

document.title = "Welcome | Voc-Trainer";
</script>

<style>
#app {
  color: #2c3e50;
}
</style>
