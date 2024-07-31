package service.custom;

import java.util.ArrayList;

import dto.BorrowDto;
import service.SuperService;

public interface BorrowService extends SuperService{

    public String handOver(BorrowDto borrowDto) throws Exception;
    public BorrowDto get(String borrowId) throws Exception;
    public ArrayList<BorrowDto> getAll() throws Exception;
    public BorrowDto getLastEnteredId() throws Exception;

}
