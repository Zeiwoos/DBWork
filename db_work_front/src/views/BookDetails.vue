<template>
  <div>
    <Navbar class="details-navbar"></Navbar>

    <div class="main-body">
      <div class="content">
        <img :src="book1" alt="Book Image" class="book-img" />
        <div class="book-info">
          <h2 class="book-title">《{{ book.title }}》</h2>
          <p class="book-price">￥{{ book.price }}</p>
          <div class="quantity-selector">
            <el-input-number v-model="quantity" :min="1" :max="10" @change="handleChange" />
          </div>
          <div class="other-info">
            <p><strong style="font-weight: bold">作者：</strong>{{ book.author }}</p>
            <p><strong style="font-weight: bold">关键词：</strong>{{ book.keywords }}</p>
            <p><div><strong style="font-weight: bold">描述：</strong>{{ book.description }}</div></p>
            <p><strong style="font-weight: bold">发货地址：</strong>{{ book.storageLocation }}</p>
            <p><strong style="font-weight: bold">出版社：</strong>{{ book.publisher }}</p>
          </div>
          <div class="details-footer">
            <el-button @click="addToCart(book.bookID)" type="primary" style="width: 200px">加入购物车</el-button>
          </div>
        </div>
      </div>
    </div>

    <div>
      <el-footer class="set-footer">
        <el-col :span="12" class="set-footer">
          <p>&copy; 2024 网上书店 - 版权声明</p>
          <p>联系我们：info@bookstore.com</p>
        </el-col>
      </el-footer>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { getBookByID } from '@/api/Book';
import Navbar from '@/components/Navbar.vue';
import { useRoute } from 'vue-router';
import book1 from '@/assets/image/default.jpg';

const book = ref({
  bookID: 0,
  title: '',
  author: '',
  publisher: '',
  price: 0,
  keywords: '',
  description: '',
  stock: '',
  storageLocation: '',
  seriesID: '',
  SupplierID: '',
});

const quantity = ref(1);

const fetchBookDetails = async (id: number) => {
  try {
    const response = await getBookByID(id);
    if (response.data.code === 1) {
      book.value = response.data.data;
    } else {
      console.error('获取书籍详情失败:', response.data.msg);
    }
  } catch (error) {
    console.error('请求出错:', error);
  }
};

const route = useRoute();
onMounted(() => {
  const bookID = Number(route.params.id);
  fetchBookDetails(bookID);
});

const handleChange = (value: number) => {
  quantity.value = value;
};

// 向父组件发送事件
const emit = defineEmits();
const addToCart = (id: number) => {
  const bookToAdd = {
    bookID: book.value.bookID,
    title: book.value.title,
    price: book.value.price,
    num: quantity.value,
  };
  emit('add-to-cart', bookToAdd);
};
</script>

<style scoped>
.details-navbar {
  height: 80px;
  background-color: #409eff;
  color: white;
  display: flex;
  align-items: center;
  position: fixed;
  top: 0;
  z-index: 1000;
}

.main-body {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f0f0;
  height: 700px; /* 固定高度 */
  width: 98vw; /* 固定宽度 */
  margin: auto; /* 居中对齐 */
  box-sizing: border-box;
}

.content {
  display: flex;
  gap: 40px;
  background-color: #fff;
  border-radius: 10px;
  padding: 30px;
  margin-top: 50px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  max-width: 900px;
  width: 80%;
}

.book-img {
  width: 300px; /* 增大图片宽度 */
  height: auto;
  border-radius: 10px;
  flex-shrink: 0;
}

.book-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 100%;
  width:400px;
  align-items: flex-start;
}

.book-title {
  font-size: 28px;
  font-weight: bold;
}

.book-price {
  font-size: 24px;
  font-weight: bold;
  color: red;
  margin-top: 5px;
  display: flex;
}

.quantity-selector {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-top: 10px;
}

.quantity-btn {
  background-color: #409eff;
  border: none;
  color: white;
  font-size: 20px;
  width: 40px;
  height: 40px;
  border-radius: 5px;
  cursor: pointer;
}

.quantity-btn:hover {
  background-color: #66b1ff;
}

.other-info {
  margin-top: 20px;
  width: 90%;
  gap:10px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}


.other-info p {
  font-size: large;
  display: flex;
  white-space: normal; /* 允许换行 */
  word-wrap: break-word; /* 自动换行 */
  word-break: break-all; /* 长单词强制换行 */
}


.details-footer {
  margin-top: 20px;
  display: flex;
  gap: 15px;
  justify-content: flex-start;
  align-items: center;
}

.set-footer {
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>
