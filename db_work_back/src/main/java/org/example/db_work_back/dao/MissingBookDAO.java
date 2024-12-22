package org.example.db_work_back.dao;

import org.example.db_work_back.entity.MissingBook;
import org.example.db_work_back.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MissingBookDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 插入缺书记录
    public void insertMissingBook(MissingBook missingBook) {
        String sql = "INSERT INTO missingBooks (BookID, CustomerID, Quantity, RegisterDate, Status) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, missingBook.getBookId(), missingBook.getCustomerId(),
                missingBook.getQuantity(), missingBook.getRegisterDate(),missingBook.getStatus());
    }

    // 更新缺书记录
    public void updateMissingBook(MissingBook missingBook) {
        String sql = "UPDATE missingBooks SET BookID = ?, CustomerID = ?, Quantity = ?, Status = ?," +
                "RegisterDate = ? WHERE MissingID = ?";
        jdbcTemplate.update(sql, missingBook.getBookId(), missingBook.getCustomerId(),
                missingBook.getQuantity(), missingBook.getRegisterDate(), missingBook.getMissingId(),missingBook.getStatus());
    }

    // 根据ID查询缺书记录
    public MissingBook selectMissingBookById(Integer id) {
        String sql = "SELECT * FROM missingBooks WHERE MissingID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, missingBookRowMapper());
    }

    // 获取所有缺书记录
    public List<MissingBook> selectAllMissingBooks() {
        String sql = "SELECT * FROM missingBooks";
        return jdbcTemplate.query(sql, missingBookRowMapper());
    }

    // 根据顾客ID获取缺书记录
    public List<MissingBook> selectMissingBooksByCustomerId(Integer customerId) {
        String sql = "SELECT * FROM missingBooks WHERE CustomerID = ?";
        return jdbcTemplate.query(sql, new Object[]{customerId}, missingBookRowMapper());
    }

    // 根据书籍ID获取缺书记录
    public List<MissingBook> selectMissingBooksByBookId(Integer bookId) {
        String sql = "SELECT * FROM missingBooks WHERE BookID = ?";
        return jdbcTemplate.query(sql, new Object[]{bookId}, missingBookRowMapper());
    }

    // 删除缺书记录
    public void deleteMissingBook(Integer id) {
        String sql = "DELETE FROM missingBooks WHERE MissingID = ?";
        jdbcTemplate.update(sql, id);
    }

    // 提取映射逻辑为一个单独的RowMapper方法
    private RowMapper<MissingBook> missingBookRowMapper() {
        return (rs, rowNum) -> {
            MissingBook missingBook = new MissingBook();
            missingBook.setMissingId(rs.getInt("MissingID"));
            missingBook.setBookId(rs.getInt("BookID"));
            missingBook.setCustomerId(rs.getInt("CustomerID"));
            missingBook.setQuantity(rs.getInt("Quantity"));
            missingBook.setRegisterDate(rs.getDate("RegisterDate"));
            missingBook.setStatus(rs.getString("Status"));
            return missingBook;
        };
    }
}
