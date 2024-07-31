package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import dto.BookDto;
import dto.BorrowDetailDto;
import dto.BorrowDto;
import dto.CollectDto;
import dto.FineDto;
import dto.MemberDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.CollectService;
import session.SessionManager;
import tm.BorrowDetailsTM;

public class CollectController extends CommonController{

    CollectService collectService = (CollectService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.COLLECT);

    private final BorrowController BORROW_CONTROLLER;
    private final BookController BOOK_CONTROLLER;
    private final MemberController MEMBER_CONTROLLER;
    private final FineController FINE_CONTROLLER;

    private BorrowDto borrowDto;
    private MemberDto memberDto;

    private final Stage PRIMARY_STAGE;

    private ArrayList<BorrowDetailDto> borrowDetailDto;

    public CollectController(Stage primaryStage) {
        super(primaryStage);

        this.BORROW_CONTROLLER = new BorrowController(primaryStage);
        this.BOOK_CONTROLLER = new BookController(primaryStage);
        this.MEMBER_CONTROLLER = new MemberController(primaryStage);
        this.FINE_CONTROLLER = new FineController(primaryStage);

        this.PRIMARY_STAGE = primaryStage;
    }

    @FXML
    private JFXButton btnBorrowSearch;

    @FXML
    private JFXButton btnCollect;

    @FXML
    private TableColumn<BorrowDetailsTM, String> colAuthor;

    @FXML
    private TableColumn<BorrowDetailsTM, String> colBookId;

    @FXML
    private TableColumn<BorrowDetailsTM, String> colBookName;

    @FXML
    private TableColumn<BorrowDetailsTM, String> colPublisher;

    @FXML
    private Label lblMemberData;

    @FXML
    private Label lblUserName;

    @FXML
    private TableView<BorrowDetailsTM> tblBorrowDetails;

    @FXML
    private TextField txtBorrowId;

    @FXML
    private TextField txtCollectId;

    @FXML
    private TextField txtCollectedDate;

    @FXML
    private TextField txtReturnDate;

    @FXML
    private TextField txtMemberId;

    public void initialize(){ 
        setBackButton();
        this.lblUserName.setText("Welcome " + SessionManager.getInstance().getUserName());

        setNewCollectId();

        txtCollectedDate.setText(getCurrentDate());

        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    }

    @FXML
    void btnBorrowSearchOnAction(ActionEvent event) {
        
        try {
            this.borrowDto = this.BORROW_CONTROLLER.getBorrowData(txtBorrowId.getText());

            if(this.borrowDto != null){

                txtReturnDate.setText(this.borrowDto.getReturnDate());

                this.borrowDetailDto = this.borrowDto.getBorrowDetailDtos();

                if(this.borrowDetailDto != null){

                    ObservableList<BorrowDetailsTM> borrowDetailsTMList = FXCollections.observableArrayList();

                    for (BorrowDetailDto dto : this.borrowDetailDto) {

                        BookDto bookDto = this.BOOK_CONTROLLER.getBook(dto.getBookId());
                        
                        BorrowDetailsTM borrowDetailsTM = new BorrowDetailsTM(
                                                                bookDto.getBookId(),
                                                                bookDto.getTitle(), 
                                                                bookDto.getAuthor(), 
                                                                bookDto.getPublisher()
                                                            );

                        borrowDetailsTMList.add(borrowDetailsTM);
                        
                    }

                    tblBorrowDetails.setItems(borrowDetailsTMList);
                }

                this.memberDto = this.MEMBER_CONTROLLER.getMember(this.borrowDto.getMemberId());

                if(this.memberDto != null){
                    txtMemberId.setText(this.memberDto.getMemberId());
                    lblMemberData.setText(this.memberDto.getFirstName() + " " + this.memberDto.getLastName() + " | " + this.memberDto.getAddress());
                }

            }

        } catch (Exception e) { System.out.println(e.getMessage());
            showMessage("Error", "Please enter a valid Borrow ID !");
        }
    }

    @FXML
    void btnCollectOnAction(ActionEvent event) {

        String collectId = txtCollectId.getText();
        String collectedDate = txtCollectedDate.getText();

        if(collectId == null || collectId == ""){
            showMessage("Error", "Please enter a valid Collect ID !");
        }else if(this.borrowDto.getBorrowId() == null || this.borrowDto.getBorrowId() == ""){
            showMessage("Error", "Please enter a valid Borrow ID !");
        }else if(collectedDate == null || collectedDate == ""){
            showMessage("Error", "Please enter a valid Collected Date !");
        }else{
            try {
                CollectDto collectDtoAccordingToBorrowId = getAccordingToBorrowId(this.borrowDto.getBorrowId());
                FineDto fineDto = this.FINE_CONTROLLER.getAccordingToBorrowId(this.borrowDto.getBorrowId());

                if(collectDtoAccordingToBorrowId.getCollectId() != null){

                    showMessage("Error", "This Borrow Id has already entered !");

                }else if((checkReturnDate() == true) && (collectDtoAccordingToBorrowId.getCollectId() == null) && (fineDto.getFineId() == null)){

                    try {
                        
                        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Fine.fxml"));
                        loader.setController(new FineController(this.PRIMARY_STAGE, this.borrowDto.getBorrowId(), this.memberDto.getMemberId()));
                        Parent root = loader.load();

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        
                        Image imgBook = new Image("/icons/book-icon.png");
                        stage.getIcons().add(imgBook);

                        stage.setTitle("  Library Management System");
                        stage.setX(600);
                        stage.setY(220);
                        stage.show();

                    } catch (IOException e) {
                        showMessage("Error", "Failed to load the Fine view.");
                    }

                }else{
                    try {

                        CollectDto collectDto = new CollectDto();

                        collectDto.setCollectId(collectId);
                        collectDto.setMemberId(this.memberDto.getMemberId());
                        collectDto.setBorrowId(this.borrowDto.getBorrowId());
                        collectDto.setCollectedDate(collectedDate);

                        collectDto.setBorrowDetailDtos(this.borrowDetailDto);

                        String response = collect(collectDto);

                        if(response == "Successfully saved !"){
                            showMessage("Success", response);
                            setNewCollectId();
                            clearTextFields();
                        }else{
                            showMessage("Error", response);
                        }
                    } catch (Exception e) {
                        showMessage("Error", "Failed to save collect data.");
                    }
                    
                }

            } catch (Exception e) { 
                showMessage("Error", "Error occurred !");
            }
        }
    }

    private boolean checkReturnDate(){

        LocalDate returnDate = LocalDate.parse(this.borrowDto.getReturnDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate collectedDate = LocalDate.parse(txtCollectedDate.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return (returnDate.isBefore(collectedDate)) ? true : false;
    }

    public String collect(CollectDto collectDto) throws Exception{
        return collectService.collect(collectDto);
    }

    private void setNewCollectId(){
        try {
            CollectDto lastCollectDto =  getLastEnteredId();
            if(lastCollectDto != null){ 
                txtCollectId.setText(createNewId(lastCollectDto.getCollectId()));
            }
        } catch (Exception e) { 
            showMessage("Error", "Error occurs creating New Collect ID !"); 
        }
    }

    public CollectDto getLastEnteredId() throws Exception{ 
        return collectService.getLastEnteredId();
    }

    public CollectDto getAccordingToBorrowId(String borrowId) throws Exception{ 
        return collectService.getAccordingToBorrowId(borrowId);
    }

    public ArrayList<CollectDto> getAllCollects() throws Exception{
        return collectService.getAll();
    }

    private void clearTextFields(){

        txtBorrowId.setText("");
        txtMemberId.setText("");
        lblMemberData.setText("");
        tblBorrowDetails.getItems().clear();
    }

}
