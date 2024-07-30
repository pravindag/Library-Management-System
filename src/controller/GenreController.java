package controller;

import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import dto.GenreDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.GenreService;
import tm.GenreTM;

public class GenreController extends CommonController{

    GenreService genreService = (GenreService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.GENRE);

    private GenreDto setGenreDto = new GenreDto();

    public GenreController(Stage primaryStage) {
        super(primaryStage);
    }

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<GenreTM, String> colDelete;

    @FXML
    private TableColumn<GenreTM, String> colGenreId;

    @FXML
    private TableColumn<GenreTM, String> colGenreName;

    @FXML
    private TableColumn<GenreTM, String> colUpdate;

    @FXML
    private Label lblUserName;

    @FXML
    private TableView<GenreTM> tblGenreDetails;

    @FXML
    private TextField txtGenreId;

    @FXML
    private TextField txtGenreName;

    public void initialize(){

        setNewGenreId();

        btnUpdate.setDisable(true);

        colGenreId.setCellValueFactory(new PropertyValueFactory<>("genreId"));
        colGenreName.setCellValueFactory(new PropertyValueFactory<>("genreName"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("btnUpdate"));

        loadGenreTable();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        Boolean response = setAndValidateFields();

        if(response == true){
            try {
                String saveResponse = saveGenre(this.setGenreDto);

                if(saveResponse == "Success"){
                    showMessage("Success", "Successfully saved !");
                    setNewGenreId();
                    clearTextFields();
                    loadGenreTable();
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
                String saveResponse = updateGenre(this.setGenreDto);

                if(saveResponse == "Success"){
                    showMessage("Success", "Successfully updated !");
                    setNewGenreId();
                    clearTextFields();
                    loadGenreTable();
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

    private void loadGenreTable(){

        try {
            ArrayList<GenreDto> genreDto = getAllGenres();

            ObservableList<GenreTM> genreTMList = FXCollections.observableArrayList();

            for (GenreDto dto : genreDto) { 
                
                ButtonController deleteButton = createButton("delete", dto.getGenreId(), this::delete);
                ButtonController updateButton = createButton("update", dto.getGenreId(), this::setUpdateDetails);
                
                GenreTM genreTM = new GenreTM(
                                        dto.getGenreId(),
                                        dto.getGenreName(),
                                        deleteButton,
                                        updateButton
                                );

                genreTMList.add(genreTM);
                
            }

            tblGenreDetails.setItems(genreTMList);

        } catch (Exception e) {  e.printStackTrace();
            showMessage("Error", "Error occured loding table !"); 
        }
    }

    private void setNewGenreId(){
        try {
            GenreDto lastGenreDto =  getLastEnteredId();
            if(lastGenreDto != null){ 
                txtGenreId.setText(createNewId(lastGenreDto.getGenreId()));
            }
        } catch (Exception e) { 
            showMessage("Error", "Error occured creating New Genre ID !"); 
        }
    }

    private Boolean setAndValidateFields(){

        String genreName = txtGenreName.getText();
        Boolean response = true;

        if(genreName == null || genreName == ""){
            showMessage("Error", "Please enter a valid Genre Name !");
            response = false;
        }else{

            this.setGenreDto.setGenreId(txtGenreId.getText());
            this.setGenreDto.setGenreName(genreName);
            
        }
        return response;
    }

    private void delete(String genreId){
        
        try {
            String response = deleteGenre(genreId);

            if(response == "Success"){
                showMessage("Success", "Successfully deleted !");
                loadGenreTable();
            }else{
                showMessage("Error", "Error occured deleting !");
            }
        } catch (Exception e) {
            showMessage("Error", "Error occured deleting !");
        }
    }

    private void setUpdateDetails(String genreId){

        btnAdd.setDisable(true);
        btnUpdate.setDisable(false);

        try {

            GenreDto genreDto = new GenreDto();

            genreDto = getGenre(genreId);

            txtGenreId.setText(genreDto.getGenreId());
            txtGenreName.setText(genreDto.getGenreName());

        } catch (Exception e) {
            showMessage("Error", "Error occured loading !");
        }
    }

    private void clearTextFields(){

        txtGenreName.setText("");
    }

    public String saveGenre(GenreDto genreDto) throws Exception{ 
        return genreService.save(genreDto);
    }

    public String updateGenre(GenreDto genreDto) throws Exception{ 
        return genreService.update(genreDto);
    }

    private String deleteGenre(String genreId) throws Exception{
        return genreService.delete(genreId);
    }

    public GenreDto getGenre(String genreId) throws Exception{
        return genreService.get(genreId);
    }

    public ArrayList<GenreDto> getAllGenres() throws Exception{
        return genreService.getAll();
    }

    private GenreDto getLastEnteredId() throws Exception{ 
        return genreService.getLastEnteredId();
    }
}
