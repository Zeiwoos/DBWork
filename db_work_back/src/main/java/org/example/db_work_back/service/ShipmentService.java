//package org.example.db_work_back.service;
//
//import org.example.db_work_back.dao.ShipmentDAO;
//import org.example.db_work_back.entity.Shipment;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ShipmentService {
//
//    @Autowired
//    private ShipmentDAO shipmentDAO;
//
//    // 获取所有发货记录
//    public List<Shipment> getAllShipments() {
//        return shipmentDAO.selectAllShipments();
//    }
//
//    // 根据ID获取发货记录
//    public Shipment getShipmentById(Integer id) {
//        return shipmentDAO.selectShipmentById(id);
//    }
//
//    // 创建新的发货记录
//    public Shipment createShipment(Shipment shipment) {
//        shipmentDAO.insertShipment(shipment);
//        return shipment;
//    }
//
//    // 更新发货记录
//    public Shipment updateShipment(Integer id, Shipment shipment) {
//        return shipmentDAO.updateShipment(id, shipment);
//    }
//
//    // 删除发货记录
//    public void deleteShipment(Integer id) {
//        shipmentDAO.deleteShipment(id);
//    }
//}
