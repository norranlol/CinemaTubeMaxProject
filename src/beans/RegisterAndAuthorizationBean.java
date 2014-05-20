package beans;

import ejb.RegAndAuthBeanLocal;
import model.Account;
import model.Client;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Named("registerAndAuthorizationBean")
@SessionScoped
public class RegisterAndAuthorizationBean implements Serializable {

    @EJB
    private RegAndAuthBeanLocal regAndAuthBean;

    @Size(min = 2,max = 30,message = "Фамилия должна быть от 2 до 30 символов")
    private String surname;

    @Size(min = 2,max = 30,message = "Имя должно быть от 2 до 30 символов")
    private String name;

    @Size(min = 2,max = 30,message = "Отчество должно быть от 2 до 30 символов")
    private String patronymic;

    @Size(min = 2,max = 30,message = "Логин должен быть от 2 до 30 символов")
    private String login;

    @Size(min = 2,max = 30,message = "Пароль должен быть от 2 до 30 символов")
    private String password;

    @Size(min = 2, max = 30, message = "Пароль должен быть от 2 до 30 символов")
    private String passwordRepeat;

    @Size(min = 2, max =  100, message = "Секретный вопрос должен быть от 2 до 100 символов")
    private String secretQuestion;

    @Size(min = 2, max =  50, message = "Секретный ответ должен быть от 2 до 50 символов")
    private String secretAnswer;

    @Size(min = 6, max = 11, message = "Номер телефона должен быть от 6 до 11 символов")
    private String phoneNumber;

    private Date dateOfBirth;

    private Account accountOnline = null;

    public String register(){
        Account account = new Account(login,password,secretQuestion,secretAnswer,new Date());
        Client client = new Client(surname, name, patronymic, dateOfBirth, phoneNumber, account);
        boolean result = regAndAuthBean.register(client,account);
        if (result)
            return "about";
        return  "register";
    }

    public String authorize(){
        accountOnline = regAndAuthBean.checkLoginAndPassword(login,password);
        return "about";
    }

    public String logout(){
        accountOnline = null;
        return "about";
    }

    public void validatePassword(ComponentSystemEvent event)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? ""
                : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("repeatPassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }
        if (!password.equals(confirmPassword))
        {
            FacesMessage msg = new FacesMessage("Пароль и повторение пароля должны совпадать");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(passwordId, msg);
            fc.renderResponse();
        }
    }

    public void validateNoExistingOfLogin(ComponentSystemEvent event)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        UIInput uiInputLogin = (UIInput) components.findComponent("login");
        String login = uiInputLogin.getLocalValue() == null ? ""
                : uiInputLogin.getLocalValue().toString();
        String loginId = uiInputLogin.getClientId();
        if (!regAndAuthBean.checkOnTwoMatchesOfLogin(login))
        {
            FacesMessage msg = new FacesMessage("Пользователь с таким логином уже зарегистрирован");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(loginId, msg);
            fc.renderResponse();
        }

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Account getAccountOnline() {
        return accountOnline;
    }
}
