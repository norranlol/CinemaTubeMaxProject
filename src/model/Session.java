package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the session database table.
 * 
 */
@Entity
@NamedQuery(name="Session.findAll", query="SELECT s FROM Session s")
public class Session implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int sessionID;

	@Temporal(TemporalType.DATE)
	private Date dateOfSession;

	private int price;

	private Time timeOfStart;

	//bi-directional many-to-one association to Cinemahall
	@ManyToOne
	@JoinColumn(name="cinemaHallID")
	private Cinemahall cinemahall;

	//bi-directional many-to-one association to Film
	@ManyToOne
	@JoinColumn(name="filmID")
	private Film film;

	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="session")
	private List<Ticket> tickets;

	public Session() {
	}

	public int getSessionID() {
		return this.sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public Date getDateOfSession() {
		return this.dateOfSession;
	}

	public void setDateOfSession(Date dateOfSession) {
		this.dateOfSession = dateOfSession;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Time getTimeOfStart() {
		return this.timeOfStart;
	}

	public void setTimeOfStart(Time timeOfStart) {
		this.timeOfStart = timeOfStart;
	}

	public Cinemahall getCinemahall() {
		return this.cinemahall;
	}

	public void setCinemahall(Cinemahall cinemahall) {
		this.cinemahall = cinemahall;
	}

	public Film getFilm() {
		return this.film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public List<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
}