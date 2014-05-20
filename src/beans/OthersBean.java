package beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ejb.OtherActionBeanLocal;
import model.Cinemahall;
import model.Vacation;

@Named
@RequestScoped
public class OthersBean implements Serializable {
	
	@EJB
	private OtherActionBeanLocal otherAction;
	
	private List<Vacation> listOfVacations;

	private List<Cinemahall> listOfCinemahalls;
	
	public List<Vacation> getListOfVacations() {
		listOfVacations = otherAction.getListOfVacations();
		return listOfVacations;
	}

	public List<Cinemahall> getListOfCinemahalls() {
		listOfCinemahalls = otherAction.getListOfCinemahalls();
		return listOfCinemahalls;
	}	
	

}
