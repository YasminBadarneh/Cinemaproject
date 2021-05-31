package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import il.cshaifasweng.OCSFMediatorExample.entities.AllocatedObject;
import il.cshaifasweng.OCSFMediatorExample.entities.MovieScreening;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import java.util.ArrayList;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;

public class SimpleClient extends AbstractClient
{
    private static SimpleClient client;
    public static ArrayList<Movie> movies;
    public static ArrayList<MovieScreening> screenings;
    public static String m_name;
    
    private SimpleClient(final String host, final int port) {
        super(host, port);
    }
    
    protected void handleMessageFromServer(Object msg) {

    	
		if ( ((AllocatedObject)msg).getMessage().equals("#receiveMovies") ) //dates 
		{
	     	setMovies((ArrayList<Movie>)((AllocatedObject)msg).getObject());
		    movies = SimpleClient.getClient().getMovies();

		    Platform.runLater(()->{
			
			Stage primaryStage = new Stage();
			Parent Root;
			try {
				Root = FXMLLoader.load(getClass().getResource("MoviesList.fxml"));
				Scene scene = new Scene(Root); 
				primaryStage.setScene(scene);
				primaryStage.show(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	});
		}
		
		if ( ((AllocatedObject)msg).getMessage().equals("#receiveScreenings") ) //dates 
		{
			setScreenings((ArrayList<MovieScreening>)((AllocatedObject)msg).getObject());
			screenings = SimpleClient.getClient().getScreenings(); 
			
			Platform.runLater(()->{
							
				Stage primaryStage = new Stage();
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("Dates.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle("Movie screening dates");
					primaryStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
							
				});  	 		
		}	
		if ( ((AllocatedObject)msg).getMessage().startsWith("#deletedScreenings") ) //dates 
		{
			setScreenings((ArrayList<MovieScreening>)((AllocatedObject)msg).getObject());
			screenings = SimpleClient.getClient().getScreenings(); 
			m_name = ((AllocatedObject)msg).getMessage().substring(18);

			Platform.runLater(()->{
							
				Stage primaryStage = new Stage();
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("Dates.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle("Movie screening dates");
					primaryStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
							
				});  	 		
		}	

	}
	
	private void setScreenings(ArrayList<MovieScreening> screenings) {
		 this.screenings = screenings;
	}
	
	private ArrayList<MovieScreening> getScreenings() {
		return screenings;
	}

	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}
}