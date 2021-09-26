<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container row">
      <div class="col-auto">
        <a href="/" title="Home">
          <House />
        </a>
      </div>
      <div v-if="getCookie('access_token')" class="col-auto">
        <a href="/train" title="Journal">
          <Journal />
        </a>
      </div>
      <div v-if="getCookie('access_token')" class="col-auto">
        <a href="/add" title="Add new dictionary">
          <Add />
        </a>
      </div>
      <div class="col"></div>
      <div class="col-auto">
        <a v-if="!getCookie('access_token')" @click="openLogin()">
          Login <Login />
        </a>
        <div v-else>
          <a
            class="nav-link dropdown-toggle"
            href="#"
            id="navbarDropdown"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
            title="You, xD"
          >
            <img
              v-if="appUser.avatarurl != null"
              class="avatar"
              :src="appUser.avatarurl"
            />
            <Person v-else />
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="#">Comming soon!</a></li>
            <li><hr class="dropdown-divider" /></li>
            <li>
              <a @click="logout()" class="dropdown-item text-danger" href="#">
                Logout <Logout />
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </nav>
  <!-- DUMMY -->
  <nav class="dummy"></nav>
</template>

<script setup>
import { getAuthURL } from "../tools/auth";
import { loadUser, getAvatarURL } from "../tools/user";
import { getCookie, setCookie, setCookieSeasson } from "../tools/cookie";
import { v4 as uuidv4 } from "uuid";
import { reactive } from "vue";

import House from "./icons/House.vue";
import Add from "./icons/Add.vue";
import Journal from "./icons/Journal.vue";
import Login from "./icons/Login.vue";
import Logout from "./icons/Logout.vue";
import Person from "./icons/Person.vue";

var appUser = reactive({
  username: String,
  avatarurl: null,
});

if (getCookie("access_token") && !getCookie("season_id")) {
  var token = getCookie("access_token");
  loadUser(token, setUser);
  setCookieSeasson("season_id", uuidv4());
}
if (getCookie("access_token") && getCookie("season_id")) {
  setUser();
}

function setUser() {
  appUser.username = localStorage.getItem("username");
  appUser.avatarurl = getAvatarURL();
}

function logout() {
  localStorage.removeItem("username");
  localStorage.removeItem("avatar");
  localStorage.removeItem("id");
  setCookie("access_token", "", -1);
  setCookie("season_id", "", -1);
  window.location = "/";
}

function openLogin() {
  var url = getAuthURL();
  var state = uuidv4();
  localStorage.setItem("state", state);
  setCookie("season_id", "", -1);
  window.location = url + "&state=" + state;
}
</script>

<style scoped>
.container {
  max-width: 95vw;
  margin: auto;
}
.dummy {
  height: 58px;
}
.navbar {
  padding: unset;
  min-height: 55px;
  position: fixed;
  margin: 0 auto;
  min-width: 100vw;
  margin-top: 0;
  padding-top: 0;
  top: 0px;
  right: 0px;
  z-index: 100;
}
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
div {
  font-family: "Courier New", Courier, monospace;
}
.avatar {
  max-height: 40px;
  border-radius: 5px;
}
.dropdown-menu.show {
  position: fixed;
  z-index: 5;
  top: unset;
  left: unset;
  right: 5px;
}
</style>