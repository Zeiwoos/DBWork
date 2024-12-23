package org.example.db_work_back.service;

import org.example.db_work_back.dao.SupplierDAO;
import org.example.db_work_back.entity.Supplier;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierDAO supplierDAO;

    // 获取所有供应商
    public List<Supplier> getAllSuppliers() {
        return supplierDAO.selectAllSuppliers();
    }

    // 根据ID获取供应商
    public Supplier getSupplierById(Integer id) {
        return supplierDAO.selectSupplierById(id);
    }

    // 创建新的供应商
    public Supplier addSupplier(Supplier supplier) {
        supplierDAO.insertSupplier(supplier);
        return supplier;
    }

    // 更新供应商信息
    public Result updateSupplier(Supplier supplier) {
        supplierDAO.updateSupplier(supplier);
        return Result.success(supplier);
    }

    // 删除供应商
    public Result deleteSupplier(Integer id) {
        supplierDAO.deleteSupplier(id);
        return Result.success("供应商删除成功");
    }
}
