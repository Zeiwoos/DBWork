package org.example.db_work_back.dao;

import org.example.db_work_back.entity.Book;
import org.example.db_work_back.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询所有书籍
    public List<Book> selectAllBooks() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, this::mapRowToBook);
    }

    // 根据ID查询书籍
    public Book selectBookById(Integer id) {
        String sql = "SELECT * FROM books WHERE BookID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToBook);
    }
    //根据系列id查询书籍
    public List<Book> selectBookBySeriesId(Integer SeriesID) {
        String sql = "SELECT * FROM books WHERE SeriesID = ?";
        return jdbcTemplate.query(sql, new Object[]{SeriesID}, (rs, rowNum) -> {
            Book book = new Book();
            book.setBookID(rs.getInt("BookID"));
            book.setTitle(rs.getString("Title"));
            book.setAuthor(rs.getString("Author"));
            book.setPublisher(rs.getString("Publisher"));
            book.setPrice(rs.getBigDecimal("Price"));
            book.setKeywords(rs.getString("Keywords"));
            book.setDescription(rs.getString("Description"));
//        book.setCoverImage(rs.getBytes("CoverImage"));
            book.setStock(rs.getInt("Stock"));
            book.setStorageLocation(rs.getString("StorageLocation"));
            book.setSeriesID(rs.getInt("SeriesID"));
            book.setSupplierID(rs.getInt("SupplierID"));
            return book;
        });
    }
    // 插入新书籍
    public void insertBook(Book book) {
        String sql = "INSERT INTO books (Title, Author, Publisher, Price, Keywords, Description, Stock, Storagelocation, SeriesID,SupplierID) " +
                "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublisher(), book.getPrice(),
                book.getKeywords(), book.getDescription(), book.getStock(), book.getStorageLocation(), book.getSeriesID(),book.getSupplierID());
    }

    // 更新书籍信息
    public void updateBook(Integer id, Book book) {
        String sql = "UPDATE books SET Title = ?, Author = ?, Publisher = ?, Price = ?, Keywords = ?, Description = ?, Stock = ?, StorageLocation = ?, SeriesID = ? ,SupplierID=? WHERE BookID = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublisher(), book.getPrice(),
                book.getKeywords(), book.getDescription(), book.getStock(), book.getStorageLocation(), book.getSeriesID(),book.getSupplierID(), id);
    }

    // 删除书籍
    public void deleteBook(Integer id) {
        String sql = "DELETE FROM books WHERE BookID = ?";
        jdbcTemplate.update(sql, id);
    }

    // 映射结果集到 Book 实体类
    private Book mapRowToBook(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setBookID(rs.getInt("BookID"));
        book.setTitle(rs.getString("Title"));
        book.setAuthor(rs.getString("Author"));
        book.setPublisher(rs.getString("Publisher"));
        book.setPrice(rs.getBigDecimal("Price"));
        book.setKeywords(rs.getString("Keywords"));
        book.setDescription(rs.getString("Description"));
//        book.setCoverImage(rs.getBytes("CoverImage"));
        book.setStock(rs.getInt("Stock"));
        book.setStorageLocation(rs.getString("StorageLocation"));
        book.setSeriesID(rs.getInt("SeriesID"));
        book.setSupplierID(rs.getInt("SupplierID"));
        return book;
    }
}
