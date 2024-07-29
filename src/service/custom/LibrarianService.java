package service.custom;

import java.util.ArrayList;

import dto.LibrarianDto;
import service.SuperService;

public interface LibrarianService extends SuperService{

    public String save(LibrarianDto librarianDto) throws Exception;
    public String update(LibrarianDto librarianDto) throws Exception;
    public String delete(String librarianId) throws Exception;
    public LibrarianDto get(String librarianId) throws Exception;
    public ArrayList<LibrarianDto> getAll() throws Exception;
    public LibrarianDto checkLibrarianData(String email, String password) throws Exception;

}
