import {createRouter, createWebHistory} from "vue-router";
import index from "../pages/index.vue";
import team from "../pages/team.vue";
import user from "../pages/user.vue";
const router = createRouter({
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
        }
    ]
})

export default router;