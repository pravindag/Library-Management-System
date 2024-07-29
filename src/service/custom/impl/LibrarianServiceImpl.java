package service.custom.impl;

import java.util.ArrayList;

import dao.DaoFactory;
import dao.custom.LibrarianDao;
import dto.LibrarianDto;
import entity.LibrarianEntity;
import service.custom.LibrarianService;

public class LibrarianServiceImpl implements LibrarianService{

    private LibrarianDao librarianDao = (LibrarianDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.LIBRARIAN);

    @Override
    public String save(LibrarianDto librarianDto) throws Exception {
        return null;
    }

    @Override
    public String update(LibrarianDto librarianDto) throws Exception {
        return null;
    }

    @Override
    public String delete(String librarianId) throws Exception {
        return null;
    }

    @Override
    public LibrarianDto get(String librarianId) throws Exception {
        LibrarianEntity entity = librarianDao.get(librarianId);
        return getLibrarianDto(entity);
    }

    @Override
    public ArrayList<LibrarianDto> getAll() throws Exception {
        return null;
    }

    @Override
    public LibrarianDto checkLibrarianData(String email, String password) throws Exception {
        LibrarianEntity entity = librarianDao.checkLibrarianData(email, password);
        return getLibrarianDto(entity);
    }

    private LibrarianDto getLibrarianDto(LibrarianEntity entity){

        LibrarianDto librarianEntityDto = new LibrarianDto(
                    entity.getLibrarianId(), entity.getFirstName(), entity.getLastName(),
                    entity.getDateOfBirth(), entity.getAddress(), entity.getPhoneNumber(), 
                    entity.getEmail(), entity.getEmploymentDate(), entity.getPassword());

        return librarianEntityDto;
    }
    
    private LibrarianEntity getLibrarianEntity(LibrarianDto dto){

        LibrarianEntity entity = new LibrarianEntity(
                    dto.getLibrarianId(), dto.getFirstName(), dto.getLastName(),
                    dto.getDateOfBirth(), dto.getAddress(), dto.getPhoneNumber(), 
                    dto.getEmail(), dto.getEmploymentDate(), dto.getPassword());

         return entity;
    }

}
