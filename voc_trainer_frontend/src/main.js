import { createApp, h } from 'vue/dist/vue.esm-bundler.js';
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";

import HomeComponent from './Main.vue';
import CallBack from './CallBack.vue'
import Add from './Add.vue';
import Read from './Read.vue';
import Search from './Search.vue';

const NotFoundComponent = {
    template: '<p>Page not found</p>'
}

const routes = {
    '/': HomeComponent,
    '/callback': CallBack,
    '/add': Add,
    '/read': Read,
    '/train': HomeComponent,
    '/search': Search
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