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
          <span>订单总额</span>
          <el-input v-model="internalOrderPrice" style="width: 240px" disabled></el-input>
        </el-col>
        <el-col class="item">
          <span>配送地址</span>
          <el-input v-model="internalOrderAddress" style="width: 240px" disabled></el-input>
        </el-col>
        <el-col class="item">
          <span>订单状态</span>
          <el-input v-model="internalOrderStatus" style="width: 240px" disabled></el-input>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row class="row">
        <el-col class="item">

          <el-popover
              placement="top-start"
              title="订单详情"
              :width="200"
              trigger="hover"
              content="this is content, this is content, this is content"
          >
            <el-col>
              <elrow>

              </elrow>
            </el-col>
            <template #reference>
              <el-button type="primary" @click="showOrderDetails">查看订单详情</el-button>
            </template>
          </el-popover>
        </el-col>
        <el-col class="item">
          <el-button type="primary" @click="handleModifyOrder">取消订单</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { DeleteOrder, getDetailsByOrderID } from "@/api/Order";
import { ref } from "vue";

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
      internalOrderStatus: this.orderStatus,
      dialogVisible: false,
      orderDetails: null
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
        await DeleteOrder(this.internalOrderID);
        this.$emit('orderDeleted', this.internalOrderID); // 通知父组件订单已删除
        alert("订单已删除");
        window.location.reload();
      } catch (error) {
        console.error("删除订单失败", error);
        alert("删除订单失败");
      }
    },

    async showOrderDetails() {
      try {
        const responseOrderDetail = await getDetailsByOrderID(this.internalOrderID);
        console.log(responseOrderDetail)
        if (responseOrderDetail.data.code === 1) {
          this.orderDetails = responseOrderDetail.data.data;
          this.dialogVisible = true;
        } else {
          alert("未找到订单详情");
        }
      } catch (error) {
        console.error("获取订单详情失败", error);
        alert("获取订单详情失败");
      }
    },

    handleClose(done) {
      this.dialogVisible = false;
      done();
    }
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
