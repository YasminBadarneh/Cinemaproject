package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hall_seats")
public class Seat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int seatNum;
	boolean isTaken; 
	
	public Seat() {}
	
	public Seat(int seatNum, boolean isTaken) {
		super();
		this.seatNum = seatNum;
		this.isTaken = false;
	}
	
	public int getSeatNum() {
		return seatNum;
	}
	
	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}
	
	public boolean isTaken() {
		return isTaken;
	}
	
	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}
	
	
}
