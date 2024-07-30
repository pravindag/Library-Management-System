package service.custom.impl;

import dao.DaoFactory;
import dao.custom.FineDao;
import dto.FineDto;
import entity.FineEntity;
import service.custom.FineService;

public class FineServiceImpl implements FineService {

    private FineDao fineDao = (FineDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.FINE);

    @Override
    public FineDto get(String fineId) throws Exception {
        FineEntity entity = fineDao.get(fineId);
        return getFineDto(entity);
    }

    @Override
    public FineDto getAccordingToBorrowId(String borrowId) throws Exception {
        FineEntity entity = fineDao.getAccordingToBorrowId(borrowId);
        return getFineDto(entity);
    }

    @Override
    public String save(FineDto fineDto) throws Exception {
        FineEntity entity = getFineEntity(fineDto);
        return fineDao.save(entity);
    }

    private FineDto getFineDto(FineEntity entity){

        FineDto fineEntityDto = new FineDto(
                    entity.getFineId(), entity.getBorrowId(), entity.getPaidDate(), entity.getAmount());

        return fineEntityDto;

    }

    private FineEntity getFineEntity(FineDto fineDto){

        FineEntity entity = new FineEntity(
                    fineDto.getFineId(), fineDto.getBorrowId(), fineDto.getPaidDate(), fineDto.getAmount());

        return entity;
   }

    @Override
    public FineDto getLastEnteredId() throws Exception {
        FineEntity entity = fineDao.getLastEnteredId();
        return getFineDto(entity);
    }

}
