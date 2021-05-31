package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.collections.ObservableList;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Collection;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Window;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.scene.Node;
import il.cshaifasweng.OCSFMediatorExample.entities.AllocatedObject;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import il.cshaifasweng.OCSFMediatorExample.entities.MovieScreening;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;

public class DatesController implements Initializable
{
    @FXML
    private AnchorPane screening_anchor;
    @FXML
    private TableView<MovieScreening> screenings_table;
    @FXML
    private Text screening_text;
    @FXML
    private Button cancelScreening_button;
    @FXML
    private Button editScreening_button;
    @FXML
    private Button addScreening_button;
    @FXML
    private TextField date_field;
    @FXML
    private Button goToMoviesList;
    @FXML
    private TableColumn<MovieScreening, String> screening_column;
    @FXML
    private Text movie_text;
    
    @FXML
    void addScreening(final ActionEvent event) {
        String date = this.date_field.getText();
        if (date != null && !this.date_field.getText().equals("")) {
            AllocatedObject msg = new AllocatedObject(movie_text.getText(), "#addScreening"+date);
            final Window window = ((Node)event.getSource()).getScene().getWindow();
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
    void cancelScreening(final ActionEvent event) {
        MovieScreening curr_movie = screenings_table.getSelectionModel().getSelectedItem();
        if (curr_movie != null) {
        	AllocatedObject msg = new AllocatedObject((Object)curr_movie, "#deleteScreening");
            final Window window = ((Node)event.getSource()).getScene().getWindow();
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
    void editDate(final ActionEvent event) {
        MovieScreening curr_screening = screenings_table.getSelectionModel().getSelectedItem();
        String date = this.date_field.getText();
        if (curr_screening != null && !date_field.getText().equals("")) {
             AllocatedObject msg = new AllocatedObject(curr_screening, "#updateScreening"+date);
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
    void goback(final ActionEvent event) throws Exception {
        Window window = ((Node)event.getSource()).getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage)window).close();
        }
        Stage primaryStage = new Stage();
         Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("MoviesList.fxml"));
         Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Movies list");
        primaryStage.show();
    }
    
    public void initialize(final URL location, final ResourceBundle resources) {
        if (!SimpleClient.screenings.isEmpty()) {
            this.populateTable(SimpleClient.screenings);
        }
        else {
            this.movie_text.setText(SimpleClient.m_name);
        }
    }
    
    public void populateTable(final ArrayList<MovieScreening> screenings) {
    	final ObservableList<MovieScreening> data = FXCollections.observableArrayList(screenings);
		 screenings_table.setEditable(true);  
		 screening_column.setCellValueFactory(new PropertyValueFactory<MovieScreening, String>("screeningDate")); 
        screenings_table.getColumns().setAll(screening_column); 
	     screenings_table.setItems(data);
	     movie_text.setText(data.get(0).getMovie().getEngName());
    }
}