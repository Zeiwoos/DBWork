package org.example.db_work_back.service;

import org.example.db_work_back.dao.MissingBookDAO;
import org.example.db_work_back.entity.MissingBook;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissingBookService {

    @Autowired
    private MissingBookDAO missingBookDAO;

    // 获取所有缺书记录
    public List<MissingBook> getAllMissingBooks() {
        return missingBookDAO.selectAllMissingBooks();
    }

    // 根据ID获取缺书记录
    public MissingBook getMissingBookById(Integer id) {
        return missingBookDAO.selectMissingBookById(id);
    }

    // 创建缺书记录
    public Result createMissingBook(MissingBook missingBook) {
        try {
            missingBookDAO.insertMissingBook(missingBook);
            return Result.success(missingBook);
        } catch (Exception e) {
            return Result.error("创建缺书记录失败: " + e.getMessage());
        }
    }

    public Result updateMissingBookStatus(MissingBook missingBook) {
        try {
            missingBookDAO.updateMissingBook(missingBook);
            return Result.success(missingBook);
        } catch (Exception e) {
            return Result.error("创建缺书记录失败: " + e.getMessage());
        }
    }

    // 删除缺书记录
    public Result deleteMissingBook(Integer id) {
        try {
            missingBookDAO.deleteMissingBook(id);
            return Result.success("缺书记录删除成功");
        } catch (Exception e) {
            return Result.error("删除缺书记录失败: " + e.getMessage());
        }
    }

    // 根据顾客ID获取顾客的缺书记录
    public List<MissingBook> getMissingBooksByCustomerId(Integer customerId) {
        return missingBookDAO.selectMissingBooksByCustomerId(customerId);
    }

    // 获取特定书籍的缺书记录
    public List<MissingBook> getMissingBooksByBookId(Integer bookId) {
        return missingBookDAO.selectMissingBooksByBookId(bookId);
    }
}
