package org.example.db_work_back.controller;

import io.swagger.annotations.Api;
import org.example.db_work_back.entity.Series;
import org.example.db_work_back.service.SeriesService;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
@Api(tags = "图书相关接口")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @GetMapping("/getAllseries")
    public Result<List<Series>> getAllSeries() {
        return Result.success(seriesService.getAllSeries());
    }

    @PostMapping("/add")
    public Result addSeries(@RequestBody Series series) {
        return Result.success(seriesService.addSeries(series));
    }

    @GetMapping("/{id}")
    public Result<Series> getSeriesById(@PathVariable Integer id) {
        return Result.success(seriesService.getSeriesById(id));
    }

    @PutMapping("/update/{id}")
    public Result updateSeries(@PathVariable Integer id, @RequestBody Series series) {
        return seriesService.updateSeries(id, series);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteSeries(@PathVariable Integer id) {
        return seriesService.deleteSeries(id);
    }
}
