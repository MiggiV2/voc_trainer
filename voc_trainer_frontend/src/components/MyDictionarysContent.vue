<template>
  <div class="yours-wrapped">
    <div v-if="getCookie('access_token')">
      <div class="title">
        <h2>Your dictionarys!</h2>
      </div>
      <div v-if="yours.content.length == 0" class="spinner-border"></div>
      <div class="yours" v-for="(item, index) in yours.content" :key="index">
        <div class="yours-item">
          <a :href="'/read?id=' + item.id">
            <div class="row">
              <div class="col-auto">
                <h2>{{ item.name }}</h2>
              </div>
              <div class="col"></div>
              <div class="col-auto">
                <img
                  v-if="item.userAvater != null && item.userAvater.length > 10"
                  class="avatar"
                  :src="getAvatar(item.userID, item.userAvater)"
                  :title="item.userName"
                />
              </div>
            </div>
            <hr />
            <p>created on {{ item.createdAd }}</p>
          </a>
        </div>
      </div>
    </div>
    <div v-else>
      <div class="title">
        <h2>You need to login first!</h2>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { HOST } from "../tools/auth";
import { getCookie } from "../tools/cookie";

var yours = reactive({
  content: [],
});

if (getCookie("access_token")) {
  load();
} else {
  setTimeout(function () {
    window.location = "/";
  }, 5000);
}

function getAvatar(id, avatar) {
  return (
    "https://cdn.discordapp.com/avatars/" + id + "/" + avatar + ".webp?size=128"
  );
}

function load() {
  fetch(HOST + "api/get/my-dictionarys", {
    credentails: "same-origin",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + getCookie("access_token"),
    },
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        console.log(response.statusText);
      }
    })
    .then((yoursResponse) => {
      yours.content = yoursResponse;
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
.yours-wrapped {
  margin-bottom: 4rem;
}
.yours {
  max-width: 50rem;
  margin: auto;
}
.title {
  margin: 2rem auto 3.5rem;
  text-align: center;
}
.yours-item {
  border: solid 1px black;
  border-radius: 5px 10px 5px 10px;
  padding: 15px;
  max-width: 98%;
  margin: 2rem auto 2rem;
  cursor: pointer;
}
.avatar {
  max-height: 40px;
  border-radius: 5px;
}
.spinner-border {
  margin-left: 45vw;
  margin-top: 2rem;
}
@media (max-width: 768px) {
  .title {
    margin-bottom: 2rem;
  }
}
</style>