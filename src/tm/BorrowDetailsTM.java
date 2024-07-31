package tm;

import javafx.scene.control.Button;

public class BorrowDetailsTM {

    private String bookId;
    private String bookName;
    private String author;
    private String publisher;
    private Button btnDelete;
    
    public BorrowDetailsTM() {
    }

    public BorrowDetailsTM(String bookId, String bookName, String author, String publisher, Button btnDelete) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.btnDelete = btnDelete;
    }

    public BorrowDetailsTM(String bookId, String bookName, String author, String publisher) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    
}
