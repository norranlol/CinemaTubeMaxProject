package beans;

import ejb.FilmActionBeanLocal;
import model.Bonusscore;
import model.History;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@RequestScoped
public class HistoryBean {

    @EJB
    private FilmActionBeanLocal filmActionBeanLocal;

    @Inject
    private RegisterAndAuthorizationBean registerAndAuthorizationBean;

    private Bonusscore bonusscore;

    private List<History> listOfOperations;

    public Bonusscore getBonusscore() {
        bonusscore = filmActionBeanLocal.findBonusScoreByAccount(registerAndAuthorizationBean.getAccountOnline());
        return bonusscore;
    }

    public List<History> getListOfOperations() {
        listOfOperations = filmActionBeanLocal.getHistoryOfBonusscore(registerAndAuthorizationBean.getAccountOnline());
        return listOfOperations;
    }
}
