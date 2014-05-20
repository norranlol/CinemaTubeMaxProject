package beans;

import model.Commentary;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CommentariesBean implements Serializable {

    @Inject
    private FilmsBean filmsBean;

    @Inject
    private RegisterAndAuthorizationBean registerAndAuthorizationBean;

    @Size(min = 5, max = 400,message = "Комментарий не может быть меньше 5 символов или больше 500 символов")
    private String comment;

    private List<Commentary> listOfCommentaries;

    public List<Commentary> getListOfCommentaries() {
        listOfCommentaries = filmsBean.getFilmAction().showAllCommentariesOfFilm(filmsBean.getCurrentFilm());
        return listOfCommentaries;
    }

    public String addCommentary(){
        Commentary newCommentary = new Commentary(comment,registerAndAuthorizationBean.getAccountOnline(),
                null);
        filmsBean.getFilmAction().addCommentaryToFilm(newCommentary,filmsBean.getCurrentFilm());
        listOfCommentaries = filmsBean.getFilmAction().showAllCommentariesOfFilm(filmsBean.getCurrentFilm());
        comment = "";
        return filmsBean.goToCommentariesPage(filmsBean.getCurrentFilm());
    }

    public boolean checkCommentOnAccount(Commentary commentary){
        if (commentary.getAccount().getLogin().equals(registerAndAuthorizationBean.getAccountOnline().getLogin()))
            return true;
        else return false;
    }

    public String deleteCommentary(Commentary commentary){
        filmsBean.getFilmAction().deleteCommentary(commentary, filmsBean.getCurrentFilm());
        listOfCommentaries = filmsBean.getFilmAction().showAllCommentariesOfFilm(filmsBean.getCurrentFilm());
        comment = "";
        return filmsBean.goToCommentariesPage(filmsBean.getCurrentFilm());
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}
