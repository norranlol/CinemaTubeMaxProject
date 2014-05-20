package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int accountID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfRegister;

	private String login;

	private String password;

	private String secretAnswer;

	private String secretQuestion;

	//bi-directional many-to-one association to Client
	@OneToMany(mappedBy="account")
	private List<Client> clients;

	//bi-directional many-to-one association to Commentary
	@OneToMany(mappedBy="account")
	private List<Commentary> commentaries;

	public Account() {
	}

    public Account(String login, String password, String secretQuestion, String secretAnswer, Date dateOfRegister){
        this.login = login;
        this.password = password;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
        this.dateOfRegister = dateOfRegister;
    }

	public int getAccountID() {
		return this.accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public Date getDateOfRegister() {
		return this.dateOfRegister;
	}

	public void setDateOfRegister(Date dateOfRegister) {
		this.dateOfRegister = dateOfRegister;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecretAnswer() {
		return this.secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public String getSecretQuestion() {
		return this.secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public List<Client> getClients() {
		return this.clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public Client addClient(Client client) {
		getClients().add(client);
		client.setAccount(this);

		return client;
	}

	public Client removeClient(Client client) {
		getClients().remove(client);
		client.setAccount(null);

		return client;
	}

	public List<Commentary> getCommentaries() {
		return this.commentaries;
	}

	public void setCommentaries(List<Commentary> commentaries) {
		this.commentaries = commentaries;
	}
}