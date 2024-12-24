<template>
  <div>
    <Navbar />
    <div class="main-body">
      <div class="book-block">
        <!-- 书籍信息部分 -->
        <div class="book-img-box">
          <el-image class="book-img" :src="url" fit="cover" />
          <div>
            <h2 class="book-title">《{{ bookInfo.bookName }}》</h2>
          </div>
        </div>
        <div class="book-info-box">
          <div class="info-box">
            <p><b style="font-weight: bold">作者</b>：{{ bookInfo.bookAuthor }}</p>
            <p><b style="font-weight: bold">价格</b>：{{ bookInfo.bookPrice }}  RMB</p>
            <p><b style="font-weight: bold">类别</b>：{{ bookInfo.bookCategory }}</p>
            <p><b style="font-weight: bold">简介</b>：{{ bookInfo.bookIntro }}</p>
            <p><el-input-number v-model="bookInfo.quantity" :min="1" :max="100" @change="handleChange" /></p>
            <el-button type="primary" @click="handleAddToCart">加入购物车</el-button>
          </div>
        </div>
        <!-- 书店信息部分 -->
        <div class="bookstore-info-box">
          <div class="info-box">
            <h2>{{ bookstoreInfo.bookstore_name }}</h2>
            <p><b style="font-weight: bold">店主</b>：{{ bookstoreInfo.bookstore_owner }}</p>
            <p><b style="font-weight: bold">地址</b>：{{ bookstoreInfo.bookstore_address }}</p>
            <p><b style="font-weight: bold">电话</b>：{{ bookstoreInfo.phone_number }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { useRoute } from 'vue-router'; // 用于获取路由参数
import Navbar from "@/components/Navbar.vue";
import bookImg from "@/assets/image/database.jpeg";

export default defineComponent({
  components: { Navbar },
  data() {
    // 获取路由参数
    const route = useRoute();
    const bookId = route.params.id; // 获取动态路由参数 (例如：/book/:id)

    // 根据 `bookId` 来加载相应书籍的详细信息
    const bookInfo = this.getBookInfo(bookId);
    const bookstoreInfo = this.getBookstoreInfo();

    return {
      bookInfo,
      bookstoreInfo
    };
  },
  methods: {
    handleChange(value: number) {
      console.log(value);
    },
    handleAddToCart() {
      console.log("加入购物车");
      console.log("书籍信息：", this.bookInfo);
      console.log("书店信息：", this.bookstoreInfo);
    },
    getBookInfo(id: string) {
      // 根据书籍ID获取书籍详细信息
      // 假设在这里您从某个数据源（例如 API 或静态数据）加载书籍信息
      return {
        url: bookImg,
        bookName: "数据库系统原理",
        bookAuthor: "王珊",
        bookPrice: 99.99,
        bookCategory: "计算机",
        bookIntro: "数据库系统原理是数据库领域的经典教材，全球范围内广受欢迎。",
        quantity: 1
      };
    },
    getBookstoreInfo() {
      // 获取书店信息
      return {
        bookstore_name: "图灵书店",
        bookstore_owner: "图灵",
        bookstore_address: "北京市海淀区",
        phone_number: "12345678901"
      };
    }
  },
});
</script>

<style scoped>
.main-body {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  gap: 20px;
  height: 100vh;
  width: 100vw;
  background-color: #f0f0f0;
}

.book-block {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: row;
  width: 70%;
  height: 70%;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.book-img-box {
  flex: 3;
  height: 90%;
  gap: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin: 20px;
}

.book-img {
  width: 200px;
  height: 250px;
  margin: 20px;
}

.book-info-box {
  flex: 4;
  height: 100%;
  gap: 20px;
  display: flex;
  align-items: center;
}

.bookstore-info-box {
  flex: 3;
  height: 100%;
  gap: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center
}
.info-box{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  gap: 20px;
  padding: 20px;
}
</style>
