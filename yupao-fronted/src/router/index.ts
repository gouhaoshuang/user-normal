
import {createRouter, createWebHistory} from "vue-router";
import index from "../pages/index.vue";
import team from "../pages/team.vue";
import user from "../pages/user.vue";
import search from "../pages/search.vue";
export const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            component: index
        },
        {
            path: '/team',
            component: team
        },
        {
            path: '/user',
            component: user
        },
        {
            path: '/search',
            component: search
        }
    ]
})

