package beans;

import model.Cinemahall;
import model.Film;
import model.Session;
import model.Ticket;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ConcretSessionBean implements Serializable {

    @Inject
    private FilmsBean filmsBean;

    private Session currentSession;

    private String[][] busyPlaces;

    private List<Ticket> listOfBusyPlaces;

    private int rows;

    private int places;

    private String busyStatus = "З";

    private String reserveStatus = "Б";

    public String showBusyPlaces(Film film, Session session){
        currentSession = session;
        listOfBusyPlaces = filmsBean.getFilmAction().showPlacesOnSession(currentSession);
        Cinemahall cinemahall = session.getCinemahall();
        rows = cinemahall.getCountOfRows();
        places = cinemahall.getCountOfPlacesInHall()/cinemahall.getCountOfRows();
        busyPlaces = new String[rows + 1][places + 1];
        generateCinemaHallMas(rows,places,busyPlaces,listOfBusyPlaces);
        return "busyPlaces";
    }


    private void generateCinemaHallMas(int rows, int places, String[][] busyPlaces, List<Ticket> listOfBusyPlaces){
        for (int i = 1; i < rows + 1; i++){
            for (int j = 1; j < places + 1;j++){
                busyPlaces[i][j] = String.valueOf(j);
                for (int k = 0; k < listOfBusyPlaces.size(); k++){
                    if (listOfBusyPlaces.get(k).getRow()==i&&listOfBusyPlaces.get(k).getPlace()==j)
                        busyPlaces[i][j]=listOfBusyPlaces.get(k).getStatus();
                }
            }
        }
    }

    public FilmsBean getFilmsBean() {
        return filmsBean;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public String[][] getBusyPlaces() {
        return busyPlaces;
    }

    public int getPlaces() {
        return places;
    }

    public int getRows() {
        return rows;
    }

    public String getBusyStatus() {
        return busyStatus;
    }

    public String getReserveStatus() {
        return reserveStatus;
    }
}
