package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 * The persistent class for the history database table.
 * 
 */
@Entity
@NamedQuery(name="History.findAll", query="SELECT h FROM History h")
public class History implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int historyID;

    @Temporal(TemporalType.DATE)
	private Date date;

	private String operation;

	private int sum;

	//bi-directional many-to-one association to Bonusscore
	@ManyToOne
	@JoinColumn(name="bonusScoreID")
	private Bonusscore bonusscore;

	public History() {
	}

    public History(Date date, String operation, int sum, Bonusscore bonusscore){
        this.date = date;
        this.operation = operation;
        this.sum = sum;
        this.bonusscore = bonusscore;
    }

	public int getHistoryID() {
		return this.historyID;
	}

	public void setHistoryID(int historyID) {
		this.historyID = historyID;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int getSum() {
		return this.sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public Bonusscore getBonusscore() {
		return this.bonusscore;
	}

	public void setBonusscore(Bonusscore bonusscore) {
		this.bonusscore = bonusscore;
	}

}