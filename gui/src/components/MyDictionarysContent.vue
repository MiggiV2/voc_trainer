<template>
  <div class="wrapped">
    <div v-if="getCookie('access_token')">
      <div class="title">
        <h2>{{ title.content }}</h2>
      </div>
      <div
        v-if="yours.content.length == 0 && yours.hasContent"
        class="spinner-border"
      ></div>
      <div
        class="box-wrapped"
        v-for="(item, index) in yours.content"
        :key="index"
      >
        <div class="box">
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
      <div class="box-wrapped box-sm" v-if="!yours.hasContent">
        <div class="box">
          <h3>How to create my dictionary?</h3>
          <hr />
          <p>Click <a href="/add">HERE</a> or in the header <i class="bi bi-plus-circle"></i></p>
          <p>Then enter your words.</p>
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

var title = reactive({
  content: "Your dictionarys...",
});

var yours = reactive({
  content: [],
  hasContent: true,
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
      if (response.status === 200) {
        return response.json();
      } else if (response.status === 204) {
        yours.hasContent = false;
        title.content = "No dictionarys!";
      } else {
        console.log(response.statusText);
      }
    })
    .then((response) => {
      if (response != null) {
        yours.content = response;
        title.content = "Your dictionarys!";
      }
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
.box-md {
  text-align: center;
}
.box-md a:link {
  color: #0a58ca;
}
.title {
  margin: 2rem auto 3.5rem;
  text-align: center;
  color: var(--text-primary-color);
}
.avatar {
  max-height: 40px;
  border-radius: 5px;
}
.spinner-border {
  margin-left: 49vw;
  margin-top: 2rem;
}
.wrapped{
  padding-bottom: 4rem;
}
@media (max-width: 768px) {
  .title {
    margin-bottom: 2rem;
  }
}
</style>