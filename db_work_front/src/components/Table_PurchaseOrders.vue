<template>
  <el-input
      v-model="searchPurchaseOrderID"
      placeholder="请输入PurchaseOrderID进行查询"
      clearable
      style="margin-bottom: 20px; width: 300px;"
      @input="handleSearchPurchaseOrder"
  />
<!--  <el-input-->
<!--      v-model="searchPurchaseOrderTitle"-->
<!--      placeholder="请输入PurchaseOrderTitle进行查询"-->
<!--      clearable-->
<!--      style="margin-bottom: 20px; width: 300px;"-->
<!--      @input="handleSearchPurchaseOrderByName"-->
<!--  />-->
  <el-table
      :data="filteredPurchaseOrdersData"
      style="width: 100%"
      :row-class-name="tableRowClassName"
  >
    <el-table-column prop="purchaseId" label="PurchaseOrderID" width="140" />
    <el-table-column prop="supplierId" label="SupplierID" width="100" />
    <el-table-column prop="orderDate" label="OrderDate" width="100" />
    <el-table-column prop="status" label="Status" width="100" />


    <el-table-column fixed="right" label="Operations" min-width="120">
      <template #default="{ row }">
        <el-button link type="primary" size="small" plain @click="handleEditClick(row)">Edit</el-button>
        <el-button link type="danger" size="small" plain @click="handleDeleteClick(row.purchaseId)">Delete</el-button>
      </template>
    </el-table-column>
  </el-table>
<!--  <el-button type="primary" @click="insertDialogVisible = true">添加采购单</el-button>-->

  <!-- Edit Dialog -->
  <el-dialog
      v-model="editDialogVisible"
      title="Edit PurchaseOrder"
      width="500"
      destroy-on-close
      center
  >
    <el-form>
      <el-form-item label="PurchaseOrderID">
        <el-input v-model="currentPurchaseOrder.purchaseId" disabled></el-input>
      </el-form-item>
      <el-form-item label="SupplierID">
        <el-input v-model="currentPurchaseOrder.supplierId"></el-input>
      </el-form-item>
      <el-form-item label="OrderDate">
        <el-input v-model="currentPurchaseOrder.orderDate"></el-input>
      </el-form-item>
      <el-form-item label="Status">
        <el-input v-model="currentPurchaseOrder.status"></el-input>
      </el-form-item>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="editDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="handleSaveEdit">Confirm</el-button>
      </div>
    </template>
  </el-dialog>

<!--  &lt;!&ndash; Insert Dialog &ndash;&gt;-->
<!--  <el-dialog-->
<!--      v-model="insertDialogVisible"-->
<!--      title="Add New PurchaseOrder"-->
<!--      width="500"-->
<!--      destroy-on-close-->
<!--      center-->
<!--  >-->
<!--    <el-form :model="newPurchaseOrder">-->
<!--      <el-form-item label="PurchaseOrderID">-->
<!--        <el-input v-model="newPurchaseOrder.PurchaseOrderID" placeholder="Enter PurchaseOrderID" disabled></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="Title">-->
<!--        <el-input v-model="newPurchaseOrder.title" placeholder="Enter Title"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="Author">-->
<!--        <el-input v-model="newPurchaseOrder.author" placeholder="Enter Author"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="Publisher">-->
<!--        <el-input v-model="newPurchaseOrder.publisher" placeholder="Enter Publisher"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="Keywords">-->
<!--        <el-input v-model="newPurchaseOrder.keywords" placeholder="Enter Keywords"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="Description">-->
<!--        <el-input v-model="newPurchaseOrder.description" placeholder="Enter Description"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="Stock">-->
<!--        <el-input v-model="newPurchaseOrder.stock" placeholder="Enter Stock"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="StorageLocation">-->
<!--        <el-input v-model="newPurchaseOrder.storageLocation" placeholder="Enter Storage Location"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="SeriesID">-->
<!--        <el-input v-model="newPurchaseOrder.seriesID" placeholder="Enter SeriesID"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="SupplierID">-->
<!--        <el-input v-model="newPurchaseOrder.supplierID" placeholder="Enter SeriesID"></el-input>-->
<!--      </el-form-item>-->
<!--    </el-form>-->
<!--    <template #footer>-->
<!--      <div class="dialog-footer">-->
<!--        <el-button @click="insertDialogVisible = false">Cancel</el-button>-->
<!--        <el-button type="primary" @click="handleInsertPurchaseOrder">Confirm</el-button>-->
<!--      </div>-->
<!--    </template>-->
<!--  </el-dialog>-->
</template>

<script lang="ts" setup>
import { ElButton, ElInput, ElTable, ElTableColumn } from "element-plus";
import {onBeforeMount, onMounted, ref} from "vue";
import {
    getAllPurchaseOrder,
    addPurchaseOrder,
    getPurchaseOrder,
    deletePurchaseOrder,
    updatePuchaseOrder
} from '@/api/PurchaseOrder';

// 当前搜索框和对话框的控制
const searchPurchaseOrderID = ref('');
const searchPurchaseOrderTitle = ref('');
const editDialogVisible = ref(false);
const insertDialogVisible = ref(false);

export interface PurchaseOrder {
  purchaseId:number;
  supplierId:number;
  orderDate:string;
  status:string;
}
// 当前正在编辑的采购单
const currentPurchaseOrder = ref<PurchaseOrder>({
  purchaseId:0,
  supplierId:0,
  orderDate:'',
  status:''
});

// 新采购单的数据对象
const newPurchaseOrder = ref<PurchaseOrder>({
  purchaseId:0,
  supplierId:0,
  orderDate:'',
  status:''
});

const filteredPurchaseOrdersData = ref([]);

// 获取所有采购单
const fetchPurchaseOrders = async () => {
  try {
    const response = await getAllPurchaseOrder();
    console.info(response.data)
    // 假设 response.data.data 是一个数组类型，但不一定是 PurchaseOrder[] 类型
    if (Array.isArray(response.data.data)) {
      filteredPurchaseOrdersData.value = response.data.data;  // 使用类型断言将其视为 PurchaseOrder[] 类型
      // console.info('Filtered PurchaseOrders data:', filteredPurchaseOrdersData.value);

    } else {
      console.error('返回的数据格式错误，应该是一个数组');
      filteredPurchaseOrdersData.value = [];  // 如果数据格式不正确，赋予空数组
    }
  } catch (error) {
    console.error('获取采购单失败', error);
  }
};

// ID搜索功能
const handleSearchPurchaseOrder = () => {
  // console.info(searchPurchaseOrderID.value)
  if (searchPurchaseOrderID.value) {
    // 仅根据输入的 PurchaseOrderID 过滤当前数据
    filteredPurchaseOrdersData.value = filteredPurchaseOrdersData.value.filter(PurchaseOrder =>
        PurchaseOrder.PurchaseOrderID.toString().includes(searchPurchaseOrderID.value)
    );
  } else {
    // 如果没有输入搜索ID，则重新加载所有采购单数据
    fetchPurchaseOrders();
  }
};
// 名字搜索功能
const handleSearchPurchaseOrderByName = () => {
  // console.info(searchPurchaseOrderID.value)
  if (searchPurchaseOrderTitle.value) {
    // 仅根据输入的 PurchaseOrderID 过滤当前数据
    filteredPurchaseOrdersData.value = filteredPurchaseOrdersData.value.filter(PurchaseOrder =>
        PurchaseOrder.title.toString().includes(searchPurchaseOrderTitle.value)
    );
  } else {
    // 如果没有输入搜索ID，则重新加载所有采购单数据
    fetchPurchaseOrders();
  }
};

// 行样式
const tableRowClassName = ({ row }: { row: PurchaseOrder }) => {
  return '';
};

// 点击编辑按钮时，设置当前编辑的采购单
const handleEditClick = (row: PurchaseOrder) => {
  currentPurchaseOrder.value = { ...row }; // 复制当前行的数据到编辑对象
  editDialogVisible.value = true;
};

// 保存编辑后的采购单
const handleSaveEdit = async () => {
  try {
    await updatePuchaseOrder(currentPurchaseOrder.value.purchaseId, currentPurchaseOrder.value);
    await fetchPurchaseOrders(); // 更新采购单数据
    editDialogVisible.value = false; // 关闭编辑对话框
  } catch (error) {
    console.error('更新采购单失败', error);
  }
};

// 插入新采购单
const handleInsertPurchaseOrder = async () => {
  try {
    await addPurchaseOrder(newPurchaseOrder.value);
    await fetchPurchaseOrders(); // 获取最新的采购单数据
    insertDialogVisible.value = false;
    newPurchaseOrder.value = {
      purchaseId:0,
      supplierId:0,
      orderDate:'',
      status:''
    };
  } catch (error) {
    console.error('添加采购单失败', error);
  }
};

// 删除采购单
const handleDeleteClick = async (PurchaseOrderID: number) => {
  try {
    // console.log(PurchaseOrderID)
    await deletePurchaseOrder(PurchaseOrderID);
    await fetchPurchaseOrders(); // 删除后重新获取采购单数据
  } catch (error) {
    console.error('删除采购单失败', error);
    alert("删除采购单失败！可能有别的项目于依赖该采购单");
  }
};

// 删除采购单
const handleGetPurchaseOrderByIDClick = async (PurchaseOrderID: number) => {
  try {
    // console.log(PurchaseOrderID)
    await getPurchaseOrder(PurchaseOrderID);
    await fetchPurchaseOrders(); // 删除后重新获取采购单数据
  } catch (error) {
    console.error('搜索采购单失败', error);
    alert("未查询到这本采购单！");
  }
};
// 初始化加载采购单
onBeforeMount(() => {
  fetchPurchaseOrders();
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
