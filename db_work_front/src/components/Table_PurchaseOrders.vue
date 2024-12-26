<template>
  <!-- 搜索框 -->
  <el-input
      v-model="searchPurchaseOrderID"
      placeholder="请输入采购单ID进行查询"
      clearable
      style="margin-bottom: 20px; width: 300px;"
      @input="handleSearchPurchaseOrder"
  />

  <!-- 表格 -->
  <el-table
      :data="filteredPurchaseOrdersData"
      style="width: 100%"
      :row-class-name="tableRowClassName"
  >
    <el-table-column prop="PurchaseOrderID" label="采购单ID" width="100" />
    <el-table-column prop="SupplierID" label="供应商ID" width="120" />
    <el-table-column prop="OrderDate" label="采购时间" width="120" />
    <el-table-column prop="Status" label="采购状态" width="150" />
    <el-table-column fixed="right" label="操作" min-width="140">
      <template #default="{ row }">
        <el-button link type="primary" @click="handleClickPurchaseOrder(row)">Edit</el-button>
        <el-button link type="danger" @click="handleClickDeletePurchaseOrder(row.PurchaseOrderID)">Delete</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!-- 添加缺书对话框 -->
  <el-dialog
      v-model="addPurchaseOrderDialogVisible"
      title="添加采购单"
      width="500"
      destroy-on-close
      center
  >
    <el-form :model="newPurchaseOrder">
      <el-form-item label="供应商ID">
        <el-input v-model="newPurchaseOrder.SupplierID" placeholder="请输入供应商ID"></el-input>
      </el-form-item>
      <el-form-item label="采购时间">
        <el-input v-model="newPurchaseOrder.OrderDate" placeholder="请输入采购时间"></el-input>
      </el-form-item>
      <el-form-item label="采购状态">
        <el-select v-model="newPurchaseOrder.Status" placeholder="请选择采购状态">
          <el-option label="Pending" value="Pending"></el-option>
          <el-option label="Completed" value="Completed"></el-option>
          <el-option label="Canceled" value="Canceled"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="addPurchaseOrderDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="handleAddPurchaseOrder">Confirm</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 编辑缺书对话框 -->
  <el-dialog
      v-model="editPurchaseOrderDialogVisible"
      title="编辑采购单"
      width="500"
      destroy-on-close
      center
  >
    <el-form :model="currentPurchaseOrder">
      <el-form-item label="采购单ID">
        <el-input v-model="currentPurchaseOrder.PurchaseOrderID" disabled></el-input>
      </el-form-item>
      <el-form-item label="供应商ID">
        <el-input v-model="currentPurchaseOrder.SupplierID" disabled></el-input>
      </el-form-item>
      <el-form-item label="采购时间">
        <el-input v-model="currentPurchaseOrder.OrderDate" disabled></el-input>
      </el-form-item>
      <el-form-item label="采购状态">
        <el-select v-model="currentPurchaseOrder.Status" placeholder="请选择采购状态">
          <el-option label="Pending" value="Pending"></el-option>
          <el-option label="Completed" value="Completed"></el-option>
          <el-option label="Canceled" value="Canceled"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="editPurchaseOrderDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="handleEditPurchaseOrder">Confirm</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 添加缺书单按钮 -->
  <el-button type="primary" @click="addPurchaseOrderDialogVisible = true">添加缺书单</el-button>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ElButton, ElInput, ElTable, ElTableColumn, ElDialog, ElForm, ElFormItem } from 'element-plus';

const searchPurchaseOrderID = ref('');
const addPurchaseOrderDialogVisible = ref(false);
const editPurchaseOrderDialogVisible = ref(false);
const currentPurchaseOrder = ref<PurchaseOrder>({
  PurchaseOrderID: 0,
  SupplierID: 0,
  OrderDate: '',
  Status: '',
});
const newPurchaseOrder = ref<PurchaseOrder>({
  PurchaseOrderID: 0,
  SupplierID: 0,
  OrderDate: '',
  Status: '',
});

interface PurchaseOrder {
  PurchaseOrderID: number;
  SupplierID: number;
  OrderDate: string;
  Status: string;
}

const PurchaseOrdersData: PurchaseOrder[] = [
  {
    PurchaseOrderID: 1,
    SupplierID: 101,
    OrderDate: '2024-12-15',
    Status: 'Pending',
  },
];

const filteredPurchaseOrdersData = ref(PurchaseOrdersData);

// 搜索功能
const handleSearchPurchaseOrder = () => {
  if (searchPurchaseOrderID.value) {
    filteredPurchaseOrdersData.value = PurchaseOrdersData.filter(
        (item) => item.PurchaseOrderID.toString().includes(searchPurchaseOrderID.value)
    );
  } else {
    filteredPurchaseOrdersData.value = PurchaseOrdersData;
  }
};

// 行样式
const tableRowClassName = ({ row }: { row: PurchaseOrder }) => {
  if (row.MissingQuantity > 5) {
    return 'warning-row';
  }
  return '';
};

// 点击编辑按钮
const handleClickPurchaseOrder = (row: PurchaseOrder) => {
  currentPurchaseOrder.value = { ...row }; // 复制当前行的数据到编辑对象
  editPurchaseOrderDialogVisible.value = true;
};

// 保存编辑后的缺书单
const handleEditPurchaseOrder = () => {
  const index = PurchaseOrdersData.findIndex(
      (item) => item.PurchaseOrderID === currentPurchaseOrder.value.PurchaseOrderID
  );
  if (index !== -1) {
    PurchaseOrdersData[index] = { ...currentPurchaseOrder.value }; // 更新缺书单数据
  }
  filteredPurchaseOrdersData.value = [...PurchaseOrdersData]; // 更新表格显示
  editPurchaseOrderDialogVisible.value = false;
};

// 添加缺书单
const handleAddPurchaseOrder = () => {
  const newPurchaseOrderID = PurchaseOrdersData.length + 1;
  const newPurchaseOrderData = { ...newPurchaseOrder.value, PurchaseOrderID: newPurchaseOrderID };
  PurchaseOrdersData.push(newPurchaseOrderData);
  filteredPurchaseOrdersData.value = [...PurchaseOrdersData];
  addPurchaseOrderDialogVisible.value = false;
  newPurchaseOrder.value = {
    PurchaseOrderID: 0,
    SupplierID: 0,
    OrderDate: '',
    Status: '',
  };
};

// 删除缺书单
const handleClickDeleteMissing = (PurchaseOrderID: number) => {
  const index = PurchaseOrdersData.findIndex(
      (item) => item.PurchaseOrderID === PurchaseOrderID
  );
  if (index !== -1) {
    PurchaseOrdersData.splice(index, 1); // 删除缺书单
  }
  filteredPurchaseOrdersData.value = [...PurchaseOrdersData]; // 更新表格显示
};
</script>

<style scoped>
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}

.el-table .danger-row {
  --el-table-tr-bg-color: var(--el-color-danger-light-9);
}
</style>
