import { createApp, h } from 'vue/dist/vue.esm-bundler.js';
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";
import "bootstrap-icons/font/bootstrap-icons.css";

import HomeComponent from './MainView.vue';
import CallBack from './CallBackView.vue'
import Add from './AddView.vue';
import Read from './ReadView.vue';
import Search from './SearchView.vue';
import Trainer from './TrainView.vue';
import MyDictionarys from './MyDictionarysView.vue';
import UsersDictionarys from './UsersDictionarysView.vue';
import Saved from './SavedView.vue';
import NoPage from './NoPageView.vue';

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