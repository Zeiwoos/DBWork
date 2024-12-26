package org.example.db_work_back.dao;

import org.example.db_work_back.entity.Order;
import org.example.db_work_back.entity.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PurchaseOrderDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 插入采购单
    public void insertPurchaseOrder(PurchaseOrder purchaseOrder) {
        String sql = "INSERT INTO purchaseOrders (SupplierID, OrderDate, Status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, purchaseOrder.getSupplierId(), purchaseOrder.getOrderDate(), purchaseOrder.getStatus());
    }

    // 更新采购单
    public void updatePurchaseOrder(PurchaseOrder purchaseOrder) {
        String sql = "UPDATE purchaseOrders SET SupplierID = ?, OrderDate = ?, Status = ? WHERE PurchaseID = ?";
        jdbcTemplate.update(sql, purchaseOrder.getSupplierId(), purchaseOrder.getOrderDate(),
                purchaseOrder.getStatus(), purchaseOrder.getPurchaseId());
    }

    // 根据ID查询采购单
    public PurchaseOrder selectPurchaseOrderById(Integer id) {
        String sql = "SELECT * FROM purchaseOrders WHERE PurchaseID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, purchaseOrderRowMapper());
    }

    // 获取所有采购单
    public List<PurchaseOrder> selectAllPurchaseOrders() {
        String sql = "SELECT * FROM purchaseOrders";
        return jdbcTemplate.query(sql, purchaseOrderRowMapper());
    }

    // 根据供应商ID获取采购单
    public List<PurchaseOrder> selectPurchaseOrdersBySupplierId(Integer supplierId) {
        String sql = "SELECT * FROM purchaseOrders WHERE SupplierID = ?";
        return jdbcTemplate.query(sql, new Object[]{supplierId}, purchaseOrderRowMapper());
    }

    // 删除采购单
    public void deletePurchaseOrder(Integer id) {
        String sql = "DELETE FROM purchaseOrders WHERE PurchaseID = ?";
        jdbcTemplate.update(sql, id);
    }

    // RowMapper方法，简化映射逻辑
    private RowMapper<PurchaseOrder> purchaseOrderRowMapper() {
        return (rs, rowNum) -> {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setPurchaseId(rs.getInt("PurchaseID"));
            purchaseOrder.setSupplierId(rs.getInt("SupplierID"));
            purchaseOrder.setOrderDate(rs.getDate("OrderDate"));
            purchaseOrder.setStatus(rs.getString("Status"));
            return purchaseOrder;
        };
    }
}
