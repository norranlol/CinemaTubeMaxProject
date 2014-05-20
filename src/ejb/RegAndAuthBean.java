package ejb;

import model.Account;
import model.Client;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class RegAndAuthBean implements RegAndAuthBeanLocal {

    @PersistenceContext(name = "persistence/cinema", unitName= "CinemaProjectCinema")
    private EntityManager emC;

    public RegAndAuthBean(){}

    @Override
    public boolean register(Client client, Account account) {
        emC.joinTransaction();
        Account newAccount = emC.merge(account);
        client.setAccount(newAccount);
        emC.merge(client);
        return true;
    }

    @Override
    public boolean checkOnTwoMatchesOfLogin(String login){
        CriteriaBuilder cb = emC.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = cb.createQuery(Account.class);
        Root<Account> account = criteriaQuery.from(Account.class);
        Predicate predicate = account.get("login").in(login);
        criteriaQuery.select(account).where(predicate);
        List<Account> accounts = emC.createQuery(criteriaQuery).getResultList();
        if (accounts.size() == 0)
            return true;
        else return false;
    }

    public Account checkLoginAndPassword(String login, String password){
        CriteriaBuilder cb = emC.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = cb.createQuery(Account.class);
        Root<Account> account = criteriaQuery.from(Account.class);
        Predicate predicate = account.get("login").in(login);
        Predicate predicate2 = account.get("password").in(password);
        criteriaQuery.select(account).where(predicate).where(predicate2);
        List<Account> accounts = emC.createQuery(criteriaQuery).getResultList();
        if (accounts.size() == 0)
            return null;
        else return accounts.get(0);
    }

}
