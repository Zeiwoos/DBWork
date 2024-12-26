package org.example.db_work_back.service;

import org.example.db_work_back.dao.BookDAO;
import org.example.db_work_back.entity.Book;
import org.example.db_work_back.entity.Customer;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDAO bookDAO;

    // 获取所有书籍
    public List<Book> getAllBooks() {
        return bookDAO.selectAllBooks();
    }

    // 根据ID获取书籍
    public Book getBookById(Integer id) {
        return bookDAO.selectBookById(id);
    }

    // 创建新的书籍
    public Result addBook(Book book) {
        bookDAO.insertBook(book);
        return Result.success("书目创建成功");
    }

    public List<Book> SearchBook(String keywords){
        return bookDAO.BookSearch(keywords);
    }
    public List<Book> SearchBookByTitle(String keywords){
        return bookDAO.selectBookByNameSearch(keywords);
    }
    public List<Book> SearchBookByAuthor(String keywords){
        return bookDAO.selectBookByAuthorSearch(keywords);
    }
    public List<Book> SearchBookByKey(String keywords){
        return bookDAO.selectBookByKeySearch(keywords);
    }
    // 更新客户
    public Result<Book> updateBook(Integer id, Book book) {
        bookDAO.updateBook(id, book);
        return Result.success(book);
    }

    // 删除书籍
    public Result deleteBook(Integer id) {
        bookDAO.deleteBook(id);
        return Result.success("书目删除成功");
    }
}
