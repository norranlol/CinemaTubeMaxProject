package beans;

import ejb.FilmActionBeanLocal;
import model.Client;
import model.Ticket;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Named
@SessionScoped
public class ReserveBean implements Serializable {

    @Inject
    private RegisterAndAuthorizationBean registerAndAuthorizationBean;

    @Inject
    private ConcretSessionBean concretSessionBean;

    @EJB
    private FilmActionBeanLocal filmAction;

    private int row;

    private int place;

    private boolean payBonusScores;

    private int countBonusScores;

    private int finalPrice = 0;

    private String bonusNotice;

    private String noticeOverful;

    private String noticeForReserve;

    @Size(min = 2,max = 30,message = "Фамилия должна быть от 2 до 30 символов")
    private String surname;

    @Size(min = 2,max = 30,message = "Имя должно быть от 2 до 30 символов")
    private String name;

    @Size(min = 2,max = 30,message = "Отчество должно быть от 2 до 30 символов")
    private String patronymic;

    public String reserveTicket(int row, String place){
        this.row = row;
        this.place = Integer.parseInt(place);
        if (registerAndAuthorizationBean.getAccountOnline() != null){
            Client onlineClient = filmAction.findClientByAccount(registerAndAuthorizationBean.getAccountOnline());
            this.surname = onlineClient.getSurname();
            this.name = onlineClient.getName();
            this.patronymic = onlineClient.getPatronymic();
        }
        return  "reserveplacing";
    }

    public String makeReserveTicket(){
        Client onlineClient = null;
        if (registerAndAuthorizationBean.getAccountOnline() != null){
            onlineClient = filmAction.findClientByAccount(registerAndAuthorizationBean.getAccountOnline());
        } else {
            if ((filmAction.findClientByFIO(surname, name, patronymic) == null))
                onlineClient = new Client(surname, name, patronymic, null, null, null);
            else if (filmAction.findClientByFIO(surname, name, patronymic) != null)
                onlineClient = filmAction.findClientByFIO(surname, name, patronymic);
                if (!filmAction.getTicketsOfClient(onlineClient, concretSessionBean.getCurrentSession())){
                    noticeOverful = "На ваше ФИО уже забронировано 2 билета на этот сеанс";
                    return "reserveplacing";
                }
        }
        Ticket ticket = new Ticket(row,place,"Б",concretSessionBean.getCurrentSession(),null);
        filmAction.reservePlace(onlineClient,ticket,concretSessionBean.getCurrentSession());
        noticeForReserve = "Вы забронировали место под номером " + place + " на ряду " + row;
        return concretSessionBean.showBusyPlaces(
                concretSessionBean.getFilmsBean().getCurrentFilm(), concretSessionBean.getCurrentSession());
    }

    public String calculateFinalPrice(){
        finalPrice = filmAction.calculateDiscountForSession(registerAndAuthorizationBean.getAccountOnline(),
                concretSessionBean.getCurrentSession(), countBonusScores);
        if (countBonusScores <= 0){
            bonusNotice = "Необходимо ввести количество бонусных очков";
            return "reserveplacing";
        }
        if (finalPrice == -1){
            bonusNotice = "На вашем счету нет столько бонусных очков";
            return "reserveplacing";
        }
        bonusNotice = "";
        return "reserveplacing";
    }

    public String makeReserveTicketWithBonuses(){
        Client onlineClient = filmAction.findClientByAccount(registerAndAuthorizationBean.getAccountOnline());
        Ticket ticket = new Ticket(row, place, "Б", concretSessionBean.getCurrentSession(), onlineClient);
        if (countBonusScores <= 0){
            bonusNotice = "Необходимо ввести количество бонусных очков";
            return "reserveplacing";
        }
        if (finalPrice == -1){
            bonusNotice = "На вашем счету нет столько бонусных очков";
            return "reserveplacing";
        }
        //filmAction.reservePlace(onlineClient, ticket, concretSessionBean.getCurrentSession());
        filmAction.reservePlaceWithBonusPoints(registerAndAuthorizationBean.getAccountOnline(), concretSessionBean.getCurrentSession(),
                countBonusScores, finalPrice, onlineClient, ticket);
        bonusNotice = "";
        noticeForReserve = "Вы забронировали место под номером " + place + " на ряду " + row;
        return concretSessionBean.showBusyPlaces(
                concretSessionBean.getFilmsBean().getCurrentFilm(), concretSessionBean.getCurrentSession());
    }

    public String updatePage(){
        return "reserveplacing";
    }

    public int getRow() {
        return row;
    }

    public int getPlace() {
        return place;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setPayBonusScores(boolean payBonusScores) {
        this.payBonusScores = payBonusScores;
    }

    public boolean isPayBonusScores() {
        return payBonusScores;
    }

    public int getCountBonusScores() {
        return countBonusScores;
    }

    public void setCountBonusScores(int countBonusScores) {
        this.countBonusScores = countBonusScores;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getBonusNotice() { return bonusNotice; }

    public String getNoticeOverful() {
        return noticeOverful;
    }

    public String getNoticeForReserve() {
        return noticeForReserve;
    }
}
