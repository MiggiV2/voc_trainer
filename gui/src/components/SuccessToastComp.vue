<template>
  <div v-if="props.success" class="boxes success">
    <h2>{{props.content}}</h2>
    <hr />
    <p>Redirect in 5sec</p>
  </div>
  <div v-else class="boxes error">
    <h2>{{props.content}}</h2>
    <hr />
    <p>Redirect in 5sec</p>
  </div>
</template>

<script setup>
import { defineProps } from "vue";
const props = defineProps({
  content: String,
  success: Boolean
});
</script>

<style scoped>
.boxes {
  border: solid 1px white;
  border-radius: 10px 5px 10px 5px;
  max-width: 30rem;
  margin: auto;
  margin-top: 4rem;
  padding: 20px;
}
.success {
  background: rgb(0, 172, 0);
  color: white;
}
.error{
  background: rgb(235, 23, 23);
  color: white;
}
</style>