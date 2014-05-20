package ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import model.*;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FilmActionBean implements FilmActionBeanLocal {

	@PersistenceContext(name = "persistence/cinema", unitName= "CinemaProjectCinema")
	private EntityManager emC;
	
	@PersistenceContext(name = "persistence/bonuses", unitName= "CinemaProjectBonuses")
	private EntityManager emB;

    public FilmActionBean() {}

    @Override
    public List<Film> showAllFilms(){
    	CriteriaQuery<Film> criteriaQuery = emC.getCriteriaBuilder().createQuery(Film.class);
    	criteriaQuery.select(criteriaQuery.from(Film.class));
    	List<Film> listOfFilms = emC.createQuery(criteriaQuery).getResultList();
    	return listOfFilms;
    }

    @Override
    public List<Session> showSessionsOfFilm(Film film){
        Film findFilm = emC.find(Film.class, film.getFilmID());
        return findFilm.getSessions();
    }

    @Override
    public List<Ticket> showPlacesOnSession(Session session){
        Session findSession = emC.find(Session.class,session.getSessionID());
        return findSession.getTickets();
    }

    @Override
    public Client findClientByAccount(Account account){
        Account findAccount = emC.find(Account.class,account.getAccountID());
        return findAccount.getClients().get(0);
    }

    @Override
    public boolean reservePlace(Client client, Ticket ticket, Session session){
        emC.joinTransaction();
        Client newClient = emC.merge(client);
        ticket.setClient(newClient);
        client.getTickets().add(ticket);
        session.getTickets().add(ticket);
        emC.merge(session);
        emC.flush();
        return true;
    }

    @Override
    public List<Commentary> showAllCommentariesOfFilm(Film film){
        CriteriaBuilder cb = emC.getCriteriaBuilder();
        CriteriaQuery<Commentary> criteriaQuery = cb.createQuery(Commentary.class);
        Root commRoot = criteriaQuery.from(Commentary.class);
        criteriaQuery.select(commRoot).orderBy(cb.asc(commRoot.get("commentaryID")));
        List<Commentary> listOfCommentary = emC.createQuery(criteriaQuery).getResultList();
        return listOfCommentary;
    }

    @Override
    public boolean addCommentaryToFilm(Commentary commentary, Film film){
        Film findFilm = emC.find(Film.class, film.getFilmID());
        emC.joinTransaction();
        commentary.setFilm(film);
        film.getCommentaries().add(commentary);
        //Film newFilm = emC.merge(film);
        emC.persist(commentary);
        emC.flush();
        return true;
    }

    @Override
    public Client findClientByFIO(String surname, String name, String patronymic){
        CriteriaQuery<Client> criteriaQuery = emC.getCriteriaBuilder().createQuery(Client.class);
        Root clientRoot = criteriaQuery.from(Client.class);
        Predicate predicate1 = clientRoot.get("surname").in(surname);
        Predicate predicate2 = clientRoot.get("name").in(name);
        Predicate predicate3 = clientRoot.get("patronymic").in(patronymic);
        criteriaQuery.select(clientRoot).where(predicate1);
        criteriaQuery.select(clientRoot).where(predicate2);
        criteriaQuery.select(clientRoot).where(predicate3);
        List<Client> clients = emC.createQuery(criteriaQuery).getResultList();
        List<Client> listOfResult = new ArrayList<Client>();
        for (Client client : clients){
            if (surname.equals(client.getSurname()) && name.equals(client.getName())
                    && patronymic.equals(client.getPatronymic())){{
                    listOfResult.add(client);
                }
            }
        }
        if (listOfResult.size() > 0)
            return listOfResult.get(0);
        return null;
    }

    @Override
    public boolean deleteCommentary(Commentary commentary, Film film){
        Commentary commentaryFind = emC.find(Commentary.class, commentary.getCommentaryID());
        Film filmFind = emC.find(Film.class, film.getFilmID());
        emC.joinTransaction();
        film.getCommentaries().remove(commentary);
        emC.remove(commentaryFind);
        emC.flush();
        return true;
    }

    @Override
    public Bonusscore findBonusScoreByAccount(Account accountOnline){
        Client clientOnline = findClientByAccount(accountOnline);
        CriteriaQuery<Bonusscore> criteriaQuery = emB.getCriteriaBuilder().createQuery(Bonusscore.class);
        Root bonusscoreRoot = criteriaQuery.from(Bonusscore.class);
        Predicate predicateClient = bonusscoreRoot.get("clientID").in(clientOnline.getClientID());
        criteriaQuery.select(bonusscoreRoot).where(predicateClient);
        Bonusscore bonusscore = emB.createQuery(criteriaQuery).getSingleResult();
        return bonusscore;
    }

    @Override
    public List<History> getHistoryOfBonusscore(Account accountOnline){
        Bonusscore bonusscore = findBonusScoreByAccount(accountOnline);
        return bonusscore.getHistories();
    }

    @Override
    public int calculateDiscountForSession(Account account, Session session, int score){
        Bonusscore bonusscore = findBonusScoreByAccount(account);
        if (score < bonusscore.getCountOfScores()){
            int finalPrice = session.getPrice() - score;
            return finalPrice;
        }
        else return -1;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean reservePlaceWithBonusPoints(Account account, Session session, int score, int finalPrice, Client client, Ticket ticket){
        Client client2 = emC.merge(client);
        ticket.setClient(client2);
        client.getTickets().add(ticket);
        session.getTickets().add(ticket);
        emC.merge(session);
        emC.flush();
        Bonusscore bonusscore = findBonusScoreByAccount(account);
        int newCountscores = bonusscore.getCountOfScores() - score;
        bonusscore.setCountOfScores(newCountscores);
        Discount discount = new Discount(score ,finalPrice, session.getSessionID(), bonusscore);
        bonusscore.getDiscounts().add(discount);
        History history = new History(new Date(), "Снятие", score, bonusscore);
        bonusscore.getHistories().add(history);
        emB.merge(bonusscore);
        emB.flush();
        return true;
    }

    @Override
    public boolean getTicketsOfClient(Client client,Session session){
        Client clientFind = emC.find(Client.class, client.getClientID());
        int counter = 0;
        if (clientFind.getTickets().size()!=0){
            for (Ticket ticket : clientFind.getTickets()){
                if (ticket.getSession().getSessionID() == session.getSessionID()){
                    counter++;
                    if (counter >= 2)
                        return false;
                }
            }
        }
        return true;
    }
}
