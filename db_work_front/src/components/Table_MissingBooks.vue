<template>
  <el-input
      v-model="searchMissingBookID"
      placeholder="请输入MissingBookID进行查询"
      clearable
      style="margin-bottom: 20px; width: 300px;"
      @input="handleSearchMissingBook"
  />

  <el-table
      :data="filteredMissingBooksData"
      style="width: 100%"
      :row-class-name="tableRowClassName"
  >
    <el-table-column prop="missingId" label="MissingID" width="85" />
    <el-table-column prop="bookId" label="BookID" width="70"/>
    <el-table-column prop="customerId" label="CustomerID" width="100"/>
    <el-table-column prop="quantity" label="Quantity" width="80"/>
    <el-table-column prop="registerDate" label="RegisterDate" width="140"/>
    <el-table-column prop="status" label="Status" width="100"/>

    <el-table-column fixed="right" label="Operations" min-width="120">
      <template #default="{ row }">
        <el-button link type="primary" plain @click="handleEditClick(row)">Edit</el-button>
        <el-button link type="danger" plain @click="handleDeleteClick(row.missingId)">Delete</el-button>
      </template>
    </el-table-column>
  </el-table>
<!--  <el-button type="primary" @click="insertDialogVisible = true">添加客户</el-button>-->

  <!-- Edit Dialog -->
  <el-dialog
      v-model="editDialogVisible"
      title="Edit MissingBook"
      width="500"
      destroy-on-close
      center
  >
    <el-form>
      <el-form-item label="MissingID">
        <el-input v-model="currentMissingBook.missingId" disabled></el-input>
      </el-form-item>
      <el-form-item label="BookID">
        <el-input v-model="currentMissingBook.bookId" disabled></el-input>
      </el-form-item>
      <el-form-item label="CustomerID">
        <el-input v-model="currentMissingBook.customerId" disabled></el-input>
      </el-form-item>
      <el-form-item label="Quantity">
        <el-input v-model="currentMissingBook.quantity"></el-input>
      </el-form-item>
      <el-form-item label="RegisterDate">
        <el-input v-model="currentMissingBook.registerDate" disabled></el-input>
      </el-form-item>
      <el-form-item label="Status">
        <el-input v-model="currentMissingBook.status"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="editDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="handleSaveEdit">Confirm</el-button>
      </div>
    </template>
  </el-dialog>

</template>

<script lang="ts" setup>
import { ElButton, ElInput, ElTable, ElTableColumn } from "element-plus";
import {onBeforeMount, onMounted, ref} from "vue";
import {
  getAllMissingBook,
  addMissingBook,
  getMissingBookByCustomerID,
  editStatus,
  deleteMissingBook,
  editMissing
} from '@/api/MissingBook';

// 当前搜索框和对话框的控制
const searchMissingBookID = ref('');
const searchMissingBookName = ref('');
const editDialogVisible = ref(false);
const insertDialogVisible = ref(false);

export interface MissingBook {
  missingId:number;
  bookId: number;
  quantity: number;
  registerDate: string;
  customerId  : number;
  status: string;
}
// 当前正在编辑的缺书记录
const currentMissingBook = ref<MissingBook>({
  missingId: 0,
  bookId: 0,
  quantity: 0,
  registerDate: '',
  customerId: 0,
  status: '',
});

// 新缺书记录的数据对象
const newMissingBook = ref<MissingBook>({
  missingId: 0,
  bookId: 0,
  quantity: 0,
  registerDate: '',
  customerId: 0,
  status: '',
});

const filteredMissingBooksData = ref([]);

// 获取所有缺书记录
const fetchMissingBooks = async () => {
  try {
    const response = await getAllMissingBook();
    // console.info(response.data)
    // 假设 response.data.data 是一个数组类型，但不一定是 MissingBook[] 类型
    if (Array.isArray(response.data.data)) {
      filteredMissingBooksData.value = response.data.data;  // 使用类型断言将其视为 MissingBook[] 类型
      // console.info('Filtered MissingBooks data:', filteredMissingBooksData.value);

    } else {
      console.error('返回的数据格式错误，应该是一个数组');
      filteredMissingBooksData.value = [];  // 如果数据格式不正确，赋予空数组
    }
  } catch (error) {
    console.error('获取缺书记录失败', error);
  }
};

// ID搜索功能
const handleSearchMissingBook = () => {
  // console.info(searchMissingBookID.value)
  if (searchMissingBookID.value) {
    // 仅根据输入的 MissingBookID 过滤当前数据
    filteredMissingBooksData.value = filteredMissingBooksData.value.filter(MissingBook =>
        MissingBook.missingId.toString().includes(searchMissingBookID.value)
    );
  } else {
    // 如果没有输入搜索ID，则重新加载所有缺书记录数据
    fetchMissingBooks();
  }
};
// 名字搜索功能
// const handleSearchMissingBookByName = () => {
//   // console.info(searchMissingBookID.value)
//   if (searchMissingBookName.value) {
//     // 仅根据输入的 MissingBookID 过滤当前数据
//     filteredMissingBooksData.value = filteredMissingBooksData.value.filter(MissingBook =>
//         MissingBook.title.toString().includes(searchMissingBookName.value)
//     );
//   } else {
//     // 如果没有输入搜索ID，则重新加载所有缺书记录数据
//     fetchMissingBooks();
//   }
// };

// 行样式
const tableRowClassName = ({ row }: { row: MissingBook }) => {
  // if (row.status =="UNSOLVED") {
  //   return 'warning-row';
  // }
  return '';
};

// 点击编辑按钮时，设置当前编辑的缺书记录
const handleEditClick = (row: MissingBook) => {
  currentMissingBook.value = { ...row }; // 复制当前行的数据到编辑对象
  editDialogVisible.value = true;
};

// 保存编辑后的缺书记录
const handleSaveEdit = async () => {
  try {
    console.info(currentMissingBook.value)
    await editMissing(currentMissingBook.value.missingId,currentMissingBook.value);
    await fetchMissingBooks(); // 更新缺书记录数据
    editDialogVisible.value = false; // 关闭编辑对话框
  } catch (error) {
    console.error('更新缺书记录失败', error);
  }
};

// // 插入新缺书记录
// const handleInsertMissingBook = async () => {
//   try {
//     await addMissingBook(newMissingBook.value);
//     await fetchMissingBooks(); // 获取最新的缺书记录数据
//     insertDialogVisible.value = false;
//     newMissingBook.value = {
//       missingId: 0,
//       bookId: 0,
//       quantity: 0,
//       registerDate: '',
//       customerId: 0,
//       status: '',
//     };
//   } catch (error) {
//     console.error('添加缺书记录失败', error);
//   }
// };

// 删除缺书记录
const handleDeleteClick = async (MissingBookID: number) => {
  try {
    // console.log(MissingBookID)
    await deleteMissingBook(MissingBookID);
    await fetchMissingBooks(); // 删除后重新获取缺书记录数据
  } catch (error) {
    console.error('删除缺书记录失败', error);
    alert("删除缺书记录失败！可能有别的项目依赖于该缺书记录");
  }
};

// const handleGetMissingBookByIDClick = async (MissingBookID: number) => {
//   try {
//     // console.log(MissingBookID)
//     await getMissingBook(MissingBookID);
//     await fetchMissingBooks(); // 删除后重新获取缺书记录数据
//   } catch (error) {
//     console.error('搜索缺书记录失败', error);
//     alert("未查询到该缺书记录！");
//   }
// };
// 初始化加载缺书记录
onBeforeMount(() => {
  fetchMissingBooks();
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
