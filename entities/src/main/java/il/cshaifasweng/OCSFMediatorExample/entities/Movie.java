package il.cshaifasweng.OCSFMediatorExample.entities;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.nio.Buffer;
import java.util.List;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "cinema_movies")
public class Movie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private String engName;
    private String hebName;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] movieIm; 
    private String producer;
    private String summary;
    private int price;
    private String actors;
    
    public Movie() {}
    
    public Movie(String engName, String hebName, byte[] movieIm, String producer, String summary, int price, String actors) {
		super();	
		this.engName = engName;
		this.hebName = hebName;
		this.movieIm = movieIm;
		this.producer = producer;
		this.summary = summary;
		this.price = price;
		this.actors = actors;
	} 
	
	public String getEngName() {
		return engName;
	}
	
	public void setEngName(String engName) {
		this.engName = engName;
	}
	
	public String getHebName() {
		return hebName;
	}
	
	public void setHebName(String hebName) {
		this.hebName = hebName;
	}
	
	public byte[] getMovieIm() {
		return movieIm;
	}
	
	public void setMovieIm(byte[] movieIm) {
		this.movieIm = movieIm;
	} 
	
	
	public String getProducer() {
		return producer;
	}
	
	public void setProducer(String producer) {
		this.producer = producer;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getActors() {
		return actors;
	}
	
	public void setActors(String actors) {
		this.actors = actors;
	}
    
     
     
}
