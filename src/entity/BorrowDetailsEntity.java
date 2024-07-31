package entity;

public class BorrowDetailsEntity {

    private String borrowId;
    private String bookId;
    
    public BorrowDetailsEntity() {
    }

    public BorrowDetailsEntity(String borrowId, String bookId) {
        this.borrowId = borrowId;
        this.bookId = bookId;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    
}
