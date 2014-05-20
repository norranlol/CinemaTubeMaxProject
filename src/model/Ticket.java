package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ticket database table.
 * 
 */
@Entity
@NamedQuery(name="Ticket.findAll", query="SELECT t FROM Ticket t")
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ticketID;

	private int place;

	private int row;

	private String status;

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="clientID")
	private Client client;

	//bi-directional many-to-one association to Session
	@ManyToOne
	@JoinColumn(name="sessionID")
	private Session session;

	public Ticket() {
	}

    public Ticket(int row, int place, String status, Session session, Client client){
        this.row = row;
        this.place = place;
        this.status = status;
        this.session = session;
        this.client = client;
    }

	public int getTicketID() {
		return this.ticketID;
	}

	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}

	public int getPlace() {
		return this.place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public int getRow() {
		return this.row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Session getSession() {
		return this.session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}