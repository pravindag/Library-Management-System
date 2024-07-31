package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.BorrowDao;
import entity.BorrowEntity;

public class BorrowDaoImpl implements BorrowDao{

    @Override
    public BorrowEntity get(String borrowId) throws Exception {
        
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_borrow WHERE borrow_id = ?", borrowId);

        if(rst.next()){
            return new BorrowEntity(
                    rst.getString("borrow_id"),
                    rst.getString("member_id"), 
                    rst.getString("librarian_id"), 
                    rst.getString("issue_date"), 
                    rst.getInt("due_date"), 
                    rst.getString("return_date"), 
                    rst.getInt("status"));
        }
        
        return null;
    }

    @Override
    public String save(BorrowEntity entity) throws Exception {
        boolean isSaved = CrudUtil.executeUpdate("INSERT INTO table_borrow VALUES (?,?,?,?,?,?,?)", 
                                                entity.getBorrowId(), entity.getMemberId(), entity.getLibrarianId(), 
                                                entity.getIssueDate(), entity.getDueDate(), entity.getReturnDate(), entity.getStatus());
        return isSaved ? "Success" : "Fail";
    }

    @Override
    public String update(BorrowEntity entity) throws Exception {
        boolean isUpdated = CrudUtil.executeUpdate("UPDATE table_borrow SET member_id=?, librarian_id=?, issue_date=?, due_date=?, return_date=?, status=? WHERE borrow_id=?", 
                                                entity.getMemberId(), entity.getLibrarianId(), entity.getIssueDate(), entity.getDueDate(), 
                                                entity.getReturnDate(), entity.getStatus(), entity.getBorrowId());
        return isUpdated ? "Success" : "Fail";
    }

    @Override
    public String delete(String id) throws Exception {
        return null;
    }

    @Override
    public ArrayList<BorrowEntity> getAll() throws Exception {
        ArrayList<BorrowEntity> borrowEntity = new ArrayList<>();

        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_borrow ORDER BY CAST(SUBSTRING(borrow_id, 2) AS UNSIGNED) DESC");

        while (rst.next()) {
            BorrowEntity entity = new BorrowEntity(
                            rst.getString("borrow_id"),
                            rst.getString("member_id"), 
                            rst.getString("librarian_id"), 
                            rst.getString("issue_date"), 
                            rst.getInt("due_date"), 
                            rst.getString("return_date"), 
                            rst.getInt("status"));
            borrowEntity.add(entity);
        }

        return borrowEntity;
    }

    @Override
    public BorrowEntity getLastEnteredId() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_borrow ORDER BY CAST(SUBSTRING(borrow_id, 2) AS UNSIGNED) DESC LIMIT 1");
        
        if(rst.next()){
            return new BorrowEntity(
                    rst.getString("borrow_id"),
                    rst.getString("member_id"), 
                    rst.getString("librarian_id"), 
                    rst.getString("issue_date"), 
                    rst.getInt("due_date"), 
                    rst.getString("return_date"), 
                    rst.getInt("status"));
        }else{

            ResultSet all = CrudUtil.executeQuery("SELECT * FROM table_borrow");

            if(all.getRow() == 0){
                    return new BorrowEntity("B000", "", "", "", 0, "",0);
            }else{
                return new BorrowEntity();
            }
        }
        
    }

}
