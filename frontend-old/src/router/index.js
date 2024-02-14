import { createRouter, createWebHistory } from 'vue-router'
import ProjectsPage from "@/views/projects/ProjectsPage.vue"
import AboutPage from "@/views/about/AboutPage.vue"
import NotFoundPage from "@/views/notfound/NotFoundPage.vue"

const routes = [
    {
        path: '/', redirect: { name: 'projects' }
    },
    {
        path: '/projects',
        name: 'projects',
        component: ProjectsPage
    },
    {
        path: '/about',
        name: 'about',
        component: AboutPage
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'not-found',
        component: NotFoundPage
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})


export default router