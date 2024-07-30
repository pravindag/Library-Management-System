package dao.custom;

import dao.CrudDao;
import entity.GenreEntity;

public interface GenreDao extends CrudDao<GenreEntity, String>{

    GenreEntity getLastEnteredId() throws Exception;
}
