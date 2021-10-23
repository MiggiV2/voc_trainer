<template>
  <div class="container">
    <form @submit="search" onsubmit="return false">
    <div class="input-group">
      <input autofocus type="text" class="form-control" placeholder="Search term" v-model="request.query" />
      <button type="submit" class="btn btn-primary">
        <Search />
      </button>
    </div>
    </form>
  </div>
</template>

<script setup>
import { reactive } from "vue";

import Search from "./icons/Search.vue";

var urlParams = new URLSearchParams(window.location.search);
var request = reactive({
  query: "",
});

if(urlParams.has('query'))
{
  request.query = urlParams.get('query');
}

function search()
{
  //request.query = request.query.replace(/ /g,'');
  if(request.query === '')
  {
    window.location = '/';
  }
  else{
    window.location = '/search?query=' + request.query;
  }
}
</script>

<style scoped>
input:focus {
  outline:none;
  box-shadow: none;
}
.container {
  padding-top: 3rem;
}
.input-group {
  margin: 5px;
}
.form-control {
  background-color: unset;
  color: var(--text-primary-color);
}
@media (max-width: 768px) {
  .container {
    max-width: 100vw;
  }
}
</style>