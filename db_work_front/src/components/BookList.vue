<template>
  <el-card :body-style="{ padding: '20px' }">
    <img :src="book.img" class="book-img" />
    <div class="book-info">
      <h3>{{ book.name }}</h3>
      <p>作者：{{ book.author }}</p>
      <p>价格：￥{{ book.price }}</p>
      <el-button @click="addToCart(book.id)" type="primary">加入购物车</el-button>
    </div>
  </el-card>
</template>

<script>
export default {
  name: 'BookItem',
  props: {
    book: Object
  },
  methods: {
    addToCart(bookId) {
      this.$message.success(`书籍ID ${bookId} 已加入购物车`)
    }
  }
}
</script>

<style scoped>
.book-img {
  width: 100%;
  height: auto;
  border-radius: 5px;
}

.book-info {
  margin-top: 10px;
}
</style>
