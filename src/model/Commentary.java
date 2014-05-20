package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the commentary database table.
 * 
 */
@Entity
@NamedQuery(name="Commentary.findAll", query="SELECT c FROM Commentary c")
public class Commentary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int commentaryID;

	private String commentary;

	//bi-directional many-to-one association to Film
	@ManyToOne
	@JoinColumn(name="filmID")
	private Film film;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="accountID")
	private Account account;

	public Commentary() {
	}

    public Commentary(String commentary, Account account, Film film){
        this.commentary = commentary;
        this.account = account;
        this.film = film;
    }

	public int getCommentaryID() {
		return this.commentaryID;
	}

	public void setCommentaryID(int commentaryID) {
		this.commentaryID = commentaryID;
	}

	public String getCommentary() {
		return this.commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public Film getFilm() {
		return this.film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}