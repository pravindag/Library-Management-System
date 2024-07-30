package service.custom;

import dto.FineDto;
import service.SuperService;

public interface FineService extends SuperService{

    public String save(FineDto fineDto) throws Exception;
    public FineDto get(String fineId) throws Exception;
    public FineDto getAccordingToBorrowId(String borrowId) throws Exception;
    public FineDto getLastEnteredId() throws Exception;
}
