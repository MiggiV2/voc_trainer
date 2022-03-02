<template>
<Header/>
<SearchForm />
<SearchContent />
<Footer />
</template>

<script setup>
import Header from './components/Header.vue';
import SearchForm from './components/SearchForm.vue'
import SearchContent from './components/SearchContent.vue';
import Footer from './components/Footer.vue';

document.title = 'Welcome | Voc-Trainer';
</script>

<style>
#app {
  color: #2c3e50;
}
</style>
