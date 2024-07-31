package dao.custom;

import java.util.ArrayList;

import dao.CrudDao;
import entity.BorrowDetailsEntity;

public interface BorrowDetailsDao extends CrudDao<BorrowDetailsEntity, String>{

    public ArrayList<BorrowDetailsEntity> getAllAccordingToBorrowId(String borrowId) throws Exception;
}
