import { createApp, h } from 'vue/dist/vue.esm-bundler.js';
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";

import HomeComponent from './Main.vue';
import CallBack from './CallBack.vue'

const NotFoundComponent = {
    template: '<p>Page not found</p>'
}
const AboutComponent = {
    template: '<p>About page</p>'
}

const routes = {
    '/': HomeComponent,
    '/about': AboutComponent,
    '/callback': CallBack
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

createApp(SimpleRouter).mount('#app')