import { request } from '@/request';

interface MissingBookData {
    code: number;
    msg: string;
    data: any; // 接口返回的具体数据
}

interface MissingBook {
    // MissingID:number;

    BookID: number;
    CustomerID: number;
    phone: string;
    email: string;
    address: string;
}

export function getAllMissingBook() {
    return request<MissingBookData>({
        url: '/api/missingBooks/getAllMissingBooks',
        method: 'get',
    });
}

export function addMissingBook(missingBook: MissingBook) {
    return request<MissingBookData>({
        url: '/api/missingBooks/create',
        method: 'post',
        data: missingBook,
    });
}

export function getMissingBook(id: number) {
    return request<MissingBookData>({
        url: `/api/missingBooks/${id}`,
        method: 'get',
    });
}
export function getMissingBookByCustomerID(customerid: number) {
    return request<MissingBookData>({
        url: `/api/missingBooks/customer/${customerid}`,
        method: 'get',
    });
}
export function editStatus(customerid: number,status:string){
    return request<MissingBookData>(
        {
            url:`/api/missingBooks/updateStatus/${customerid}`,
            method: "put",
            data: {
                status
            }
        }
    )
}

export function deleteMissingBook(id: number) {
    return request<MissingBookData>({
        url: `/api/missingBooks/delete/${id}`,
        method: 'delete',
    });
}
