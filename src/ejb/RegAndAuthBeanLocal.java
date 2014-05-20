package ejb;

import model.Account;
import model.Client;

import javax.ejb.Local;

@Local
public interface RegAndAuthBeanLocal {
    public boolean register(Client client, Account account);
    public boolean checkOnTwoMatchesOfLogin(String login);
    public Account checkLoginAndPassword(String login, String password);
}
