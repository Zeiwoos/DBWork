package org.example.db_work_back.controller;

import io.swagger.annotations.Api;
import org.example.db_work_back.entity.Supplier;
import org.example.db_work_back.service.SupplierService;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Api(tags = "供应商相关接口")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/getAllSuppliers")
    public Result<List<Supplier>> getAllSuppliers() {
        return Result.success(supplierService.getAllSuppliers());
    }

    @PostMapping("/addSupplier")
    public Result addSupplier(@RequestBody Supplier supplier) {
        return Result.success(supplierService.addSupplier(supplier));
    }

    @GetMapping("/{id}")
    public Result<Supplier> getSupplierById(@PathVariable Integer id) {
        return Result.success(supplierService.getSupplierById(id));
    }

    @PutMapping("/update/{id}")
    public Result updateSupplier(@RequestBody Supplier supplier) {
        return Result.success(supplierService.updateSupplier(supplier));
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteSupplier(@PathVariable Integer id) {
        return Result.success(supplierService.deleteSupplier(id));
    }
}
