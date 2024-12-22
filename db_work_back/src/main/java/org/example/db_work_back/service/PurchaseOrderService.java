package org.example.db_work_back.service;

import org.example.db_work_back.dao.PurchaseOrderDAO;
import org.example.db_work_back.entity.PurchaseOrder;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderDAO purchaseOrderDAO;

    // 获取所有采购单
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderDAO.selectAllPurchaseOrders();
    }

    // 根据ID获取采购单
    public PurchaseOrder getPurchaseOrderById(Integer id) {
        return purchaseOrderDAO.selectPurchaseOrderById(id);
    }

    // 创建新的采购单
    public Result createPurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderDAO.insertPurchaseOrder(purchaseOrder);
        return Result.success("采购单已添加");
    }

    // 更新采购单信息
    public Result updatePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderDAO.updatePurchaseOrder(purchaseOrder);
        return Result.success(purchaseOrder);
    }

    // 删除采购单
    public Result deletePurchaseOrder(Integer id) {
        purchaseOrderDAO.deletePurchaseOrder(id);
        return Result.success("采购单已删除");
    }
}
