<template>
  <el-menu class="menu" :default-active="activeIndex" mode="horizontal">
    <el-menu-item index="1" @click="goToHome">首页</el-menu-item>
    <el-menu-item index="2" @click="goToBookSearch">书籍搜索</el-menu-item>
    <el-menu-item index="3" @click="goToMissingBookRegister">缺书登记</el-menu-item>
    <el-menu-item index="4" @click="goToLogout">退出登录</el-menu-item>
    <el-menu-item index="5" @click="goToUserInfo">个人中心</el-menu-item>
  </el-menu>
</template>

<script setup>
import {useRouter} from 'vue-router';
import {ref} from 'vue';

const router = useRouter();
const activeIndex = ref('1'); // 可以根据实际情况动态设置当前激活的菜单项

// 导航到不同页面
const goToHome = () => {
  router.push('/');
};
const goToBookSearch = () => {
  router.push('/book-search');
};
const goToMissingBookRegister = () => {
  router.push('/missing-book-register');
}
const goToUserInfo = () => {
  router.push('/user-info');
};
const goToLogout = () => {
  localStorage.setItem('isLoggedIn', 'false');
  router.push('/login');
};
</script>

<style scoped>
.menu {
  display: flex;
  margin-left: auto;
  margin-right: 40px;
  width: auto;
  position: relative;
  z-index: 1001;
}
</style>
