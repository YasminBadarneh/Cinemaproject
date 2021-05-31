package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import il.cshaifasweng.OCSFMediatorExample.entities.AllocatedObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController
{
    @FXML
    private Button haifa_button;
    @FXML
    private Button nazareth_button;
    
    @FXML
    void goToMoviesList(final ActionEvent event) throws Exception {
    	AllocatedObject msg = new AllocatedObject((Object)null, "#getAllMovies");
        try {
            SimpleClient.getClient().sendToServer((Object)msg);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}