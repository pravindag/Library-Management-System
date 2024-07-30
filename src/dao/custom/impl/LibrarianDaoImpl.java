package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.LibrarianDao;
import entity.LibrarianEntity;

public class LibrarianDaoImpl implements LibrarianDao{

    @Override
    public String save(LibrarianEntity t) throws Exception {
        return null;
    }

    @Override
    public String update(LibrarianEntity t) throws Exception {
        return null;
    }

    @Override
    public String delete(String librarianId) throws Exception {
        return null;
    }

    @Override
    public LibrarianEntity get(String librarianId) throws Exception {
        
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_librarian WHERE librarian_id = ?", librarianId);

        if(rst.next()){
            return new LibrarianEntity(
                    rst.getString("librarian_id"),
                    rst.getString("first_name"), 
                    rst.getString("last_name"), 
                    rst.getString("date_of_birth"), 
                    rst.getString("address"), 
                    rst.getString("phone_number"),
                    rst.getString("email"), 
                    rst.getString("employment_date"), 
                    rst.getString("password"));
        }

        return null;
    }

    @Override
    public ArrayList<LibrarianEntity> getAll() throws Exception {
        return null;
    }

    public LibrarianEntity checkLibrarianData(String email, String password) throws Exception {
        
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_librarian WHERE email = ? AND password = ?", email, password);

        if(rst.next()){
            return new LibrarianEntity(
                    rst.getString("librarian_id"),
                    rst.getString("first_name"), 
                    rst.getString("last_name"), 
                    rst.getString("date_of_birth"), 
                    rst.getString("address"), 
                    rst.getString("phone_number"),
                    rst.getString("email"), 
                    rst.getString("employment_date"), 
                    rst.getString("password"));
        }

        return null;
    }

}
