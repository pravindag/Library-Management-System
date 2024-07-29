package controller;

import dto.LibrarianDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import session.SessionManager;

public class LoginController extends CommonController{

    private final LibrarianController LIBRARIAN_CONTROLLER;

    public LoginController(Stage primaryStage) {
        super(primaryStage);
        this.LIBRARIAN_CONTROLLER = new LibrarianController(primaryStage);
    }

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnLoginOnAction(ActionEvent event) { 

        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if(userName == null || userName == ""){
            showMessage("Error", "Please enter a valid user name !");
        }else if(password == null || password == ""){
            showMessage("Error", "Please enter a valid password !");
        }else{
            try {
                LibrarianDto librarianDto = LIBRARIAN_CONTROLLER.checkLibrarianData(userName, password);

                SessionManager.getInstance().setUserId(librarianDto.getLibrarianId());
                SessionManager.getInstance().setUserName(librarianDto.getFirstName());

                loadScene("borrow","/view/Borrow.fxml", "Library Management System");

            } catch (Exception e) {
                showMessage("Error", "Please enter a valid data !");
            }
            
        }
    }

}
