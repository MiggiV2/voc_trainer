import { createApp, h } from 'vue/dist/vue.esm-bundler.js';
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";
import "bootstrap-icons/font/bootstrap-icons.css";

import HomeComponent from './Main.vue';
import CallBack from './CallBack.vue'
import Add from './Add.vue';
import Read from './Read.vue';
import Search from './Search.vue';
import Trainer from './Train.vue';
import MyDictionarys from './MyDictionarys.vue';
import UsersDictionarys from './UsersDictionarys.vue';
import Saved from './Saved.vue';
import NoPage from './NoPage.vue';

require('@/assets/css/style.css');

const routes = {
    '/': HomeComponent,
    '/callback': CallBack,
    '/add': Add,
    '/read': Read,
    '/train': Trainer,
    '/search': Search,
    '/my-dictionarys': MyDictionarys,
    '/users': UsersDictionarys,
    '/saved': Saved,
}

const SimpleRouter = {
    data: () => ({
        currentRoute: window.location.pathname
    }),

    computed: {
        CurrentComponent() {
            return routes[this.currentRoute] || NoPage
        }
    },

    render() {
        return h(this.CurrentComponent)
    }
}

if (localStorage.getItem('theme') == null) {
    var theme = getMediaPreference();
    console.log(localStorage.getItem('theme'));
    localStorage.setItem('theme', theme);
    document.documentElement.className = theme;
}

const app = createApp(SimpleRouter);

app.mount('#app')

function getMediaPreference() {
    const hasDarkPreference = window.matchMedia("(prefers-color-scheme: dark)").matches;
    if (hasDarkPreference) {
        return "dark-mode";
    } else {
        return "light-mode";
    }
}