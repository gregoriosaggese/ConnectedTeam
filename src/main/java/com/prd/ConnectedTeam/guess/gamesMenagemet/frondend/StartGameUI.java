package com.prd.ConnectedTeam.guess.gamesMenagemet.frondend;

import com.prd.ConnectedTeam.error.ErrorPage;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.GuessController;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.broadcaster.Broadcaster;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.broadcaster.BroadcasterSuggerisci;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.listeners.SuggerisciListener;
import com.prd.ConnectedTeam.utility.MessageList;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@StyleSheet("frontend://stile/stile.css")
@StyleSheet("frontend://stile/style.css")
public class StartGameUI extends VerticalLayout implements SuggerisciListener{

    private VerticalLayout parolaLayout = new VerticalLayout();
    private Button button;
    private GuessController guessController;
    private boolean vincente;
    boolean flag = false;


    public StartGameUI(GuessController guessController) {

        try {
            setId("StartGameUI");
            this.guessController = guessController;
            BroadcasterSuggerisci.register(this);
            Image image = new Image("frontend/img/Guess.jpeg", "guess");
            image.setWidth("200px");
            image.setHeight("200px");
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.addClassName("horizontalLayoutStartGameUI");
            TextField suggertisci = new TextField();
            Label label = new Label("Suggerisci una soluzione");
            label.addClassName("labelSuggerisci");
            add(label, image);
            MessageList messageList = new MessageList("message-list");
            Button sendParola = new Button("Suggerisci");
            sendParola.addClickListener(buttonClickEvent -> {
                String mess = suggertisci.getValue();
                if (!mess.equals("")) {
                    BroadcasterSuggerisci.broadcast(suggertisci.getValue());
                    suggertisci.setValue("");
                }
            });
            Label paroleVotate = new Label("Parole votate: ");
            paroleVotate.addClassName("parolevotatelabel");
            add(paroleVotate);
            sendParola.getStyle().set("cursor", "pointer");
            sendParola.setWidth(null);
            parolaLayout.addClassName("suggerisciParolaLayout");
            parolaLayout.setWidth("250px");
            horizontalLayout.add(suggertisci, sendParola, parolaLayout);
            add(horizontalLayout);
        }

        catch (Exception e){
            removeAll();
            ErrorPage errorPage = new ErrorPage();
            add(errorPage);
            e.printStackTrace();
        }

    }

    @Override
    public Button receiveBroadcast(String message) {
        getUI().get().access(() -> {
            MessageList messageList = new MessageList("message-list");
            Div div = new Div();
            Label label = new Label(message);
            button = new Button(VaadinIcon.PLUS.create());

            button.addClickListener(buttonClickEvent -> {
                Broadcaster.addString(message);
                Map<String, Integer> stringIntegerMap = countFrequencies(Broadcaster.getStrings());
                Broadcaster.getVotoParola(stringIntegerMap);
                disableButton();

                stringIntegerMap.forEach((s, integer) -> {
                    if (integer == Broadcaster.getListeners().size()) {
                        for (int i = 0; i < Broadcaster.getPartiteThread().size(); i++) {
                            if (i == 0) {
                                flag = false;
                            }
                            try {
                                Broadcaster.getPartiteThread().get(i).interrupt();
                                Broadcaster.getPartiteThread().get(i).stopTimer();
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            } finally {
                                int punteggio = 0;
                                if (Broadcaster.getIndiziRicevuti() == 1) {
                                    punteggio = 100;
                                } else if (Broadcaster.getIndiziRicevuti() == 2) {
                                    punteggio = 60;
                                } else if (Broadcaster.getIndiziRicevuti() == 3) {
                                    punteggio = 30;
                                } else if (Broadcaster.getIndiziRicevuti() == 4) {
                                    punteggio = 10;
                                }

                                vincente = guessController.partitaVincente(s, Broadcaster.getItems().get(i));

                                if (vincente == false && flag == false) {
                                    Broadcaster.partitanonVincente();
                                } else if (vincente == true && flag == false) {
                                    Broadcaster.partitaVincente(s, punteggio);
                                }
                            }

                        }

                    }
                });
            });

            button.getStyle().set("background-color","#007d99");
            button.getStyle().set("border-radius","30px 10px 30px 40px");
            button.getStyle().set("margin-left","30px");
            button.getStyle().set("cursor","pointer");
            button.getStyle().set("color","white");
            div.add(label,button);
            messageList.add(div);
            parolaLayout.add(messageList);

        });

        return button;
    }

    public Map<String, Integer> countFrequencies(ArrayList<String> list) {

        Map<String, Integer> hm = new HashMap<String, Integer>();
        for (String i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }

        /*for (Map.Entry<String, Integer> val : hm.entrySet()) {
            System.out.println("Element " + val.getKey() + " "
                    + "occurs"
                    + ": " + val.getValue() + " times");
        }*/

        return hm;
    }

    void disableButton(){
        button.setEnabled(false);
    }


}
