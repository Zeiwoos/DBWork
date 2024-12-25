<template>
  <Navbar />
  <div>
    <div class="main-body">
      <!-- Adjusted carousel box -->
      <div class="carousel-box">
        <el-carousel :interval="4000" type="card" height="400px" style="margin-top: 20px">
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
                <img :src="book.img" class="book-img" alt="book img"/>
                <div class="book-info">
                  <router-link :to="'/book/' + book.id" style="text-decoration: none; color: #181818">
                    <h3 style="font-weight: bold">《{{ book.name }}》</h3>
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
<script>
import { defineComponent, onMounted, ref } from 'vue';
import Navbar from '@/components/Navbar.vue';
import book1 from '@/assets/image/book1.png';
import book2 from '@/assets/image/book2.png';
import book3 from '@/assets/image/book3.png';
import book4 from '@/assets/image/book4.png';
import default_book from '@/assets/image/default.jpg';
import { getAllBook } from '@/api/Book'; // 假设getAllBook已经正确引入

export default defineComponent({
  name: 'BookList',
  components: {
    Navbar
  },
  setup() {
    const carouselImages = [book1, book2, book3, book4];
    const books = ref([]);  // 使用ref来创建响应式数据

    const getEightBooks = async () => {
      try {
        const response = await getAllBook();  // 请求获取书籍数据
        if (response.data.code === 1) {
          // 取前8本书
          books.value = response.data.data.slice(0, 8).map((book) => ({
            id: book.bookID,            // 书籍ID 使用返回的 bookID
            name: book.title,           // 书籍名称 使用返回的 title
            author: book.author,        // 作者 使用返回的 author
            price: book.price,          // 价格 使用返回的 price
            img: default_book // 图片，若接口返回的 img 字段不存在，使用默认图
          }));
          console.log(books.value);
        }
      } catch (error) {
        console.error('获取书籍信息失败', error);
      }
    };

    // 组件挂载时调用获取书籍数据
    onMounted(() => {
      console.log('mounted');
      getEightBooks();
    });

    return {
      carouselImages,
      books
    };
  }
});
</script>

<style scoped>
/* Main layout adjustments */
.main-body {
  gap: 30px;
  display: flex;
  justify-content: center;
  align-items: flex-start;  /* Start alignment to avoid overlapping with the navbar */
  flex-direction: column;
  background-color: #f0f0f0;
  padding-top: 60px; /* Ensuring space for Navbar */
}

/* Carousel box adjustment */
.carousel-box {
  width: 100vw;
  height: 400px; /* Slightly reduced height */
  padding: 0 0 50px 0; /* Ensure some horizontal space */
  z-index: 1; /* Set to higher priority for visibility */
}

.el-carousel__item {
  text-align: center;
}

.carousel-img {
  width: 100%;
  height: auto;
  object-fit: cover;
  border-radius: 5px;
}

/* Book list and card styles */
.book-show {
  padding: 20px 0 40px 0;
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
  flex-direction: column; /* 纵向排列，让图片和信息在垂直方向上对齐 */
  justify-content: center; /* 确保卡片内容在竖直方向上居中 */
  align-items: center; /* 确保卡片内容在水平方向上居中 */
  padding: 10px; /* 可选，为了给卡片内容加点间距 */
}

.book-img {
  width: 140px;
  height: 160px;
  object-fit: cover; /* 确保图片按比例填充 */
  border-radius: 5px;
  display: block; /* 取消图片的内联元素属性，确保其独占一行 */
  margin: 0 auto; /* 图片居中 */
}

.book-info {
  margin-bottom: 0;
  text-align: center;
  gap: 5px;
}
.book-info h3 {
  width: 100%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
  font-size: 14px;
}
.book-info p{
  width: 100%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
  font-size: 12px;
}

/* Footer styles */
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
