//package org.example.db_work_back.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.example.db_work_back.entity.Series;
//import org.example.db_work_back.service.SeriesService;
//import org.example.db_work_back.result.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/series")
//@Api(tags = "丛书相关接口")
//public class SeriesController {
//
//    @Autowired
//    private SeriesService seriesService;
//
//    @GetMapping("/getAllSeries")
//    public Result<List<Series>> getAllSeries() {
//        return Result.success(seriesService.getAllSeries());
//    }
//
//    @PostMapping("/create")
//    public Result createSeries(@RequestBody Series series) {
//        return Result.success(seriesService.createSeries(series));
//    }
//
//    @GetMapping("/{id}")
//    public Result<Series> getSeriesById(@PathVariable Integer id) {
//        return Result.success(seriesService.getSeriesById(id));
//    }
//
//    @PutMapping("/update/{id}")
//    public Result updateSeries(@PathVariable Integer id, @RequestBody Series series) {
//        return Result.success(seriesService.updateSeries(id, series));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public Result deleteSeries(@PathVariable Integer id) {
//        return Result.success(seriesService.deleteSeries(id));
//    }
//}
