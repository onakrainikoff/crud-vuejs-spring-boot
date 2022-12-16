import {createRouter, createWebHistory} from 'vue-router'
import ProjectsPage from "../views/projects/ProjectsPage.vue"
import TasksPage from "../views/tasks/TasksPage.vue"

const routes = [
    {
        path: '/',
        name: 'projects',
        component: ProjectsPage
    },
    {
        path: '/tasks', 
        name: 'tasks',
        component: TasksPage
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})


export default router