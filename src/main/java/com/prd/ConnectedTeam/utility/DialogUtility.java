package com.prd.ConnectedTeam.utility;


import com.prd.ConnectedTeam.entity.Account;
import com.prd.ConnectedTeam.entityRepository.AccountRepository;
import com.prd.ConnectedTeam.gamesRules.Game;
import com.prd.ConnectedTeam.guess.gamesMenagemet.frondend.GuessUI;
import com.prd.ConnectedTeam.userOperation.HomeView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.server.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@HtmlImport("style.html")
@StyleSheet("frontend://stile/style.css")
public class DialogUtility extends VerticalLayout {

    private String control= "";

    public DialogUtility(){

    }

    public void loginDialog(Account account){
        Dialog dialog = new Dialog();
        Div content = new Div();
        Button accedi = new Button("Accedi");
        accedi.addClassName("my-style2");
        content.addClassName("my-style");
        content.setText("Registrazione completata");
        String styles = ".my-style { "
                + "margin-left: 60px;"
                + " }"
                + " .my-style2 { "
                + "margin-top: 27px;"
                + "margin-left: 110px;"
                + "cursor: pointer;"
                + "background-color: #007d99;"
                + "color: white;"
                + " }";

        StreamRegistration resource = UI.getCurrent().getSession()
                .getResourceRegistry()
                .registerResource(new StreamResource("styles.css", () -> {
                    byte[] bytes = styles.getBytes(StandardCharsets.UTF_8);
                    return new ByteArrayInputStream(bytes);
                }));
        UI.getCurrent().getPage().addStyleSheet(
                "base://" + resource.getResourceUri().toString());
        dialog.setWidth("300px");
        dialog.setHeight("120px");
        dialog.add(content,accedi);
        dialog.open();

        accedi.addClickShortcut(Key.ENTER);

        accedi.addClickListener(clickEvent -> {
            dialog.close();
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggato", true);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userId", account.getId());
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("user", account);

            UI.getCurrent().navigate(HomeView.class);
        });
    }

    public void passwordDimenticata(){

        Dialog dialog = new Dialog();
        Label label= new Label("Inserisci l'email, ti manderemo una password provvisoria");
        AccountRepository accountRepository = (AccountRepository) VaadinRequest.getCurrent().getWrappedSession().getAttribute("rep");
        FormLayout form= new FormLayout();
        TextField email= new TextField("Email");
        Button sendMail= new Button("Invia");
        sendMail.setEnabled(false);
        dialog.setWidth("400px");
        dialog.setHeight("120px");
        dialog.open();
        Binder<String> binder = new Binder<>();
        binder.setBean(control);
        Label validationStatus = new Label();
        binder.setStatusLabel(validationStatus);
        binder.forField(email)
                .asRequired("Inserisci L'e-mail")
                .withValidator(new EmailValidator("Indirizzo e-mail non valido"))
                .bind(s -> control, (s, v) -> control = v);
        binder.withValidator(Validator.from(account ->{
            if(email.isEmpty()){
                dialog.remove(validationStatus);
                return true;
            }else{
                Account a = accountRepository.findOneByEmail(email.getValue());
                if(a!=null){
                    return true;
                }else{
                    Div space = new Div();
                    space.setWidth("100%");
                    dialog.add(space,validationStatus);
                    return false;
                }
            }
        },"Email non esistente" ));
        binder.addStatusChangeListener(
                event -> sendMail.setEnabled(binder.isValid()));
        sendMail.addClickListener(clickEvent -> {
            Random rand = new Random();
            int n = rand.nextInt(9000) + 1000;
            accountRepository.updatePassword(email.getValue(), n + "");
            SendMail.sendMailTLS(email.getValue(), "Cambia password", "La nuova password è: " + n);
            InfoEventUtility infoEventUtility =  new InfoEventUtility();
            infoEventUtility.infoEvent("Email Inviata con successo","115");
            dialog.close();
        });
        dialog.add(label,form,email,sendMail);
    }

    public void partitaVincente(String parola, int punteggio, Game game){
        Dialog dialog = new Dialog();
        VerticalLayout content = new VerticalLayout();
        Image image;
        Label label;

        if(game.getNomeGioco() == "Guess") {
            image = new Image("frontend/img/Guess.jpeg", "guess");
            image.setWidth("200px");
            image.setHeight("200px");
            label = new Label("Hai  indovinato la parola: " + parola);
            label.getStyle().set("font-size", "30px");


        }else {
            image = new Image("frontend/img/Maty.jpeg", "maty");
            image.setWidth("200px");
            image.setHeight("200px");
            label = new Label("Hai  trovato la soluzione: " + parola);
            label.getStyle().set("font-size", "30px");

        }

        Label punti = new Label("Hai ottenuto: " + punteggio + " punti");
        punti.getStyle().set("font-size", "30px");
        Button listGiochi = new Button("Vai alla lista giochi");
        listGiochi.addClassName("my-style2");
        content.addClassName("my-style");
        content.setAlignItems(Alignment.CENTER);
        String styles = ".my-style { "
                + " }"
                + " .my-style2 { "
                + "cursor: pointer;"
                + "background-color: #007d99;"
                + "color: white;"
                + " }";
        StreamRegistration resource = UI.getCurrent().getSession()
                .getResourceRegistry()
                .registerResource(new StreamResource("styles.css", () -> {
                    byte[] bytes = styles.getBytes(StandardCharsets.UTF_8);
                    return new ByteArrayInputStream(bytes);
                }));
        UI.getCurrent().getPage().addStyleSheet(
                "base://" + resource.getResourceUri().toString());
        dialog.setWidth("600px");
        dialog.setHeight("420px");
        listGiochi.addClickListener(buttonClickEvent -> {
            GuessUI.reset();
            UI.getCurrent().navigate(HomeView.class);
            dialog.close();
            UI.getCurrent().getPage().reload(); //da aggiungere quando si è su pc o browser diversi
        });
        content.add(image,label,punti,listGiochi);
        dialog.add(content);
        dialog.open();
    }

    public void partitanonVincente(Game game){
        Label punti;
        Dialog dialog = new Dialog();
        VerticalLayout content = new VerticalLayout();
        Image image;
        Label label;

        if(game.getNomeGioco() == "Guess"){
            punti = new Label("Hai ottenuto: "+0+ " punti");
            image = new Image("frontend/img/Guess.jpeg", "guess");
            image.setWidth("200px");
            image.setHeight("200px");
            label = new Label("Hai perso, ritenta!");
            label.getStyle().set("font-size","30px");
            dialog.setWidth("600px");
            punti.getStyle().set("font-size","30px");
        }else {
            punti = new Label("Hai comunque ottenuto: "+5+ " punti");
            image = new Image("frontend/img/Maty.jpeg", "maty");
            image.setWidth("200px");
            image.setHeight("200px");
            label = new Label("Dai riprova! Impegnatevi di più la prossima volta!");
            Image image1 = new Image("frontend/img/smile.png", "smile");
            image1.setHeight("50px");
            image1.setWidth("70px");
            label.add(image1);
            label.getStyle().set("font-size","30px");
            dialog.setWidth("800px");
            punti.getStyle().set("font-size","30px");
        }

        Button listGiochi = new Button("Vai alla lista giochi");
        listGiochi.addClassName("my-style2");
        content.addClassName("my-style");
        content.setAlignItems(Alignment.CENTER);
        String styles = ".my-style { "
                + " }"
                + " .my-style2 { "
                + "cursor: pointer;"
                + "background-color: #007d99;"
                + "color: white;"
                + " }";
        StreamRegistration resource = UI.getCurrent().getSession()
                .getResourceRegistry()
                .registerResource(new StreamResource("styles.css", () -> {
                    byte[] bytes = styles.getBytes(StandardCharsets.UTF_8);
                    return new ByteArrayInputStream(bytes);
                }));
        UI.getCurrent().getPage().addStyleSheet(
                "base://" + resource.getResourceUri().toString());
        dialog.setHeight("420px");
        listGiochi.addClickListener(buttonClickEvent -> {
            GuessUI.reset();
            UI.getCurrent().navigate(HomeView.class);
            dialog.close();
            UI.getCurrent().getPage().reload(); //da aggiungere quando si è su pc o browser diversi
        });
        content.add(image,label,punti,listGiochi);
        dialog.add(content);
        dialog.open();
    }

    public void partitaTerminata(Account account){

        Dialog dialog = new Dialog();
        Label label = new Label("Partita terminta da: "+account.getNome());
        Button button = new Button("Vai alla home");
        button.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate(HomeView.class);
            dialog.close();
            UI.getCurrent().getPage().reload();
        });
        button.getStyle().set("margin-left","20px");
        dialog.add(label,button);
        dialog.open();

    }

}
