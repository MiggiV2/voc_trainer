import { createApp, h } from 'vue/dist/vue.esm-bundler.js';
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";

import HomeComponent from './Main.vue';
import CallBack from './CallBack.vue'
import Add from './Add.vue';
import Read from './Read.vue';
import Search from './Search.vue';
import Trainer from './Train.vue';
import MyDictionarys from './MyDictionarys.vue';
import UsersDictionarys from './UsersDictionarys.vue';
import Saved from './Saved.vue';

const NotFoundComponent = {
    template: '<p>Page not found</p>'
}

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
            return routes[this.currentRoute] || NotFoundComponent
        }
    },

    render() {
        return h(this.CurrentComponent)
    }
}

const app = createApp(SimpleRouter);

app.mount('#app')