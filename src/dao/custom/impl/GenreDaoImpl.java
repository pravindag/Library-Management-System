package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.GenreDao;
import entity.GenreEntity;

public class GenreDaoImpl implements GenreDao{

    @Override
    public String save(GenreEntity entity) throws Exception {
        boolean isSaved = CrudUtil.executeUpdate("INSERT INTO table_genre VALUES(?,?)", entity.getGenreId(), entity.getGenreName());
        return isSaved ? "Success" : "Fail";
    }

    @Override
    public String update(GenreEntity entity) throws Exception {
        boolean isUpdated = CrudUtil.executeUpdate("UPDATE table_genre SET genre_name=? WHERE genre_id =?", entity.getGenreName(), entity.getGenreId());
        return isUpdated ? "Success" : "Fail";
    }

    @Override
    public String delete(String genreId) throws Exception {
        boolean isDeleted = CrudUtil.executeUpdate("DELETE FROM table_genre WHERE genre_id =?", genreId);
        return isDeleted ? "Success" : "Fail";
    }

    @Override
    public GenreEntity get(String genreId) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_genre WHERE genre_id = ?", genreId);

        if(rst.next()){
            return new GenreEntity(
                    rst.getString("genre_id"),
                    rst.getString("genre_name"));
        }
        
        return null;
    }

    @Override
    public ArrayList<GenreEntity> getAll() throws Exception {
        ArrayList<GenreEntity> genreEntity = new ArrayList<>();

        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_genre");

        while (rst.next()) {
            GenreEntity entity = new GenreEntity(
                    rst.getString("genre_id"),
                    rst.getString("genre_name"));
            genreEntity.add(entity);
        }

        return genreEntity;
    }

    @Override
    public GenreEntity getLastEnteredId() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_genre ORDER BY CAST(SUBSTRING(genre_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if(rst.next()){
            return new GenreEntity(
                    rst.getString("genre_id"),
                    rst.getString("genre_name"));
        }else{

            ResultSet all = CrudUtil.executeQuery("SELECT * FROM table_genre");
            
            if(all.getRow() == 0){
                    return new GenreEntity("G000", "");
            }else{
                return null;
            }
        }
    }

}
