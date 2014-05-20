package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the client database table.
 * 
 */
@Entity
@NamedQuery(name="Client.findAll", query="SELECT c FROM Client c")
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int clientID;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	private String name;

	private String patronymic;

	private String phoneNumber;

	private String surname;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="accountID")
	private Account account;

	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="client")
	private List<Ticket> tickets;

	public Client() {
	}

    public Client(String surname, String name, String patronymic, Date dateOfBirth, String phoneNumber, Account account){
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.account = account;
    }

	public int getClientID() {
		return this.clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatronymic() {
		return this.patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
}