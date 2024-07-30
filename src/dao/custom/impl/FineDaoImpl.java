package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.FineDao;
import entity.FineEntity;

public class FineDaoImpl implements FineDao{

    @Override
    public String save(FineEntity entity) throws Exception { 
        boolean isSaved = CrudUtil.executeUpdate("INSERT INTO table_fine VALUES (?,?,?,?)", 
                                                entity.getFineId(), entity.getBorrowId(), entity.getPaidDate(), entity.getAmount());
        return isSaved ? "Success" : "Fail";
    }

    @Override
    public String update(FineEntity t) throws Exception {
        return null;
    }

    @Override
    public String delete(String fineId) throws Exception {
        return null;
    }

    @Override
    public FineEntity get(String fineId) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_fine WHERE fine_id = ?", fineId);

        if(rst.next()){
            return new FineEntity(
                    rst.getString("fine_id"),
                    rst.getString("borrow_id"), 
                    rst.getString("paid_date"), 
                    rst.getDouble("amount"));
        }
        return null;
    }

    @Override
    public ArrayList<FineEntity> getAll() throws Exception {
        return null;
    }

    @Override
    public FineEntity getAccordingToBorrowId(String borrowId) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_fine WHERE borrow_id = ?", borrowId);

        if(rst.next()){
            return new FineEntity(
                    rst.getString("fine_id"),
                    rst.getString("borrow_id"), 
                    rst.getString("paid_date"), 
                    rst.getDouble("amount"));
        }

        return new FineEntity();
    }

    @Override
    public FineEntity getLastEnteredId() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_fine ORDER BY CAST(SUBSTRING(fine_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if(rst.next()){
            return new FineEntity(
                    rst.getString("fine_id"),
                    rst.getString("borrow_id"), 
                    rst.getString("paid_date"), 
                    rst.getDouble("amount"));
        }else{

            ResultSet all = CrudUtil.executeQuery("SELECT * FROM table_fine");
            
            if(all.getRow() == 0){
                    return new FineEntity("F000", "", "", 0.0);
            }else{
                return new FineEntity();
            }
        }
    }

}
