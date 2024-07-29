package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import dao.CrudUtil;
import dao.custom.BookDao;
import entity.BookEntity;

public class BookDaoImpl implements BookDao{

    @Override
    public String save(BookEntity entity) throws Exception {
        boolean isSaved = CrudUtil.executeUpdate("INSERT INTO table_book VALUES(?,?,?,?,?,?,?,?)",
                                    entity.getBookId(), entity.getTitle(), entity.getAuthor(), entity.getPublisher(), 
                                    entity.getIsbn(), entity.getGenre(), entity.getYearPublished(), entity.getNumberOfCopies());
        return isSaved ? "Success" : "Fail";
    }

    @Override
    public String update(BookEntity entity) throws Exception {
        boolean isUpdated = CrudUtil.executeUpdate("UPDATE table_book SET title=?, author=?, publisher = ?, isbn=?, genre=?, year_published=?, number_of_copies=? WHERE book_id =?",
                entity.getTitle(), entity.getAuthor(),
                entity.getPublisher(), entity.getIsbn(), entity.getGenre(), entity.getYearPublished(), entity.getNumberOfCopies(), entity.getBookId());
        return isUpdated ? "Success" : "Fail";
    }

    @Override
    public String delete(String bookId) throws Exception {
        boolean isDeleted = CrudUtil.executeUpdate("DELETE FROM table_book WHERE book_id =?", bookId);
        return isDeleted ? "Success" : "Fail";
    }

    @Override
    public BookEntity get(String bookId) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_book WHERE book_id = ?", bookId);

        if(rst.next()){
            return new BookEntity(
                    rst.getString("book_id"),
                    rst.getString("title"), 
                    rst.getString("author"), 
                    rst.getString("publisher"), 
                    rst.getString("isbn"), 
                    rst.getString("genre"),
                    rst.getString("year_published"),
                    rst.getInt("number_of_copies"));
        }
        
        return null;
    }

    @Override
    public ArrayList<BookEntity> getAll() throws Exception {

        ArrayList<BookEntity> bookEntity = new ArrayList<>();

        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_book");

        while (rst.next()) {
            BookEntity entity = new BookEntity(
                                    rst.getString("book_id"),
                                    rst.getString("title"), 
                                    rst.getString("author"), 
                                    rst.getString("publisher"), 
                                    rst.getString("isbn"), 
                                    rst.getString("genre"),
                                    rst.getString("year_published"),
                                    rst.getInt("number_of_copies")
                                );
            bookEntity.add(entity);
        }

        return bookEntity;
    }

    @Override
    public BookEntity getLastEnteredId() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM table_book ORDER BY CAST(SUBSTRING(book_id, 2) AS UNSIGNED) DESC LIMIT 1");

        if(rst.next()){
            return new BookEntity(
                    rst.getString("book_id"),
                    rst.getString("title"), 
                    rst.getString("author"), 
                    rst.getString("publisher"), 
                    rst.getString("isbn"), 
                    rst.getString("genre"),
                    rst.getString("year_published"),
                    rst.getInt("number_of_copies"));
        }else{

            ResultSet all = CrudUtil.executeQuery("SELECT * FROM table_book");
            
            if(all.getRow() == 0){
                    return new BookEntity("B000", "", "", "", "", "", "", 0);
            }else{
                return new BookEntity();
            }
        }
    }

}
