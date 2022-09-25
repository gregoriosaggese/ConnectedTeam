package com.prd.ConnectedTeam.mainView;



import com.prd.ConnectedTeam.entity.Account;
import com.prd.ConnectedTeam.entityRepository.AccountRepository;
import com.prd.ConnectedTeam.entityRepository.PartitaRepository;
import com.prd.ConnectedTeam.error.ErrorPage;
import com.prd.ConnectedTeam.gamesRules.GameList;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.db.ItemRepository;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.db.ItemRepositoryMaty;
import com.prd.ConnectedTeam.nuovoGioco.gameManagement.database.ItemRepositoryNuovoGioco;
import com.prd.ConnectedTeam.userOperation.HomeView;
import com.prd.ConnectedTeam.utility.DialogUtility;
import com.prd.ConnectedTeam.utility.InfoEventUtility;
import com.prd.ConnectedTeam.utility.SendMail;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Objects;
import java.util.Random;


@PWA(name = "Connecteam", shortName = "Connecteam",
        offlinePath = "offline-page.html",
        iconPath = "icon.png")
@Route
@HtmlImport("style.html")
@StyleSheet("frontend://stile/style.css")
@PageTitle("ConnecTeam")
public class MainView extends VerticalLayout {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PartitaRepository partitaRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemRepositoryMaty itemRepositoryMaty;

    @Autowired
    private ItemRepositoryNuovoGioco itemRepositoryNuovoGioco;

    @Autowired
    private GameList gameList;

    private TextField email;
    private String confermaReg;
    private Button b;

    public MainView() {

        try {
            confermaReg = VaadinRequest.getCurrent().getParameter("confermaRegistrazione");
            if(confermaReg!=null){
                completaRegistrazione(confermaReg);
            }
            else {

                Image i = new Image("frontend/img/pc.png", "txt");
                Image img = new Image("frontend/img/logocompleto.png", "txt");
                //Image image22 = new Image("frontend/img/sigla.png", "txt");
                //image22.setWidth("200px");
                //image22.getElement().getStyle().set("position", "absolute");
                //image22.getElement().getStyle().set("left", "180px");
                img.setWidth("400px");
                img.setHeight("120px");
                img.getStyle().set("top","5px");
                img.getElement().getStyle().set("position", "absolute");

                i.getElement().getStyle().set("position", "absolute");
                i.getElement().getStyle().set("width", "43%");
                i.getElement().getStyle().set("height", "55%");
                i.getElement().getStyle().set("top", "35%");
                i.getElement().getStyle().set("opacity", "0.5");
                i.getElement().getStyle().set("left", "200px");
                setAlignSelf(Alignment.START, i);
                setSizeFull();
                getElement().getStyle().set("background-color", "#b8dbe0");
                Div descrizione = new Div();
                Label descr = new Label("\n" +
                        "ConnecTeam è una piattaforma web che ha come obiettivo l'apprendimento collaborativo tramite dei giochi." +
                        " I giochi possono essere per qualsiasi fascia d'età e di solito sono prettamente educativi." +
                        " La collaborazione è fondamentele per ConnecTeam!" +
                        " Inoltre non mancano i servizi per l'utente, il quale troverà molto semplice gestire il suo profilo!");
                descr.setWidth("50%");
                descrizione.setWidth("64%");
                descrizione.setHeight("10%");
                descrizione.add(descr);
                descrizione.getElement().getStyle().set("position", "absolute");
                descrizione.getElement().getStyle().set("top", "150px");
                login();
                register();
                add(/*image22,*/descrizione,img,i);

                /*Button button = new Button();
                button.addClickListener(buttonClickEvent -> {
                    buttons();
                });
                add(button);*/

                /*UI.getCurrent().getPage().addBrowserWindowResizeListener(browserWindowResizeEvent -> {
                    Notification.show("Window width=" + browserWindowResizeEvent.getWidth()
                            + ", height=" + browserWindowResizeEvent.getHeight());
                });*/
            }

        }catch (Exception e){
            removeAll();
            ErrorPage errorPage = new ErrorPage();
            add(errorPage);
            e.printStackTrace();
        }
    }


    private void login(){

        TextField emailField = new TextField("Email");
        PasswordField passwordField = new PasswordField("Password");
        passwordField.getElement().getStyle().set("margin-left","10px");
        passwordField.getElement().getStyle().set("margin-right","10px");
        Button login = new Button("Accedi");
        login.setEnabled(false);
        Binder<Account> binder = new Binder<>();
        binder.forField(emailField)
                .asRequired("Inserisci l'E-mail")
                .withValidator(new EmailValidator("Indirizzo E-mail non valido"))
                .bind(Account::getEmail, Account::setEmail);
        binder.forField(passwordField)
                .asRequired("Inserisci la Password")
                .bind(Account::getPassword, Account::setPassword);
        binder.addStatusChangeListener(
                event -> login.setEnabled(binder.isValid()));

        login.addClickShortcut(Key.ENTER);

        login.addClickListener(buttonClickEvent -> {
            Account a = accountRepository.findOneByEmail(emailField.getValue());
            if ( a != null && a.getPassword().equals(passwordField.getValue())) {
                VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggato", true);
                VaadinService.getCurrentRequest().getWrappedSession().
                        setAttribute("userId", accountRepository.findOneByEmail(emailField.getValue()).getId());
                VaadinService.getCurrentRequest().getWrappedSession().
                        setAttribute("user", accountRepository.findOneByEmail(emailField.getValue()));
                VaadinService.getCurrentRequest().getWrappedSession().setAttribute("rep",accountRepository);
                VaadinService.getCurrentRequest().getWrappedSession().setAttribute("partitaRepository",partitaRepository);
                VaadinService.getCurrentRequest().getWrappedSession().setAttribute("gameList",gameList);
                VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepository",itemRepository);
                VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepositoryMaty",itemRepositoryMaty);
                VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepositoryNuovoGioco",itemRepositoryNuovoGioco);
                UI.getCurrent().navigate(HomeView.class);
            }else {
                InfoEventUtility infoEventUtility =  new InfoEventUtility();
                infoEventUtility.infoEvent("E-mail e/o password errati","100");
            }
        });

        RouterLink passwordDimenticata = new RouterLink();
        passwordDimenticata.setText("Password dimenticata?");
        passwordDimenticata.add(new Button("Clicca qui",buttonClickEvent -> {
            VaadinRequest.getCurrent().getWrappedSession().setAttribute("rep",accountRepository);
            DialogUtility dialogUtility = new DialogUtility();
            dialogUtility.passwordDimenticata();
        }));

        Div space = new Div();
        space.setWidth("100%");
        VerticalLayout divForm = new VerticalLayout();

        Span s = new Span();
        s.getStyle().set("background-color", "#007d99");
        s.getStyle().set("flex", "0 0 2px");
        s.getStyle().set("align-self", "center");
        s.getStyle().set("width", "90%");
        s.getStyle().set("margin-top", "10px");

        Div divContainerForm = new Div();
        divContainerForm.add(emailField,passwordField,login,space,passwordDimenticata);
        divForm.setHorizontalComponentAlignment(Alignment.END,divContainerForm);
        divForm.add(divContainerForm,s);
        add(divForm);
    }

    private void register(){

        Label label = new Label("Crea un nuovo account");
        label.getElement().getStyle().set("font-size","30px");

        TextField nome = new TextField("Nome");
        email = new TextField("Email");
        email.getElement().getStyle().set("margin-left","10px");
        PasswordField password = new PasswordField("Password");
        PasswordField passwordC = new PasswordField("Conferma Password");
        passwordC.getElement().getStyle().set("margin-left","10px");
        Button submit = new Button("Registrati");
        submit.getElement().getStyle().set("margin-left","25px");
        submit.getElement().getStyle().set("margin-top","25px");
        RadioButtonGroup<String> group = new RadioButtonGroup<>();
        group.setLabel("Sesso");
        group.setItems("Uomo", "Donna");
        Binder<Account> binder = new Binder<>();

        binder.forField(nome)
                .asRequired("Inserisci il Nome")
                .bind(Account::getNome, Account::setNome);

        binder.forField(email)
                .asRequired("Inserisci l'E-mail")
                .withValidator(new EmailValidator("Indirizzo e-mail non valido"))
                .bind(Account::getEmail, Account::setEmail);

        binder.forField(password)
                .asRequired("Inserisci Password")
                .withValidator(new StringLengthValidator(
                        "Almeno 7 caretteri", 7, null))
                .bind(Account::getPassword, Account::setPassword);

        binder.forField(passwordC)
                .asRequired("Conferma Password")
                .withValidator(Validator.from(account -> {
                    if (password.isEmpty() || passwordC.isEmpty()) {
                        return true;
                    } else {
                        return Objects.equals(password.getValue(),
                                passwordC.getValue());
                    }
                }, "Le password non coincidono"))
                .bind(Account::getPassword, (person, password1) -> {});

        binder.forField(group)
                .asRequired("Sesso")
                .bind(Account::getSesso, Account::setSesso);

        Label validationStatus = new Label();
        binder.setStatusLabel(validationStatus);
        binder.setBean(new Account());
        submit.setEnabled(false);

        binder.addStatusChangeListener(
                event -> submit.setEnabled(binder.isValid()));

        VerticalLayout divForm = new VerticalLayout();

        Div divContainerForm = new Div();

        submit.addClickListener(buttonClickEvent -> {
            if (group.getValue()=="Uomo"){
                binder.getBean().setSesso("0");
            }else {
                binder.getBean().setSesso("1");
            }
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("rep",accountRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("partitaRepository",partitaRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("gameList",gameList);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepository",itemRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepositoryMaty",itemRepositoryMaty);
            registraNewAccount(binder.getBean());
        });

        divContainerForm.getElement().getStyle().set("margin-right","100px");
        divContainerForm.getElement().getStyle().set("position","relative");
        divContainerForm.getElement().getStyle().set("top","-20px");

        Div space = new Div();
        space.setWidth("100%");
        Div space2 = new Div();
        space2.setWidth("100%");
        Div space3 = new Div();
        space3.setWidth("100%");

        divContainerForm.add(label,space3,nome,email,space,password,passwordC,space2,group,submit);
        divForm.setHorizontalComponentAlignment(Alignment.END,divContainerForm);

        divForm.add(divContainerForm);
        add(divForm);
    }

    private void registraNewAccount(Account account){

        AccountRepository a = (AccountRepository) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("rep");
        if (a.findOneByEmail(account.getEmail()) == null) {
            Random random = new Random();
            int n = random.nextInt(9000) + 1000;
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("codiceRegistrazione", "" + n);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("accountDaRegistrare", account);
            SendMail.sendMailTLS(email.getValue(), "ConnecTeam-Conferma E-mail",
                    "clicca sul link per completare la registrazione " +
                            "http://localhost:8080/?confermaRegistrazione=" + n);
            InfoEventUtility infoEventUtility = new InfoEventUtility();
            infoEventUtility.infoEvent("Ti abbiamo inviato un'e-mail all'indirizzo: "+ email.getValue(),"0");
        }else {
            InfoEventUtility infoEventUtility = new InfoEventUtility();
            infoEventUtility.infoEvent("E-mail esistente, riprova","100");
        }
    }

    private void completaRegistrazione(String conferma){

        String confermaAttribute = (String) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("codiceRegistrazione");
        if(confermaAttribute==null || !confermaAttribute.equals(conferma)){
            InfoEventUtility infoEventUtility = new InfoEventUtility();
            infoEventUtility.infoEvent("Impossibile completare la registrazione","100");
            return;
        }
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("codiceRegistrazione", null);
        Account account= (Account) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("accountDaRegistrare");
        if(account==null){
            InfoEventUtility infoEventUtility = new InfoEventUtility();
            infoEventUtility.infoEvent("Account non valido","100");
            return;
        }
        AccountRepository a = (AccountRepository) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("rep");
        try{
            a.save(account);
        }catch (Exception e){
            InfoEventUtility infoEventUtility = new InfoEventUtility();
            infoEventUtility.infoEvent("Account non valido","100");
            return;
        }
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("accountDaRegistrare", null);
        DialogUtility dialogUtility = new DialogUtility();
        dialogUtility.loginDialog(account);
    }

    /*private void buttons(){

        b = new Button("gregorio");
        add(b);
        b.addClickListener(buttonClickEvent -> {

            Account a = accountRepository.findOneByEmail("gregorio@gmail.com");
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggato", true);
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("userId", accountRepository.findOneByEmail("gregorio@gmail.com").getId());
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("user", accountRepository.findOneByEmail("gregorio@gmail.com"));
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("rep",accountRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("partitaRepository",partitaRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("gameList",gameList);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepository",itemRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepositoryMaty",itemRepositoryMaty);

            UI.getCurrent().navigate(HomeView.class);
        });

        b = new Button("luigi");
        add(b);
        b.addClickListener(buttonClickEvent -> {
            Account a = accountRepository.findOneByEmail("luigi@gmail.com");
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggato", true);
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("userId", accountRepository.findOneByEmail("luigi@gmail.com").getId());
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("user", accountRepository.findOneByEmail("luigi@gmail.com"));
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("rep",accountRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("partitaRepository",partitaRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("gameList",gameList);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepository",itemRepository);

            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepositoryMaty",itemRepositoryMaty);

            UI.getCurrent().navigate(HomeView.class);
        });

        b = new Button("michela");
        add(b);
        b.addClickListener(buttonClickEvent -> {
            Account a = accountRepository.findOneByEmail("michela@gmail.com");
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggato", true);
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("userId", accountRepository.findOneByEmail("michela@gmail.com").getId());
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("user", accountRepository.findOneByEmail("michela@gmail.com"));
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("rep",accountRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("partitaRepository",partitaRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("gameList",gameList);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepository",itemRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepositoryMaty",itemRepositoryMaty);

            UI.getCurrent().navigate(HomeView.class);
        });

        b = new Button("francesca");
        add(b);
        b.addClickListener(buttonClickEvent -> {
            Account a = accountRepository.findOneByEmail("francesca@gmail.com");
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggato", true);
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("userId", accountRepository.findOneByEmail("francesca@gmail.com").getId());
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("user", accountRepository.findOneByEmail("francesca@gmail.com"));
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("rep",accountRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("partitaRepository",partitaRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("gameList",gameList);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepository",itemRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepositoryMaty",itemRepositoryMaty);

            UI.getCurrent().navigate(HomeView.class);
        });

        b = new Button("antonio");
        add(b);
        b.addClickListener(buttonClickEvent -> {
            Account a = accountRepository.findOneByEmail("antonio@gmail.com");
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggato", true);
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("userId", accountRepository.findOneByEmail("antonio@gmail.com").getId());
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("user", accountRepository.findOneByEmail("antonio@gmail.com"));
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("rep",accountRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("partitaRepository",partitaRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("gameList",gameList);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepository",itemRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepositoryMaty",itemRepositoryMaty);

            UI.getCurrent().navigate(HomeView.class);
        });

        b = new Button("gianluca");
        add(b);
        b.addClickListener(buttonClickEvent -> {
            Account a = accountRepository.findOneByEmail("gianluca@gmail.com");
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggato", true);
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("userId", accountRepository.findOneByEmail("gianluca@gmail.com").getId());
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("user", accountRepository.findOneByEmail("gianluca@gmail.com"));
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("rep",accountRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("partitaRepository",partitaRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("gameList",gameList);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepository",itemRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepositoryMaty",itemRepositoryMaty);

            UI.getCurrent().navigate(HomeView.class);
        });

        b = new Button("simone");
        add(b);
        b.addClickListener(buttonClickEvent -> {
            Account a = accountRepository.findOneByEmail("simone@gmail.com");
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("loggato", true);
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("userId", accountRepository.findOneByEmail("simone@gmail.com").getId());
            VaadinService.getCurrentRequest().getWrappedSession().
                    setAttribute("user", accountRepository.findOneByEmail("simone@gmail.com"));
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("rep",accountRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("partitaRepository",partitaRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("gameList",gameList);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepository",itemRepository);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("itemRepositoryMaty",itemRepositoryMaty);

            UI.getCurrent().navigate(HomeView.class);
        });

    }*/


}
