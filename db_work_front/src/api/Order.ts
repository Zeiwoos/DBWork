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

export function deleteBook(id:number){
    return request<OrderData>(
        {
            url:`/api/orders/delete/${id}`,
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

// export function getPageList(pageQueryDTO:PageQueryDTO){
//     return request<any>(
//         {
//             url:'/api/user/page',
//             method: "post",
//             data: pageQueryDTO,
//         }
//     )
// }
