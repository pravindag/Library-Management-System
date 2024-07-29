package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import dto.MemberDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.MemberService;
import session.SessionManager;
import tm.MemberTM;

public class MemberController extends CommonController{

    MemberService memberService = (MemberService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.MEMBER);

    private MemberDto setMemberDto = new MemberDto();
    
    public MemberController(Stage primaryStage) {
        super(primaryStage);
    }

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<MemberTM, String> colAddress;

    @FXML
    private TableColumn<MemberTM, String> colDelete;

    @FXML
    private TableColumn<MemberTM, String> colMemberId;

    @FXML
    private TableColumn<MemberTM, String> colMemberName;

    @FXML
    private TableColumn<MemberTM, String> colPhoneNumber;

    @FXML
    private TableColumn<MemberTM, String> colUpdate;

    @FXML
    private Label lblUserName;

    @FXML
    private TableView<MemberTM> tblMemberDetails;

    @FXML
    private TextField txtAddress;

    @FXML
    private DatePicker txtDateOfBirth;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtPhoneNumber;

    public void initialize(){ 
        this.lblUserName.setText("Welcome " + SessionManager.getInstance().getUserName());

        setNewMemberId();

        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("memberFullName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("btnUpdate"));

        loadMemberTable();

        btnUpdate.setDisable(true);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        Boolean response = setAndValidateFields();

        if(response == true){
            try {
                String saveResponse = saveMember(this.setMemberDto);

                if(saveResponse == "Success"){
                    showMessage("Success", "Successfully saved !");
                    setNewMemberId();
                    clearTextFields();
                    loadMemberTable();
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
                String saveResponse = updateMember(this.setMemberDto);

                if(saveResponse == "Success"){
                    showMessage("Success", "Successfully updated !");
                    setNewMemberId();
                    clearTextFields();
                    loadMemberTable();
                    btnAdd.setDisable(false);
                    btnUpdate.setDisable(true);
                }else{
                    showMessage("Error", "Error occured updating !");
                }

            } catch (Exception e) { System.out.println(e.getMessage());
                showMessage("Error", "Error occured !");
            }
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        backToMainScene();
    }

    private void setNewMemberId(){
        try {
            MemberDto lastMemberDto =  getLastEnteredId();
            if(lastMemberDto != null){ 
                txtMemberId.setText(createNewId(lastMemberDto.getMemberId()));
            }
        } catch (Exception e) { System.out.println(e.getMessage());
            showMessage("Error", "Error occured creating New Book ID !"); 
        }
    }

    private MemberDto getLastEnteredId() throws Exception{ 
        return memberService.getLastEnteredId();
    }

    private void loadMemberTable(){

        try {
            ArrayList<MemberDto> memberDto = getMemberAll();

            ObservableList<MemberTM> memberTMList = FXCollections.observableArrayList();

            for (MemberDto dto : memberDto) { 
                
                ButtonController deleteButton = createButton("delete", dto.getMemberId(), this::delete);
                ButtonController updateButton = createButton("update", dto.getMemberId(), this::setUpdateDetails);
                
                MemberTM memberTM = new MemberTM(
                                        dto.getMemberId(),
                                        dto.getFirstName(),
                                        dto.getLastName(),
                                        dto.getAddress(), 
                                        dto.getPhoneNumber(),
                                        deleteButton,
                                        updateButton
                                );

                memberTMList.add(memberTM);
                
            }

            tblMemberDetails.setItems(memberTMList);
        } catch (Exception e) {
            showMessage("Error", "Error occured loding table !"); 
        }
    }

    private Boolean setAndValidateFields(){

        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String address = txtAddress.getText();
        String phoneNumber = txtPhoneNumber.getText();
        Boolean response = true;

        if(firstName == null || firstName == ""){
            showMessage("Error", "Please enter a valid First Name !");
            response = false;
        }else if(lastName == null || lastName == ""){
            showMessage("Error", "Please enter a valid Last Name !");
            response = false;
        }else if(address == null || address == ""){
            showMessage("Error", "Please enter a valid Address !");
            response = false;
        }else if(phoneNumber == null || phoneNumber == ""){
            showMessage("Error", "Please enter a valid Phone Number !");
            response = false;
        }else{

            String dateOfBirth  = txtDateOfBirth.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            this.setMemberDto.setMemberId(txtMemberId.getText());
            this.setMemberDto.setFirstName(firstName);
            this.setMemberDto.setLastName(lastName);
            this.setMemberDto.setAddress(address);
            this.setMemberDto.setPhoneNumber(phoneNumber);
            this.setMemberDto.setDateOfBirth(dateOfBirth);
            this.setMemberDto.setEmail(txtEmail.getText());
            
        }
        return response;
    }

    private void delete(String memberId){
        
        try {
            String response = deleteMember(memberId);
            if(response == "Success"){
                showMessage("Success", "Successfully deleted !");
                loadMemberTable();
            }else{
                showMessage("Error", "Error occured deleting !");
            }
        } catch (Exception e) {
            showMessage("Error", "Error occured deleting !");
        }
    }

    private void setUpdateDetails(String memberId){

        btnAdd.setDisable(true);
        btnUpdate.setDisable(false);

        try {

            MemberDto memberDto = new MemberDto();

            memberDto = getMember(memberId);

            LocalDate dateOfBirth = LocalDate.parse(memberDto.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            txtMemberId.setText(memberDto.getMemberId());
            txtFirstName.setText(memberDto.getFirstName());
            txtLastName.setText(memberDto.getLastName());
            txtDateOfBirth.setValue(dateOfBirth);
            txtAddress.setText(memberDto.getAddress());
            txtPhoneNumber.setText(memberDto.getPhoneNumber());
            txtEmail.setText(memberDto.getEmail());

        } catch (Exception e) { System.out.println(e.getMessage());
            showMessage("Error", "Error occured loading !");
        }
    }

    private void clearTextFields(){

        txtFirstName.setText("");
        txtLastName.setText("");
        txtDateOfBirth.setValue(null);
        txtAddress.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
    }

    public String saveMember(MemberDto memberDto) throws Exception{
        return memberService.save(memberDto);
    }
    
    public String updateMember(MemberDto memberDto) throws Exception{
        return memberService.update(memberDto);
    }
    
    public String deleteMember(String custId) throws Exception{
        return memberService.delete(custId);
    }
    
    public MemberDto getMember(String custId) throws Exception{
        return memberService.get(custId);
    }
    
    public ArrayList<MemberDto> getMemberAll() throws Exception{
        return memberService.getAll();
    }

}
