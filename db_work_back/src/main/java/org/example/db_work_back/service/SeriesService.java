package org.example.db_work_back.service;

import org.example.db_work_back.dao.SeriesDAO;
import org.example.db_work_back.dao.BookDAO;
import org.example.db_work_back.entity.OrderDetail;
import org.example.db_work_back.entity.Series;
import org.example.db_work_back.entity.Book;
import org.example.db_work_back.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {

    @Autowired
    private SeriesDAO seriesDAO;
    @Autowired
    private BookDAO bookDAO;

    // 获取所有丛书
    public List<Series> getAllSeries() {
        return seriesDAO.selectAllSeries();
    }

    // 根据ID获取丛书
    public Series getSeriesById(Integer id) {
        return seriesDAO.selectSeriesById(id);
    }

    // 添加丛书
    public Result addSeries(Series series) {
        seriesDAO.insertSeries(series);
        return Result.success(series);
    }

    // 更新丛书
    public Result updateSeries(Integer id, Series series) {
        seriesDAO.updateSeries(id, series);
        return Result.success(series);
    }

    // 删除丛书
    public Result deleteSeries(Integer id) {
        List<Book> books = bookDAO.selectBookBySeriesId(id);
        for (Book book : books) {
            Integer bookID = book.getBookID();
            bookDAO.deleteBook(bookID); // 使用 OrderDetailDAO 插入订单详情
        }
        seriesDAO.deleteSeries(id);
        return Result.success("丛书删除成功");
    }
}
