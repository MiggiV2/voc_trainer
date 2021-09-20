<template>
  <div class="preview-wrapped">
    <div class="preview" v-for="(item, index) in preview.content" :key="index">
      <a :href="'/read?id=' + item.id">
        <div class="row">
          <div class="col-auto">
            <h2>{{ item.name }}</h2>
          </div>
          <div class="col"></div>
          <div class="col-auto">
            <img
            v-if="(item.userAvater != null && item.userAvater.length > 10)"
              class="avatar"
              :src="getAvatar(item.userID, item.userAvater)"
            />
          </div>
        </div>
        <hr />
        <p>By {{ item.userName }}</p>
      </a>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { HOST } from "../tools/auth";

var urlParams = new URLSearchParams(window.location.search);
var query = urlParams.get("query");
var preview = reactive({
  content: [],
});

if (urlParams.has("query")) {
  sendRequest();
} else {
  window.location = "/";
}

function getAvatar(id, avatar) {
  return (
    "https://cdn.discordapp.com/avatars/" + id + "/" + avatar + ".webp?size=128"
  );
}

function sendRequest() {
  fetch(HOST + "api/search", {
    method: "POST",
    credentails: "same-origin",
    mode: "cors",
    body: JSON.stringify({
      query: query,
    }),
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
}
</script>

<style scoped>
a {
  cursor: pointer;
}
a:link {
  color: unset;
  text-decoration: none;
}
a:visited {
  color: unset;
  text-decoration: none;
}
.preview-wrapped {
  margin-bottom: 4rem;
}
.preview {
  border: solid 1px black;
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