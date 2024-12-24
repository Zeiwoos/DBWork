<template>
  <div>
    <Navbar />
    <div class="main-body">
      <div class="carousel-box">
        <el-carousel :interval="4000" type="card" height="400px">
          <el-carousel-item v-for="(item, index) in carouselImages" :key="index">
            <!-- 显示图片 -->
            <el-link><img :src="item" alt="Book Image" class="carousel-img" /></el-link>
          </el-carousel-item>
        </el-carousel>
      </div>
      <div class="book-show">
        <el-row :gutter="20" class="book-list">
          <div class="book-grid">
            <el-col v-for="book in books" :key="book.id" :span="6" class="book-box">
              <el-card class="book-card">
                <img :src="book.img" class="book-img" alt=""/>
                <div class="book-info">
                  <router-link :to="'/book/' + book.id" style="text-decoration: none; color: #181818">
                    <h3>{{ book.name }}</h3>
                  </router-link>
                  <p>作者：{{ book.author }}</p>
                  <p>价格：￥{{ book.price }}</p>
                  <router-link :to="'/book/' + book.id" style="text-decoration: none; color: #181818">
                    <el-button @click="" type="primary">查看详情</el-button>
                  </router-link>
                </div>
              </el-card>
            </el-col>
          </div>
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

<script lang="ts">
import { defineComponent } from 'vue';
import Navbar from '@/components/Navbar.vue';
import book1 from '@/assets/image/book1.png';
import book2 from '@/assets/image/book2.png';
import book3 from '@/assets/image/book3.png';
import book4 from '@/assets/image/book4.png';

export default defineComponent({
  name: 'BookList',
  components: {
    Navbar
  },
  data() {
    return {
      carouselImages: [book1, book2, book3, book4],
      books: [
        { id: 1, name: '《数据库系统原理》', author: '作者1', price: 99.99, img: 'https://via.placeholder.com/150' },
        { id: 2, name: '《深入浅出Vue.js》', author: '作者2', price: 129.99, img: 'https://via.placeholder.com/150' },
        { id: 3, name: '《算法导论》', author: '作者3', price: 89.99, img: 'https://via.placeholder.com/150' },
        { id: 4, name: '《设计模式》', author: '作者4', price: 149.99, img: 'https://via.placeholder.com/150' },
        { id: 5, name: '《深入理解计算机系统》', author: '作者5', price: 109.99, img: 'https://via.placeholder.com/150' },
        { id: 6, name: '《计算机网络》', author: '作者6', price: 119.99, img: 'https://via.placeholder.com/150' },
        { id: 7, name: '《C++ Primer》', author: '作者7', price: 139.99, img: 'https://via.placeholder.com/150' },
        { id: 8, name: '《操作系统概念》', author: '作者8', price: 159.99, img: 'https://via.placeholder.com/150' }
      ]
    };
  }
});
</script>

<style scoped>

.main-body {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  gap: 30px;
  height: auto;
  width: 100vw;
  background-color: #f0f0f0;
}

.carousel-box {
  margin-top: 120px;
  width: 100vw;
  height: 450px;
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.book-show {
  padding-bottom: 40px;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
}

.book-list {
  width: 90vw;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}

.book-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr); /* 每行 4 个 */
  gap: 20px;
  width: 100%;
}

.book-box {
  display: flex;
  justify-content: center;
  align-items: center;
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
.book-info h3 {
  width: 100%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
  font-size: 14px;
}

.home-footer {
  background-color: aliceblue;
  width: 100vw;
  padding: 10px;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
}

.set-footer {
  display: inline-block;
  flex-direction: column;
  justify-content: center;
}
</style>
