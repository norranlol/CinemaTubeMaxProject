package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the bonusscore database table.
 * 
 */
@Entity
@NamedQuery(name="Bonusscore.findAll", query="SELECT b FROM Bonusscore b")
public class Bonusscore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int bonusScoreID;

	private int clientID;

	private int countOfScores;

	//bi-directional many-to-one association to Discount
	@OneToMany(mappedBy="bonusscore")
	private List<Discount> discounts;

	//bi-directional many-to-one association to History
	@OneToMany(mappedBy="bonusscore")
	private List<History> histories;

	public Bonusscore() {
	}

	public int getBonusScoreID() {
		return this.bonusScoreID;
	}

	public void setBonusScoreID(int bonusScoreID) {
		this.bonusScoreID = bonusScoreID;
	}

	public int getClientID() {
		return this.clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public int getCountOfScores() {
		return this.countOfScores;
	}

	public void setCountOfScores(int countOfScores) {
		this.countOfScores = countOfScores;
	}

	public List<Discount> getDiscounts() {
		return this.discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}

	public Discount addDiscount(Discount discount) {
		getDiscounts().add(discount);
		discount.setBonusscore(this);

		return discount;
	}

	public Discount removeDiscount(Discount discount) {
		getDiscounts().remove(discount);
		discount.setBonusscore(null);

		return discount;
	}

	public List<History> getHistories() {
		return this.histories;
	}

	public void setHistories(List<History> histories) {
		this.histories = histories;
	}
}