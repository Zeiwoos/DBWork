<template>
  <Navbar />
  <div class="main-body">
    <div class="sidebar">
      <el-menu :default-active="menuActiveIndex"
               @select="handleMenuSelect"
               mode="vertical" class="sidebar-box">
        <div>
          <img class="self-img" :src="url" alt=""/>
        </div>
        <div class="items-box">
          <el-menu-item class="menu-item" index="1">个人信息</el-menu-item>
          <el-menu-item class="menu-item" index="2">历史订单</el-menu-item>
        </div>
      </el-menu>
    </div>
    <div>
      <!-- 个人信息展示 -->
      <div v-if="menuActiveIndex === '1'" class="info-box">
        <div class="input-box">
          <span class="character">用户ID</span>
          <el-input v-model="show_ID" style="width: 240px" disabled />
          <el-button @click="handleEdit('show_ID')">修改</el-button>
        </div>
        <div class="input-box">
          <span class="character">信用等级</span>
          <el-input v-model="show_Level" style="width: 240px" disabled />
          <el-button @click="handleEdit('show_Level')">修改</el-button>
        </div>
        <div v-for="(item, index) in showItems" :key="index" class="input-box">
          <span class="character">{{ item.label }}</span>
          <el-input v-model="item.v_model.value" style="width: 240px" />
          <el-button @click="handleEditItem(index)">修改</el-button>
        </div>
        <div class="input-box">
          <span class="character">密码</span>
          <el-input v-model="show_password" style="width: 240px" type="password" />
          <el-button @click="handlePasswordChange">修改</el-button>
        </div>
        <div class="input-box">
          <span class="character">余额</span>
          <el-input v-model="show_balance" style="width: 240px" />
          <el-button @click="handleBalanceRecharge">充值</el-button>
        </div>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </div>

      <!-- 历史订单展示 -->
      <div v-if="menuActiveIndex === '2'" class="orders-box">
        <div v-for="(order, index) in orders" :key="index">
          <OrderItem
              :orderID="order.orderID"
              :orderTime="order.orderTime"
              :userID="order.userID"
              :bookID="order.bookID"
              :orderAmount="order.orderAmount"
              :orderPrice="order.orderPrice"
              :orderAddress="order.orderAddress"
              :orderStatus="order.orderStatus" />
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import Navbar from "@/components/Navbar.vue";
import { ElMenu, ElMenuItem } from "element-plus";
import selfImg from "@/assets/image/self-img.jpg";
import OrderItem from "@/components/OrderItem.vue";

// 引入图片路径
const url = selfImg;

// 使用响应式变量来跟踪当前选中的菜单项
const menuActiveIndex = ref('1');

// 定义显示的项
const show_ID = ref('');
const show_name = ref('');
const show_password = ref('');
const show_Level = ref('');
const show_address = ref('');
const show_email = ref('');
const show_phone = ref('');
const show_balance = ref('');

// 用于绑定到表单的显示项
const showItems = [
  { label: "用户名称", v_model: show_name },
  { label: "地址", v_model: show_address },
  { label: "邮箱", v_model: show_email },
  { label: "电话号码", v_model: show_phone },
];

// 初始化数据
const init = () => {
  show_ID.value = '123456';
  show_name.value = '张三';
  show_Level.value = '1';
  show_address.value = '北京市海淀区';
  show_email.value = '123456789@qq.com';
  show_phone.value = '12345678901';
  show_password.value = '123456';
  show_balance.value = '1000';
}

const orders = ref([
  {
    orderID: '1001',
    orderTime: '2024-12-23',
    userID: 'user123',
    bookID: 'book001',
    orderAmount: '2',
    orderPrice: '50',
    orderAddress: '北京市海淀区',
    orderStatus: '已发货'
  },
  {
    orderID: '1002',
    orderTime: '2024-12-22',
    userID: 'user456',
    bookID: 'book002',
    orderAmount: '1',
    orderPrice: '30',
    orderAddress: '上海市浦东新区',
    orderStatus: '待发货'
  }
]);

// 处理菜单选择事件，更新当前选中的菜单项
const handleMenuSelect = (index: string) => {
  menuActiveIndex.value = index;
};

// 处理每个输入框修改按钮的点击事件
const handleEdit = (field: string) => {
  console.log(`编辑字段: ${field}`);
  // 这里可以加入修改逻辑
};

// 处理显示项修改按钮的点击事件
const handleEditItem = (index: number) => {
  const item = showItems[index];
  console.log(`${item.label} 的值是: `, item.v_model.value);
  item.v_model = `已提交：${item.v_model.value}`; // 更新为已提交状态
};

// 处理密码修改按钮点击事件
const handlePasswordChange = () => {
  console.log('密码修改');
  // 处理密码修改逻辑
};

// 处理余额充值按钮点击事件
const handleBalanceRecharge = () => {
  console.log('余额充值');
  // 处理余额充值逻辑
};

// 保存按钮的点击事件
const handleSave = () => {
  console.log('保存数据');
  // 执行保存操作
};

onMounted(() => {
  init();
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
.sidebar {
  width: 20%;
  height: 100%;
  background-color: white;
  margin-top: 10%;
  position: fixed;
  left: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
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
  width: 150px;
  height: 150px;
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
.info-box {
  height: 80%;
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-left: 20%;
  margin-top: 10%;
}
.input-box {
  display: flex;
  gap: 20px;
}
.character {
  width: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.orders-box {
  margin-left: 20%;
  margin-top: 10%;
}
</style>
