<template>
  <div style="gap: 20px">
    <!-- 搜索框 -->
    <el-input
        v-model="searchOrderID"
        placeholder="请输入订单ID进行查询"
        clearable
        style="margin-bottom: 20px; width: 300px;"
        @input="handleSearchOrder"
    />

    <!-- 表格 -->
    <el-table
        :data="filteredOrdersData"
        style="width: 100%"
        :row-class-name="tableRowClassName"
    >
      <el-table-column prop="OrderID" label="订单ID" width="100" />
      <el-table-column prop="CustomerID" label="顾客ID" width="120" />
      <el-table-column prop="OrderDate" label="订单日期" width="120" />
      <el-table-column prop="TotalAmount" label="总金额" width="100" />
      <el-table-column prop="ShippingAddress" label="送货地址" width="140" />
      <el-table-column prop="Status" label="订单状态" width="100" />
      <el-table-column fixed="right" label="操作" min-width="140">
        <template #default="{ row }">
          <el-button link type="primary" size="small" @click="handleClickEditOrder(row)">Edit</el-button>
          <el-button link type="danger" size="small" @click="handleClickDeleteOrder(row.OrderID)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 添加订单按钮 -->
    <el-button type="primary" @click="addOrderDialogVisible = true">添加订单</el-button>
    <!-- 添加订单对话框 -->
    <el-dialog
        v-model="addOrderDialogVisible"
        title="添加订单"
        width="500"
        destroy-on-close
        center
    >
      <el-form :model="newOrder">
        <el-form-item label="顾客ID">
          <el-input v-model="newOrder.CustomerID" placeholder="请输入顾客ID"></el-input>
        </el-form-item>
        <el-form-item label="订单日期">
          <el-input v-model="newOrder.OrderDate" placeholder="请输入订单日期"></el-input>
        </el-form-item>
        <el-form-item label="总金额">
          <el-input v-model="newOrder.TotalAmount" placeholder="请输入总金额"></el-input>
        </el-form-item>
        <el-form-item label="送货地址">
          <el-input v-model="newOrder.ShippingAddress" placeholder="请输入送货地址"></el-input>
        </el-form-item>
        <el-form-item label="订单状态">
          <el-select v-model="newOrder.Status" placeholder="请选择订单状态">
            <el-option label="Pending" value="Pending"></el-option>
            <el-option label="Completed" value="Completed"></el-option>
            <el-option label="Canceled" value="Canceled"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addOrderDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleAddOrder">确认</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 编辑订单对话框 -->
    <el-dialog
        v-model="editOrderDialogVisible"
        title="编辑订单"
        width="500"
        destroy-on-close
        center
    >
      <el-form :model="currentOrder">
        <el-form-item label="订单ID">
          <el-input v-model="currentOrder.OrderID" disabled></el-input>
        </el-form-item>
        <el-form-item label="顾客ID">
          <el-input v-model="currentOrder.CustomerID"></el-input>
        </el-form-item>
        <el-form-item label="订单日期">
          <el-input v-model="currentOrder.OrderDate"></el-input>
        </el-form-item>
        <el-form-item label="总金额">
          <el-input v-model="currentOrder.TotalAmount"></el-input>
        </el-form-item>
        <el-form-item label="送货地址">
          <el-input v-model="currentOrder.ShippingAddress"></el-input>
        </el-form-item>
        <el-form-item label="订单状态">
          <el-select v-model="currentOrder.Status" placeholder="请选择订单状态">
            <el-option label="Pending" value="Pending"></el-option>
            <el-option label="Completed" value="Completed"></el-option>
            <el-option label="Canceled" value="Canceled"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="editOrderDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleEditOrder">确认</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ElButton, ElInput, ElTable, ElTableColumn, ElDialog, ElForm, ElFormItem, ElSelect, ElOption } from 'element-plus';

// 搜索框绑定值
const searchOrderID = ref('');

// 订单数据接口
interface Order {
  OrderID: number;
  CustomerID: number;
  OrderDate: string;
  TotalAmount: number;
  ShippingAddress: string;
  Status: string;
}
const OrdersData: Order[] = [
  {
    OrderID: 1,
    CustomerID: 1,
    OrderDate: '2024-12-15',
    TotalAmount: 150,
    ShippingAddress: 'Address1',
    Status: 'Pending',
  },
];
const filteredOrdersData = ref(OrdersData);

// 添加订单的对话框可见性
const addOrderDialogVisible = ref(false);
const editOrderDialogVisible = ref(false);

// 当前订单编辑数据
const currentOrder = ref<Order>({
  OrderID: 0,
  CustomerID: 0,
  OrderDate: '',
  TotalAmount: 0,
  ShippingAddress: '',
  Status: 'Pending',
});

// 新订单数据
const newOrder = ref<Order>({
  OrderID: 0,
  CustomerID: 0,
  OrderDate: '',
  TotalAmount: 0,
  ShippingAddress: '',
  Status: 'Pending',
});

// 搜索功能
const handleSearchOrder = () => {
  if (searchOrderID.value) {
    filteredOrdersData.value = OrdersData.filter(
        (item) => item.OrderID.toString().includes(searchOrderID.value)
    );
  } else {
    filteredOrdersData.value = OrdersData;
  }
};

// 行样式
const tableRowClassName = ({ row }: { row: Order }) => {
  if (row.Status === 'Pending') {
    return 'info-row'; // 待处理订单
  } else if (row.Status === 'Completed') {
    return 'success-row'; // 已完成订单
  }
  return '';
};

// 编辑订单
const handleClickEditOrder = (row: Order) => {
  currentOrder.value = { ...row }; // 复制当前订单的数据到编辑对象
  editOrderDialogVisible.value = true;
};

// 更新订单
const handleEditOrder = () => {
  const index = OrdersData.findIndex(
      (item) => item.OrderID === currentOrder.value.OrderID
  );
  if (index !== -1) {
    OrdersData[index] = { ...currentOrder.value }; // 更新订单数据
  }
  filteredOrdersData.value = [...OrdersData]; // 更新表格显示
  editOrderDialogVisible.value = false;
};

// 添加订单
const handleAddOrder = () => {
  const newOrderID = OrdersData.length + 1;
  const newOrderData = { ...newOrder.value, OrderID: newOrderID };
  OrdersData.push(newOrderData);
  filteredOrdersData.value = [...OrdersData];
  addOrderDialogVisible.value = false;
  newOrder.value = {
    OrderID: 0,
    CustomerID: 0,
    OrderDate: '',
    TotalAmount: 0,
    ShippingAddress: '',
    Status: 'Pending',
  };
};

// 删除订单
const handleClickDeleteOrder = (OrderID: number) => {
  const index = OrdersData.findIndex(
      (item) => item.OrderID === OrderID
  );
  if (index !== -1) {
    OrdersData.splice(index, 1); // 删除订单
  }
  filteredOrdersData.value = [...OrdersData]; // 更新表格显示
};
</script>

<style scoped>
.el-table .info-row {
  --el-table-tr-bg-color: var(--el-color-info-light-9);
}

.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}

.el-table .danger-row {
  --el-table-tr-bg-color: var(--el-color-danger-light-9);
}
</style>
