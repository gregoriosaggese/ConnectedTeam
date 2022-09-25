package com.prd.ConnectedTeam.guess.gamesMenagemet.frondend;

import com.prd.ConnectedTeam.entity.Account;
import com.prd.ConnectedTeam.entity.Partita;
import com.prd.ConnectedTeam.entityRepository.AccountRepository;
import com.prd.ConnectedTeam.entityRepository.PartitaRepository;
import com.prd.ConnectedTeam.error.ErrorPage;
import com.prd.ConnectedTeam.gamesRules.Game;
import com.prd.ConnectedTeam.games.Guess;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.GuessController;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.broadcaster.Broadcaster;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.broadcaster.BroadcasterChat;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.broadcaster.BroadcasterSuggerisci;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.db.Item;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.listeners.BroadcastListener;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.listeners.ChatListener;
import com.prd.ConnectedTeam.utility.DialogUtility;
import com.prd.ConnectedTeam.utility.FireWorks;
import com.prd.ConnectedTeam.utility.InfoEventUtility;
import com.prd.ConnectedTeam.utility.MessageList;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinService;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.Date;

@Push
@Route("guess")
@HtmlImport("chat.html")
@StyleSheet("frontend://stile/stile.css")
@StyleSheet("frontend://stile/style.css")
@StyleSheet("frontend://stile/chat.css")
@PageTitle("ConnecTeam-Guess")
public class GuessUI extends HorizontalLayout implements BroadcastListener, ChatListener, PageConfigurator {

    private AccountRepository accountRepository;
    private Account account;
    private GuessController guessController;
    private Label numeroUtenti = new Label();
    private Div containerUtenti = new Div();
    private Button start = new Button("Gioca");
    private Image imageU = new Image();
    private VerticalLayout chatMessages = new VerticalLayout();
    private Label secondi = new Label();
    private Label indizio = new Label("Indizi: ");
    private VerticalLayout players = new VerticalLayout();
    private VerticalLayout secondContainer = new VerticalLayout();
    private VerticalLayout verticalLayout = new VerticalLayout();
    private Div containerParoleVotate = new Div();
    private Guess guess;
    private PartitaRepository partitaRepository;
    private Partita partita;
    private GuessController.PartitaThread partitaThread;
    private Item item;
    boolean isStarted = false;
    boolean isRegisterd = false;
    private Div chat = new Div();
    MessageList messageList = new MessageList("chatlayoutmessage2");
    Label label1;
    private Image image333;

    public GuessUI() {

        try {

            guess = new Guess();
            partitaRepository = (PartitaRepository) VaadinService.getCurrentRequest().
                    getWrappedSession().getAttribute("partitaRepository");
            guessController = new GuessController(partitaRepository);
            accountRepository = (AccountRepository) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("rep");
            account = (Account) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user");
            setId("GuessUI");
            for (int i = 0; i < Broadcaster.getPartiteThread().size(); i++) {
                if (Broadcaster.getPartiteThread().get(i) != null) {
                    isStarted = true;
                }
            }
            if (isStarted != true) {
                Broadcaster.register(account, this);
                BroadcasterChat.register(this);
                Broadcaster.aggiornaUtentiConnessi(UI.getCurrent());
                Broadcaster.addUsers(UI.getCurrent());
            } else {
                InfoEventUtility infoEventUtility = new InfoEventUtility();
                infoEventUtility.infoEvent("C'è una partita in corso aspetta che finisca", "0");
            }

            secondContainer.setHorizontalComponentAlignment(Alignment.CENTER, start);
            Image image = new Image("frontend/img/Guess.jpeg", "guess");
            image.setWidth("200px");
            image.setHeight("200px");
            VerticalLayout descrizionegioco = new VerticalLayout();
            descrizionegioco.add(image);
            descrizionegioco.setWidth("50%");
            Paragraph descrizione = new Paragraph();
            descrizione.setText(guess.getDescrizioneLungaGioco());
            descrizionegioco.add(descrizione);
            start.addClassName("goPlayGuess");
            secondContainer.add(descrizionegioco);
            add(secondContainer);
            players.getStyle().set("position", "absolute");
            players.getStyle().set("top", "400px");
            players.setWidth(null);
            players.add(numeroUtenti, containerUtenti);
            numeroUtenti.getStyle().set("font-size", "30px");
            players.add(start);
            add(players);


            Div device = new Div();
            Label label = new Label("Chat");
            label.getStyle().set("font-size", "30px");
            device.add(label);
            device.setId("device");


            chat.addClassName("chat");



            TextField message1 = new TextField();
            Icon icon = VaadinIcon.PAPERPLANE_O.create();
            icon.setColor("white");

            Button send = new Button(icon);
            message1.addKeyDownListener(Key.ENTER, keyDownEvent -> {
                String mess = message1.getValue();
                if (!mess.equals("")) {
                    BroadcasterChat.broadcast(account.getNome() + ": " + message1.getValue()+":"+account.getId());
                    message1.setValue("");
                }
            });
            message1.getStyle().set("width","85%");
            message1.getStyle().set("margin-right","16px");
            send.addClickListener(buttonClickEvent -> {
                String mess = message1.getValue();
                if (!mess.equals("")) {
                    BroadcasterChat.broadcast(account.getNome() + ": " + message1.getValue()+":"+account.getId());
                    message1.setValue("");
                }
            });
            send.addClassName("buttonSendChat");

            chat.add(messageList);
            device.add(chat);
            device.add(message1);
            device.add(send);
            add(device);
            containerUtenti.addClassName("layoutUsers");
            containerParoleVotate.addClassName("containerParoleVotate");

            label1 = new Label(account.getNome());
            label1.getStyle().set("position","absolute");
            label1.getStyle().set("top","140px");
            label1.getStyle().set("left","230px");
            label1.getStyle().set("font-size","40px");
            add(label1);

            start.addClickListener(buttonClickEvent -> {
                for (int i = 0; i < Broadcaster.getPartiteThread().size(); i++) {
                    if (Broadcaster.getPartiteThread().get(i) != null) {
                        isStarted = true;
                    }
                }
                if (isStarted != true) {
                    partita = new Partita(new Timestamp(new Date().getTime()), "Guess");
                    guessController.startGame(partita);
                    partitaThread = guessController.getPartitaThread();
                    item = guessController.getItem();
                    Broadcaster.startGame(partitaThread, item);
                } else {
                    InfoEventUtility infoEventUtility = new InfoEventUtility();
                    infoEventUtility.infoEvent("C'è una partita in corso aspetta che finisca", "10");
                }
            });

        }

        catch (Exception e) {
            removeAll();
            ErrorPage errorPage = new ErrorPage();
            add(errorPage);
            e.printStackTrace();
        }
    }

    @Override
    public void receiveBroadcast(String message) {
        getUI().get().access(() -> {


            String string = message;
            String[] parts = string.split(":");
            String nome = parts[0]+":";
            String testo = parts[1];
            String id = parts[2];
            Account a = accountRepository.findAccountById(Long.parseLong(id));
            Div divmessage = new Div();
            Div nomeP = new Div();
            Label label = new Label(nome);

            nomeP.add(label);
            label.getStyle().set("margin-right","20px");

            Div div = new Div();
            Paragraph paragraph = new Paragraph(testo);
            div.add(paragraph);
            divmessage.addClassName("message");
            if (a.getProfilePicture()!=null){
                image333 = generateImage(a);
                image333.getStyle().set("order","0");
                divmessage.add(image333);
            }else {
                if(a.getSesso()=="1"){
                    image333 = new Image("frontend/img/profiloGirl.png", "foto profilo");
                    image333.getStyle().set("order","0");
                    divmessage.add(image333);
                }
                else {
                    image333 = new Image("frontend/img/profiloBoy.png", "foto profilo");
                    image333.getStyle().set("order","0");
                    divmessage.add(image333);
                }
            }
            divmessage.add(label,div);
            messageList.add(divmessage);




        });
    }

    @Override
    public void countUser(UI ui, String nome) {
        ui.getUI().get().access(() -> {
            numeroUtenti.setEnabled(false);
            numeroUtenti.setText("Utenti connessi: "+Broadcaster.getListeners().size());
            numeroUtenti.setEnabled(true);
        });
    }

    @Override
    public void startGame1() {
        try {
            getUI().get().access(() -> {
                remove(players);
                remove(secondContainer);
                remove(label1);
                StartGameUI startGameUI = new StartGameUI(guessController);
                verticalLayout.add(startGameUI);
                verticalLayout.add(secondi,indizio);
                indizio.getStyle().set("font-size","30px");
                indizio.getStyle().set("margin-left","15px");
                add(verticalLayout);
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void receiveIndizio(String message) {
        getUI().get().access(() -> {
            Paragraph paragraph = new Paragraph(message);
            paragraph.getStyle().set("font-size","18px");
            paragraph.getStyle().set("margin","5px 0px 0px 15px");
            verticalLayout.add(paragraph);
        });
    }

    @Override
    public void countDown(String n) {
        getUI().get().access(() -> {
            secondi.setEnabled(false);
            secondi.setText("Time: "+ n+" secondi");
            secondi.getStyle().set("font-size","30px");
            secondi.getStyle().set("margin-left","15px");
            secondi.setEnabled(true);
        });
    }

    @Override
    public void addUsers(UI ui, int in) {
        ui.getUI().get().access(() -> {
            containerUtenti.removeAll();
            Broadcaster.getListeners().forEach((account1, broadcastListener) -> {

                if(account1.getProfilePicture() != null){
                    imageU = generateImage(account1);
                    imageU.getStyle().set("width","50px");
                    imageU.getStyle().set("height","50px");
                    imageU.getStyle().set("border-radius","80px");

                }else {
                    if(account1.getSesso()=="1"){
                        imageU = new Image("frontend/img/profiloGirl.png", "foto profilo");
                        imageU.getStyle().set("width","50px");
                        imageU.getStyle().set("height","50px");
                        imageU.getStyle().set("border-radius","80px");

                    }
                    else {
                        imageU = new Image("frontend/img/profiloBoy.png", "foto profilo");
                        imageU.getStyle().set("width","50px");
                        imageU.getStyle().set("height","50px");
                        imageU.getStyle().set("border-radius","80px");

                    }
                }

                System.out.println("Account id  = "+account1.getId());

                Button button = new Button(imageU);
                button.addClassName("buttonUserGuess");
                button.setText(account1.getNome());
                button.setEnabled(false);
                MessageList messageList = new MessageList("message-list");
                messageList.add(button);
                containerUtenti.add(messageList);

            });
        });
        //getUI().get().getPage().reload();
    }

    @Override
    public void parolaVotata() {
        getUI().get().access(() -> {
            containerParoleVotate.removeAll();
            Broadcaster.getVotes().forEach((s, integer) -> {
                String a = "";
                if(integer == 1){
                    a = " voto";
                }else
                    a = " voti";

                Label label = new Label(s +" ha "+ integer + a );
                MessageList messageList = new MessageList("message-list");
                messageList.add(label);
                containerParoleVotate.add(messageList);
                add(containerParoleVotate);
            });

        });
    }

    @Override
    public void partititaVincente(String parola,int punteggio) {
        Game game = guess;
        getUI().get().access(() -> {
            reset();
            removeAll();
            FireWorks fireWorks = new FireWorks();
            add(fireWorks);
            DialogUtility dialogUtility = new DialogUtility();
            dialogUtility.partitaVincente(parola,punteggio,game);
        });

    }

    @Override
    public void partititanonVincente(){
        Game game = guess;
        getUI().get().access(() -> {
            reset();
            removeAll();
            DialogUtility dialogUtility = new DialogUtility();
            dialogUtility.partitanonVincente(game);
        });
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

    static public void reset(){
        Broadcaster.getPartiteThread().clear();
        Broadcaster.getVotes().clear();
        Broadcaster.getAccountList().clear();
        Broadcaster.getItems().clear();
        Broadcaster.getListeners().clear();
        BroadcasterChat.getListeners().clear();
        BroadcasterSuggerisci.getListeners().clear();
        Broadcaster.getStrings().clear();
    }

    @Override
    public void configurePage(InitialPageSettings initialPageSettings) {
        String script = "window.onbeforeunload = function (e) " +
                "{ var e = e || window.event; document.getElementById(\"GuessUI\").$server.browserIsLeaving(); return; };";
        initialPageSettings.addInlineWithContents(InitialPageSettings.Position.PREPEND, script, InitialPageSettings.WrapMode.JAVASCRIPT);
    }

    @ClientCallable
    public void browserIsLeaving() {

        Broadcaster.getListeners().forEach((account1, broadcastListener) -> {
            System.out.println("Account registrato alla partita = "+account1.getNome());
        });

        try {
            Broadcaster.getListeners().forEach((account1, broadcastListener) -> {
                if (account1.equals(account)) {
                    Broadcaster.browserIsLeavingCalled(account);
                    Broadcaster.unregister(account, this);
                    for (int i = 0; i < Broadcaster.getPartiteThread().size(); i++) {
                        try {
                            Broadcaster.getPartiteThread().get(i).interrupt();
                            Broadcaster.getPartiteThread().get(i).stopTimer();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                    }
                    reset();
                }
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void browserIsLeavingCalled(Account account) {
        try {
            getUI().get().access(() -> {
                DialogUtility dialogUtility = new DialogUtility();
                dialogUtility.partitaTerminata(account);
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


}
