<template>
  <!-- 搜索框 -->
  <el-input
      v-model="searchCustomerID"
      placeholder="请输入CustomerID进行查询"
      clearable
      style="margin-bottom: 20px; width: 300px;"
      @input="handleSearchCustomer"
  />

  <!-- 客户数据表格 -->
  <el-table
      :data="filteredCustomersData"
      style="width: 100%"
      :row-class-name="tableRowClassName"
  >
    <el-table-column prop="CustomerID" label="CustomerID" width="100" />
    <el-table-column prop="CustomerName" label="CustomerName" width="140" />
    <el-table-column prop="Email" label="Email" width="150" />
    <el-table-column prop="Phone" label="Phone" width="120" />
    <el-table-column prop="Balance" label="Balance" width="80" />
    <el-table-column prop="CreditLevel" label="CreditLevel" width="100"/>
    <el-table-column fixed="right" label="Operations" min-width="180">
      <template #default="{ row }">
        <el-button link type="primary" size="small" @click="handleEditClick(row)">Edit</el-button>
        <el-button link type="danger" size="small" @click="handleDeleteCustomer(row.CustomerID)">Delete</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!-- 插入新客户的按钮 -->
  <el-button type="primary" @click="insertDialogVisible = true">添加用户</el-button>

  <!-- 修改客户对话框 -->
  <el-dialog
      v-model="editDialogVisible"
      title="Edit Customer"
      width="500"
      destroy-on-close
      center
  >
    <el-form :model="currentCustomer">
      <el-form-item label="CustomerID">
        <el-input v-model="currentCustomer.CustomerID" disabled></el-input>
      </el-form-item>
      <el-form-item label="CustomerName">
        <el-input v-model="currentCustomer.CustomerName"></el-input>
      </el-form-item>
      <el-form-item label="Email">
        <el-input v-model="currentCustomer.Email"></el-input>
      </el-form-item>
      <el-form-item label="Phone">
        <el-input v-model="currentCustomer.Phone"></el-input>
      </el-form-item>
      <el-form-item label="Balance">
        <el-input v-model="currentCustomer.Balance"></el-input>
      </el-form-item>
      <el-form-item label="CreditLevel">
        <el-input v-model="currentCustomer.CreditLevel"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="editDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="handleSaveEdit">Confirm</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 添加新客户的对话框 -->
  <el-dialog
      v-model="insertDialogVisible"
      title="Add New Customer"
      width="500"
      destroy-on-close
      center
  >
    <el-form :model="newCustomer">
      <el-form-item label="CustomerName">
        <el-input v-model="newCustomer.CustomerName"></el-input>
      </el-form-item>
      <el-form-item label="Email">
        <el-input v-model="newCustomer.Email"></el-input>
      </el-form-item>
      <el-form-item label="Phone">
        <el-input v-model="newCustomer.Phone"></el-input>
      </el-form-item>
      <el-form-item label="Balance">
        <el-input v-model="newCustomer.Balance"></el-input>
      </el-form-item>
      <el-form-item label="CreditLevel">
        <el-input v-model="newCustomer.CreditLevel"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="insertDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="handleInsertCustomer">Confirm</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ElButton, ElInput, ElTable, ElTableColumn, ElDialog, ElForm, ElFormItem } from "element-plus";
import { ref } from "vue";

// 客户数据接口
interface Customer {
  CustomerID: number;
  CustomerName: string;
  Email: string;
  Phone: string;
  Balance: number;
  CreditLevel: number;
}

// 客户数据（初始数据）
const CustomersData: Customer[] = [
  {
    CustomerID: 1,
    CustomerName: 'John Brown',
    Email: 'john@example.com',
    Phone: '1234567890',
    Balance: 100.0,
    CreditLevel: 1,
  },
  {
    CustomerID: 2,
    CustomerName: 'Alice Smith',
    Email: 'alice@example.com',
    Phone: '0987654321',
    Balance: 200.0,
    CreditLevel: 2,
  },
];

const filteredCustomersData = ref(CustomersData);

// 搜索功能
const searchCustomerID = ref('');
const handleSearchCustomer = () => {
  if (searchCustomerID.value) {
    filteredCustomersData.value = CustomersData.filter(customer =>
        customer.CustomerID.toString().includes(searchCustomerID.value)
    );
  } else {
    filteredCustomersData.value = CustomersData;
  }
};

// 当前正在编辑的客户
const editDialogVisible = ref(false);
const currentCustomer = ref<Customer>({
  CustomerID: 0,
  CustomerName: '',
  Email: '',
  Phone: '',
  Balance: 0,
  CreditLevel: 0,
});

// 新客户数据
const insertDialogVisible = ref(false);
const newCustomer = ref<Customer>({
  CustomerID: 0,
  CustomerName: '',
  Email: '',
  Phone: '',
  Balance: 0,
  CreditLevel: 0,
});

// 点击编辑按钮时，设置当前编辑的客户
const handleEditClick = (row: Customer) => {
  currentCustomer.value = { ...row };
  editDialogVisible.value = true;
};

// 保存编辑后的客户信息
const handleSaveEdit = () => {
  const index = CustomersData.findIndex(customer => customer.CustomerID === currentCustomer.value.CustomerID);
  if (index !== -1) {
    CustomersData[index] = { ...currentCustomer.value };
  }
  filteredCustomersData.value = [...CustomersData]; // 更新表格
  editDialogVisible.value = false; // 关闭编辑对话框
};

// 删除客户
const handleDeleteCustomer = (customerID: number) => {
  CustomersData.splice(CustomersData.findIndex(customer => customer.CustomerID === customerID), 1);
  filteredCustomersData.value = [...CustomersData]; // 更新表格
};

// 插入新客户
const handleInsertCustomer = () => {
  const newCustomerID = CustomersData.length + 1;
  const customerData = { ...newCustomer.value, CustomerID: newCustomerID };
  CustomersData.push(customerData);
  filteredCustomersData.value = [...CustomersData]; // 更新表格
  insertDialogVisible.value = false; // 关闭插入对话框
  newCustomer.value = { CustomerID: 0, CustomerName: '', Email: '', Phone: '', Balance: 0, CreditLevel: 0 }; // 重置新客户数据
};

// 行样式
const tableRowClassName = ({ row }: { row: Customer }) => {
  if (row.Balance < 100) {
    return 'warning-row'; // 提示余额低
  }
  return '';
};
</script>

<style scoped>
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
</style>
