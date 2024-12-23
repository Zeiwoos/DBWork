<template>
  <div class="page">
    <Navbar class="home-navbar"></Navbar>
    <main class="main-body">
      <el-carousel :interval="5000" class="home-carousel" arrow="always">
        <el-carousel-item class="carousel_item">
          <img src="https://via.placeholder.com/1200x400/ff7f7f/333333?text=欢迎来到网上书店" alt="banner1" />
        </el-carousel-item >
        <el-carousel-item class="carousel_item">
          <img src="https://via.placeholder.com/1200x400/7f7fff/333333?text=畅销书籍推荐" alt="banner2" />
        </el-carousel-item>
        <el-carousel-item class="carousel_item">
          <img src="https://via.placeholder.com/1200x400/7fff7f/333333?text=热卖图书" alt="banner3" />
        </el-carousel-item>
      </el-carousel>

      <el-row :gutter="20" class="book-list">
        <el-col v-for="book in books" :key="book.id" class="book-box">
          <el-card class="book-card">
            <img :src="book.img" class="book-img" />
            <div class="book-info">
              <h3>{{ book.name }}</h3>
              <p>作者：{{ book.author }}</p>
              <p>价格：￥{{ book.price }}</p>
              <el-button @click="addToOrders(book.id)" type="primary">加入购物车</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </main>
    <!-- 底部信息 -->
    <el-footer class="home-footer">
      <el-col :span="12" class="set-footer">
        <p>&copy; 2024 网上书店 - 版权声明</p>
        <p>联系我们：info@bookstore.com</p>
      </el-col>
    </el-footer>
  </div>
</template>

<script>

import { ref,reactive,onMounted } from 'vue';
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'Home',
  components: {Navbar},
  setup() {
    const activeMenu = ref('1')


    const books = ref([
      { id: 1, name: '《数据库系统原理》', author: '作者1', price: 99.99, img: 'https://via.placeholder.com/150' },
      { id: 2, name: '《深入浅出Vue.js》', author: '作者2', price: 129.99, img: 'https://via.placeholder.com/150' },
      { id: 3, name: '《算法导论》', author: '作者3', price: 89.99, img: 'https://via.placeholder.com/150' },
      { id: 4, name: '《设计模式》', author: '作者4', price: 149.99, img: 'https://via.placeholder.com/150' }
    ])
    return {
      activeMenu,
      books,
    }
  }
}
</script>

<style scoped>
.page{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
  gap: 20px;
}

.home-navbar {
  height: 80px;
  background-color: #409EFF;
  color: white;
  display: flex;
  align-items: center;
  position: fixed;
  top: 0;
  z-index: 1000;
}

.main-body{
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 100vh;
  width: 100vw;
  background-color: #f0f0f0;
}
.home-carousel{
  width: 90vw;
  height: 400px;
  margin-top: 300px;
}
.carousel_item{
  display: flex;
  align-items: center;
  justify-content: center;
}
.el-carousel__item img {
  width: 80%;
  height: 100%;
  object-fit: cover;
}
.book-list {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.book-box {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;

}
.book-card {
  height: 300px;
  width: 200px;
  display: flex;
  flex-direction: column;
  justify-content: center; /* 上下居中 */
  align-items: center; /* 左右居中 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}
.book-img {
  width: 100%;
  height: auto;
  border-radius: 5px;
}

.book-info {
  margin-bottom: 0;
  text-align: center;
}

.home-footer {
  background-color: #f1f1f1;
  width: 100vw;
  text-align: center;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}
.set-footer{
  display: inline-block;
  flex-direction: column;
  justify-content: center;
}
</style>
