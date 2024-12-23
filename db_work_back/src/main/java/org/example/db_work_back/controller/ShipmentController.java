//package org.example.db_work_back.controller;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.example.db_work_back.entity.Shipment;
//import org.example.db_work_back.service.ShipmentService;
//import org.example.db_work_back.result.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/shipments")
//@Api(tags = "发货记录相关接口")
//public class ShipmentController {
//
//    @Autowired
//    private ShipmentService shipmentService;
//
//    @GetMapping("/getAllShipments")
//    public Result<List<Shipment>> getAllShipments() {
//        return Result.success(shipmentService.getAllShipments());
//    }
//
//    @PostMapping("/create")
//    public Result createShipment(@RequestBody Shipment shipment) {
//        return Result.success(shipmentService.createShipment(shipment));
//    }
//
//    @GetMapping("/{id}")
//    public Result<Shipment> getShipmentById(@PathVariable Integer id) {
//        return Result.success(shipmentService.getShipmentById(id));
//    }
//
//    @PutMapping("/update/{id}")
//    public Result updateShipment(@PathVariable Integer id, @RequestBody Shipment shipment) {
//        return Result.success(shipmentService.updateShipment(id, shipment));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public Result deleteShipment(@PathVariable Integer id) {
//        return Result.success(shipmentService.deleteShipment(id));
//    }
//}
