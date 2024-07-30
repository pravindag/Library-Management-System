package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import dto.BookDto;
import dto.GenreDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.BookService;
import session.SessionManager;
import tm.BookTM;

public class BookController extends CommonController{

    BookService bookService = (BookService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.BOOK);

    private final GenreController GENRE_CONTROLLER;
    private final Stage PRIMARY_STAGE;

    private BookDto setBookDto = new BookDto();

    public BookController(Stage primaryStage) {
        super(primaryStage);

        this.GENRE_CONTROLLER = new GenreController(primaryStage);
        this.PRIMARY_STAGE = primaryStage;
    }

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnUpdateGenre;

    @FXML
    private TableColumn<BookTM, String> colAuthor;

    @FXML
    private TableColumn<BookTM, String> colBookId;

    @FXML
    private TableColumn<BookTM, String> colBookName;

    @FXML
    private TableColumn<BookTM, String> colDelete;

    @FXML
    private TableColumn<BookTM, Integer> colNumberOfCopies;

    @FXML
    private TableColumn<BookTM, String> colPublisher;

    @FXML
    private TableColumn<BookTM, String> colUpdate;

    @FXML
    private Label lblUserName;

    @FXML
    private TableView<BookTM> tblBookDetails;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtBookId;

    @FXML
    private ComboBox<String> txtGenre;

    @FXML
    private TextField txtIsbn;

    @FXML
    private TextField txtPublisher;

    @FXML
    private TextField txtNumberOfCopies;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtYearOfPublish;

    public void initialize(){ 
        this.lblUserName.setText("Welcome " + SessionManager.getInstance().getUserName());

        setNewBookId();

        btnUpdate.setDisable(true);

        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colNumberOfCopies.setCellValueFactory(new PropertyValueFactory<>("numberOfCopies"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("btnUpdate"));

        loadBookTable(); 
        
        setGenreDropDown();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        Boolean response = setAndValidateFields();

        if(response == true){
            try {
                String saveResponse = saveBook(this.setBookDto);

                if(saveResponse == "Success"){
                    showMessage("Success", "Successfully saved !");
                    setNewBookId();
                    clearTextFields();
                    loadBookTable();
                }else{
                    showMessage("Error", "Error occured saving !");
                }

            } catch (Exception e) {
                showMessage("Error", "Error occured !");
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        Boolean response = setAndValidateFields();

        if(response == true){
            try {
                String saveResponse = updateBook(this.setBookDto);

                if(saveResponse == "Success"){
                    showMessage("Success", "Successfully updated !");
                    setNewBookId();
                    clearTextFields();
                    loadBookTable();
                    btnAdd.setDisable(false);
                    btnUpdate.setDisable(true);
                }else{
                    showMessage("Error", "Error occured updating !");
                }

            } catch (Exception e) {
                showMessage("Error", "Error occured !");
            }
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        backToMainScene();
    }

    @FXML
    void btnUpdateGenreOnAction(ActionEvent event) {

        try {
                        
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Genre.fxml"));
            loader.setController(new GenreController(this.PRIMARY_STAGE));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            Image imgBook = new Image("/icons/book-icon.png");
            stage.getIcons().add(imgBook);

            stage.setTitle("  Library Management System");
            stage.setX(500);
            stage.setY(110);
            stage.show();

        } catch (IOException e) {
            showMessage("Error", "Failed to load the Genre view.");
        }
    }

    public BookDto getBook(String bookId) throws Exception{
        return bookService.get(bookId);
    }

    private void setNewBookId(){
        try {
            BookDto lastBookDto =  getLastEnteredId();
            if(lastBookDto != null){ 
                txtBookId.setText(createNewId(lastBookDto.getBookId()));
            }
        } catch (Exception e) { 
            showMessage("Error", "Error occured creating New Book ID !"); 
        }
    }

    public void setGenreDropDown(){

        try {
            ArrayList<GenreDto> genreDto = this.GENRE_CONTROLLER.getAllGenres();

            txtGenre.getItems().addAll(genreDto.stream().map(GenreDto::getGenreName).toList());

        } catch (Exception e) { 
            showMessage("Error", "Error occured loding Genre !"); 
        }
    }

    private BookDto getLastEnteredId() throws Exception{ 
        return bookService.getLastEnteredId();
    }

    private void loadBookTable(){

        try {
            ArrayList<BookDto> bookDto = getAllBooks(); 

            ObservableList<BookTM> bookTMList = FXCollections.observableArrayList();

            for (BookDto dto : bookDto) { 
                
                ButtonController deleteButton = createButton("delete", dto.getBookId(), this::delete);
                ButtonController updateButton = createButton("update", dto.getBookId(), this::setUpdateDetails);
                
                BookTM bookTM = new BookTM(
                                        dto.getBookId(),
                                        dto.getTitle(), 
                                        dto.getAuthor(), 
                                        dto.getPublisher(),
                                        dto.getNumberOfCopies(),
                                        deleteButton,
                                        updateButton
                                );

                bookTMList.add(bookTM);
                
            }

            tblBookDetails.setItems(bookTMList);
        } catch (Exception e) {  
            showMessage("Error", "Error occured loding table !"); 
        }
    }

    public ArrayList<BookDto> getAllBooks() throws Exception{
        return bookService.getAll();
    }

    private Boolean setAndValidateFields(){

        String title = txtTitle.getText();
        String genre = txtGenre.getValue();
        String isbn = txtIsbn.getText();
        int numberOfCopies = (txtNumberOfCopies.getText() != null && txtNumberOfCopies.getText() != "") ? Integer.parseInt(txtNumberOfCopies.getText()) : 0;
        Boolean response = true;

        if(title == null || title == ""){
            showMessage("Error", "Please enter a valid Title !");
            response = false;
        }else if(genre == null || genre == ""){
            showMessage("Error", "Please enter a valid Genre !");
            response = false;
        }else if(isbn == null || isbn == ""){
            showMessage("Error", "Please enter a valid ISBN !");
            response = false;
        }else{

            this.setBookDto.setBookId(txtBookId.getText());
            this.setBookDto.setTitle(title);
            this.setBookDto.setAuthor(txtAuthor.getText());
            this.setBookDto.setGenre(genre);
            this.setBookDto.setPublisher(txtPublisher.getText());
            this.setBookDto.setIsbn(isbn);
            this.setBookDto.setYearPublished(txtYearOfPublish.getText());
            this.setBookDto.setNumberOfCopies(numberOfCopies);
            
        }
        return response;
    }

    public String saveBook(BookDto bookDto) throws Exception{ 
        return bookService.save(bookDto);
    }

    public String updateBook(BookDto bookDto) throws Exception{ 
        return bookService.update(bookDto);
    }

    private void delete(String bookId){
        
        try {
            String response = deleteBook(bookId);
            if(response == "Success"){
                showMessage("Success", "Successfully deleted !");
                loadBookTable();
            }else{
                showMessage("Error", "Error occured deleting !");
            }
        } catch (Exception e) {
            showMessage("Error", "Error occured deleting !");
        }
    }

    private void setUpdateDetails(String bookId){

        btnAdd.setDisable(true);
        btnUpdate.setDisable(false);

        try {

            BookDto bookDto = new BookDto();

            bookDto = getBook(bookId);

            txtBookId.setText(bookDto.getBookId());
            txtTitle.setText(bookDto.getTitle());
            txtAuthor.setText(bookDto.getAuthor());
            txtGenre.setValue(bookDto.getGenre());
            txtPublisher.setText(bookDto.getPublisher());
            txtIsbn.setText(bookDto.getIsbn());
            txtYearOfPublish.setText(bookDto.getYearPublished());
            txtNumberOfCopies.setText(String.valueOf(bookDto.getNumberOfCopies()));

        } catch (Exception e) {
            showMessage("Error", "Error occured loading !");
        }
    }

    private String deleteBook(String bookId) throws Exception{
        return bookService.delete(bookId);
    }

    private void clearTextFields(){

        txtTitle.setText("");
        txtAuthor.setText("");
        txtGenre.setValue("");
        txtPublisher.setText("");
        txtIsbn.setText("");
        txtYearOfPublish.setText("");
        txtNumberOfCopies.setText("");
    }

}
