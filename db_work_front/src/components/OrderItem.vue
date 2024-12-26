<template>
  <div class="order-box">
    <el-form class="form">
      <!-- 第一行 -->
      <el-row class="row">
        <el-col class="item">
          <span>订单号</span>
          <el-input v-model="internalOrderID" style="width: 240px" disabled></el-input>
        </el-col>
        <el-col class="item">
          <span>用户ID</span>
          <el-input v-model="internalUserID" style="width: 240px" disabled></el-input>
        </el-col>
        <el-col class="item">
          <span>订货时间</span>
          <el-input v-model="internalOrderTime" style="width: 240px" disabled></el-input>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row class="row">
        <el-col class="item">
          <span>书号</span>
          <el-input v-model="internalBookID" style="width: 240px" disabled></el-input>
        </el-col>
        <el-col class="item">
          <span>订货数量</span>
          <el-input v-model="internalOrderAmount" style="width: 240px" disabled></el-input>
        </el-col>
        <el-col class="item">
          <span>订单总额</span>
          <el-input v-model="internalOrderPrice" style="width: 240px" disabled></el-input>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row class="row">
        <el-col class="item">
          <span>配送地址</span>
          <el-input v-model=" internalOrderAddress" style="width: 240px" disabled></el-input>
        </el-col>
        <el-col class="item">
          <span>订单状态</span>
          <el-input v-model=" internalOrderStatus" style="width: 240px" disabled></el-input>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row class="row">
        <el-col class="btn-col">
          <el-form-item>
            <el-button type="primary" @click="handleModifyOrder">取消订单</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import {DeleteOrder} from "@/api/Order";
import router from "@/router/index.js";
export default {
  props: {
    orderID: Number,
    orderTime: String,
    userID: String,
    bookID: Number,
    orderAmount: Number,
    orderPrice: Number,
    orderAddress: String,
    orderStatus: String
  },
  data() {
    return {
      internalOrderID: this.orderID,
      internalOrderTime: this.orderTime,
      internalUserID: this.userID,
      internalBookID: this.bookID,
      internalOrderAmount: this.orderAmount,
      internalOrderPrice: this.orderPrice,
      internalOrderAddress: this.orderAddress,
      internalOrderStatus: this.orderStatus
    };
  },
  watch: {
    internalOrderID(newValue) {
      this.$emit('update:orderID', newValue);
    },
    internalOrderTime(newValue) {
      this.$emit('update:orderTime', newValue);
    },
    internalUserID(newValue) {
      this.$emit('update:userID', newValue);
    },
    internalBookID(newValue) {
      this.$emit('update:bookID', newValue);
    },
    internalOrderAmount(newValue) {
      this.$emit('update:orderAmount', newValue);
    },
    internalOrderPrice(newValue) {
      this.$emit('update:orderPrice', newValue);
    },
    internalOrderAddress(newValue) {
      this.$emit('update:orderAddress', newValue);
    },
    internalOrderStatus(newValue) {
      this.$emit('update:orderStatus', newValue);
    }
  },

  methods: {
    async handleModifyOrder() {
      try {
        // 调用 API 删除订单
        DeleteOrder(this.internalOrderID);
        this.$emit('orderDeleted', this.internalOrderID); // 通知父组件订单已删除
        alert("订单已删除");
        // 如果你不想使用 reload，应该更新父组件的状态，或者手动清空子组件状态
        window.location.reload();
      } catch (error) {
        console.error("删除订单失败", error);
        alert("删除订单失败");
      }
    },
  }
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
