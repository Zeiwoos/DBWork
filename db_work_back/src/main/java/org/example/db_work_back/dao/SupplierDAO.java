package org.example.db_work_back.dao;

import org.example.db_work_back.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SupplierDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询所有供应商
    public List<Supplier> selectAllSuppliers() {
        String sql = "SELECT * FROM suppliers";
        return jdbcTemplate.query(sql, this::mapRowToSupplier);
    }

    // 根据ID查询供应商
    public Supplier selectSupplierById(Integer id) {
        String sql = "SELECT * FROM suppliers WHERE SupplierID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToSupplier);
    }

    // 插入新供应商
    public void insertSupplier(Supplier supplier) {
        String sql = "INSERT INTO suppliers (SupplierName, ContactName, Phone, Email, Address) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, supplier.getSupplierName(), supplier.getContactName(), supplier.getPhone(),
                supplier.getEmail(), supplier.getAddress());
    }

    // 更新供应商信息
    public void updateSupplier(Supplier supplier) {
        String sql = "UPDATE suppliers SET SupplierName = ?, ContactName = ?, Phone = ?, Email = ?, Address = ? WHERE SupplierID = ?";
        jdbcTemplate.update(sql, supplier.getSupplierName(), supplier.getContactName(), supplier.getPhone(),
                supplier.getEmail(), supplier.getAddress(), supplier.getSupplierID());
    }

    // 删除供应商
    public void deleteSupplier(Integer id) {
        String sql = "DELETE FROM suppliers WHERE SupplierID = ?";
        jdbcTemplate.update(sql, id);
    }

    // 映射结果集到 Supplier 实体类
    private Supplier mapRowToSupplier(ResultSet rs, int rowNum) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setSupplierID(rs.getInt("SupplierID"));
        supplier.setSupplierName(rs.getString("SupplierName"));
        supplier.setContactName(rs.getString("ContactName"));
        supplier.setPhone(rs.getString("Phone"));
        supplier.setEmail(rs.getString("Email"));
        supplier.setAddress(rs.getString("Address"));
        return supplier;
    }
}
