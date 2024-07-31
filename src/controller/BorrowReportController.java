package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dto.BorrowDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import session.SessionManager;
import tm.BorrowReportTM;

public class BorrowReportController extends CommonController{

    private final BorrowController BORROW_CONTROLLER;

    private final static int rowsPerPage = 15;

    public BorrowReportController(Stage primaryStage) {
        super(primaryStage);

        this.BORROW_CONTROLLER = new BorrowController(primaryStage);
    }

    @FXML
    private TableColumn<BorrowReportTM, String> colBorrowId;

    @FXML
    private TableColumn<BorrowReportTM, String> colMemberId;

    @FXML
    private TableColumn<BorrowReportTM, String> colIssueDate;

    @FXML
    private TableColumn<BorrowReportTM, String> colReturnDate;

    @FXML
    private TableColumn<BorrowReportTM, String> colStatus;

    @FXML
    private Label lblUserName;

    @FXML
    private TableView<BorrowReportTM> tblBorrowReport = createBorrowReportTable();

    @FXML
    private Pagination tblBorrowReportPagination;

    public void initialize(){ 
        setBackButton();
        this.lblUserName.setText("Welcome " + SessionManager.getInstance().getUserName());

        tblBorrowReportPagination.setPageFactory(pageIndex -> loadReportTable(pageIndex));
    }

    private TableView<BorrowReportTM> createBorrowReportTable(){

        TableView<BorrowReportTM> tblBorrowReport = new TableView<>();

        colBorrowId = new TableColumn<>("Borrow ID");
        colBorrowId.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        colBorrowId.setPrefWidth(110);
        colBorrowId.setStyle("-fx-alignment: CENTER;");

        colMemberId = new TableColumn<>("Member ID");
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colMemberId.setPrefWidth(110);
        colMemberId.setStyle("-fx-alignment: CENTER;");

        colIssueDate = new TableColumn<>("Issue Date");
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        colIssueDate.setPrefWidth(130);
        colIssueDate.setStyle("-fx-alignment: CENTER;");

        colReturnDate = new TableColumn<>("Return Date");
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colReturnDate.setPrefWidth(130);
        colReturnDate.setStyle("-fx-alignment: CENTER;");

        colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setPrefWidth(130);
        colStatus.setStyle("-fx-alignment: CENTER;");

        tblBorrowReport.getColumns().addAll(colBorrowId, colMemberId, colIssueDate, colReturnDate, colStatus);

        return tblBorrowReport;
    }

    private Node loadReportTable(int pageIndex){

        try {
            ArrayList<BorrowDto> borrowDto = this.BORROW_CONTROLLER.getAllBorrows();

            int fromIndex = pageIndex * rowsPerPage;
            int toIndex = Math.min(fromIndex + rowsPerPage, borrowDto.size()); 

            tblBorrowReportPagination.setPageCount((int) Math.ceil((double) borrowDto.size() / rowsPerPage));

            if (fromIndex >= borrowDto.size()) {
                return tblBorrowReport;
            }

            ObservableList<BorrowReportTM> borrowReportTMList = FXCollections.observableArrayList();

            for (BorrowDto dto : borrowDto.subList(fromIndex, toIndex)) { 

                if(dto.getBorrowId() != null){

                    String status = (dto.getStatus() == 0) ? "Active" : "Collected";
                    
                    BorrowReportTM borrowReportTM = new BorrowReportTM(
                                            dto.getBorrowId(),
                                            dto.getMemberId(), 
                                            dto.getIssueDate(), 
                                            dto.getReturnDate(),
                                            status
                                    );

                    borrowReportTMList.add(borrowReportTM);
                }
                
            }

            tblBorrowReport.setItems(borrowReportTMList);
            customTableColumn();

        } catch (Exception e) {  
            showMessage("Error", "Error occurred loading table!"); 
        }

        return tblBorrowReport;
    }

    private void customTableColumn(){

        colReturnDate.setCellFactory((Callback<TableColumn<BorrowReportTM, String>, TableCell<BorrowReportTM, String>>) new Callback<TableColumn<BorrowReportTM, String>, TableCell<BorrowReportTM, String>>() {
            @Override
            public TableCell<BorrowReportTM, String> call(TableColumn<BorrowReportTM, String> param) {
                return new TableCell<BorrowReportTM, String>() {
                    
                    private final Label label = new Label();
                    
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        }else {
                            LocalDate returnDate = LocalDate.parse(item, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            LocalDate collectedDate = LocalDate.parse(getCurrentDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                            if(returnDate.isBefore(collectedDate) || returnDate.isEqual(collectedDate)){
                                label.setText(item);
                                label.setPrefWidth(80);
                                label.setAlignment(Pos.CENTER); 
                                label.setStyle("-fx-background-radius: 10; -fx-padding: 1;");
                                label.setStyle(label.getStyle() + " -fx-background-color: #ffb9b9;");
                                setGraphic(label);
                            }else{
                                setText(item);
                            }
                        }
                    }
                };
            }
        });

        colStatus.setCellFactory((Callback<TableColumn<BorrowReportTM, String>, TableCell<BorrowReportTM, String>>) new Callback<TableColumn<BorrowReportTM, String>, TableCell<BorrowReportTM, String>>() {
            @Override
            public TableCell<BorrowReportTM, String> call(TableColumn<BorrowReportTM, String> param) {
                return new TableCell<BorrowReportTM, String>() {
                    
                    private final Label label = new Label();
                    
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        }else {
                            label.setText(item);
                            label.setPrefWidth(70);
                            label.setAlignment(Pos.CENTER); 
                            label.setStyle("-fx-background-radius: 10; -fx-padding: 1;");
                            if ("Active".equals(item)) {
                                label.setStyle(label.getStyle() + " -fx-background-color: #a0eeaf;");
                            } else {
                                label.setStyle(label.getStyle() + " -fx-background-color: #eecfa0;");
                            }
                            setGraphic(label);
                        }
                    }
                };
            }
        });
    }

}
