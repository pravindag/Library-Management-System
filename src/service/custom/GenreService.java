package service.custom;

import java.util.ArrayList;

import dto.GenreDto;
import service.SuperService;

public interface GenreService extends SuperService{

    public String save(GenreDto genreDto) throws Exception;
    public String update(GenreDto genreDto) throws Exception;
    public String delete(String genreId) throws Exception;
    public GenreDto get(String genreId) throws Exception;
    public ArrayList<GenreDto> getAll() throws Exception;
    public GenreDto getLastEnteredId() throws Exception;
}
