//package org.example.db_work_back.service;
//
//import org.example.db_work_back.dao.BrowsingLogDAO;
//import org.example.db_work_back.entity.BrowsingLog;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BrowsingLogService {
//
//    @Autowired
//    private BrowsingLogDAO browsingLogDAO;
//
//    // 获取所有浏览记录
//    public List<BrowsingLog> getAllBrowsingLogs() {
//        return browsingLogDAO.selectAllBrowsingLogs();
//    }
//
//    // 根据ID获取浏览记录
//    public BrowsingLog getBrowsingLogById(Integer id) {
//        return browsingLogDAO.selectBrowsingLogById(id);
//    }
//
//    // 创建新的浏览记录
//    public BrowsingLog createBrowsingLog(BrowsingLog browsingLog) {
//        browsingLogDAO.insertBrowsingLog(browsingLog);
//        return browsingLog;
//    }
//
//    // 更新浏览记录
//    public BrowsingLog updateBrowsingLog(Integer id, BrowsingLog browsingLog) {
//        return browsingLogDAO.updateBrowsingLog(id, browsingLog);
//    }
//
//    // 删除浏览记录
//    public void deleteBrowsingLog(Integer id) {
//        browsingLogDAO.deleteBrowsingLog(id);
//    }
//}
