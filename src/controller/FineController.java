package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXButton;

import dto.FineDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.FineService;

public class FineController extends CommonController{

    FineService fineService = (FineService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.FINE);

    private final String BORROW_ID;
    private final String MEMBER_ID;

    public FineController(Stage primaryStage){
        super(primaryStage);

        this.BORROW_ID = "";
        this.MEMBER_ID = "";
    }

    public FineController(Stage primaryStage, String borrowId, String memberId){
        super(primaryStage);
        
        this.BORROW_ID = borrowId;
        this.MEMBER_ID = memberId;
    }

    @FXML
    private JFXButton btnFineCollect;

    @FXML
    private Label lblUserName;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtBorrowId;

    @FXML
    private TextField txtFineId;

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtPaidDate;

    public void initialize(){ 
        txtBorrowId.setText(this.BORROW_ID);
        txtMemberId.setText(this.MEMBER_ID);

        setNewFineId();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime currentDate = LocalDateTime.now();  
        txtPaidDate.setText(dtf.format(currentDate)); 
    }

    @FXML
    void btnFineCollectOnAction(ActionEvent event) {

        String fineId = txtFineId.getText();
        String borrowId = txtBorrowId.getText();
        String paidDate = txtPaidDate.getText();
        Double amount = Double.parseDouble(txtAmount.getText());

        if(fineId == null || fineId == ""){
            showMessage("Error", "Please enter a valid Fine ID !");
        }else if(amount == null || amount <= 0){
            showMessage("Error", "Please enter a valid Amount !");
        }else{

            FineDto fineDto = new FineDto();

            fineDto.setFineId(fineId);
            fineDto.setBorrowId(borrowId);
            fineDto.setPaidDate(paidDate);
            fineDto.setAmount(amount);

            try {
                String response = saveFine(fineDto);

                if(response == "Success"){
                    showMessage("Success", "Successfully saved !");
                }else{
                    showMessage("Error", "Error occurs when saving !"); 
                }

            } catch (Exception e) {
                showMessage("Error", "Error occurs !");
            }
        }

    }

    public FineDto getAccordingToBorrowId(String borrowId) throws Exception{ 
        return fineService.getAccordingToBorrowId(borrowId);
    }

    public String saveFine(FineDto fineDto) throws Exception{ 
        return fineService.save(fineDto);
    }

    private void setNewFineId(){
        try {
            FineDto lastFineDto =  getLastEnteredId();
            if(lastFineDto != null){ 
                txtFineId.setText(createNewId(lastFineDto.getFineId()));
            }
        } catch (Exception e) { System.out.println(e.getMessage());
            showMessage("Error", "Error occurs creating New Fine ID !"); 
        }
    }

    public FineDto getLastEnteredId() throws Exception{ 
        return fineService.getLastEnteredId();
    }
}
