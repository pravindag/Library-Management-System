package service.custom;

import java.util.ArrayList;

import dto.CollectDto;
import service.SuperService;

public interface CollectService extends SuperService{

    public String collect(CollectDto collectDto) throws Exception;
    public CollectDto getLastEnteredId() throws Exception;
    public CollectDto getAccordingToBorrowId(String borrowId) throws Exception;
    public ArrayList<CollectDto> getAll() throws Exception;
}
