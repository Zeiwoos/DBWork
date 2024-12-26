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
    <!-- Author 列，文本超长时显示省略号 -->
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
    <!--    <el-table-column prop="keywords" label="Keywords" width="120" />-->
    <!--    <el-table-column prop="description" label="Description" width="120"/>-->
    <el-table-column prop="status" label="Status" width="100"/>

    <el-table-column fixed="right" label="Operations" min-width="120">
      <template #default="{ row }">
        <el-button link type="primary" size="small" plain @click="handleEditClick(row)">edit</el-button>
        <el-button link type="danger" size="small" plain @click="handleDeleteClick(row.orderId)">Delete</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-button type="primary" @click="insertDialogVisible = true">添加订单</el-button>

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
import {onBeforeMount, onMounted, ref} from "vue";
import {
  editOrder,
  getDetailsByOrderID,
  createWithDetails,
  getAllOrders,
  editStatus,
  getOrderByCustomerID,
  deleteOrder,
  getOrderByID
} from '@/api/Order';

// 当前搜索框和对话框的控制
const searchOrderID = ref('');
const searchCustomerId = ref('');
const editDialogVisible = ref(false);
const insertDialogVisible = ref(false);

export interface Order {
  orderId:number;
  customerId: number;
  orderDate: string;
  totalAmount: number;
  shippingAddress: string;
  status: string;
}

export interface OrderDTO {
  customerId: number;
  shippingAddress: string;
}





const currentOrder = ref<Order>({
  orderId: 0,
  customerId: 0,
  orderDate: '',
  totalAmount: 0,
  shippingAddress: '',
  status: '',
});


const newOrder = ref<Order>({
  orderId: 0,
  customerId: 0,
  orderDate: '',
  totalAmount: 0,
  shippingAddress: '',
  status: '',
});

const filteredOrdersData = ref([]);

// 获取所有书籍
const fetchOrders = async () => {
  try {
    const response = await getAllOrders();
    // console.info(response.data)
    // 假设 response.data.data 是一个数组类型，但不一定是 Order[] 类型
    if (Array.isArray(response.data.data)) {
      filteredOrdersData.value = response.data.data;  // 使用类型断言将其视为 Order[] 类型
      // console.info('Filtered Orders data:', filteredOrdersData.value);

    } else {
      console.error('返回的数据格式错误，应该是一个数组');
      filteredOrdersData.value = [];  // 如果数据格式不正确，赋予空数组
    }
  } catch (error) {
    console.error('获取订单失败', error);
  }
};

// ID搜索功能
const handleSearchOrder = () => {
  // console.info(searchOrderID.value)
  if (searchOrderID.value) {
    // 仅根据输入的 OrderID 过滤当前数据
    filteredOrdersData.value = filteredOrdersData.value.filter(Order =>
        Order.orderId.toString().includes(searchOrderID.value)
    );
  } else {
    // 如果没有输入搜索ID，则重新加载所有书籍数据
    fetchOrders();
  }
};
// 名字搜索功能
const handleSearchOrderByCustomerId = () => {
  // console.info(searchOrderID.value)
  if (searchCustomerId.value) {

    filteredOrdersData.value = filteredOrdersData.value.filter(Order =>
        Order.customerId.toString().includes(searchCustomerId.value)
    );
  } else {
    // 如果没有输入搜索ID，则重新加载所有书籍数据
    fetchOrders();
  }
};

// 行样式
const tableRowClassName = ({ row }: { row: Order }) => {
  if (row.status == "Pending") {
    return 'warning-row';
  }
  return '';
};

// 点击编辑按钮时，设置当前编辑的书籍
const handleEditClick = (row: Order) => {
  currentOrder.value = { ...row }; // 复制当前行的数据到编辑对象
  editDialogVisible.value = true;
};

const handleSaveEdit = async () => {
  try {
    console.log(currentOrder.value)
    await editOrder(currentOrder.value.orderId, currentOrder.value);
    await fetchOrders();
    editDialogVisible.value = false; // 关闭编辑对话框
  } catch (error) {
    console.error('更新订单失败', error);
  }
};

// 插入新书籍
const handleInsertOrder = async () => {
  try {
    await createWithDetails(newOrder.value);
    await fetchOrders(); // 获取最新的书籍数据
    insertDialogVisible.value = false;
    newOrder.value = {
      orderId: 0,
      customerId: 0,
      orderDate: '',
      totalAmount: 0,
      shippingAddress: '',
      status: '',
    };
  } catch (error) {
    console.error('添加订单失败', error);
  }
};

// 删除书籍
const handleDeleteClick = async (OrderID: number) => {
  try {
    // console.log(OrderID)
    await deleteOrder(OrderID);
    await fetchOrders(); // 删除后重新获取书籍数据
  } catch (error) {
    console.error('删除订单失败', error);
    alert("删除订单失败！可能有别的项目于依赖该书");
  }
};

// 删除书籍
const handleGetOrderByIDClick = async (OrderID: number) => {
  try {
    // console.log(OrderID)
    await getOrderByID(OrderID);
    await fetchOrders(); // 删除后重新获取书籍数据
  } catch (error) {
    console.error('搜索订单失败', error);
    alert("未查询到该订单！");
  }
};
// 初始化加载书籍
onBeforeMount(() => {
  fetchOrders();
})
</script>

<style scoped>
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}

.ellipsis-text {
  white-space: nowrap;         /* 禁止换行 */
  overflow: hidden;            /* 隐藏溢出的部分 */
  text-overflow: ellipsis;     /* 超出部分显示省略号 */
  display: inline-block;       /* 使 div 可以应用 text-overflow */
  max-width: 100%;             /* 使文本自适应列的宽度 */
}
</style>
