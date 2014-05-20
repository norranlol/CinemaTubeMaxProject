package ejb;

import java.util.List;

import javax.ejb.Local;

import model.Cinemahall;
import model.Vacation;

@Local
public interface OtherActionBeanLocal {
	public List<Vacation> getListOfVacations();
	public List<Cinemahall> getListOfCinemahalls();
}
