package tm;

import javafx.scene.control.Button;

public class GenreTM {

    private String genreId;
    private String genreName;
    private Button btnDelete;
    private Button btnUpdate;
    
    public GenreTM() {
    }

    public GenreTM(String genreId, String genreName, Button btnDelete, Button btnUpdate) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.btnDelete = btnDelete;
        this.btnUpdate = btnUpdate;
    }

    public GenreTM(String genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    public Button getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(Button btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    
}
