import { request } from '@/request';

interface PurchaseOrderData {
    code: number;
    msg: string;
    data: any; // 接口返回的具体数据
}

interface PurchaseOrder {
    // PurchaseOrderID:number;

    supplierId: number;
    //orderDate
    //Status
}

export function getAllPurchaseOrder() {
    return request<PurchaseOrderData>({
        url: '/api/purchaseOrders/getAllPurchaseOrders',
        method: 'get',
    });
}

export function getDetailsByPurchaseID(orderid:number) {
    return request<PurchaseOrderData>({
        url: `/api/purchaseOrders/details/${orderid}`,
        method: 'get',
    });
}
export function updatePuchaseOrder(id:number,purchaseOrder:PurchaseOrder) {
    return request<PurchaseOrderData>({
        url: `/api/purchaseOrders/update/${id}`,
        method: 'put',
        data:  purchaseOrder
    });
}


export function addPurchaseOrder(purchaseOrder: PurchaseOrder) {
    return request<PurchaseOrderData>({
        url: '/api/purchaseOrders/create',
        method: 'post',
        data: purchaseOrder,
    });
}

export function getPurchaseOrder(id: number) {
    return request<PurchaseOrderData>({
        url: `/api/purchaseOrders/${id}`,
        method: 'get',
    });
}

export function deletePurchaseOrder(id: number) {
    return request<PurchaseOrderData>({
        url: `/api/purchaseOrders/delete/${id}`,
        method: 'delete',
    });
}
