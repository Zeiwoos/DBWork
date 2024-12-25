<template>
  <!-- 搜索框 -->
  <el-input
      v-model="searchMissingBookID"
      placeholder="请输入缺书ID进行查询"
      clearable
      style="margin-bottom: 20px; width: 300px;"
      @input="handleSearchMissingBook"
  />

  <!-- 表格 -->
  <el-table
      :data="filteredMissingBooksData"
      style="width: 100%"
      :row-class-name="tableRowClassName"
  >
    <el-table-column prop="MissingBookID" label="缺书单ID" width="100" />
    <el-table-column prop="BookID" label="书ID" width="120" />
    <el-table-column prop="CustomerID" label="登记人ID" width="120" />
    <el-table-column prop="MissingQuantity" label="缺书数量" width="150" />
    <el-table-column prop="RegisterDate" label="登记日期" width="120" />
    <el-table-column fixed="right" label="操作" min-width="140">
      <template #default="{ row }">
        <el-button link type="primary" size="small" @click="handleClickMissingBook(row)">Edit</el-button>
        <el-button link type="danger" size="small" @click="handleClickDeleteMissing(row.MissingBookID)">Delete</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!-- 添加缺书对话框 -->
  <el-dialog
      v-model="addMissingBookDialogVisible"
      title="添加缺书单"
      width="500"
      destroy-on-close
      center
  >
    <el-form :model="newMissingBook">
      <el-form-item label="书ID">
        <el-input v-model="newMissingBook.BookID" placeholder="请输入书ID"></el-input>
      </el-form-item>
      <el-form-item label="登记人ID">
        <el-input v-model="newMissingBook.CustomerID" placeholder="请输入登记人ID"></el-input>
      </el-form-item>
      <el-form-item label="缺书数量">
        <el-input v-model="newMissingBook.MissingQuantity" placeholder="请输入缺书数量"></el-input>
      </el-form-item>
      <el-form-item label="登记日期">
        <el-input v-model="newMissingBook.RegisterDate" placeholder="请输入登记日期"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="addMissingBookDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="handleAddMissingBook">Confirm</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 编辑缺书对话框 -->
  <el-dialog
      v-model="editMissingBookDialogVisible"
      title="编辑缺书单"
      width="500"
      destroy-on-close
      center
  >
    <el-form :model="currentMissingBook">
      <el-form-item label="缺书单ID">
        <el-input v-model="currentMissingBook.MissingBookID" disabled></el-input>
      </el-form-item>
      <el-form-item label="书ID">
        <el-input v-model="currentMissingBook.BookID" disabled></el-input>
      </el-form-item>
      <el-form-item label="登记人ID">
        <el-input v-model="currentMissingBook.CustomerID" disabled></el-input>
      </el-form-item>
      <el-form-item label="缺书数量">
        <el-input v-model="currentMissingBook.MissingQuantity"></el-input>
      </el-form-item>
      <el-form-item label="登记日期">
        <el-input v-model="currentMissingBook.RegisterDate" disabled></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="editMissingBookDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="handleEditMissingBook">Confirm</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 添加缺书单按钮 -->
  <el-button type="primary" @click="addMissingBookDialogVisible = true">添加缺书单</el-button>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ElButton, ElInput, ElTable, ElTableColumn, ElDialog, ElForm, ElFormItem } from 'element-plus';

const searchMissingBookID = ref('');
const addMissingBookDialogVisible = ref(false);
const editMissingBookDialogVisible = ref(false);
const currentMissingBook = ref<MissingBook>({
  MissingBookID: 0,
  BookID: 0,
  CustomerID: 0,
  MissingQuantity: 0,
  RegisterDate: '',
});
const newMissingBook = ref<MissingBook>({
  MissingBookID: 0,
  BookID: 0,
  CustomerID: 0,
  MissingQuantity: 0,
  RegisterDate: '',
});

interface MissingBook {
  MissingBookID: number;
  BookID: number;
  CustomerID: number;
  MissingQuantity: number;
  RegisterDate: string;
}

const MissingBooksData: MissingBook[] = [
  {
    MissingBookID: 1,
    BookID: 101,
    CustomerID: 1,
    MissingQuantity: 10,
    RegisterDate: '2024-12-15',
  },
];

const filteredMissingBooksData = ref(MissingBooksData);

// 搜索功能
const handleSearchMissingBook = () => {
  if (searchMissingBookID.value) {
    filteredMissingBooksData.value = MissingBooksData.filter(
        (item) => item.MissingBookID.toString().includes(searchMissingBookID.value)
    );
  } else {
    filteredMissingBooksData.value = MissingBooksData;
  }
};

// 行样式
const tableRowClassName = ({ row }: { row: MissingBook }) => {
  if (row.MissingQuantity > 5) {
    return 'warning-row';
  }
  return '';
};

// 点击编辑按钮
const handleClickMissingBook = (row: MissingBook) => {
  currentMissingBook.value = { ...row }; // 复制当前行的数据到编辑对象
  editMissingBookDialogVisible.value = true;
};

// 保存编辑后的缺书单
const handleEditMissingBook = () => {
  const index = MissingBooksData.findIndex(
      (item) => item.MissingBookID === currentMissingBook.value.MissingBookID
  );
  if (index !== -1) {
    MissingBooksData[index] = { ...currentMissingBook.value }; // 更新缺书单数据
  }
  filteredMissingBooksData.value = [...MissingBooksData]; // 更新表格显示
  editMissingBookDialogVisible.value = false;
};

// 添加缺书单
const handleAddMissingBook = () => {
  const newMissingBookID = MissingBooksData.length + 1;
  const newMissingBookData = { ...newMissingBook.value, MissingBookID: newMissingBookID };
  MissingBooksData.push(newMissingBookData);
  filteredMissingBooksData.value = [...MissingBooksData];
  addMissingBookDialogVisible.value = false;
  newMissingBook.value = {
    MissingBookID: 0,
    BookID: 0,
    CustomerID: 0,
    MissingQuantity: 0,
    RegisterDate: '',
  };
};

// 删除缺书单
const handleClickDeleteMissing = (MissingBookID: number) => {
  const index = MissingBooksData.findIndex(
      (item) => item.MissingBookID === MissingBookID
  );
  if (index !== -1) {
    MissingBooksData.splice(index, 1); // 删除缺书单
  }
  filteredMissingBooksData.value = [...MissingBooksData]; // 更新表格显示
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
