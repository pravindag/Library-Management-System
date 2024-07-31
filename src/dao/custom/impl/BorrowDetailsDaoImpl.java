package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.BorrowDetailsDao;
import entity.BorrowDetailsEntity;

public class BorrowDetailsDaoImpl implements BorrowDetailsDao{

    @Override
    public String save(BorrowDetailsEntity entity) throws Exception {
        boolean isSaved = CrudUtil.executeUpdate("INSERT INTO table_borrow_details VALUES (?,?)", entity.getBorrowId(), entity.getBookId());
        return isSaved ? "Success" : "Fail";
    }

    @Override
    public String update(BorrowDetailsEntity entity) throws Exception {
        return null;
    }

    @Override
    public String delete(String id) throws Exception {
        return null;
    }

    @Override
    public BorrowDetailsEntity get(String borrowId) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_borrow_details WHERE borrow_id = ?", borrowId);

        if(rst.next()){
            return new BorrowDetailsEntity(
                    rst.getString("borrow_id"),
                    rst.getString("book_id"));
        }
        
        return null;
    }

    @Override
    public ArrayList<BorrowDetailsEntity> getAll() throws Exception {
        return null;
    }

    public ArrayList<BorrowDetailsEntity> getAllAccordingToBorrowId(String borrowId) throws Exception {

        ArrayList<BorrowDetailsEntity> borrowDetailsEntity = new ArrayList<>();

        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_borrow_details WHERE borrow_id = ?", borrowId);

        while (rst.next()) {
            BorrowDetailsEntity entity = new BorrowDetailsEntity(rst.getString("borrow_id"), rst.getString("book_id"));
            borrowDetailsEntity.add(entity);
        }

        return borrowDetailsEntity;
    }

}
