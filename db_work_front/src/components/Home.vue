<template>
  <el-container class="home-container">
    <!-- 头部 -->
    <el-header class="home-header">
      <el-row class="home-row">
        <el-col :span="6">
          <h1 class="logo">网上书店</h1>
        </el-col>
        <el-col :span="18">
          <el-menu :default-active="activeMenu" mode="horizontal">
            <el-menu-item index="1" @click="goToHome">首页</el-menu-item>
            <el-menu-item index="2" @click="goToCatalog">书籍目录</el-menu-item>
            <el-menu-item index="3" @click="goToCart">购物车</el-menu-item>
            <el-menu-item index="4" @click="goToAccount">个人中心</el-menu-item>
            <el-menu-item index="5" @click="goToLogin">登录</el-menu-item>
          </el-menu>
        </el-col>
      </el-row>
    </el-header>

    <!-- 轮播图或推荐区域 -->
    <el-main style="flex-grow: 1; padding: 20px;">
      <el-carousel :interval="5000" arrow="always">
        <el-carousel-item>
          <img src="https://via.placeholder.com/1200x400/ff7f7f/333333?text=欢迎来到网上书店" alt="banner1" />
        </el-carousel-item>
        <el-carousel-item>
          <img src="https://via.placeholder.com/1200x400/7f7fff/333333?text=畅销书籍推荐" alt="banner2" />
        </el-carousel-item>
        <el-carousel-item>
          <img src="https://via.placeholder.com/1200x400/7fff7f/333333?text=热卖图书" alt="banner3" />
        </el-carousel-item>
      </el-carousel>

      <el-row :gutter="20" class="book-list">
        <el-col v-for="book in books" :key="book.id" :span="6">
          <el-card :body-style="{ padding: '20px' }" class="book-card">
            <img :src="book.img" class="book-img" />
            <div class="book-info">
              <h3>{{ book.name }}</h3>
              <p>作者：{{ book.author }}</p>
              <p>价格：￥{{ book.price }}</p>
              <el-button @click="addToCart(book.id)" type="primary">加入购物车</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-main>

    <!-- 底部信息 -->
    <el-footer style="background-color: #f1f1f1; padding: 20px 0;">
      <el-row>
        <el-col :span="12">
          <p>&copy; 2024 网上书店 - 版权声明</p>
        </el-col>
        <el-col :span="12" class="text-right">
          <p>联系我们：info@bookstore.com</p>
        </el-col>
      </el-row>
    </el-footer>
  </el-container>
</template>

<script>
import { ref } from 'vue'

export default {
  name: 'Home',
  setup() {
    const activeMenu = ref('1')

    const books = ref([
      {id: 1, name: '《数据库系统原理》', author: '作者1', price: 99.99, img: 'https://via.placeholder.com/150'},
      // {id: 2, name: '《深入浅出Vue.js》', author: '作者2', price: 129.99, img: 'https://via.placeholder.com/150'},
      // {id: 3, name: '《算法导论》', author: '作者3', price: 89.99, img: 'https://via.placeholder.com/150'},
      // {id: 4, name: '《设计模式》', author: '作者4', price: 149.99, img: 'https://via.placeholder.com/150'}
    ])

    const goToHome = () => {
      this.$router.push('/')
    }
    const goToCatalog = () => {
      this.$router.push('/catalog')
    }
    const goToCart = () => {
      this.$router.push('/cart')
    }
    const goToAccount = () => {
      this.$router.push('/account')
    }
    const goToLogin = () => {
      this.$router.push('/login')
    }
    const addToCart = (bookId) => {
      this.$message.success(`书籍ID ${bookId} 已加入购物车`)
    }

    return {
      activeMenu,
      books,
      goToHome,
      goToCatalog,
      goToCart,
      goToAccount,
      goToLogin,
      addToCart
    }
  }
}
</script>

<style scoped>
html, body {
  height: 100%;
  margin: 0;
}

.home-container {
  height: 100%;
  width: 100vw;
  display: flex;
  flex-direction: column;
  margin: 0;
}

.el-header {
  background-color: #409EFF;
  color: #fff;
  padding: 10px 0;
}

.el-footer {
  background-color: #f1f1f1;
  padding: 20px 0;
}

.logo {
  font-size: 24px;
  color: #fff;
  margin: 0;
}

.book-img {
  width: 100%;
  height: auto;
}
</style>
