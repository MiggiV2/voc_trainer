<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container row">
      <div class="col-auto">
        <a href="/">HOME</a>
      </div>
      <div class="col-auto">SEARCH</div>
      <div class="col-auto">
        <a href="/add">ADD</a>
      </div>
      <div class="col"></div>
      <div class="col-auto">
        <a v-if="appUser.avatarurl == null" @click="openLogin()">LOGIN</a>
        <div v-else>
          <a
            class="nav-link dropdown-toggle"
            href="#"
            id="navbarDropdown"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            <img class="avatar" :src="appUser.avatarurl" />
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="#">Comming soon!</a></li>
            <li><a class="dropdown-item" href="#">Comming soon!</a></li>
            <li><hr class="dropdown-divider" /></li>
            <li>
              <a @click="logout()" class="dropdown-item text-danger" href="#"
                >Logout</a
              >
            </li>
          </ul>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { getAuthURL } from "../tools/auth";
import { loadUser, getAvatarURL } from "../tools/user";
import { getCookie, setCookie } from "../tools/cookie";
import { v4 as uuidv4 } from "uuid";
import { reactive } from "vue";

var appUser = reactive({
  username: String,
  avatarurl: null,
});

if (getCookie("access_token") && localStorage.getItem("avatar") === null) {
  var token = getCookie("access_token");
  loadUser(token, setUser);
}
if (getCookie("access_token") && localStorage.getItem("avatar") !== null) {
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
  window.location = "/";
}

function openLogin() {
  var url = getAuthURL();
  var state = uuidv4();
  localStorage.setItem("state", state);
  window.location = url + "&state=" + state;
}
</script>

<style scoped>
.container {
  max-width: 95vw;
  padding: 5px 20px 5px 20px;
  margin: auto;
}
.navbar {
  padding: unset;
  min-height: 80px;
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
  max-height: 50px;
  border-radius: 5px;
}
.dropdown-menu.show {
  position: fixed;
  z-index: 5;
  top: unset;
  left: unset;
}
</style>