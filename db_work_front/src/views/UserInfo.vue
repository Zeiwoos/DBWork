<template>
  <Navbar />
  <div class="main-body">
    <div class="sidebar">
      <el-menu :default-active="menuActiveIndex" @select="handleMenuSelect" mode="vertical" class="sidebar-box">
        <div>
          <img class="self-img" :src="url" alt=""/>
        </div>
        <div class="items-box">
          <el-menu-item class="menu-item" index="1">个人信息</el-menu-item>
          <el-menu-item class="menu-item" index="2">历史订单</el-menu-item>
        </div>
      </el-menu>
    </div>
    <div class="content">
      <div v-if="menuActiveIndex === '1'" class="info-box">
        <div class="input-box">
          <span class="character">用户ID</span>
          <el-input v-model="show_ID" style="width: 240px" disabled />
          <el-button @click="">修改</el-button>
        </div>
        <div class="input-box">
          <span class="character">信用等级</span>
          <el-input v-model="show_Level" style="width: 240px" disabled />
          <el-button @click="">修改</el-button>
        </div>
        <div v-for="(item, index) in showItems" :key="index" class="input-box">
          <span class="character">{{ item.label }}</span>
          <el-input v-model="item.v_model.value" style="width: 240px" />
          <el-button @click="handleButtonClick(index)">修改</el-button>
        </div>
        <div class="input-box">
          <span class="character">密码</span>
          <el-input v-model="show_password" style="width: 240px" type="password" show-password />
          <el-button @click="handlePasswordChange">修改</el-button>
        </div>
        <div class="input-box">
          <span class="character">余额</span>
          <el-input v-model="show_balance" style="width: 240px" />
          <el-button @click="handleBalanceRecharge">充值</el-button>
        </div>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </div>
      <div v-if="menuActiveIndex === '2'" class="orders-block">
        <div v-for="(order, index) in orders" :key="index" class="order">
          <OrderItem
              v-model:orderID="order.orderId"
              v-model:orderTime="order.orderDate"
              v-model:userID=CustomerId
              v-model:bookID="order.bookID"
              v-model:orderAmount="order.totalAmount"
              v-model:orderPrice="order.totalAmount"
              v-model:orderAddress="order.skippingAddress"
              v-model:orderStatus="order.status" />
        </div>
      </div>
    </div>
  </div>
</template>


<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import Navbar from "@/components/Navbar.vue";
import { getCustomerById } from "@/api/Customer";
import { getOrderByCustomerID } from "@/api/Order";


// 引入图片路径
import selfImg from "@/assets/image/self-img.jpg";
const url = selfImg

// 引入组件
import { ElMenu, ElMenuItem } from "element-plus";
import OrderItem from "@/components/OrderItem.vue";

// 获取用户ID
const CustomerId = localStorage.getItem('customerId');

// 存储用户信息
const show_ID = ref('');
const show_name = ref('');
const show_password = ref('');
const show_Level = ref('');
const show_address = ref('');
const show_email = ref('');
const show_phone = ref('');
const show_balance = ref('');

// 存储订单信息
const orders = ref([]);

// 用于绑定到表单的显示项
const showItems = [
  { label: "用户名称", v_model: show_name },
  { label: "地址", v_model: show_address },
  { label: "邮箱", v_model: show_email },
  { label: "电话号码", v_model: show_phone },
];

// 响应式菜单索引
const menuActiveIndex = ref('1');

// 获取用户信息和订单信息
const fetchUserData = async () => {
  if (CustomerId) {
    try {
      const responseCustomer = await getCustomerById(CustomerId);
      console.log(responseCustomer)
      if (responseCustomer.data.code===1) {
        const customer = responseCustomer.data.data;
        show_ID.value = customer.customerID;
        show_name.value = customer.customerName;
        show_Level.value = customer.creditLevel.toString();
        show_address.value = customer.address;
        show_email.value = customer.email;
        show_phone.value = customer.phone;
        show_balance.value = customer.balance.toString();
        show_password.value = customer.password
      }
    } catch (error) {
      console.error("获取用户信息失败:", error);
    }
  }
};

// 获取订单信息
const fetchOrders = async () => {
  if (CustomerId) {
    try {
      const responseOrders = await getOrderByCustomerID(CustomerId);
      console.log(responseOrders)
      if (responseOrders.data.code===1) {
        orders.value = responseOrders.data.data;
        console.log(orders)
      }
    } catch (error) {
      console.error("获取订单信息失败:", error);
    }
  }
};

// 初始化页面数据
const init = async () => {
  await fetchUserData();
  await fetchOrders();
};

onMounted(() => {
  init();
});

// 处理菜单选择事件，更新当前选中的菜单项
const handleMenuSelect = (index: string) => {
  menuActiveIndex.value = index;
};

// 处理每个输入框修改按钮的点击事件
const handleButtonClick = (index: number) => {
  const item = showItems[index];
  console.log(`修改后${item.label}的值是: `, item.v_model.value);
  item.v_model.value = `${item.v_model.value}`;
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
const handleSave = async () => {
  try {
    const updatedCustomer = {
      id: show_ID.value,
      name: show_name.value,
      creditLevel: show_Level.value,
      address: show_address.value,
      email: show_email.value,
      phone: show_phone.value,
      balance: show_balance.value,
    };

    // 调用API保存修改后的用户信息
    await updateCustomer(updatedCustomer);

    console.log('保存成功');
  } catch (error) {
    console.error('保存失败:', error);
  }
};

// 更新用户信息 API 请求
const updateCustomer = async (customerData: any) => {
  // 这里应该根据后端的API来发送PUT或PATCH请求
  try {
    const response = await updateCustomerById(customerData);
    return response.data;
  } catch (error) {
    console.error("更新用户信息失败:", error);
    throw error;
  }
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
.info-box{
  gap: 20px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.input-box{
  display: flex;
  gap: 20px;
}
.character {
  width: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.orders-block {
  gap: 20px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #f0f0f0;
}
.order{
  width:90%;
  margin-bottom: 20px;
}
</style>
