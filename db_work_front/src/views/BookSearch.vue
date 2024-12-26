<template>
  <Navbar />
  <div>
    <div class="main-body">
      <!-- 搜索框 -->
      <div class="search-box">
        <!-- 选择搜索条件 -->
        <el-select v-model="searchCriteria" placeholder="选择搜索条件" style="width: 100px">
          <el-option label="不限" value="all"></el-option>
          <el-option label="书名" value="title"></el-option>
          <el-option label="作者" value="author"></el-option>
          <el-option label="类别" value="category"></el-option>
        </el-select>
        <!-- 搜索输入框 -->
        <el-input
            v-model="searchQuery"
            placeholder="请输入搜索内容"
            clearable
            style="width: 400px"
        ></el-input>
        <!-- 搜索按钮 -->
        <el-button type="primary" @click="handleSearch" style="width: 100px">
          <p style="font-weight: bold">搜索</p>
        </el-button>
      </div>

      <!-- 书籍展示 -->
      <div class="book-show" v-if="!loading">
        <el-row :gutter="20" class="book-list">
          <div class="book-grid">
            <el-col v-for="book in filteredBooks" :key="book.bookID" :span="6" class="book-box">
              <el-card class="book-card">
                <img :src="book.img || default_book" class="book-img" alt="book img"/>
                <div class="book-info">
                  <router-link :to="'/book/' + book.bookID" style="text-decoration: none; color: #181818">
                    <h3 style="font-weight: bold">《{{ book.title }}》</h3>
                  </router-link>
                  <p>作者：{{ book.author }}</p>
                  <p>价格：￥{{ book.price }}</p>
                  <router-link :to="'/book/' + book.bookID" style="text-decoration: none; color: #181818">
                    <el-button type="primary">查看详情</el-button>
                  </router-link>
                </div>
              </el-card>
            </el-col>
          </div>
        </el-row>
      </div>

      <!-- 加载中状态 -->
      <div v-if="loading" class="loading-container">
        <el-spinner type="line" size="large"></el-spinner>
        <p>加载中...</p>
      </div>

      <!-- 错误提示 -->
      <div v-if="error" class="error-container">
        <p style="color: red;">加载书籍数据失败，请稍后再试。</p>
      </div>
    </div>

    <!-- 底部信息 -->
    <el-footer class="home-footer">
      <el-col :span="12" class="set-footer">
        <p>&copy; 2024 网上书店 - 版权声明</p>
        <p>联系我们：info@bookstore.com</p>
      </el-col>
    </el-footer>
  </div>
</template>

<script setup>
import { defineComponent, onMounted, ref, watch } from 'vue';
import Navbar from '@/components/Navbar.vue';
import default_book from '@/assets/image/default.jpg';
import { getAllBook, searchBook, searchBookByTitle, searchBookByAuthor, searchBookBykeywords } from '@/api/Book'; // 假设getAllBook已经正确引入

// 响应式变量
const books = ref([]); // 所有书籍数据
const filteredBooks = ref([]); // 根据搜索条件过滤后的书籍
const searchQuery = ref(''); // 搜索内容
const searchCriteria = ref('all'); // 搜索条件（不限、书名、作者、类别）
const loading = ref(false); // 加载中状态
const error = ref(false); // 错误状态

// 从后端获取书籍数据
const fetchBooks = async () => {
  loading.value = true;
  error.value = false;
  try {
    const response = await getAllBook();
    books.value = response.data.data;
    filteredBooks.value = books.value; // 默认显示所有书籍
  } catch (err) {
    error.value = true;
    console.error("Error fetching books:", err);
  } finally {
    loading.value = false;
  }
};

// 搜索功能：根据条件过滤书籍
const handleSearch = async () => {
  // alert(searchQuery.value)
  if (!searchQuery.value) {
    filteredBooks.value = books.value; // 如果没有输入搜索内容，显示所有书籍
    return;
  }

  loading.value = true;
  error.value = false;

  try {
    let response;
    // const searchParams = {
    //   string: searchQuery.value
    // };
    // console.log(searchParams)
    // console.log("Search Criteria:", searchCriteria.value); // 打印搜索条件
    console.log("Search Query:", searchQuery.value); // 打印搜索内容
    console.log(typeof searchQuery.value); // 打印数据类型

    switch (searchCriteria.value) {
      case 'title':
        response = await searchBookByTitle(searchQuery.value);
        break;
      case 'author':
        response = await searchBookByAuthor(searchQuery.value);
        break;
      case 'category':
        // alert(searchQuery.value)
        response = await searchBookBykeywords(searchQuery.value); // 假设类别通过keywords字段查询
        break;
      default:
        response = await searchBook(searchQuery.value);
    }

    // 如果搜索成功，更新显示的书籍数据
    filteredBooks.value = response.data.data || [];
  } catch (err) {
    error.value = true;
    console.error("Error fetching search results:", err);
  } finally {
    loading.value = false;
  }
};


// 监听搜索条件或搜索内容变化，自动触发搜索
watch([searchQuery, searchCriteria], handleSearch);

// 页面加载时获取书籍数据
onMounted(fetchBooks);
</script>

<style>
/* Main layout adjustments */
/* Main layout adjustments */
.main-body {
  gap: 30px;
  display: flex;
  width: 100vw;
  height: 100vh;
  justify-content: flex-start; /* 开始对齐，避免与 navbar 重叠 */
  flex-direction: column;
  background-color: #f0f0f0;
  padding-top: 60px; /* 为了确保内容不被顶部 Navbar 遮挡 */
}

/* 固定在顶部的搜索框 */
.search-box {
  position: fixed; /* 固定定位 */
  top: 100px; /* 调整为顶部距离 navbar 的位置 */
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 40px;
  padding: 10px;
  background-color: white; /* 确保背景颜色不透明，便于显示 */
  z-index: 10; /* 确保它位于其他内容之上 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 为了视觉效果添加阴影 */
}

/* 书籍展示区域 */
.book-show {
  padding: 20px 0 40px 0;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 120px; /* 给内容增加一些顶部间距，避免被固定的搜索框遮挡 */
}

/* Book list and card styles */
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

.book-info p {
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
  position: fixed;
  bottom: 0;
}

.set-footer {
  display: inline-block;
  flex-direction: column;
  justify-content: center;
}

.loading-container {
  text-align: center;
  margin-top: 20px;
}

.error-container {
  text-align: center;
  margin-top: 20px;
}

</style>
