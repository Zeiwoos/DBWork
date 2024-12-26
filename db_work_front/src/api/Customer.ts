import { request} from '@/request';

interface CustomerData {
    code: number;
    msg: string;
    data: any;
    // 其他属性
}

interface Customer {
    // customerID:number;
    customerName: string;
    email: string;
    phone: string;
    address: string;
    password: string;
}
/*
{
    "customerName": "颜国平",
    "email": "p1pscp_kdw52@qq.com",
    "phone": "055 7504 1835",
    "address": "安徽省 宁门市 四方台区 尉街16号 92单元",
    "password": "UuMYimZ5qbCoPEW"
}
 */
interface CustomerLoginDTO {
    customerID: number;
    password: string;
}
/*{
    "customerID": 1,
    "password": "4VBbRL4xSJbuv_I"
}*/


export function getCustomerById(id: number) {
    return request<CustomerData>({
        url: `/api/customers/${id}`,
        method: 'get',  // GET 请求
    });
}

export function login(data:CustomerLoginDTO){
    return request<CustomerData>({
        url:'/api/customers/login',
        method: "post",
        data: data

    })
}

export function register(customer:Customer){
    return request<CustomerData>(
        {
            url:'/api/customers/register',
            method: "post",
            data: customer
        }
    )
}
export function logout(uid:number){
    return request<CustomerData>(
        {
            url:'/api/customers/logout',
            method: "post",
            data: {uid},
        }
    )
}

export function getCustomerByID(uid:number){
    return request<CustomerData>(
        {
            url:`/api/customers/${uid}`,
            method: "get"
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
export function editBalance(id: number,balance:number){
    return request<CustomerData>(
        {
            url:`/api/customers/updateBalance/${id}`,
            method: "put",
            data: {
                balance
            }
        }
    )
}
