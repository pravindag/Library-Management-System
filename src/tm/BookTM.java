package tm;

import javafx.scene.control.Button;

public class BookTM {

    private String bookId;
    private String bookName;
    private String author;
    private int numberOfCopies;
    private String publisher;
    private Button btnDelete;
    private Button btnUpdate;
    
    public BookTM() {
    }

    public BookTM(String bookId, String bookName, String author, String publisher, int numberOfCopies, Button btnDelete, Button btnUpdate) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.numberOfCopies = numberOfCopies;
        this.btnDelete = btnDelete;
        this.btnUpdate = btnUpdate;
    }

    public BookTM(String bookId, String bookName, String author, String publisher, int numberOfCopies) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.numberOfCopies = numberOfCopies;
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

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public Button getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(Button btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    
}
