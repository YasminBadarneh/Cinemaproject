package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.InputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;

public class MoreInfoController implements Initializable
{
    @FXML
    private Text summary_text;
    @FXML
    private Text producer_text;
    @FXML
    private Text actors_text;
    @FXML
    private Text movie_text;
    @FXML
    private ImageView movie_image;
    @FXML
    private Button goback_button;
    
    @FXML
    void goToMoviesList(final ActionEvent event) throws Exception {
        final Window window = ((Node)event.getSource()).getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage)window).close();
        }
        final Stage primaryStage = new Stage();
        final Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("MoviesList.fxml"));
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Movies list");
        primaryStage.show();
    }
    
    public void initialize(final URL location, final ResourceBundle resources) {
    	movie_text.setText(MoviesListController.chosen_movie.getEngName());
    	actors_text.setText(MoviesListController.chosen_movie.getActors());
    	producer_text.setText(MoviesListController.chosen_movie.getProducer());
    	summary_text.setText(MoviesListController.chosen_movie.getSummary());
    	InputStream stream;
		try {

			stream = new FileInputStream("@"+ MoviesListController.chosen_movie.getEngName()+".jpg");
	        Image image = new Image(stream);
	        movie_image.setImage(image);
	        movie_image.setFitWidth(70);
	        movie_image.setFitHeight(70);
	        movie_image.setPreserveRatio(true);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}