package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.scene.image.ImageView;

public class MyImage
{
    private ImageView image;
    
    MyImage(final ImageView img) {
        this.image = img;
    }
    
    public void setImageView(final ImageView value) {
        this.image = value;
    }
    
    public ImageView getImageView() {
        return this.image;
    }
}