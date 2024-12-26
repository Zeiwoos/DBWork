<template>
  <Navbar_Admin />
  <div class="main-body">
    <div class="sidebar">
      <el-menu :default-active="menuActiveIndex"
               @select="handleMenuSelect"
               mode="vertical" class="sidebar-box">
        <div>
          <img class="self-img" :src="url" alt=""/>
        </div>
        <div class="items-box">
          <el-menu-item class="menu-item" index="1">用户管理</el-menu-item>
          <el-menu-item class="menu-item" index="2">书籍管理</el-menu-item>
          <el-menu-item class="menu-item" index="3">缺书管理</el-menu-item>
          <el-menu-item class="menu-item" index="4">采购单管理</el-menu-item>
          <el-menu-item class="menu-item" index="5">客户订单管理</el-menu-item>
        </div>
      </el-menu>
    </div>
    <div class="content">
      <!-- 用户管理页面 -->
      <div v-if="menuActiveIndex === '1'" class="users-manage">
        <Table_Customer />
      </div>
      <!-- 书籍管理页面 -->
      <div v-if="menuActiveIndex === '2'" class="books-manage">
        <Table_Books />
      </div>
      <!-- 库存管理页面 -->
      <div v-if="menuActiveIndex === '3'" class="quantity-manage">
        <Table_MissingBooks />
      </div>
      <!-- 采购单管理页面-->
      <div v-if="menuActiveIndex === '4'" class="purchaseOrders-manage">
        <Table_PurchaseOrders />
      </div>
      <!-- 客户订单管理页面 -->
      <div v-if="menuActiveIndex === '5'" class="orders-manage">
        <Table_Orders />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, watch } from 'vue';
import { ElMenu, ElMenuItem, ElInput, ElButton, ElTable, ElTableColumn } from 'element-plus';
import selfImg from "@/assets/image/self-img.jpg";
import Navbar_Admin from "@/components/Navbar_Admin.vue";
import Table_Customer from "@/components/Table_Customers.vue";
import Table_Books from "@/components/Table_Books.vue";
import Table_MissingBooks from "@/components/Table_MissingBooks.vue";
import Table_Orders from "@/components/Table_Orders.vue";
import Table_PurchaseOrders from "@/components/Table_PurchaseOrders.vue";
import { } from '@/api/Customer'
import { } from '@/api/Book'
import { } from '@/api/MissingBook'
import { } from '@/api/Order'
import { } from '@/api/PurchaseOrder'
import { } from '@/api/Series'
import { } from '@/api/Supplier'
// 引入图片路径
const url = selfImg;

// 使用响应式变量来跟踪当前选中的菜单项
const menuActiveIndex = ref('1');


const handleMenuSelect = (index: string) => {
  menuActiveIndex.value = index; // 更新当前选中的菜单项
};
</script>

<style scoped>
.main-body {
  gap: 20px;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
  flex-direction: column;
  background-color: #f0f0f0;
  overflow-y: auto;
}
.sidebar {
  width: 20%;
  height: 90vh;
  position: fixed;
  left: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: white;
}
.sidebar-box {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}
.self-img {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  margin-top: 40px;
}
.items-box {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}
.menu-item {
  width: 100%;
  height: 70px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.content {
  gap: 15px;
  width: 80%;
  height: 90vh;
  margin-right: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  flex-grow: 1;
}
.books-manage{
  height: 100%;
  margin-top: 100px;
}
</style>
