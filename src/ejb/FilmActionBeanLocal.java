package ejb;

import java.util.List;

import javax.ejb.Local;

import model.*;

@Local
public interface FilmActionBeanLocal {
	public List<Film> showAllFilms();
    public List<Session> showSessionsOfFilm(Film film);
    public List<Ticket> showPlacesOnSession(Session session);
    public Client findClientByAccount(Account account);
    public boolean reservePlace(Client client, Ticket ticket, Session session);
    public List<Commentary> showAllCommentariesOfFilm(Film film);
    public boolean addCommentaryToFilm(Commentary commentary, Film film);
    public boolean deleteCommentary(Commentary commentary, Film film);
    public Bonusscore findBonusScoreByAccount(Account accountOnline);
    public List<History> getHistoryOfBonusscore(Account accountOnline);
    public int calculateDiscountForSession(Account account, Session session, int score);
    //public boolean reservePlaceWithBonusPoints(Account account, Session session, int score, int finalPrice);
    public boolean reservePlaceWithBonusPoints(Account account, Session session, int score, int finalPrice, Client client, Ticket ticket);
    public Client findClientByFIO(String surname, String name, String patronymic);
    public boolean getTicketsOfClient(Client client, Session session);
}
