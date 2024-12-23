<template>
  <div class="order-box">
    <el-form class="form">
      <!-- 第一行 -->
      <el-row class="row">
        <el-col class="item">
          <span>订单号</span>
          <el-input v-model="orderID" style="width: 240px" disabled></el-input>
        </el-col>
        <el-col class="item">
          <span>用户ID</span>
          <el-input v-model="userID" style="width: 240px" disabled></el-input>
        </el-col>
        <el-col class="item">
          <span>订货时间</span>
          <el-input v-model="orderTime" style="width: 240px" disabled></el-input>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row class="row">
        <el-col class="item">
          <span>书号</span>
          <el-input v-model="bookID" style="width: 240px" disabled></el-input>
        </el-col>
        <el-col class="item">
          <span>订货数量</span>
          <el-input v-model="orderAmount" style="width: 240px" disabled></el-input>
        </el-col>
        <el-col class="item">
          <span>订单价格</span>
          <el-input v-model="orderPrice" style="width: 240px" disabled></el-input>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row class="row">
        <el-col class="item">
          <span>配送地址</span>
          <el-input v-model="orderAddress" style="width: 240px" disabled></el-input>
        </el-col>
        <el-col class="item">
          <span>订单状态</span>
          <el-input v-model="orderStatus" style="width: 240px" disabled></el-input>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row class="row">
        <el-col class="btn-col">
          <el-form-item>
            <el-button type="primary" @click="handleModifyOrder">删除订单</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';

const orderID = ref('');
const orderTime = ref('');
const userID = ref('');
const bookID = ref('');
const orderAmount = ref('');
const orderPrice = ref('');
const orderAddress = ref('');
const orderStatus = ref('');

const handleModifyOrder = () => {
  console.log("订单修改逻辑");
  // 执行订单修改逻辑
};
</script>

<style scoped>
.order-box {
  padding: 20px;
  width: 100%;
  background-color: #fff;
  border-radius: 10px;
}

.form {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  row-gap: 20px;
}

.row {
  display: contents; /* 保证每一行项在grid布局内 */
}

.item {
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 100%;
}

.item span {
  font-size: 14px;
  color: #333;
}

.el-input {
  width: 100%;
}

.btn-col {
  grid-column: span 3; /* 按钮独占一行 */
  text-align: center;
}

.el-button {
  width: 240px;
  margin-top: 20px;
}
</style>
