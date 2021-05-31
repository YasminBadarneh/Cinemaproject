package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name= "cinema_branch")
public class Cinema implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	private String branch;
    @OneToMany()
	private List<Hall> halls;
	
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="table_join_movie_cinema",
    joinColumns=@JoinColumn(name="id_A", referencedColumnName="branch"),
    inverseJoinColumns=@JoinColumn(name="id_B", referencedColumnName="engName"))
    
	private List<Movie> movies;
	
    public Cinema() {}
    
	public Cinema(String branch, List<Hall> halls, List<Movie> movies) {
		super();
		this.branch = branch;
		this.halls = halls;
		this.movies = movies;
	}
	
	public String getBranch() {
		return branch;
	}
	
	public void setBranch(String branch) {
		this.branch = branch;
	}
	

	public List<Hall> getHalls() {
		return halls;
	}
	
	public void setHalls(List<Hall> halls) {
		this.halls = halls;
	}
	
	public List<Movie> getMovies() {
		return movies;
	}
	
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	
	
	
	
}
