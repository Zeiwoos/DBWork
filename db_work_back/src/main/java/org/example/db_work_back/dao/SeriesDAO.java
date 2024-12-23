//package org.example.db_work_back.dao;
//
//import org.example.db_work_back.entity.Series;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//@Repository
//public class SeriesDAO {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    // 插入丛书
//    public void insertSeries(Series series) {
//        String sql = "INSERT INTO series (SeriesName, Description) VALUES (?, ?)";
//        jdbcTemplate.update(sql, series.getSeriesName(), series.getDescription());
//    }
//
//    // 根据 ID 查询丛书
//    public Series selectSeriesById(Integer id) {
//        String sql = "SELECT * FROM series WHERE SeriesID = ?";
//        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToSeries);
//    }
//
//    // 获取所有丛书
//    public List<Series> selectAllSeries() {
//        String sql = "SELECT * FROM series";
//        return jdbcTemplate.query(sql, this::mapRowToSeries);
//    }
//
//    // 更新丛书
//    public void updateSeries(Integer id, Series series) {
//        String sql = "UPDATE series SET SeriesName = ?, Description = ? WHERE SeriesID = ?";
//        jdbcTemplate.update(sql, series.getSeriesName(), series.getDescription(), id);
//    }
//
//    // 删除丛书
//    public void deleteSeries(Integer id) {
//        String sql = "DELETE FROM series WHERE SeriesID = ?";
//        jdbcTemplate.update(sql, id);
//    }
//
//    // 映射查询结果到 Series 对象
//    private Series mapRowToSeries(ResultSet rs, int rowNum) throws SQLException {
//        Series series = new Series();
//        series.setSeriesID(rs.getInt("SeriesID"));
//        series.setSeriesName(rs.getString("SeriesName"));
//        series.setDescription(rs.getString("Description"));
//        return series;
//    }
//}
