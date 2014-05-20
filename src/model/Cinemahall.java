package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cinemahall database table.
 * 
 */
@Entity
@NamedQuery(name="Cinemahall.findAll", query="SELECT c FROM Cinemahall c")
public class Cinemahall implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int cinemaHallID;

	private int countOfPlacesInHall;

	private int countOfRows;

	//bi-directional many-to-one association to Session
	@OneToMany(mappedBy="cinemahall")
	private List<Session> sessions;

	public Cinemahall() {
	}

	public int getCinemaHallID() {
		return this.cinemaHallID;
	}

	public void setCinemaHallID(int cinemaHallID) {
		this.cinemaHallID = cinemaHallID;
	}

	public int getCountOfPlacesInHall() {
		return this.countOfPlacesInHall;
	}

	public void setCountOfPlacesInHall(int countOfPlacesInHall) {
		this.countOfPlacesInHall = countOfPlacesInHall;
	}

	public int getCountOfRows() {
		return this.countOfRows;
	}

	public void setCountOfRows(int countOfRows) {
		this.countOfRows = countOfRows;
	}

	public List<Session> getSessions() {
		return this.sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}
}