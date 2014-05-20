package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vacation database table.
 * 
 */
@Entity
@NamedQuery(name="Vacation.findAll", query="SELECT v FROM Vacation v")
public class Vacation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int vacationID;

	private int salary;

	private String schedule;

	private String vacation;

	public Vacation() {
	}

	public int getVacationID() {
		return this.vacationID;
	}

	public void setVacationID(int vacationID) {
		this.vacationID = vacationID;
	}

	public int getSalary() {
		return this.salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getSchedule() {
		return this.schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getVacation() {
		return this.vacation;
	}

	public void setVacation(String vacation) {
		this.vacation = vacation;
	}
}