package il.cshaifasweng.OCSFMediatorExample.entities;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "movie_screenings")
public class MovieScreening implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int screening_id;
	
	@ManyToOne()
	private Movie movie;
	private String screeningDate;
	@OneToOne()
	private Cinema branch;
	@OneToOne()
	private Hall hall;
	
	public MovieScreening() {}
	
	public MovieScreening(Movie movie, String screeningDate, Cinema branch, Hall hall) {
		super();
		this.movie = movie;
		this.screeningDate = screeningDate;
		this.branch = branch;
		this.hall = hall;
	}

	public int getId() {
		return screening_id;
	}
	
	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public String getScreeningDate() {
		return screeningDate;
	}

	public void setScreeningDate(String screeningDate) {
		this.screeningDate = screeningDate;
	}

	public Cinema getBranch() {
		return branch;
	}

	public void setBranch(Cinema branch) {
		this.branch = branch;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}
	

	
}
