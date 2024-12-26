package org.example.db_work_back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.db_work_back.entity.OrderDetail;
import org.example.db_work_back.entity.PurchaseOrder;
import org.example.db_work_back.entity.PurchaseDetail;
import org.example.db_work_back.service.PurchaseOrderService;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchaseOrders")
@Api(tags = "采购单相关接口")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping("/getAllPurchaseOrders")
    public Result<List<PurchaseOrder>> getAllPurchaseOrders() {
        return Result.success(purchaseOrderService.getAllPurchaseOrders());
    }

    @PostMapping("/create")
    public Result createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        return Result.success(purchaseOrderService.createPurchaseOrder(purchaseOrder));
    }

    @GetMapping("/{id}")
    public Result<PurchaseOrder> getPurchaseOrderById(@PathVariable Integer id) {
        return Result.success(purchaseOrderService.getPurchaseOrderById(id));
    }

    @GetMapping("/details/{orderid}")
    public Result<List<PurchaseDetail>> getOrderDetailsByOrderId(@PathVariable Integer orderid) {
        return Result.success(purchaseOrderService.getOrderDetailsByOrderId(orderid));
    }

    @PutMapping("/update/{id}")
    public Result updatePurchaseOrder(@PathVariable Integer id,@RequestBody PurchaseOrder purchaseOrder) {
        return Result.success(purchaseOrderService.updatePurchaseOrder(purchaseOrder));
    }

    @DeleteMapping("/delete/{id}")
    public Result deletePurchaseOrder(@PathVariable Integer id) {
        return Result.success(purchaseOrderService.deletePurchaseOrder(id));
    }
}
