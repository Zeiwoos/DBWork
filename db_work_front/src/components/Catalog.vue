<template>
  <el-container>
    <el-header>
      <el-menu mode="horizontal">
        <el-menu-item index="1" @click="goToHome">首页</el-menu-item>
        <el-menu-item index="2" @click="goToOrder">我的订单</el-menu-item>
      </el-menu>
    </el-header>
    <el-main>
      <h2>所有书籍</h2>
      <el-row :gutter="20">
        <el-col v-for="book in books" :key="book.id" :span="6">
          <book-item :book="book" />
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import { ref } from 'vue'
import BookItem from '@/components/BookItem.vue'

export default {
  name: 'Catalog',
  components: {
    BookItem
  },
  setup() {
    const books = ref([
      { id: 1, name: '书名1', author: '作者1', price: 100, img: '/assets/book1.jpg' },
      { id: 2, name: '书名2', author: '作者2', price: 120, img: '/assets/book1.jpg' },
      // 更多书籍
    ])

    const goToHome = () => {
      this.$router.push('/')
    }
    const goToOrder = () => {
      this.$router.push('/order')
    }

    return {
      books,
      goToHome,
      goToOrder
    }
  }
}
</script>
