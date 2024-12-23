<template>
<div>
  <Navbar class="home-navbar"></Navbar>
  <div class="main-body">
    <div class="carousel-box">
      <el-carousel :interval="5000" arrow="always" class="carousel">
        <el-carousel-item class="carousel_item">
          <img :src="book_img1" alt="banner1" />
        </el-carousel-item >
        <el-carousel-item class="carousel_item">
          <img :src="book_img2" alt="banner2" />
        </el-carousel-item>
        <el-carousel-item class="carousel_item">
          <img :src="book_img3" alt="banner3" />
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="book-show">
      <el-row :gutter="20" class="book-list">
        <el-col v-for="book in books" :key="book.id" :span="4" class="book-box">
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
    </div>
  </div>
<!-- 底部信息 -->
  <div>
    <el-footer class="home-footer">
      <el-col :span="12" class="set-footer">
        <p>&copy; 2024 网上书店 - 版权声明</p>
        <p>联系我们：info@bookstore.com</p>
      </el-col>
    </el-footer>
  </div>
</div>
</template>

<script lang="ts" setup>

import { ref,reactive,onMounted } from 'vue';
import Navbar from '@/components/Navbar.vue'
import book1 from '@/assets/image/book1.png'
import book2 from '@/assets/image/book2.png'
import book3 from '@/assets/image/book3.png'

const activeMenu = ref('1')
const book_img1 = book1
const book_img2 = book2
const book_img3 = book3

const books = ref([
  { id: 1, name: '《数据库系统原理》', author: '作者1', price: 99.99, img: 'https://via.placeholder.com/150' },
  { id: 2, name: '《深入浅出Vue.js》', author: '作者2', price: 129.99, img: 'https://via.placeholder.com/150' },
  { id: 3, name: '《算法导论》', author: '作者3', price: 89.99, img: 'https://via.placeholder.com/150' },
  { id: 4, name: '《设计模式》', author: '作者4', price: 149.99, img: 'https://via.placeholder.com/150' }
])

</script>

<style scoped>
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
  gap: 30px;
  height: auto;
  width: 100vw;
  background-color: #f0f0f0;
}

.carousel-box{
  margin-top: 120px;
  width: 100vw;
  height: 350px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.carousel{
  width: 90vw;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.carousel_item img {
  width: 80%;
  height: 100%;
  object-fit: cover;
}
.book-show{
  padding-bottom: 40px;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
}
.book-list{
  width: 90vw;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  gap: 20px;
  flex-direction: row;
}
.book-box{
  margin: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 100px;
}
.book-card {
  width: 200px;
  height: 300px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
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
  background-color: aliceblue;
  width: 100vw;
  padding: 10px;
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
