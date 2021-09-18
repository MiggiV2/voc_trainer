<template>
  <div class="preview" v-for="(item, index) in preview.content" :key="index">
    <div @click="open(item.id)">
      <div class="row">
        <div class="col-auto">
          <h2>{{ item.name }}</h2>
        </div>
        <div class="col"></div>
        <div class="col-auto">
          <img class="avatar" :src="getAvatar(item.userID, item.userAvater)" />
        </div>
      </div>
      <hr />
      <p>By {{ item.userName }}</p>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { HOST } from "../tools/auth";

var preview = reactive({
  content: [],
});

function getAvatar(id, avatar) {
  return (
    "https://cdn.discordapp.com/avatars/" + id + "/" + avatar + ".webp?size=128"
  );
}

function open(dictionaryID)
{
    window.location = '/read?id=' + dictionaryID;
}

fetch(HOST + "api/get/preview", {
  credentails: "same-origin",
  mode: "cors",
  headers: {
    "Content-Type": "application/json",
  },
})
  .then((response) => {
    if (response.ok) {
      return response.json();
    } else {
      console.log(response.statusText);
    }
  })
  .then((previewResponse) => {
    preview.content = previewResponse;
  });
</script>

<style scoped>
.preview {
  border: solid 1px rgb(0, 113, 243);
  border-radius: 5px 10px 5px 10px;
  padding: 15px;
  max-width: 50rem;
  margin: 2rem auto 2rem;
  cursor: pointer;
}
.avatar {
  max-height: 40px;
  border-radius: 5px;
}
</style>