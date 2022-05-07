<template>
<Header/>
<SearchForm />
<SearchContent />
<Footer />
</template>

<script setup>
import Header from '@/components/HeaderComp.vue';
import SearchForm from '@/components/SearchFormComp.vue'
import SearchContent from '@/components/SearchContent.vue';
import Footer from '@/components/FooterComp.vue';

document.title = 'Welcome | Voc-Trainer';
</script>

<style>
#app {
  color: #2c3e50;
}
</style>
