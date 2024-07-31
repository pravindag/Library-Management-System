package service.custom.impl;

import java.sql.Connection;
import java.util.ArrayList;

import cache.CacheManager;
import dao.DaoFactory;
import dao.custom.BookDao;
import dao.custom.BorrowDao;
import dao.custom.BorrowDetailsDao;
import db.DBConnection;
import dto.BorrowDetailDto;
import dto.BorrowDto;
import entity.BookEntity;
import entity.BorrowDetailsEntity;
import entity.BorrowEntity;
import service.custom.BorrowService;

public class BorrowServiceImpl implements BorrowService{

    private BorrowDao borrowDao = (BorrowDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.BORROW);
    private BorrowDetailsDao borrowDetailsDao = (BorrowDetailsDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.BORROW_DETAILS);
    private BookDao bookDao = (BookDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.BOOK);

    private CacheManager cacheManager = CacheManager.getInstance();

    @Override
    public BorrowDto get(String borrowId) throws Exception { 
        BorrowEntity entity = borrowDao.get(borrowId);
        return getBorrowDto(entity);
    }

    public ArrayList<BorrowDetailDto> getBorrowDetailAccordingToBorrowId(String borrowId) throws Exception {

        ArrayList<BorrowDetailDto> borrowDetailDtos = new ArrayList<>();

        ArrayList<BorrowDetailsEntity> borrowDetailsEntities = borrowDetailsDao.getAllAccordingToBorrowId(borrowId);

        for (BorrowDetailsEntity borrowDetailsEntity : borrowDetailsEntities) {

            BorrowDetailDto borrowDetailDto = new BorrowDetailDto();

            borrowDetailDto.setBorrowId(borrowDetailsEntity.getBorrowId());
            borrowDetailDto.setBookId(borrowDetailsEntity.getBookId());

            borrowDetailDtos.add(borrowDetailDto);
        }

        return borrowDetailDtos;
    }

    private BorrowDto getBorrowDto(BorrowEntity entity) throws Exception{

        BorrowDto borrowEntityDto = new BorrowDto(
                    entity.getBorrowId(), entity.getMemberId(), entity.getLibrarianId(),
                    entity.getIssueDate(), entity.getDueDate(), entity.getReturnDate(), entity.getStatus());

        ArrayList<BorrowDetailDto> borrowDetailAccordingToBorrowId = getBorrowDetailAccordingToBorrowId(entity.getBorrowId());
        
        borrowEntityDto.setBorrowDetailDtos(borrowDetailAccordingToBorrowId);

        return borrowEntityDto;

    }

    @Override
    public String handOver(BorrowDto borrowDto) throws Exception {
        
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            
            BorrowEntity borrowEntity = new BorrowEntity(
                            borrowDto.getBorrowId(), borrowDto.getMemberId(), borrowDto.getLibrarianId(), 
                            borrowDto.getIssueDate(), borrowDto.getDueDate(), borrowDto.getReturnDate(), borrowDto.getStatus());
            
            String responseFromBorrow = borrowDao.save(borrowEntity);

            if(responseFromBorrow.equals("Success")){
                
                boolean isBorrowDetailSave = true;
                
                for (BorrowDetailDto borrowDetailDto : borrowDto.getBorrowDetailDtos()) {

                    BorrowDetailsEntity borrowDetailsEntity = new BorrowDetailsEntity(
                                                    borrowDto.getBorrowId(), borrowDetailDto.getBookId());

                    String responseFromBorrowDetails = borrowDetailsDao.save(borrowDetailsEntity);

                    if(!responseFromBorrowDetails.equals("Success")){
                        isBorrowDetailSave = false;
                    }
                }
                
                if(isBorrowDetailSave){
                    boolean isBookUpdated = true;
                    
                    for (BorrowDetailDto borrowDetailDto : borrowDto.getBorrowDetailDtos()) {

                        BookEntity bookEntity = bookDao.get(borrowDetailDto.getBookId());
                        bookEntity.setNumberOfCopies(bookEntity.getNumberOfCopies() - 1);

                        String responseFromBook = bookDao.update(bookEntity);

                        if(!responseFromBook.equals("Success")){
                            isBookUpdated = false;
                        }
                    }
                    
                    if(isBookUpdated){
                        cacheManager.remove("getAllBorrows");
                        connection.commit();
                        return "Successfully saved !";
                    } else {
                        connection.rollback();
                        return "Error updating book !";
                    }
                    
                } else{
                    connection.rollback();
                    return "Error saving Borrow Details !";
                }
                
            } else {
                connection.rollback();
                return "Error saving Borrow !";
            }
            
        } catch (Exception e) {
           connection.rollback();
           return "Server Error";
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public BorrowDto getLastEnteredId() throws Exception {
        BorrowEntity entity = borrowDao.getLastEnteredId();
        return getBorrowDto(entity);
    }

    @Override
    public ArrayList<BorrowDto> getAll() throws Exception {
        ArrayList<BorrowDto> borrowDtos = (ArrayList<BorrowDto>) cacheManager.get("getAllBorrows");

        if (borrowDtos == null) { 

            borrowDtos = new ArrayList<>();

            ArrayList<BorrowEntity> borrowEntitys = borrowDao.getAll();

            for (BorrowEntity borrowEntity : borrowEntitys) {
                BorrowDto dto = getBorrowDto(borrowEntity);
                borrowDtos.add(dto);
            }

            cacheManager.put("getAllBorrows", borrowDtos);
        }

        return borrowDtos;
    }

}
