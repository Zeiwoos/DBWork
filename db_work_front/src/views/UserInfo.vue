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
          <el-input v-model="userInfo.customerId" style="width: 240px" disabled />
          <el-button @click="handleUnchangable">修改</el-button>
        </div>
        <div class="input-box">
          <span class="character">信用等级</span>
          <el-input v-model="userInfo.creditLevel" style="width: 240px" disabled />
          <el-button @click="handleUnchangable">修改</el-button>
        </div>
        <div v-for="(item, index) in showItems" :key="index" class="input-box">
          <span class="character">{{ item.label }}</span>
          <el-input v-model="item.v_model" style="width: 240px" />
          <el-button @click="handleButtonClick(index)">修改</el-button>
        </div>
        <div class="input-box">
          <span class="character">密码</span>
          <el-input v-model="userInfo.password" style="width: 240px" type="password" show-password />
          <el-button @click="handleButtonClick">修改</el-button>
        </div>
        <div class="input-box">
          <span class="character">余额</span>
          <el-input v-model="userInfo.balance" style="width: 240px" />
          <el-button @click="handleBalanceRecharge">充值</el-button>
        </div>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </div>
      <div v-if="menuActiveIndex === '2'" class="orders-block">
        <div v-for="(order, index) in orders" :key="index" class="order">
          <OrderItem
              v-model:orderID="order.orderId"
              v-model:orderTime="order.orderDate"
              v-model:userID="CustomerId"
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
import { editCustomerInfo, getCustomerById } from "@/api/Customer";
import { getOrderByCustomerID } from "@/api/Order";
import selfImg from "@/assets/image/self-img.jpg";
import { ElMenu, ElMenuItem } from "element-plus";
import OrderItem from "@/components/OrderItem.vue";

const url = selfImg;
const CustomerId = localStorage.getItem('customerId');

const userInfo = ref({
  customerId: '',
  customerName: '',
  creditLevel: '',
  address: '',
  email: '',
  phone: '',
  balance: '',
  password: '',
});

const orders = ref([]);

// 用于绑定到表单的显示项
const showItems = ref([
  { label: "用户名称", v_model: '' },
  { label: "地址", v_model: '' },
  { label: "邮箱", v_model: '' },
  { label: "电话号码", v_model: '' },
]);

const menuActiveIndex = ref('1');

// 获取用户信息
const fetchUserData = async () => {
  if (CustomerId) {
    try {
      const responseCustomer = await getCustomerById(CustomerId);
      if (responseCustomer.data.code === 1) {
        const customer = responseCustomer.data.data;
        userInfo.value.customerId = customer.customerID;
        userInfo.value.customerName = customer.customerName;
        userInfo.value.creditLevel = customer.creditLevel.toString();
        userInfo.value.address = customer.address;
        userInfo.value.email = customer.email;
        userInfo.value.phone = customer.phone;
        userInfo.value.balance = customer.balance.toString();
        userInfo.value.password = customer.password;

        // 更新 showItems 中的 v_model 与 userInfo 保持一致
        showItems.value[0].v_model = userInfo.value.customerName;
        showItems.value[1].v_model = userInfo.value.address;
        showItems.value[2].v_model = userInfo.value.email;
        showItems.value[3].v_model = userInfo.value.phone;
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
      if (responseOrders.data.code === 1) {
        orders.value = responseOrders.data.data;
      }
    } catch (error) {
      console.error("获取订单信息失败:", error);
    }
  }
};

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
const handleButtonClick = async (index: number) => {
  const item = showItems.value[index];

  // 确保 showItems 中的修改值同步到 userInfo
  if (item.label === "用户名称") {
    userInfo.value.customerName = item.v_model;
  } else if (item.label === "地址") {
    userInfo.value.address = item.v_model;
  } else if (item.label === "邮箱") {
    userInfo.value.email = item.v_model;
  } else if (item.label === "电话号码") {
    userInfo.value.phone = item.v_model;
  }

  try {
    // 直接使用 userInfo 中的数据进行更新
    await editCustomerInfo(userInfo.value.customerId, userInfo.value);
    console.log('用户信息更新成功');
    alert('用户信息更新成功');
  } catch (error) {
    alert(`更新失败: ${error.message || '未知错误'}`);
  }
};

// 处理余额充值按钮点击事件
const handleBalanceRecharge = () => {
  const rechargeAmount = 1000;
  userInfo.value.balance = (parseFloat(userInfo.value.balance) + rechargeAmount).toString();
  handleButtonClick(1)
};

// 保存按钮的点击事件
const handleSave = async () => {
  try {
    // 直接通过 userInfo 更新
    await updateCustomer(userInfo.value);
    console.log('保存成功');
  } catch (error) {
    console.error('保存失败:', error);
  }
};

// 更新用户信息 API 请求
const updateCustomer = async (customerData: any) => {
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
