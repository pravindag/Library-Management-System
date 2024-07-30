package dao.custom;

import dao.CrudDao;
import entity.FineEntity;

public interface FineDao extends CrudDao<FineEntity, String>{

    FineEntity getAccordingToBorrowId(String borrowId) throws Exception;
    FineEntity getLastEnteredId() throws Exception;
}
