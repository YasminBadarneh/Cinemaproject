package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.collections.ObservableList;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Collection;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.net.URL;
import il.cshaifasweng.OCSFMediatorExample.entities.AllocatedObject;
import java.io.IOException;
import javafx.stage.Window;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import javafx.fxml.Initializable;

public class MoviesListController implements Initializable
{
    public static Movie chosen_movie;
    @FXML
    private Button moreDetails_button;
    @FXML
    private Button buyTicket_button;
    @FXML
    private Button editMovie_button;
    @FXML
    private Button addMovie_button;
    @FXML
    private TableView<Movie> movies_table;
    @FXML
    private TableColumn<Movie, byte[]> MovieIm;
    @FXML
    private TableColumn<Movie, String> EngName;
    @FXML
    private TableColumn<Movie, String> HebName;
    @FXML
    private TableColumn<Movie, Integer> Price;
    @FXML
    private Text branch_text;
    @FXML
    private Button Back_button;
    
    @FXML
    void goToAddMovie(final ActionEvent event) {
    }
    
    @FXML
    void goToBranches(final ActionEvent event) throws IOException {
        Window window = ((Node)event.getSource()).getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage)window).close();
        }
    }
    
    @FXML
    void goToBuyTicket(final ActionEvent event) {
    }
    
    @FXML
    void goToEditMovie(final ActionEvent event) throws Exception {
        final Movie curr_movie = (Movie)this.movies_table.getSelectionModel().getSelectedItem();
        if (curr_movie != null) {
        	AllocatedObject msg = new AllocatedObject(null, "#getAllScreenings " + curr_movie.getEngName());
            Window window = ((Node)event.getSource()).getScene().getWindow();
            if (window instanceof Stage) {
                ((Stage)window).close();
            }
            try {
                SimpleClient.getClient().sendToServer((Object)msg);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    void goToMoreInfo(final ActionEvent event) throws Exception {
    }
    
    public void initialize(final URL location, final ResourceBundle resources) {
        populateTable(SimpleClient.movies);
    }
    
    public void populateTable(final ArrayList<Movie> movies) {
        final ObservableList<Movie> data = FXCollections.observableArrayList(movies);
        movies_table.setEditable(true);
        MovieIm.setCellValueFactory(new PropertyValueFactory<Movie, byte[]>("movieIm")); 
        EngName.setCellValueFactory(new PropertyValueFactory<Movie, String>("engName"));
        HebName.setCellValueFactory(new PropertyValueFactory<Movie, String>("hebName"));
        Price.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("price")); 
        movies_table.getColumns().setAll(MovieIm, EngName, HebName, Price); 
        movies_table.setItems(data);

	return;
    }
}