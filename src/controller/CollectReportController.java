package controller;

import java.util.ArrayList;

import dto.CollectDto;
import dto.FineDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import session.SessionManager;
import tm.CollectReportTM;

public class CollectReportController extends CommonController{

    private final CollectController COLLECT_CONTROLLER;
    private final FineController FINE_CONTROLLER;

    private final static int rowsPerPage = 15;

    public CollectReportController(Stage primaryStage) {
        super(primaryStage);

        this.COLLECT_CONTROLLER = new CollectController(primaryStage);
        this.FINE_CONTROLLER = new FineController(primaryStage);
    }

    @FXML
    private TableColumn<CollectReportTM, String> colCollectId;

    @FXML
    private TableColumn<CollectReportTM, String> colMemberId;

    @FXML
    private TableColumn<CollectReportTM, String> colBorrowId;

    @FXML
    private TableColumn<CollectReportTM, String> colCollectedDate;

    @FXML
    private TableColumn<CollectReportTM, String> colDescription;

    @FXML
    private Label lblUserName;

    @FXML
    private TableView<CollectReportTM> tblCollectReport = createCollectReportTable();

    @FXML
    private Pagination tblCollectReportPagination;

    public void initialize(){
        setBackButton(); 
        this.lblUserName.setText("Welcome " + SessionManager.getInstance().getUserName());

        tblCollectReportPagination.setPageFactory(pageIndex -> loadReportTable(pageIndex));
    }

    private TableView<CollectReportTM> createCollectReportTable(){

        TableView<CollectReportTM> tblCollectReport = new TableView<>();

        colCollectId = new TableColumn<>("Collect ID");
        colCollectId.setCellValueFactory(new PropertyValueFactory<>("collectId"));
        colCollectId.setPrefWidth(120);
        colCollectId.setStyle("-fx-alignment: CENTER;");

        colMemberId = new TableColumn<>("Member ID");
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colMemberId.setPrefWidth(120);
        colMemberId.setStyle("-fx-alignment: CENTER;");

        colBorrowId = new TableColumn<>("Borrow ID");
        colBorrowId.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        colBorrowId.setPrefWidth(120);
        colBorrowId.setStyle("-fx-alignment: CENTER;");

        colCollectedDate = new TableColumn<>("Collected Date");
        colCollectedDate.setCellValueFactory(new PropertyValueFactory<>("collectedDate"));
        colCollectedDate.setPrefWidth(120);
        colCollectedDate.setStyle("-fx-alignment: CENTER;");

        colDescription = new TableColumn<>("Description");
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDescription.setStyle("-fx-alignment: LEFT; -fx-wrap-text: true;");

        tblCollectReport.getColumns().addAll(colCollectId, colMemberId, colBorrowId, colCollectedDate, colDescription);

        return tblCollectReport;
    }

    private Node loadReportTable(int pageIndex){

        try {
            ArrayList<CollectDto> collectDto = this.COLLECT_CONTROLLER.getAllCollects();

            int fromIndex = pageIndex * rowsPerPage;
            int toIndex = Math.min(fromIndex + rowsPerPage, collectDto.size()); 

            tblCollectReportPagination.setPageCount((int) Math.ceil((double) collectDto.size() / rowsPerPage));

            if (fromIndex >= collectDto.size()) {
                return tblCollectReport;
            }

            ObservableList<CollectReportTM> collectReportTMList = FXCollections.observableArrayList();

            for (CollectDto dto : collectDto.subList(fromIndex, toIndex)) { 

                FineDto fineDto = this.FINE_CONTROLLER.getAccordingToBorrowId(dto.getBorrowId());

                String description = "The books were returned within the due period.";
                if(fineDto.getFineId() != null){
                    description = "The books were not returned within the due period, Rs. " + String.format("%.2f",fineDto.getAmount()) + " was paid. ";
                }

                CollectReportTM collectReportTM = new CollectReportTM(
                                            dto.getCollectId(),
                                            dto.getBorrowId(),
                                            dto.getMemberId(),  
                                            dto.getCollectedDate(),
                                            description
                                    );

                collectReportTMList.add(collectReportTM);
                
            }

            tblCollectReport.setItems(collectReportTMList);

        } catch (Exception e) {  
            showMessage("Error", "Error occurred loading table!"); 
        }

        return tblCollectReport;
    }

}