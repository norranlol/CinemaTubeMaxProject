package beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import model.Film;
import ejb.FilmActionBeanLocal;
import model.Session;

@Named
@SessionScoped
public class FilmsBean implements Serializable {
	
	@EJB
	FilmActionBeanLocal filmAction;
	
	private List<Film> filmsList;

	private Film currentFilm;

    private SimpleDateFormat sdf;

    private List<Session> findSessionList;
	
	public List<Film> getFilmsList() {
		filmsList = filmAction.showAllFilms();
		return filmsList;
	}
	
    public String goToSessionsPage(Film film){
        currentFilm = film;
        return "sessions";
    }

    public String goToCommentariesPage(Film film){
        currentFilm = film;
        return "commentaries";
    }

    public Film getCurrentFilm() {
        return currentFilm;
    }

    public List<Session> getFindSessionList() {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        findSessionList = filmAction.showSessionsOfFilm(currentFilm);
        return findSessionList;
    }

    public FilmActionBeanLocal getFilmAction() {
        return filmAction;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }
}
