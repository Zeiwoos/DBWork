import { request } from '@/request';

interface SeriesData {
    code: number;
    msg: string;
    data: any; // 接口返回的具体数据
}

interface Series {
    // SeriesID:number;

    seriesName: string;
    description: string;
}

// 获取所有书籍
export function getAllseries() {
    return request<SeriesData>({
        url: '/api/series/getAllseries',
        method: 'get',
    });
}

// 添加书籍
export function addSeries(series: Series) {
    return request<SeriesData>({
        url: '/api/series/add',
        method: 'post',
        data: series,
    });
}

// 根据书籍 ID 获取书籍详情
export function getSeriesByID(id: number) {
    return request<SeriesData>({
        url: `/api/series/${id}`, // 修正 URL 占位符
        method: 'get',
    });
}

// 删除书籍
export function deleteSeries(id: number) {
    return request<SeriesData>({
        url: `/api/series/delete/${id}`,
        method: 'delete',
    });
}
