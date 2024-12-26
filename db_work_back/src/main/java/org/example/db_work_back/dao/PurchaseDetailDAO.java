package org.example.db_work_back.dao;

import org.example.db_work_back.entity.PurchaseDetail;
import org.example.db_work_back.entity.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class PurchaseDetailDAO {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    // 插入订单详情
// 插入订单详情
    public void insertDetail(PurchaseDetail detail) {
//        System.out.println(detail.getOrder());
        String sql = "INSERT INTO purchasedetails (PurchaseID, BookID, Quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                detail.getPurchaseOrder(),
                detail.getBook(),
                detail.getQuantity());
//        System.out.println("\n"+detail.getOrder());
    }


    // 更新订单详情
    public void updateDetail(PurchaseDetail detail) {
        String sql = "UPDATE purchasedetails SET BookID = ?, Quantity = ?, PurchaseID = ? WHERE PurchaseDetailID = ?";
        jdbcTemplate.update(sql,
                detail.getBook(),
                detail.getQuantity(),
                detail.getPurchaseOrder(),
                detail.getPurchaseDetailID());
    }

    // 删除订单详情
    public void deleteDetail(Integer orderDetailID) {
        String sql = "DELETE FROM purchasedetails WHERE PurchaseDetailID = ?";
        jdbcTemplate.update(sql, orderDetailID);
    }

    // 查询订单详情通过 ID
    public PurchaseDetail selectDetailById(Integer orderDetailID) {
        String sql = "SELECT * FROM purchasedetails WHERE PurchaseDetailID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{orderDetailID}, (rs, rowNum) -> {
            PurchaseDetail detail = new PurchaseDetail();
            detail.setPurchaseDetailID(rs.getInt("PurchaseDetailID"));

            // 设置关联的 OrderID
            detail.setPurchaseOrder(rs.getInt("PurchaseID"));

            // 设置关联的 BookID
            detail.setBook(rs.getInt("BookID"));

            detail.setQuantity(rs.getInt("Quantity"));
            return detail;
        });
    }

    // 查询某订单的所有详情
    public List<PurchaseDetail> selectDetailsByOrderId(Integer orderId) {
        String sql = "SELECT * FROM purchasedetails WHERE PurchaseID = ?";
        return jdbcTemplate.query(sql, new Object[]{orderId}, (rs, rowNum) -> {
            PurchaseDetail detail = new PurchaseDetail();
            detail.setPurchaseDetailID(rs.getInt("PurchaseDetailID"));

            // 设置关联的 OrderID
            detail.setPurchaseOrder(rs.getInt("PurchaseID"));

            // 设置关联的 BookID
            detail.setBook(rs.getInt("BookID"));

            detail.setQuantity(rs.getInt("Quantity"));
            return detail;
        });
    }
}
