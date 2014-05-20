package ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import model.Cinemahall;
import model.Vacation;

@Stateless
public class OtherActionBean implements OtherActionBeanLocal {

	@PersistenceContext(name = "persistence/cinema", unitName= "CinemaProjectCinema")
	private EntityManager emC;
	
	@PersistenceContext(name = "persistence/bonuses", unitName= "CinemaProjectBonuses")
	private EntityManager emB;
	
    public OtherActionBean() {
    }

    @Override
    public List<Vacation> getListOfVacations(){
    	CriteriaQuery<Vacation> criteriaQuery = emC.getCriteriaBuilder().createQuery(Vacation.class);
    	criteriaQuery.select(criteriaQuery.from(Vacation.class));
    	List<Vacation> listOfVacations = emC.createQuery(criteriaQuery).getResultList();
    	return listOfVacations;
    }    

    @Override
    public List<Cinemahall> getListOfCinemahalls(){
    	CriteriaQuery<Cinemahall> criteriaQuery = emC.getCriteriaBuilder().createQuery(Cinemahall.class);
    	criteriaQuery.select(criteriaQuery.from(Cinemahall.class));
    	List<Cinemahall> listOfCinemahalls = emC.createQuery(criteriaQuery).getResultList();
    	return listOfCinemahalls;
    }
}
