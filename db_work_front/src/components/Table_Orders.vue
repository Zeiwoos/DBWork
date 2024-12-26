<template>
  <el-input
      v-model="searchOrderID"
      placeholder="请输入OrderID进行查询"
      clearable
      style="margin-bottom: 20px; width: 300px;"
      @input="handleSearchOrder"
  />
  <el-input
      v-model="searchCustomerId"
      placeholder="请输入CustomerId进行查询"
      clearable
      style="margin-bottom: 20px; width: 300px;"
      @input="handleSearchOrderByCustomerId"
  />
  <el-table
      :data="filteredOrdersData"
      style="width: 100%"
      :row-class-name="tableRowClassName"
  >
    <el-table-column prop="orderId" label="OrderID" width="80" />
    <el-table-column prop="customerId" label="CustomerID" width="100"/>
    <el-table-column label="OrderDate" width="140">
      <template #default="{ row }">
        <div class="ellipsis-text">{{ row.orderDate }}</div>
      </template>
    </el-table-column>

    <el-table-column label="ShippingAddress" width="140">
      <template #default="{ row }">
        <div class="ellipsis-text">{{ row.shippingAddress }}</div>
      </template>
    </el-table-column>

    <el-table-column prop="status" label="Status" width="100"/>

    <!-- 订单详情列 -->
    <el-table-column label="Detail" width="100">
      <template #default="{ row }">
        <el-popover
            placement="top-start"
            title="订单详情"
            :width="300"
            trigger="click"
        >
          <div class="popover-content">
            <el-row v-for="detail in orderDetails" :key="detail.orderDetailID">
              <div class="detail-box">
                <p>采购详情号：{{ detail.orderDetailID }}</p>
                <p>书号：{{ detail.bookId }}</p>
                <p>数量：{{ detail.quantity }}</p>
                <p>总金额：{{ detail.price }}</p>
              </div>
              <el-divider></el-divider>
            </el-row>
          </div>
          <template #reference>
            <el-button link type="primary" @click="showOrderDetails(row.orderId)">Detail</el-button>
          </template>
        </el-popover>
      </template>
    </el-table-column>

    <!-- 操作列 -->
    <el-table-column fixed="right" label="Operations" min-width="120">
      <template #default="{ row }">
        <el-button link type="primary" plain @click="handleEditClick(row)">Edit</el-button>
        <el-button link type="danger" plain @click="handleDeleteClick(row.orderId)">Delete</el-button>

      </template>
    </el-table-column>
  </el-table>

  <!-- Edit Dialog -->
  <el-dialog
      v-model="editDialogVisible"
      title="Edit Order"
      width="500"
      destroy-on-close
      center
  >
    <el-form>
      <el-form-item label="OrderID">
        <el-input v-model="currentOrder.orderId" disabled></el-input>
      </el-form-item>
      <el-form-item label="CustomerID">
        <el-input v-model="currentOrder.customerId" disabled></el-input>
      </el-form-item>
      <el-form-item label="OrderDate">
        <el-input v-model="currentOrder.orderDate" disabled></el-input>
      </el-form-item>
      <el-form-item label="TotalAmount">
        <el-input v-model="currentOrder.totalAmount" disabled></el-input>
      </el-form-item>
      <el-form-item label="ShippingAddress">
        <el-input v-model="currentOrder.shippingAddress"></el-input>
      </el-form-item>
      <el-form-item label="Status">
        <el-input v-model="currentOrder.status"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="editDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="handleSaveEdit">Confirm</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- Insert Dialog -->
  <el-dialog
      v-model="insertDialogVisible"
      title="Add New Order"
      width="500"
      destroy-on-close
      center
  >
    <el-form :model="newOrder">
      <el-form-item label="OrderID">
        <el-input v-model="newOrder.orderId" placeholder="Enter OrderID" disabled></el-input>
      </el-form-item>
      <el-form-item label="CustomerId">
        <el-input v-model="newOrder.customerId" placeholder="Enter CustomerId"></el-input>
      </el-form-item>
      <el-form-item label="OrderDate">
        <el-input v-model="newOrder.orderDate" placeholder="Enter OrderDate"></el-input>
      </el-form-item>
      <el-form-item label="TotalAmount">
        <el-input v-model="newOrder.totalAmount" placeholder="Enter TotalAmount"></el-input>
      </el-form-item>
      <el-form-item label="ShippingAddress">
        <el-input v-model="newOrder.shippingAddress" placeholder="Enter ShippingAddress"></el-input>
      </el-form-item>
      <el-form-item label="Status">
        <el-input v-model="newOrder.status" placeholder="Enter Status"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="insertDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="handleInsertOrder">Confirm</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ElButton, ElInput, ElTable, ElTableColumn } from "element-plus";
import { onBeforeMount, ref } from "vue";
import { getAllOrders, getDetailsByOrderID, deleteOrder, editOrder, createWithDetails } from '@/api/Order';

// 控制弹出框
const searchOrderID = ref('');
const searchCustomerId = ref('');
const editDialogVisible = ref(false);
const insertDialogVisible = ref(false);
const orderDetails = ref([]);

// 当前订单
const currentOrder = ref({
  orderId: 0,
  customerId: 0,
  orderDate: '',
  totalAmount: 0,
  shippingAddress: '',
  status: ''
});

// 新订单
const newOrder = ref({
  orderId: 0,
  customerId: 0,
  orderDate: '',
  totalAmount: 0,
  shippingAddress: '',
  status: ''
});

// 过滤后的订单数据
const filteredOrdersData = ref([]);

// 获取所有订单
const fetchOrders = async () => {
  try {
    const response = await getAllOrders();
    if (Array.isArray(response.data.data)) {
      filteredOrdersData.value = response.data.data;
    }
  } catch (error) {
    console.error('获取订单失败', error);
  }
};

// 搜索功能
const handleSearchOrder = () => {
  if (searchOrderID.value) {
    filteredOrdersData.value = filteredOrdersData.value.filter(order =>
        order.orderId.toString().includes(searchOrderID.value)
    );
  } else {
    fetchOrders();
  }
};

const handleSearchOrderByCustomerId = () => {
  if (searchCustomerId.value) {
    filteredOrdersData.value = filteredOrdersData.value.filter(order =>
        order.customerId.toString().includes(searchCustomerId.value)
    );
  } else {
    fetchOrders();
  }
};

// 显示订单详情
const showOrderDetails = async (orderId) => {
  try {
    const response = await getDetailsByOrderID(orderId);
    console.log(response)
    if (response.data.code === 1) {
      orderDetails.value = response.data.data;
    }
  } catch (error) {
    console.error("获取订单详情失败", error);
  }
};

// 处理编辑订单
const handleEditClick = (row) => {
  currentOrder.value = { ...row };
  editDialogVisible.value = true;
};

const handleSaveEdit = async () => {
  try {
    await editOrder(currentOrder.value.orderId, currentOrder.value);
    fetchOrders();
    editDialogVisible.value = false;
  } catch (error) {
    console.error('更新订单失败', error);
  }
};

// 处理删除订单
const handleDeleteClick = async (orderId) => {
  try {
    await deleteOrder(orderId);
    fetchOrders();
  } catch (error) {
    console.error('删除订单失败', error);
  }
};

// 初始化加载订单
onBeforeMount(() => {
  fetchOrders();
});
</script>

<style scoped>
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}

.ellipsis-text {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.popover-content {
  max-height: 300px; /* 设置最大高度 */
  overflow-y: auto; /* 启用垂直滚动 */
  padding-right: 10px; /* 增加右侧内边距以防止滚动条与内容重叠 */
}
</style>
