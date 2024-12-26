import { request } from '@/request';

interface BookData {
    code: number;
    msg: string;
    data: any; // 接口返回的具体数据
}

interface Book {
    // BookID:number;

    title: string;
    author: string;
    publisher: string;
    price: number;
    keywords: string;
    description: string;
    stock: number;
    storageLocation: string;
    seriesID: number;
    supplierID:number;
    // img: string; // 图片链接
}

// 获取所有书籍
export function getAllBook() {
    return request<BookData>({
        url: '/api/books/getAllBooks',
        method: 'get',
    });
}

// 添加书籍
export function addBook(book: Book) {
    return request<BookData>({
        url: '/api/books/add',
        method: 'post',
        data: book,
    });
}

// 根据书籍 ID 获取书籍详情
export function getBookByID(id: number) {
    return request<BookData>({
        url: `/api/books/${id}`, // 修正 URL 占位符
        method: 'get',
    });
}

// 删除书籍
export function deleteBook(id: number) {
    return request<BookData>({
        url: `/api/books/delete/${id}`,
        method: 'delete',
    });
}

export function updateBook(id: number,book: Book) {
    return request<BookData>({
        url: `/api/books/update/${id}`,
        method: 'put',
        data: book
    });
}
