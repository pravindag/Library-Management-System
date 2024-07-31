package dao.custom;

import dao.CrudDao;
import entity.CollectEntity;

public interface CollectDao extends CrudDao<CollectEntity, String>{

    CollectEntity getLastEnteredId() throws Exception;
    CollectEntity getAccordingToBorrowId(String borrowId) throws Exception;
}
