package service.custom;

import java.util.ArrayList;

import dto.BookDto;
import service.SuperService;

public interface BookService extends SuperService{

    public String save(BookDto bookDto) throws Exception;
    public String update(BookDto bookDto) throws Exception;
    public String delete(String bookId) throws Exception;
    public BookDto get(String bookId) throws Exception;
    public ArrayList<BookDto> getAll() throws Exception;
    public BookDto getLastEnteredId() throws Exception;    

}
