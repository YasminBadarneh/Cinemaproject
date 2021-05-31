package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cinema_halls")
public class Hall implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private int hallId;
	private int hallSize; 
	@OneToMany() 
	private List<Seat> seats;
	
	public Hall() {}
	public Hall(int hallId, int hallSize, List<Seat> seats) {
		super();
		this.hallId = hallId;
		this.hallSize = hallSize;
		this.seats = seats;
	}
	
	public int getHallId() {
		return hallId;
	}
	
	public void setHallId(int hallId) {
		this.hallId = hallId;
	}
	
	public int getHallSize() {
		return hallSize;
	}
	
	public void setHallSize(int hallSize) {
		this.hallSize = hallSize;
	}
	
	public List<Seat> getSeats() {
		return seats;
	}
	
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	 
	
}
