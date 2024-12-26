<template>
  <el-input
      v-model="searchBookID"
      placeholder="请输入BookID进行查询"
      clearable
      style="margin-bottom: 20px; width: 300px;"
      @input="handleSearchBook"
  />
  <el-table
      :data="filteredBooksData"
      style="width: 100%"
      :row-class-name="tableRowClassName"
  >
    <el-table-column prop="BookID" label="BookID" width="80" />
    <el-table-column prop="Title" label="Title" width="80" />
    <el-table-column prop="Author" label="Author" width="80" />
    <el-table-column prop="Publisher" label="Publisher" width="100" />
    <el-table-column prop="Keywords" label="Keywords" width="120" />
    <el-table-column prop="Description" label="Description" width="120"/>
    <el-table-column prop="Stock" label="Stock" width="60"/>
    <el-table-column prop="StorageLocation" label="StorageLocation" width="140"/>
    <el-table-column prop="SeriesID" label="SeriesID" width="80"/>
    <el-table-column fixed="right" label="Operations" min-width="120">
      <template #default="{ row }">
        <el-button link type="primary" size="small" plain @click="handleEditClick(row)">Edit</el-button>
        <el-button link type="danger" size="small" plain @click="handleDeleteClick(row.BookID)">Delete</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-button type="primary" @click="insertDialogVisible = true">添加书籍</el-button>

  <!-- Edit Dialog -->
  <el-dialog
      v-model="editDialogVisible"
      title="Edit Book"
      width="500"
      destroy-on-close
      center
  >
    <el-form>
      <el-form-item label="BookID">
        <el-input v-model="currentBook.BookID" disabled></el-input>
      </el-form-item>
      <el-form-item label="Title">
        <el-input v-model="currentBook.Title"></el-input>
      </el-form-item>
      <el-form-item label="Author">
        <el-input v-model="currentBook.Author"></el-input>
      </el-form-item>
      <el-form-item label="Publisher">
        <el-input v-model="currentBook.Publisher"></el-input>
      </el-form-item>
      <el-form-item label="Keywords">
        <el-input v-model="currentBook.Keywords"></el-input>
      </el-form-item>
      <el-form-item label="Description">
        <el-input v-model="currentBook.Description"></el-input>
      </el-form-item>
      <el-form-item label="Stock">
        <el-input v-model="currentBook.Stock"></el-input>
      </el-form-item>
      <el-form-item label="StorageLocation">
        <el-input v-model="currentBook.StorageLocation"></el-input>
      </el-form-item>
      <el-form-item label="SeriesID">
        <el-input v-model="currentBook.SeriesID"></el-input>
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
      title="Add New Book"
      width="500"
      destroy-on-close
      center
  >
    <el-form :model="newBook">
      <el-form-item label="BookID">
        <el-input v-model="newBook.BookID" placeholder="Enter BookID"></el-input>
      </el-form-item>
      <el-form-item label="Title">
        <el-input v-model="newBook.Title" placeholder="Enter Title"></el-input>
      </el-form-item>
      <el-form-item label="Author">
        <el-input v-model="newBook.Author" placeholder="Enter Author"></el-input>
      </el-form-item>
      <el-form-item label="Publisher">
        <el-input v-model="newBook.Publisher" placeholder="Enter Publisher"></el-input>
      </el-form-item>
      <el-form-item label="Keywords">
        <el-input v-model="newBook.Keywords" placeholder="Enter Keywords"></el-input>
      </el-form-item>
      <el-form-item label="Description">
        <el-input v-model="newBook.Description" placeholder="Enter Description"></el-input>
      </el-form-item>
      <el-form-item label="Stock">
        <el-input v-model="newBook.Stock" placeholder="Enter Stock"></el-input>
      </el-form-item>
      <el-form-item label="StorageLocation">
        <el-input v-model="newBook.StorageLocation" placeholder="Enter Storage Location"></el-input>
      </el-form-item>
      <el-form-item label="SeriesID">
        <el-input v-model="newBook.SeriesID" placeholder="Enter SeriesID"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="insertDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="handleInsertBook">Confirm</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ElButton, ElInput, ElTable, ElTableColumn } from "element-plus";
import {onMounted, ref} from "vue";
import { getAllBook,addBook,getBookByID,deleteBook,updateBook } from '@/api/Book'
// 当前搜索框和对话框的控制
const searchBookID = ref('');
const editDialogVisible = ref(false);
const insertDialogVisible = ref(false);

// 当前正在编辑的书籍
const currentBook = ref<Book>({
  BookID: 0,
  Title: '',
  Author: '',
  Publisher: '',
  Price: 0,
  Keywords: '',
  Description: '',
  Stock: 0,
  StorageLocation: '',
  SeriesID: 0,
});

// 新书籍的数据对象
const newBook = ref<Book>({
  BookID: 0,
  Title: '',
  Author: '',
  Publisher: '',
  Price: 0,
  Keywords: '',
  Description: '',
  Stock: 0,
  StorageLocation: '',
  SeriesID: 0,
});

// 书籍数据接口
interface Book {
  BookID: number;
  Title: string;
  Author: string;
  Publisher: string;
  Price: number;
  Keywords: string;
  Description: string;
  Stock: number;
  StorageLocation: string;
  SeriesID: number;
}

const filteredBooksData = ref<Book[]>([]);

// 获取所有书籍
const fetchBooks = async () => {
  try {
    const response = await getAllBook();
    filteredBooksData.value = response.data; // 假设 API 返回的数据结构为 { data: Book[] }
  } catch (error) {
    console.error('获取书籍失败', error);
  }
};

// 搜索功能
const handleSearchBook = () => {
  fetchBooks(); // 重新获取所有书籍数据
  if (searchBookID.value) {
    filteredBooksData.value = filteredBooksData.value.filter(book =>
        book.BookID.toString().includes(searchBookID.value)
    );
  }
};

// 行样式
const tableRowClassName = ({ row }: { row: Book }) => {
  if (row.Stock < 10) {
    return 'warning-row';
  }
  return '';
};

// 点击编辑按钮时，设置当前编辑的书籍
const handleEditClick = (row: Book) => {
  currentBook.value = { ...row }; // 复制当前行的数据到编辑对象
  editDialogVisible.value = true;
};

// 保存编辑后的书籍
const handleSaveEdit = async () => {
  try {
    await updateBook(currentBook.value.BookID, currentBook.value);
    await fetchBooks(); // 更新书籍数据
    editDialogVisible.value = false; // 关闭编辑对话框
  } catch (error) {
    console.error('更新书籍失败', error);
  }
};

// 插入新书籍
const handleInsertBook = async () => {
  try {
    await addBook(newBook.value);
    await fetchBooks(); // 获取最新的书籍数据
    insertDialogVisible.value = false;
    newBook.value = {
      BookID: 0,
      Title: '',
      Author: '',
      Publisher: '',
      Price: 0,
      Keywords: '',
      Description: '',
      Stock: 0,
      StorageLocation: '',
      SeriesID: 0,
    };
  } catch (error) {
    console.error('添加书籍失败', error);
  }
};

// 删除书籍
const handleDeleteClick = async (bookID: number) => {
  try {
    await deleteBook(bookID);
    await fetchBooks(); // 删除后重新获取书籍数据
  } catch (error) {
    console.error('删除书籍失败', error);
  }
};

// 初始化加载书籍
onMounted(() => {
  fetchBooks();
});
</script>

<style scoped>
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
</style>
