package service.custom.impl;

import java.util.ArrayList;

import cache.CacheManager;
import dao.DaoFactory;
import dao.custom.BookDao;
import dto.BookDto;
import entity.BookEntity;
import service.custom.BookService;

public class BookServiceImpl implements BookService{

    private BookDao bookDao = (BookDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.BOOK);

    private CacheManager cacheManager = CacheManager.getInstance();

    @Override
    public String save(BookDto bookDto) throws Exception {
        BookEntity entity = getBookEntity(bookDto);
        String response = bookDao.save(entity);

        if(response == "Success"){
            cacheManager.remove("getAllBooks");
        }

        return response;
    }

    @Override
    public String update(BookDto bookDto) throws Exception {
        BookEntity entity = getBookEntity(bookDto);
        String response = bookDao.update(entity);

        if(response == "Success"){
            cacheManager.remove("getAllBooks");
        }

        return response;
    }

    @Override
    public String delete(String bookId) throws Exception {
        return bookDao.delete(bookId);
    }

    @Override
    public BookDto get(String bookId) throws Exception {
        BookEntity entity = bookDao.get(bookId);
        return getBookDto(entity);
    }

    @Override
    public ArrayList<BookDto> getAll() throws Exception { 

        ArrayList<BookDto> bookDtos = (ArrayList<BookDto>) cacheManager.get("getAllBooks");

        if (bookDtos == null) {

            bookDtos = new ArrayList<>();

            ArrayList<BookEntity> bookEntitys = bookDao.getAll();

            for (BookEntity bookEntity : bookEntitys) {
                BookDto dto = getBookDto(bookEntity);
                bookDtos.add(dto);
            }

            cacheManager.put("getAllBooks", bookDtos);
        }

        return bookDtos;
    }

    private BookDto getBookDto(BookEntity entity){

        BookDto bookEntityDto = new BookDto(
                    entity.getBookId(), entity.getTitle(), entity.getAuthor(),
                    entity.getPublisher(), entity.getIsbn(), entity.getGenre(), 
                    entity.getYearPublished(), entity.getNumberOfCopies());

        return bookEntityDto;

    }
    
    private BookEntity getBookEntity(BookDto dto){

        BookEntity entity = new BookEntity(
                    dto.getBookId(), dto.getTitle(), dto.getAuthor(),
                    dto.getPublisher(), dto.getIsbn(), dto.getGenre(), 
                    dto.getYearPublished(), dto.getNumberOfCopies());

         return entity;
    }

    @Override
    public BookDto getLastEnteredId() throws Exception {
        BookEntity entity = bookDao.getLastEnteredId();
        return getBookDto(entity);
    }

}
