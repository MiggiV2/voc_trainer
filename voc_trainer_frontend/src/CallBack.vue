<template>
  <Header />
</template>

<script setup>
import Header from "./components/Header.vue";
import { getURLHashParams } from "./tools/auth";
import { setCookieInSec } from "./tools/cookie";

var params = getURLHashParams();

if (typeof params["access_token"] !== "undefined") {
  if (params["state"] != localStorage.getItem("state")) {
    alert("XSRF");
  } else {
    localStorage.removeItem("state");
    setCookieInSec('access_token', params['access_token'], params['expires_in']);
    window.location = '/';
  }
}
</script>

<style scoped>
</style>