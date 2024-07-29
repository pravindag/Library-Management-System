package dao.custom;

import dao.CrudDao;
import entity.BookEntity;

public interface BookDao extends CrudDao<BookEntity, String>{

    BookEntity getLastEnteredId() throws Exception;
}
