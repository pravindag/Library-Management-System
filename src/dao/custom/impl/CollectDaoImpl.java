package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.CollectDao;
import entity.CollectEntity;

public class CollectDaoImpl implements CollectDao{

    @Override
    public String save(CollectEntity entity) throws Exception {
        boolean isSaved = CrudUtil.executeUpdate("INSERT INTO table_collect VALUES (?,?,?,?)", 
                                                entity.getCollectId(), entity.getMemberId(), entity.getBorrowId(), 
                                                entity.getCollectedDate());
        return isSaved ? "Success" : "Fail";
    }

    @Override
    public String update(CollectEntity t) throws Exception {
        return null;
    }

    @Override
    public String delete(String id) throws Exception {
        return null;
    }

    @Override
    public CollectEntity get(String id) throws Exception {
        return null;
    }

    @Override
    public ArrayList<CollectEntity> getAll() throws Exception {
        ArrayList<CollectEntity> collectEntity = new ArrayList<>();

        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_collect ORDER BY CAST(SUBSTRING(collect_id, 2) AS UNSIGNED) DESC");

        while (rst.next()) {
            CollectEntity entity = new CollectEntity(
                    rst.getString("collect_id"),
                    rst.getString("member_id"), 
                    rst.getString("borrow_id"), 
                    rst.getString("collected_date"));
            collectEntity.add(entity);
        }

        return collectEntity;
    }

    @Override
    public CollectEntity getLastEnteredId() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_collect ORDER BY CAST(SUBSTRING(collect_id, 2) AS UNSIGNED) DESC LIMIT 1");
        
        if(rst.next()){
            return new CollectEntity(
                    rst.getString("collect_id"),
                    rst.getString("member_id"), 
                    rst.getString("borrow_id"), 
                    rst.getString("collected_date"));
        }else{

            ResultSet all = CrudUtil.executeQuery("SELECT * FROM table_collect");

            if(all.getRow() == 0){
                    return new CollectEntity("C000", "", "", "");
            }else{
                return new CollectEntity();
            }
        }
    }

    @Override
    public CollectEntity getAccordingToBorrowId(String borrowId) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_collect WHERE borrow_id = ?", borrowId);

        if(rst.next()){
            return new CollectEntity(
                    rst.getString("collect_id"),
                    rst.getString("member_id"), 
                    rst.getString("borrow_id"), 
                    rst.getString("collected_date"));
        }

        return new CollectEntity();
    }

}
