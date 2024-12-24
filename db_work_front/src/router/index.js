// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';

import Home from '../views/Home.vue';
import Register from '../views/Register.vue';
import Login from '../views/Login.vue';
import UserInfo from '../views/UserInfo.vue';
import BookSearch from '../views/BookSearch.vue';
import Error from "@/views/Error.vue";
import MissingBookRegister from "@/views/MissingBookRegister.vue";
import Orders from "@/views/Orders.vue"
import BookDetails from "@/views/BookDetails.vue";


const routes = [
    {
        path:  '/',
        redirect: '/home'
    },
    {
        path: '/home',
        name: 'home',
        component: Home
    },
    {
        path: '/404',
        name: '404',
        component: Error
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path:'/login',
        name:'Login',
        component:Login
    },
    {
        path:'/user-info',
        name:'UserInfo',
        component:UserInfo
    },
    {
        path:'/book-search',
        name:'BookSearch',
        component:BookSearch
    },
    {
        path: '/missing-book-register',
        name: 'MissingBookRegister',
        component: MissingBookRegister
    },
    {
        path: '/orders',
        name: 'Orders',
        component: Orders
    },
    {
        path: '/bookDetails/:id',
        name: 'BookDetails',
        component: BookDetails,
        props: true, // 启用 props，方便将参数直接传递给组件
    }
];


// 解决重复路由报异常问题
const originalPush = createRouter.prototype.push;
createRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err);
};

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
