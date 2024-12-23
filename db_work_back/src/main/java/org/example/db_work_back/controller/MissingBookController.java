package org.example.db_work_back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.db_work_back.entity.MissingBook;
import org.example.db_work_back.service.MissingBookService;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missingBooks")
@Api(tags = "缺书记录相关接口")
public class MissingBookController {

    @Autowired
    private MissingBookService missingBookService;

    @GetMapping("/getAllMissingBooks")
    public Result<List<MissingBook>> getAllMissingBooks() {
        return Result.success(missingBookService.getAllMissingBooks());
    }

    @PostMapping("/create")
    public Result createMissingBook(@RequestBody MissingBook missingBook) {
        return Result.success(missingBookService.createMissingBook(missingBook));
    }

    @GetMapping("/{id}")
    public Result<MissingBook> getMissingBookById(@PathVariable Integer id) {
        return Result.success(missingBookService.getMissingBookById(id));
    }

    @GetMapping("/customer/{customerid}")
    public Result<List<MissingBook>> getMissingBooksByCustomerId(@PathVariable Integer customerid) {
        return Result.success(missingBookService.getMissingBooksByCustomerId(customerid));
    }

    @PutMapping("/updateStatus/{id}")
    public Result updateMissingBookStatus(@PathVariable Integer id,@RequestParam String Status) {
        MissingBook missingBook = missingBookService.getMissingBookById(id);
        missingBook.setStatus(Status);
        return Result.success(missingBookService.updateMissingBookStatus(missingBook));
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteMissingBook(@PathVariable Integer id) {
        return Result.success(missingBookService.deleteMissingBook(id));
    }
}
