package com.prd.ConnectedTeam.userOperation;

import com.prd.ConnectedTeam.entity.Account;
import com.prd.ConnectedTeam.entity.Partita;
import com.prd.ConnectedTeam.entity.Punteggio;
import com.prd.ConnectedTeam.entityRepository.AccountRepository;
import com.prd.ConnectedTeam.entityRepository.PartitaRepository;
import com.prd.ConnectedTeam.error.ErrorPage;
import com.prd.ConnectedTeam.gamesRules.Game;
import com.prd.ConnectedTeam.gamesRules.GameList;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinService;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


@Route("HomeView")
@HtmlImport("style.html")
@StyleSheet("frontend://stile/stile.css")
@StyleSheet("frontend://stile/style.css")
@JavaScript("frontend://js/script.js")
@PageTitle("ConnecTeam")
public class HomeView extends HorizontalLayout {

    private Account account;
    private PartitaRepository partitaRepository;
    private GameList gameList;
    private Image image;
    private AccountRepository accountRepository;
    private Map<String, List<String>> parametersMap;

    public HomeView() {

        try{

            NavBar navBar = new NavBar();
            add(navBar);
            VerticalLayout main1 = new VerticalLayout();
            main1.addClassName("main1");
            VerticalLayout main = new VerticalLayout();
            main.setWidth(null);
            main.add(homeUser());
            main1.add(main);
            add(main1);

        }catch (Exception e){
            removeAll();
            getStyle().set("background-color","white");
            ErrorPage errorPage = new ErrorPage();
            add(errorPage);
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    private HorizontalLayout homeUser() {

        HorizontalLayout main = new HorizontalLayout();
        main.addClassName("positioning");
        accountRepository = (AccountRepository) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("rep");
        account = (Account) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user");
        partitaRepository = (PartitaRepository) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("partitaRepository");
        gameList = (GameList) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("gameList");
        VerticalLayout verticalLayout;
        verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(false);
        verticalLayout.addClassName("positioning2");
        HorizontalLayout layoutWelcome = new HorizontalLayout();
        layoutWelcome.addClassName("banner");
        String welcome=null;

        if(account.getSesso().equals("1")){
            welcome = "Benvenuta ";
            if(account.getProfilePicture()==null){
                image = new Image("frontend/img/profiloGirl.png", "foto profilo");
                image.addClassName("welcomeProfileImg");
                image.setWidth("170px");
                image.setHeight("170px");
            }else {
                welcome = "Benvenuta ";
                image = generateImage(account);
                image.addClassName("welcomeProfileImg");
                image.setWidth("170px");
                image.setHeight("170px");
            }

        }else if (account.getSesso().equals("0")) {
            if(account.getProfilePicture()==null){
                welcome = "Benvenuto ";
                image = new Image("frontend/img/profiloBoy.png", "foto profilo");
                image.addClassName("welcomeProfileImg");
                image.setWidth("170px");
                image.setHeight("170px");
            }else {
                welcome = "Benvenuto ";
                image = generateImage(account);
                image.addClassName("welcomeProfileImg");
                image.setWidth("170px");
                image.setHeight("170px");
            }
        }

        VerticalLayout layoutNomeEPartita = new VerticalLayout();
        layoutNomeEPartita.add(image);

        Label nomeGiocatore = new Label(welcome + account.getNome());
        layoutNomeEPartita.addClassName("layoutNomeEPartita");
        layoutNomeEPartita.add(nomeGiocatore);
        nomeGiocatore.addClassName("welcomeLabel");

        Partita partita = partitaRepository.lastPartita(account);
        System.out.println("Partita: " + partita);


        if (partita == null) {
            Label label1 = new Label("Non hai ancora effettuato una partita");
            layoutNomeEPartita.add(label1);
        } else {

            VerticalLayout partitaInfo = new VerticalLayout();
            partitaInfo.addClassName("WelcomePartita");
            String s = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(partita.getTimestamp());
            Label ultimaPartita = new Label("Ultima Partita: "+partita.getGioco() + " " + s);
            List<Punteggio> punteggi = partita.getArray();
            int n = 0;
            for (Punteggio p : punteggi) {
                if (p.getAccount().getId() == account.getId()) {
                    n = p.getPunti();
                    break;
                }
            }
            Label punti = new Label("Punteggio: " + n);
            punti.getStyle().set("margin","0");
            partitaInfo.add(ultimaPartita, punti);
            layoutNomeEPartita.add(partitaInfo);
        }
        layoutWelcome.add(layoutNomeEPartita);
        verticalLayout.add(layoutWelcome);
        main.add(verticalLayout);

        List<Game> list= gameList.getGameList();
        for(Game g:list) {
            main.add(creaLayoutGioco(g, ""));
        }
        return main;
    }

    private VerticalLayout creaLayoutGioco (Game game, String left){

        VerticalLayout layoutGioco = new VerticalLayout();
        layoutGioco.addClassName("layoutWelcomeGame");
        Image img = new Image("frontend/img/"+game.getNomeGioco()+".jpeg", "logo gioco");
        img.addClassName("imgGame");
        Label gameName = new Label(game.getNomeGioco());
        gameName.addClassName("WelcomeGameName");
        layoutGioco.add(gameName,img);
        Label gameDescr = new Label(game.getDescrizioneGioco());
        gameDescr.addClassName("gameDescrWelcome");
        Label ageRande = new Label(game.getAgeRange());
        layoutGioco.add(ageRande,gameDescr);
        layoutGioco.getStyle().set("left",left);
        layoutGioco.getStyle().set("width","30%");
        VerticalLayout verticalLayout = new VerticalLayout();

        Button play = new Button("Gioca");

        play.addClickListener(clickEvent -> {
            try {
                System.out.println(UI.getCurrent().getPage());
                String expression = "window.open(\"http://localhost:8080/"+game.getPathName()+"\");";
                UI.getCurrent().getPage().executeJavaScript(expression);
                /*Map<String,List<String>> stringMap = new HashMap();
                stringMap.put("cod", Collections.singletonList(broadcasterId));
                QueryParameters queryParameters = new QueryParameters(stringMap);
                UI.getCurrent().navigate("guess",queryParameters);*///passaggio parametri
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        });

        verticalLayout.add(play);
        verticalLayout.setAlignItems(Alignment.CENTER);
        layoutGioco.setAlignItems(Alignment.CENTER);
        layoutGioco.add(verticalLayout);
        return layoutGioco;
    }

    public Image generateImage(Account account) {
        Long id = account.getId();
        StreamResource sr = new StreamResource("user", () ->  {
            Account attached = accountRepository.findWithPropertyPictureAttachedById(id);
            return new ByteArrayInputStream(attached.getProfilePicture());
        });
        sr.setContentType("image/png");
        Image image = new Image(sr, "profile-picture");
        return image;

    }

}
