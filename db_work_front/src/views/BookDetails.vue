<template>
  <div>
    <Navbar class="details-navbar"></Navbar>

    <div class="main-body">
      <div class="content">
        <img :src="book.img" alt="Book Image" class="book-img" />
        <div class="book-info">
          <h2 class="book-title">{{ book.title }}</h2>
          <p class="book-price">￥{{ book.price }}</p>
          <div class="quantity-selector">
            <button class="quantity-btn" @click="decreaseQuantity">-</button>
            <span>{{ quantity }}</span>
            <button class="quantity-btn" @click="increaseQuantity">+</button>
          </div>
          <div class="other-info">
            <p><strong>作者：</strong>{{ book.author }}</p>
            <p><strong>关键词：</strong>{{ book.keywords }}</p>
            <p><strong>描述：</strong>{{ book.description }}</p>
            <p><strong>发货地址：</strong>{{ book.storageLocation }}</p>
            <p><strong>出版社：</strong>{{ book.publisher }}</p>
          </div>
          <div class="details-footer">
            <el-button @click="addToCart(book.bookID)" type="primary">加入购物车</el-button>
            <el-button @click="buyNow(book.bookID)" type="success">立即购买</el-button>
          </div>
        </div>
      </div>
    </div>

    <div>
      <el-footer class="details-footer">
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
import { useRoute } from 'vue-router'; // 导入 useRoute 函数

// 定义响应式变量
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
  // img: require('@/assets/images/default-book.png'),
});

const quantity = ref(1);

// 请求书籍数据
const fetchBookDetails = async (id: number) => {
  try {
    const response = await getBookByID(id);
    if (response.data.code === 1) {
      book.value = response.data.data; // 更新书籍数据
    } else {
      console.log(response.data.code);
      console.error('获取书籍详情失败:', response.data.msg);
    }
  } catch (error) {
    console.error('请求出错:', error);
  }
};
const route = useRoute();
// 页面加载时获取书籍数据
onMounted(() => {
  const bookID = Number(route.params.id); // 替换为实际的书籍 ID
  fetchBookDetails(bookID);
});

// 增加/减少数量逻辑
const increaseQuantity = () => {
  quantity.value++;
};

const decreaseQuantity = () => {
  if (quantity.value > 1) quantity.value--;
};

const addToCart = (id: number) => {
  console.log(`书籍ID ${id} 已加入购物车，数量：${quantity.value}`);
};

const buyNow = (id: number) => {
  console.log(`立即购买书籍ID ${id}，数量：${quantity.value}`);
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
  gap: 20px;
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
}

.details-footer {
  margin-top: 20px;
  display: flex;
  gap: 15px;
}

.set-footer {
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>
