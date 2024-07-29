package dao.custom;

import dao.CrudDao;
import entity.LibrarianEntity;

public interface LibrarianDao extends CrudDao<LibrarianEntity, String>{

    public LibrarianEntity checkLibrarianData(String email, String password) throws Exception;

}
