import { request} from '@/request';

interface OrderData {
    code: number;
    msg: string;
    data: any;
    // 其他属性
}
interface Order {
    // orderID:number;
    customerId: number;
    shippingAddress: string;
}
interface updateOrderDTO {
    orderId:number;
    customerId: number;
    orderDate:string;
    totalAmount:number;
    shippingAddress: string;
    status: string;
}
interface orderDetails {
    quantity: number;
    bookId: number;
}
interface OrderRequestDTO {
    order:Order;
    OrderDetails: Array<orderDetails>;
}

export function getAllOrders(){
    return request<OrderData>({
        url: '/api/orders/getAllOrders',
        method: "get"
    })
}
export function getDetailsByOrderID(orderid: number){
    return request<OrderData>({
        url: `/api/orders/details/${orderid}`,
        method: "get"
    })
}
export function createWithDetails(orderRequestDTO:OrderRequestDTO){
    return request<OrderData>(
        {
            url:'/api/orders/createWithDetails',
            method: "post",
            data: orderRequestDTO
        }
    )
}
export function editStatus(id: number,status:string){
    return request<OrderData>(
        {
            url:`/api/orders/updateStatus/${id}`,
            method: "put",
            data: {
                status
            }
        }
    )
}
export function editOrder(id: number,order:updateOrderDTO){
    return request<OrderData>(
        {
            url:`/api/orders/update/${id}`,
            method: "put",
            data: order
        }
    )
}
export function deleteOrder(id:number){
    return request<OrderData>(
        {
            url:`/api/orders/delete/${id}`,
            method: "delete"
        }
    )
}
export function deleteInvalidOrder(){
    return request<OrderData>(
        {
            url:`/api/orders/deleteInvalid`,
            method: "delete"
        }
    )
}

export function getOrderByCustomerID(customerid:number){
    return request<OrderData>(
        {
            url:`/api/orders/customer/${customerid}`,
            method: "get",
        }
    )
}

export function getOrderByID(id:number){
    return request<OrderData>(
        {
            url:`/api/orders/${id}`,
            method: "get",
        }
    )
}
// export function getPageList(pageQueryDTO:PageQueryDTO){
//     return request<any>(
//         {
//             url:'/api/user/page',
//             method: "post",
//             data: pageQueryDTO,
//         }
//     )
// }
export function DeleteOrder(orderid: number) {
    return request<any>({
        url: `api/orders/delete/${orderid}`,
        method: "DELETE", // HTTP 方法应该是大写
    });
}

