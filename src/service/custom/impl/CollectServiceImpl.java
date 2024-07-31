package service.custom.impl;

import java.sql.Connection;
import java.util.ArrayList;

import cache.CacheManager;
import dao.DaoFactory;
import dao.custom.BookDao;
import dao.custom.BorrowDao;
import dao.custom.CollectDao;
import db.DBConnection;
import dto.BorrowDetailDto;
import dto.CollectDto;
import entity.BookEntity;
import entity.BorrowEntity;
import entity.CollectEntity;
import service.custom.CollectService;

public class CollectServiceImpl implements CollectService{

    private CollectDao collectDao = (CollectDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.COLLECT);
    private BookDao bookDao = (BookDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.BOOK);
    private BorrowDao borrowDao = (BorrowDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.BORROW);

    private CacheManager cacheManager = CacheManager.getInstance();

    @Override
    public String collect(CollectDto collectDto) throws Exception {
        
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            
            CollectEntity collectEntity = new CollectEntity(
                                        collectDto.getCollectId(), collectDto.getMemberId(), collectDto.getBorrowId(), 
                                        collectDto.getCollectedDate());
            
            String responseFromCollect = collectDao.save(collectEntity);

            if(responseFromCollect.equals("Success")){
                
                boolean isBookUpdated = true;
                    
                for (BorrowDetailDto borrowDetailDto : collectDto.getBorrowDetailDtos()) {

                    BookEntity bookEntity = bookDao.get(borrowDetailDto.getBookId());
                    bookEntity.setNumberOfCopies(bookEntity.getNumberOfCopies() + 1);

                    String responseFromBook = bookDao.update(bookEntity);

                    if(!responseFromBook.equals("Success")){
                        isBookUpdated = false;
                    }
                }
                
                if(isBookUpdated){

                    BorrowEntity borrowEntity = borrowDao.get(collectDto.getBorrowId());
                    borrowEntity.setStatus(1);

                    String responseFromBorrow = borrowDao.update(borrowEntity);

                    if(responseFromBorrow.equals("Success")){
                        cacheManager.remove("getAllBorrows");
                        cacheManager.remove("getAllCollects");
                        connection.commit();
                        return "Successfully saved !";
                    } else {
                        connection.rollback();
                        return "Error updating borrow !";
                    }
                } else {
                    connection.rollback();
                    return "Error updating book !";
                }
                
            } else {
                connection.rollback();
                return "Error saving Collect !";
            }
            
        } catch (Exception e) {
           connection.rollback();
           return "Server Error";
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public CollectDto getLastEnteredId() throws Exception {
        CollectEntity entity = collectDao.getLastEnteredId();
        return getCollectDto(entity);
    }

    private CollectDto getCollectDto(CollectEntity entity){

        CollectDto collectEntityDto = new CollectDto(
                    entity.getCollectId(), entity.getMemberId(), entity.getBorrowId(), entity.getCollectedDate());

        return collectEntityDto;

    }

    @Override
    public CollectDto getAccordingToBorrowId(String borrowId) throws Exception {
        CollectEntity entity = collectDao.getAccordingToBorrowId(borrowId);
        return getCollectDto(entity);
    }

    @Override
    public ArrayList<CollectDto> getAll() throws Exception {
        ArrayList<CollectDto> collectDtos = (ArrayList<CollectDto>) cacheManager.get("getAllCollects");

        if (collectDtos == null) { 

            collectDtos = new ArrayList<>();

            ArrayList<CollectEntity> collectEntities = collectDao.getAll();

            for (CollectEntity collectEntity : collectEntities) {
                CollectDto dto = getCollectDto(collectEntity);
                collectDtos.add(dto);
            }

            cacheManager.put("getAllCollects", collectDtos);
        }

        return collectDtos;
    }

}
