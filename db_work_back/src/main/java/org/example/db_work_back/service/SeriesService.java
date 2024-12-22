//package org.example.db_work_back.service;
//
//import org.example.db_work_back.dao.SeriesDAO;
//import org.example.db_work_back.entity.Series;
//import org.example.db_work_back.result.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class SeriesService {
//
//    @Autowired
//    private SeriesDAO seriesDAO;
//
//    // 获取所有丛书
//    public Result<List<Series>> getAllSeries() {
//        List<Series> seriesList = seriesDAO.selectAllSeries();
//        return Result.success(seriesList);
//    }
//
//    // 根据ID获取丛书
//    public Result<Series> getSeriesById(Integer id) {
//        Series series = seriesDAO.selectSeriesById(id);
//        return Result.success(series);
//    }
//
//    // 添加丛书
//    public Result<Series> addSeries(Series series) {
//        seriesDAO.insertSeries(series);
//        return Result.success(series);
//    }
//
//    // 更新丛书
//    public Result<Series> updateSeries(Integer id, Series series) {
//        seriesDAO.updateSeries(id, series);
//        return Result.success(series);
//    }
//
//    // 删除丛书
//    public Result<String> deleteSeries(Integer id) {
//        seriesDAO.deleteSeries(id);
//        return Result.success("丛书删除成功");
//    }
//}
