//package org.example.db_work_back.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.example.db_work_back.entity.BrowsingLog;
//import org.example.db_work_back.service.BrowsingLogService;
//import org.example.db_work_back.result.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/browsingLogs")
//@Api(tags = "浏览日志相关接口")
//public class BrowsingLogController {
//
//    @Autowired
//    private BrowsingLogService browsingLogService;
//
//    @GetMapping("/getAllBrowsingLogs")
//    public Result<List<BrowsingLog>> getAllBrowsingLogs() {
//        return Result.success(browsingLogService.getAllBrowsingLogs());
//    }
//
//    @PostMapping("/create")
//    public Result createBrowsingLog(@RequestBody BrowsingLog browsingLog) {
//        return Result.success(browsingLogService.createBrowsingLog(browsingLog));
//    }
//
//    @GetMapping("/{id}")
//    public Result<BrowsingLog> getBrowsingLogById(@PathVariable Integer id) {
//        return Result.success(browsingLogService.getBrowsingLogById(id));
//    }
//
//    @PutMapping("/update/{id}")
//    public Result updateBrowsingLog(@PathVariable Integer id, @RequestBody BrowsingLog browsingLog) {
//        return Result.success(browsingLogService.updateBrowsingLog(id, browsingLog));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public Result deleteBrowsingLog(@PathVariable Integer id) {
//        return Result.success(browsingLogService.deleteBrowsingLog(id));
//    }
//}
