package controller;

import dto.LibrarianDto;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.LibrarianService;

public class LibrarianController extends CommonController{

    public LibrarianController(Stage primaryStage) {
        super(primaryStage);
    }

    LibrarianService librarianService = (LibrarianService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.LIBRARIAN);

    public LibrarianDto checkLibrarianData(String userName, String password) throws Exception{
        return librarianService.checkLibrarianData(userName, password);
    }
}
