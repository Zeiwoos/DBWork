package org.example.db_work_back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.db_work_back.entity.Book;
import org.example.db_work_back.service.BookService;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Api(tags = "图书相关接口")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/getAllBooks")
    public Result<List<Book>> getAllBooks() {
        return Result.success(bookService.getAllBooks());
    }

    @PostMapping("/add")
    public Result addBook(@RequestBody Book book) {
        return Result.success(bookService.addBook(book));
    }

    @GetMapping("/{id}")
    public Result<Book> getBookById(@PathVariable Integer id) {
        return Result.success(bookService.getBookById(id));
    }

    @PutMapping("/update/{id}")
    public Result updateBook(@PathVariable Integer id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteBook(@PathVariable Integer id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/SearchBook")
    public Result<List<Book>> SearchBook(@RequestParam String keywords) {
        return Result.success(bookService.SearchBook(keywords));
    }

    @GetMapping("/SearchTitle")
    public Result<List<Book>> SearchBookByTitle(@RequestParam String keywords) {
        return Result.success(bookService.SearchBookByTitle(keywords));
    }

    @GetMapping("/SearchAuthor")
    public Result<List<Book>> SearchBookByAuthor(@RequestParam String keywords) {
        return Result.success(bookService.SearchBookByAuthor(keywords));
    }

    @GetMapping("/SearchKey")
    public Result<List<Book>> SearchBookByKey(@RequestParam String keywords) {
        return Result.success(bookService.SearchBookByKey(keywords));
    }
}
