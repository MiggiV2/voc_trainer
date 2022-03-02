<template>
  <div class="dictionarys-wrapped">
    <div v-if="!dictionarys.isEmpty">
      <div class="title">
        <h2>{{ dictionarys.userName }} dictionarys!</h2>
      </div>
      <div v-if="dictionarys.content.length == 0" class="spinner-border"></div>
      <div
        class="box-wrapped"
        v-for="(item, index) in dictionarys.content"
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
    </div>
    <div v-else>
      <div class="title">
        <h2>Sorry, this user has no dictionarys!</h2>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { HOST } from "../tools/auth";
import { getCookie } from "../tools/cookie";

var urlParams = new URLSearchParams(window.location.search);
var dictionarys = reactive({
  content: [],
  userName: "...",
  isEmpty: false,
});

if (getCookie("access_token") && urlParams.has("id")) {
  load();
}

function getAvatar(id, avatar) {
  return (
    "https://cdn.discordapp.com/avatars/" + id + "/" + avatar + ".webp?size=128"
  );
}

function load() {
  fetch(HOST + "api/get/users-dictionarys?id=" + urlParams.get("id"), {
    credentails: "same-origin",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else if (response.status === 204 || response.status === 400) {
        dictionarys.isEmpty = true;
      } else {
        console.log(response.statusText);
      }
    })
    .then((dictionarysResponse) => {
      if (dictionarysResponse != null) {
        dictionarys.content = dictionarysResponse;
        if (dictionarysResponse.length != 0) {
          var userName = dictionarysResponse[0].userName;
          dictionarys.userName =
            userName.substring(0, userName.length - 5) + "'s";
          document.title = dictionarys.userName + " | Voc-Trainer";
        }
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
.dictionarys-wrapped {
  margin-bottom: 4rem;
}
.dictionarys {
  max-width: 50rem;
  margin: auto;
}
.title {
  margin: 2rem auto 3.5rem;
  text-align: center;
  color: var(--text-primary-color);
}
.dictionarys-item {
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
  margin-left: 49vw;
  margin-top: 2rem;
}
@media (max-width: 768px) {
  .title {
    margin-bottom: 2rem;
  }
}
</style>