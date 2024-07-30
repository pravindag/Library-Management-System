package service.custom.impl;

import java.util.ArrayList;

import cache.CacheManager;
import dao.DaoFactory;
import dao.custom.GenreDao;
import dto.GenreDto;
import entity.GenreEntity;
import service.custom.GenreService;

public class GenreServiceImpl implements GenreService{

    private GenreDao genreDao = (GenreDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.GENRE);

    @Override
    public String save(GenreDto genreDto) throws Exception {
        GenreEntity entity = getGenreEntity(genreDto);
        return genreDao.save(entity);
    }

    @Override
    public String update(GenreDto genreDto) throws Exception {
        GenreEntity entity = getGenreEntity(genreDto);
        return genreDao.update(entity);
    }

    @Override
    public String delete(String genreId) throws Exception {
        return genreDao.delete(genreId);
    }

    @Override
    public GenreDto get(String genreId) throws Exception {
        GenreEntity entity = genreDao.get(genreId);
        return getGenreDto(entity);
    }

    @Override
    public ArrayList<GenreDto> getAll() throws Exception { 
        ArrayList<GenreDto> genreDtos = new ArrayList<>();

        ArrayList<GenreEntity> genreEntities = genreDao.getAll();

        for (GenreEntity genreEntity : genreEntities) {
            GenreDto dto = getGenreDto(genreEntity);
            genreDtos.add(dto);
        }

        return genreDtos;
    }

    @Override
    public GenreDto getLastEnteredId() throws Exception {
        GenreEntity entity = genreDao.getLastEnteredId();
        return getGenreDto(entity);
    }

    private GenreDto getGenreDto(GenreEntity entity){

        GenreDto genreEntityDto = new GenreDto(
                    entity.getGenreId(), entity.getGenreName());

        return genreEntityDto;

    }
    
    private GenreEntity getGenreEntity(GenreDto dto){

        GenreEntity entity = new GenreEntity(
                    dto.getGenreId(), dto.getGenreName());

         return entity;
    }

}
