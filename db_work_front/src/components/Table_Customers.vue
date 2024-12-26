<template>
  <el-input
      v-model="searchCustomerID"
      placeholder="请输入CustomerID进行查询"
      clearable
      style="margin-bottom: 20px; width: 300px;"
      @input="handleSearchCustomer"
  />
  <el-input
      v-model="searchCustomerTitle"
      placeholder="请输入CustomerTitle进行查询"
      clearable
      style="margin-bottom: 20px; width: 300px;"
      @input="handleSearchCustomerByName"
  />
  <el-table
      :data="filteredCustomersData"
      style="width: 100%"
      :row-class-name="tableRowClassName"
  >
    <el-table-column prop="customerID" label="CustomerID" width="100" />
    <!-- Title 列，文本超长时显示省略号 -->
    <el-table-column label="CustomerName" width="140">
      <template #default="{ row }">
        <div class="ellipsis-text">{{ row.customerName }}</div>
      </template>
    </el-table-column>
    <!-- Author 列，文本超长时显示省略号 -->
    <el-table-column label="Email" width="80">
      <template #default="{ row }">
        <div class="ellipsis-text">{{ row.email }}</div>
      </template>
    </el-table-column>
    <el-table-column label="Phone" width="100">
      <template #default="{ row }">
        <div class="ellipsis-text">{{ row.phone }}</div>
      </template>
    </el-table-column>
    <!--    <el-table-column prop="keywords" label="Keywords" width="120" />-->
    <!--    <el-table-column prop="description" label="Description" width="120"/>-->
    <el-table-column prop="balance" label="Balance" width="60"/>
    <el-table-column label="Address" width="140">
      <template #default="{ row }">
        <div class="ellipsis-text">{{ row.address }}</div>
      </template>
    </el-table-column>
    <el-table-column label="Password" width="80">
      <template #default="{ row }">
        <div class="ellipsis-text">{{ row.password }}</div>
      </template>
    </el-table-column>
    <el-table-column prop="creditLevel" label="CreditLevel" width="100"/>
    <el-table-column fixed="right" label="Operations" min-width="120">
      <template #default="{ row }">
        <el-button link type="primary" size="small" plain @click="handleEditClick(row)">Edit</el-button>
        <el-button link type="danger" size="small" plain @click="handleDeleteClick(row.customerID)">Delete</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-button type="primary" @click="insertDialogVisible = true">添加客户</el-button>

  <!-- Edit Dialog -->
  <el-dialog
      v-model="editDialogVisible"
      title="Edit Customer"
      width="500"
      destroy-on-close
      center
  >
    <el-form>
      <el-form-item label="CustomerID">
        <el-input v-model="currentCustomer.customerID" disabled></el-input>
      </el-form-item>
      <el-form-item label="CustomerName">
        <el-input v-model="currentCustomer.customerName"></el-input>
      </el-form-item>
      <el-form-item label="Email">
        <el-input v-model="currentCustomer.email"></el-input>
      </el-form-item>
      <el-form-item label="Phone">
        <el-input v-model="currentCustomer.phone"></el-input>
      </el-form-item>
      <el-form-item label="Address">
        <el-input v-model="currentCustomer.address"></el-input>
      </el-form-item>
      <el-form-item label="Balance">
        <el-input v-model="currentCustomer.balance"></el-input>
      </el-form-item>
      <el-form-item label="Password">
        <el-input v-model="currentCustomer.password" disabled></el-input>
      </el-form-item>
      <el-form-item label="CreditLevel">
        <el-input v-model="currentCustomer.creditLevel"></el-input>
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
      title="Add New Customer"
      width="500"
      destroy-on-close
      center
  >
    <el-form :model="newCustomer">
      <el-form-item label="CustomerID">
        <el-input v-model="newCustomer.customerID" placeholder="Enter CustomerID" disabled></el-input>
      </el-form-item>
      <el-form-item label="CustomerName">
        <el-input v-model="newCustomer.customerName" placeholder="Enter CustomerName"></el-input>
      </el-form-item>
      <el-form-item label="Email">
        <el-input v-model="newCustomer.email" placeholder="Enter Email"></el-input>
      </el-form-item>
      <el-form-item label="Phone">
        <el-input v-model="newCustomer.phone" placeholder="Enter Phone"></el-input>
      </el-form-item>
      <el-form-item label="Address">
        <el-input v-model="newCustomer.address" placeholder="Enter Address"></el-input>
      </el-form-item>
      <el-form-item label="Balance">
        <el-input v-model="newCustomer.balance" placeholder="Enter Balance"></el-input>
      </el-form-item>
      <el-form-item label="Password">
        <el-input v-model="newCustomer.password" placeholder="Enter Password"></el-input>
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

<script lang="ts" setup>
import { ElButton, ElInput, ElTable, ElTableColumn } from "element-plus";
import {onBeforeMount, onMounted, ref} from "vue";
import {
  getCustomerById,
  register,
  getAllCustomer,
  login,
  editBalance,
  editCustomerInfo,
  deleteCustomer
} from '@/api/Customer';

// 当前搜索框和对话框的控制
const searchCustomerID = ref('');
const searchCustomerName = ref('');
const editDialogVisible = ref(false);
const insertDialogVisible = ref(false);

export interface Customer {
  customerID:number;
  customerName: string;
  email: string;
  phone: string;
  address: string;
  balance: number;
  password: string;
  creditLevel: number;
}
// 当前正在编辑的书籍
const currentCustomer = ref<Customer>({
  customerID: 0,
  customerName: '',
  email: '',
  phone: '',
  address: '',
  balance: 0,
  password: '',
  creditLevel: 0,
});

// 新书籍的数据对象
const newCustomer = ref<Customer>({
  customerID: 0,
  customerName: '',
  email: '',
  phone: '',
  address: '',
  balance: 0,
  password: '',
  creditLevel: 0,
});

const filteredCustomersData = ref([]);

// 获取所有书籍
const fetchCustomers = async () => {
  try {
    const response = await getAllCustomer();
    // console.info(response.data)
    // 假设 response.data.data 是一个数组类型，但不一定是 Customer[] 类型
    if (Array.isArray(response.data.data)) {
      filteredCustomersData.value = response.data.data;  // 使用类型断言将其视为 Customer[] 类型
      // console.info('Filtered Customers data:', filteredCustomersData.value);

    } else {
      console.error('返回的数据格式错误，应该是一个数组');
      filteredCustomersData.value = [];  // 如果数据格式不正确，赋予空数组
    }
  } catch (error) {
    console.error('获取书籍失败', error);
  }
};

// ID搜索功能
const handleSearchCustomer = () => {
  // console.info(searchCustomerID.value)
  if (searchCustomerID.value) {
    // 仅根据输入的 CustomerID 过滤当前数据
    filteredCustomersData.value = filteredCustomersData.value.filter(Customer =>
        Customer.CustomerID.toString().includes(searchCustomerID.value)
    );
  } else {
    // 如果没有输入搜索ID，则重新加载所有书籍数据
    fetchCustomers();
  }
};
// 名字搜索功能
const handleSearchCustomerByName = () => {
  // console.info(searchCustomerID.value)
  if (searchCustomerName.value) {
    // 仅根据输入的 CustomerID 过滤当前数据
    filteredCustomersData.value = filteredCustomersData.value.filter(Customer =>
        Customer.title.toString().includes(searchCustomerName.value)
    );
  } else {
    // 如果没有输入搜索ID，则重新加载所有书籍数据
    fetchCustomers();
  }
};

// 行样式
const tableRowClassName = ({ row }: { row: Customer }) => {
  if (row.balance < 10) {
    return 'warning-row';
  }
  return '';
};

// 点击编辑按钮时，设置当前编辑的书籍
const handleEditClick = (row: Customer) => {
  currentCustomer.value = { ...row }; // 复制当前行的数据到编辑对象
  editDialogVisible.value = true;
};

// 保存编辑后的书籍
const handleSaveEdit = async () => {
  try {
    await editCustomerInfo(currentCustomer.value.customerID, currentCustomer.value);
    await fetchCustomers(); // 更新书籍数据
    editDialogVisible.value = false; // 关闭编辑对话框
  } catch (error) {
    console.error('更新书籍失败', error);
  }
};

// 插入新书籍
const handleInsertCustomer = async () => {
  try {
    await register(newCustomer.value);
    await fetchCustomers(); // 获取最新的书籍数据
    insertDialogVisible.value = false;
    newCustomer.value = {
      customerID: 0,
      customerName: '',
      email: '',
      phone: '',
      address: '',
      balance: 0,
      password: '',
      creditLevel: 0,
    };
  } catch (error) {
    console.error('添加书籍失败', error);
  }
};

// 删除书籍
const handleDeleteClick = async (CustomerID: number) => {
  try {
    // console.log(CustomerID)
    await deleteCustomer(CustomerID);
    await fetchCustomers(); // 删除后重新获取书籍数据
  } catch (error) {
    console.error('删除顾客失败', error);
    alert("删除顾客失败！可能有别的项目依赖于该顾客");
  }
};

// 删除书籍
const handleGetCustomerByIDClick = async (CustomerID: number) => {
  try {
    // console.log(CustomerID)
    await getCustomerById(CustomerID);
    await fetchCustomers(); // 删除后重新获取书籍数据
  } catch (error) {
    console.error('搜索客户失败', error);
    alert("未查询到该客户！");
  }
};
// 初始化加载书籍
onBeforeMount(() => {
  fetchCustomers();
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
