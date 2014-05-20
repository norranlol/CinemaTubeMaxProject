package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the discount database table.
 * 
 */
@Entity
@NamedQuery(name="Discount.findAll", query="SELECT d FROM Discount d")
public class Discount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int discountID;

	private int discount;

	private int finalPrice;

	private int sessionID;

	//bi-directional many-to-one association to Bonusscore
	@ManyToOne
	@JoinColumn(name="bonusScoreID")
	private Bonusscore bonusscore;

	public Discount() {
	}

    public Discount(int discount, int finalPrice, int sessionID, Bonusscore bonusscore){
        this.discount = discount;
        this.finalPrice = finalPrice;
        this.sessionID = sessionID;
        this.bonusscore = bonusscore;
    }

	public int getDiscountID() {
		return this.discountID;
	}

	public void setDiscountID(int discountID) {
		this.discountID = discountID;
	}

	public int getDiscount() {
		return this.discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getFinalPrice() {
		return this.finalPrice;
	}

	public void setFinalPrice(int finalPrice) {
		this.finalPrice = finalPrice;
	}

	public int getSessionID() {
		return this.sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public Bonusscore getBonusscore() {
		return this.bonusscore;
	}

	public void setBonusscore(Bonusscore bonusscore) {
		this.bonusscore = bonusscore;
	}

}