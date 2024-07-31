package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.jfoenix.controls.JFXButton;

import dto.BookDto;
import dto.BorrowDetailDto;
import dto.BorrowDto;
import dto.MemberDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.BorrowService;
import session.SessionManager;
import tm.BorrowDetailsTM;

public class BorrowController extends CommonController{

    BorrowService borrowService = (BorrowService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.BORROW);

    private final MemberController MEMBER_CONTROLLER;
    private final BookController BOOK_CONTROLLER;
    private ArrayList<BorrowDetailDto> borrowDetailDtos = new ArrayList<>();
    private BookDto bookDto;

    private ObservableList<BorrowDetailsTM> borrowDetailsTMList = FXCollections.observableArrayList();

    @FXML
    private JFXButton btnAddToTable;

    @FXML
    private JFXButton btnBookSearch;

    @FXML
    private JFXButton btnHandOver;

    @FXML
    private JFXButton btnMemberSearch;

    @FXML
    private Label lblBookData;

    @FXML
    private Label lblBorrowData;

    @FXML
    private Label lblMemberData;

    @FXML
    private TextField txtBookId;

    @FXML
    private TextField txtBorrowId;

    @FXML
    private TextField txtDueDate;

    @FXML
    private TextField txtIssueDate;

    @FXML
    private TextField txtLibrarianId;

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtReturnDate;

    @FXML
    private TableView<BorrowDetailsTM> tblBorrowDetails;

    @FXML
    private TableColumn<BorrowDetailsTM, String> colAuthor;

    @FXML
    private TableColumn<BorrowDetailsTM, String> colBookId;

    @FXML
    private TableColumn<BorrowDetailsTM, String> colBookName;

    @FXML
    private TableColumn<BorrowDetailsTM, Button> colDelete;

    @FXML
    private TableColumn<BorrowDetailsTM, String> colPublisher;

    @FXML
    private Label lblUserName;

    public BorrowController(Stage primaryStage) { 
        super(primaryStage);

        this.MEMBER_CONTROLLER = new MemberController(primaryStage);
        this.BOOK_CONTROLLER = new BookController(primaryStage);
    }

    public void initialize(){ 
        this.lblUserName.setText("Welcome " + SessionManager.getInstance().getUserName());

        setNewBorrowId();

        txtLibrarianId.setText(SessionManager.getInstance().getUserId());
        txtIssueDate.setText(getCurrentDate());
        txtReturnDate.setText(setReturnDate("0"));
        txtDueDate.textProperty().addListener((observable, oldValue, newValue) -> {
            txtReturnDate.setText(setReturnDate(newValue));
        });

        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
    }

    @FXML
    void btnAddToTableOnAction(ActionEvent event) {

        if(this.bookDto.getNumberOfCopies() > 0){

            BorrowDetailDto borrowDetailDto = new BorrowDetailDto();
            borrowDetailDto.setBorrowId(txtBorrowId.getText());
            borrowDetailDto.setBookId(this.bookDto.getBookId());

            borrowDetailDtos.add(borrowDetailDto);

            setDetailsTable();
        }else{
            showMessage("Error", "Book are not in stock !"); 
        }

    }

    @FXML
    void btnBookSearchOnAction(ActionEvent event) {

        try {
            String bookId = txtBookId.getText();
            this.bookDto = this.BOOK_CONTROLLER.getBook(bookId);
            if(this.bookDto != null){
                lblBookData.setText(this.bookDto.getTitle() + " | " + this.bookDto.getGenre() + " | " + this.bookDto.getAuthor());
            } else {
                showMessage("Error", "Book not found !");
            }
        } catch (Exception e) {
            showMessage("Error", "Please enter a valid Book ID !");
        }
    }

    @FXML
    void btnHandOverOnAction(ActionEvent event) {

        String borrowId = txtBorrowId.getText();
        String librarianId = txtLibrarianId.getText();
        String memberId = txtMemberId.getText();
        String issueDate = txtIssueDate.getText();
        int dueDate = Integer.parseInt(txtDueDate.getText());
        String returnDate = txtReturnDate.getText();

        if(borrowId == null || borrowId == ""){
            showMessage("Error", "Please enter a valid Borrow ID !");
        }else if(memberId == null || memberId == ""){
            showMessage("Error", "Please enter a valid Member ID !");
        }else if(borrowDetailDtos.isEmpty()){
            showMessage("Error", "Please add one or more Books !");
        }else{

            try{
                BorrowDto borrowDto = new BorrowDto();

                borrowDto.setBorrowId(borrowId);
                borrowDto.setMemberId(memberId);
                borrowDto.setLibrarianId(librarianId);
                borrowDto.setIssueDate(issueDate);
                borrowDto.setDueDate(dueDate);
                borrowDto.setReturnDate(returnDate);

                borrowDto.setBorrowDetailDtos(borrowDetailDtos);

                String response = handOver(borrowDto);

                if(response == "Successfully saved !"){
                    showMessage("Success", response);
                    setNewBorrowId();
                    clearTextFields();
                }else{
                    showMessage("Error", response);
                }

            } catch (Exception e) {
                showMessage("Error", "Error ocurrs saving !");
            }
        }
            
    }

    @FXML
    void btnMemberSearchOnAction(ActionEvent event) {

        try {
            String memberId = txtMemberId.getText();
            MemberDto memberDto = this.MEMBER_CONTROLLER.getMember(memberId);
            if(memberDto != null){
                lblMemberData.setText(memberDto.getFirstName() + " " + memberDto.getLastName() + " | " + memberDto.getAddress());
            } else {
                showMessage("Error", "Member not found !");
            }
        } catch (Exception e) {
            showMessage("Error", "Please enter a valid Member ID !");
        }

    }

    private String setReturnDate(String dueDate){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
          
        Calendar cal = Calendar.getInstance();  

        String returnDate = getCurrentDate();

        try{  
            if(dueDate != null && !dueDate.isEmpty()){
                cal.setTime(sdf.parse(getCurrentDate())); 
                cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(dueDate)); 
                returnDate = sdf.format(cal.getTime());
            }            
        }catch(ParseException e){  
            showMessage("Error", "Return Date is loding faild !");  
        }      

        return returnDate;
    }

    private void setDetailsTable(){

        deleteBorrowDetails(this.bookDto.getBookId());
            
        ButtonController deleteButton = createButton("delete", this.bookDto.getBookId(), this::deleteBorrowDetails);
        
        BorrowDetailsTM borrowDetailsTM = new BorrowDetailsTM(
                                        this.bookDto.getBookId(),
                                        this.bookDto.getTitle(), 
                                        this.bookDto.getAuthor(), 
                                        this.bookDto.getPublisher(),
                                        deleteButton
                                    );

        borrowDetailsTMList.add(borrowDetailsTM);

        tblBorrowDetails.setItems(borrowDetailsTMList);
    }

    private void deleteBorrowDetails(String bookId){
        
        ObservableList<BorrowDetailsTM> borrowDetailsTMList = tblBorrowDetails.getItems();
        borrowDetailsTMList.removeIf(item -> item.getBookId().equals(bookId));
    }

    public BorrowDto getBorrowData(String borrowId) throws Exception{ 
        return borrowService.get(borrowId);
    }

    public ArrayList<BorrowDto> getAllBorrows() throws Exception{
        return borrowService.getAll();
    }

    public String handOver(BorrowDto borrowDto)  throws Exception{
        return borrowService.handOver(borrowDto);
    }

    private void setNewBorrowId(){
        try {
            BorrowDto lastBorrowDto =  getLastEnteredId();
            if(lastBorrowDto != null){ 
                txtBorrowId.setText(createNewId(lastBorrowDto.getBorrowId()));
            }
        } catch (Exception e) { 
            showMessage("Error", "Error occurs creating New Borrow ID !"); 
        }
    }

    public BorrowDto getLastEnteredId() throws Exception{ 
        return borrowService.getLastEnteredId();
    }

    private void clearTextFields(){

        txtMemberId.setText("");
        lblMemberData.setText("");
        txtBookId.setText("");
        lblBookData.setText("");
        txtDueDate.setText("0");
        txtReturnDate.setText(setReturnDate("0"));
        tblBorrowDetails.getItems().clear();
    }

}
