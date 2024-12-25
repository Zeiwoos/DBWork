import { request } from '@/request';

interface SupplierData {
    code: number;
    msg: string;
    data: any; // 接口返回的具体数据
}

interface Supplier {
    // supplierID:number;

    supplierName: string;
    contactName: string;
    phone: string;
    email: string;
    address: string;
}

// 获取所有书籍
export function getAllSupplier() {
    return request<SupplierData>({
        url: '/api/suppliers/getAllSuppliers',
        method: 'get',
    });
}

// 添加书籍
export function addSupplier(supplier: Supplier) {
    return request<SupplierData>({
        url: '/api/suppliers/addSupplier',
        method: 'post',
        data: supplier,
    });
}

// 根据书籍 ID 获取书籍详情
export function getSupplierByID(id: number) {
    return request<SupplierData>({
        url: `/api/suppliers/${id}`, // 修正 URL 占位符
        method: 'get',
    });
}

// 删除书籍
export function deleteSupplier(id: number) {
    return request<SupplierData>({
        url: `/api/suppliers/delete/${id}}`,
        method: 'delete',
    });
}
