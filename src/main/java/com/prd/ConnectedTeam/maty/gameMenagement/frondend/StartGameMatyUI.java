package com.prd.ConnectedTeam.maty.gameMenagement.frondend;


import com.prd.ConnectedTeam.entity.Account;
import com.prd.ConnectedTeam.error.ErrorPage;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.MatyController;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.broadcaster.BroadcasterMaty;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.broadcaster.BroadcasterSuggerisciMaty;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.db.ItemMaty;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.listeners.SuggerisciListenerMaty;
import com.prd.ConnectedTeam.utility.MessageList;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

@StyleSheet("frontend://stile/stile.css")
@StyleSheet("frontend://stile/style.css")
@StyleSheet("frontend://stile/button.css")
@StyleSheet("frontend://stile/buttonsend1.scss")
@StyleSheet("frontend://stile/divbox.css")
@StyleSheet("frontend://stile/animation.css")
@JavaScript("frontend://js/script.js")
public class StartGameMatyUI extends HorizontalLayout implements SuggerisciListenerMaty {

    private VerticalLayout parolaLayout = new VerticalLayout();
    private VerticalLayout cronologiaNUmeri = new VerticalLayout();
    private Button button;
    private MatyController matyController;
    private boolean vincente;
    boolean flag = false;
    private String operazioneLabel;
    private String paroleVotateLabel;
    private String sendParolaButton;
    private Label label = new Label();
    private Label paroleVotate = new Label();
    private Button sendParola = new Button();
    private Account account;
    private Div containerNumeriSS = new Div();
    private Div box = new Div();
    private Div wrapper = new Div();
    Label cronologiaMosse = new Label("Cosa hanno fatto fino ad ora:");

    public StartGameMatyUI(MatyController matyController, Account account) {

        try {

            containerNumeriSS.addClassName("containerNumeriSS");
            box.addClassName("box");
            setId("StartGameMatyUI");
            this.account = account;
            this.matyController = matyController;
            BroadcasterSuggerisciMaty.register(this);
            Image image = new Image("frontend/img/Maty.jpeg", "maty");
            image.setWidth("200px");
            image.setHeight("200px");
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.addClassName("horizontalLayoutStartGameUI");
            TextField suggertisci = new TextField();
            ItemMaty itemMaty = matyController.getItem();
            BroadcasterSuggerisciMaty.addItems(itemMaty);
            String operazione11 = "";
            int a = 0;

            for (int i = 0; i < BroadcasterSuggerisciMaty.getItems().size(); i++) {
                try {
                    operazione11 = BroadcasterSuggerisciMaty.getItems().get(i).getOperazione();
                    System.out.println("Sono a a a " + operazione11);
                    a = i;
                } catch (Exception e) {
                }
            }
            if (operazione11.equals("sottrazione")) {
                int numS = Integer.parseInt(BroadcasterSuggerisciMaty.getItems().get(a).getParola());
                numS = numS * 2;
                BroadcasterMaty.numeroDaSottrarre(numS + "", BroadcasterSuggerisciMaty.getItems().get(a).getParola());
                BroadcasterMaty.addIntegers(numS);
            } else {
                System.out.println("A " + a);
                BroadcasterMaty.numeroDASommare(BroadcasterMaty.getItems().get(0).getParola());
            }
            BroadcasterSuggerisciMaty.setOperazione();
            label.addClassName("labelSuggerisci");
            add(label, image);
            Div divC = new Div();
            divC.addClassName("container11");
            sendParola.setId("btn");
            sendParola.addClassName("button12");
            divC.add(sendParola);

            sendParola.addClickListener(buttonClickEvent -> {
                parolaLayout.removeAll();
                getElement().executeJavaScript("sends()");
                String mess = suggertisci.getValue();
                if (!mess.equals(BroadcasterMaty.getItems().get(0).getParola())) {
                    if (!mess.equals("")) {
                        try {
                            Integer.parseInt(mess);
                            BroadcasterMaty.addContClick();
                            System.out.println("Clicked " + BroadcasterMaty.getContClick().size() + " times");
                            for (int i = 0; i < BroadcasterSuggerisciMaty.getItems().size(); i++) {
                                try {
                                    String operazione = BroadcasterSuggerisciMaty.getItems().get(i).getOperazione();
                                    if (operazione.equalsIgnoreCase("somma")) {
                                        if (BroadcasterMaty.getIntegers().size() == 0) {
                                            BroadcasterMaty.addIntegers(Integer.parseInt(mess));
                                        } else {
                                            BroadcasterMaty.addIntegers(
                                                    BroadcasterMaty.getIntegers().get(BroadcasterMaty.getIntegers().size() - 1) + Integer.parseInt(mess));
                                        }
                                        BroadcasterSuggerisciMaty.broadcast(mess, operazione,account.getNome(),true);
                                        /*----INIZIO------------------------------------------------------------------------------------------------------------------------------------*/
                                        int j;
                                        containerNumeriSS.removeAll();
                                        if (operazione.equals("somma")) {
                                            j = 0;
                                        } else {
                                            j = 1;
                                        }
                                        box.removeAll();
                                        wrapper.removeAll();
                                        Div circle = new Div();
                                        circle.addClassName("circle");
                                        circle.setId("colorpad1");
                                        Paragraph paragraph = new Paragraph();
                                        paragraph.addClassName("parag1");
                                        Span span = new Span(mess);
                                        paragraph.add(span);
                                        circle.add(paragraph);
                                        box.add(circle);
                                        add(box);
                                        Div d = new Div();
                                        d.setWidth(null);
                                        Paragraph p = new Paragraph(mess);
                                        p.addClassName("parag2");
                                        d.addClassName("paer");
                                        d.setId("colorpad");
                                        d.add(p);
                                        wrapper.add(d);
                                        wrapper.addClassName("box1");
                                        add(wrapper);
                                        getElement().executeJavaScript("setRandomColor()");
                                        MessageList messageList = new MessageList("message-list");
                                        Div div = new Div();
                                        Label label = new Label(mess);
                                        label.getStyle().set("margin-right", "15px");
                                        button = new Button("Elimina ", VaadinIcon.MINUS.create());
                                        button.setId("button");
                                        button.addClickListener(buttonClickEvent1 -> {
                                            parolaLayout.removeAll();
                                            BroadcasterSuggerisciMaty.broadcast(mess, operazione,account.getNome(),false);
                                            box.removeAll();
                                            wrapper.removeAll();
                                            button.setEnabled(false);
                                            sendParola.setEnabled(true);
                                            int num = Integer.parseInt(mess);
                                            if (operazione.equals("somma")) {
                                                BroadcasterMaty.addIntegers(
                                                        BroadcasterMaty.getIntegers().get(BroadcasterMaty.getIntegers().size() - 1) - num);
                                                box.removeAll();
                                                wrapper.removeAll();
                                                Div circle1 = new Div();
                                                circle1.addClassName("circle");
                                                circle1.setId("colorpad1");
                                                Paragraph paragraph1 = new Paragraph();
                                                paragraph1.addClassName("parag1");
                                                Span span1 = new Span("0");
                                                paragraph1.add(span1);
                                                circle1.add(paragraph1);
                                                box.add(circle1);
                                                add(box);
                                                Div d1 = new Div();
                                                d1.setWidth(null);
                                                Paragraph p1 = new Paragraph("0");
                                                p1.addClassName("parag2");
                                                d1.addClassName("paer");
                                                d1.setId("colorpad");
                                                d1.add(p1);
                                                wrapper.add(d1);
                                                wrapper.addClassName("box1");
                                                add(wrapper);
                                                getElement().executeJavaScript("setRandomColor()");
                                                checkIfWin();
                                            }

                                        });
                                        //deleteButtonStyle();
                                        div.add(label, button);
                                        messageList.add(div);
                                        parolaLayout.add(messageList);
                                        /*--FINE--------------------------------------------------------------------------------------------------------------------------------------*/
                                        suggertisci.setValue("");
                                        sendParola.setEnabled(false);
                                        if (BroadcasterMaty.getContClick().size() == 5) {
                                            String indizio = BroadcasterSuggerisciMaty.getItems().get(i).getIndizio(0);
                                            BroadcasterMaty.riceveIndizio(indizio);
                                            BroadcasterMaty.setIndiziRicevuti(1);
                                            System.out.println("Trovato");
                                        } else if (BroadcasterMaty.getContClick().size() == 10) {
                                            String indizio = BroadcasterSuggerisciMaty.getItems().get(i).getIndizio(1);
                                            BroadcasterMaty.riceveIndizio(indizio);
                                            BroadcasterMaty.setIndiziRicevuti(2);
                                            System.out.println("Trovato");
                                        } else if (BroadcasterMaty.getContClick().size() == 15) {
                                            String indizio = BroadcasterSuggerisciMaty.getItems().get(i).getIndizio(2);
                                            BroadcasterMaty.riceveIndizio(indizio);
                                            BroadcasterMaty.setIndiziRicevuti(3);
                                            System.out.println("Trovato");
                                        } else if (BroadcasterMaty.getContClick().size() == 20) {
                                            String indizio = BroadcasterSuggerisciMaty.getItems().get(i).getIndizio(3);
                                            BroadcasterMaty.riceveIndizio(indizio);
                                            BroadcasterMaty.setIndiziRicevuti(4);
                                            System.out.println("Trovato");
                                        }
                                        checkIfWin();
                                    } else {
                                        System.out.println(operazione);
                                        System.out.println(BroadcasterSuggerisciMaty.getItems().get(i).getParola());
                                        BroadcasterMaty.addIntegers(
                                                BroadcasterMaty.getIntegers().get(BroadcasterMaty.getIntegers().size() - 1) - Integer.parseInt(mess));
                                        BroadcasterSuggerisciMaty.broadcast(mess, operazione,account.getNome(),true);

                                        /*------INIZIO----------------------------------------------------------------------------------------------------------------------------------*/
                                        int j;
                                        containerNumeriSS.removeAll();
                                        if (operazione.equals("somma")) {
                                            j = 0;
                                        } else {
                                            j = 1;
                                        }
                                        box.removeAll();
                                        wrapper.removeAll();
                                        Div circle = new Div();
                                        circle.addClassName("circle");
                                        circle.setId("colorpad1");
                                        Paragraph paragraph = new Paragraph();
                                        paragraph.addClassName("parag1");
                                        Span span = new Span(mess);
                                        paragraph.add(span);
                                        circle.add(paragraph);
                                        box.add(circle);
                                        add(box);
                                        Div d = new Div();
                                        d.setWidth(null);
                                        Paragraph p = new Paragraph(mess);
                                        p.addClassName("parag2");
                                        d.addClassName("paer");
                                        d.setId("colorpad");
                                        d.add(p);
                                        wrapper.add(d);
                                        wrapper.addClassName("box1");
                                        add(wrapper);
                                        getElement().executeJavaScript("setRandomColor()");
                                        MessageList messageList = new MessageList("message-list");
                                        Div div = new Div();
                                        Label label = new Label(mess);
                                        label.getStyle().set("margin-right", "15px");
                                        button = new Button("Elimina ", VaadinIcon.MINUS.create());
                                        button.setId("button");
                                        button.addClickListener(buttonClickEvent1 -> {
                                            parolaLayout.removeAll();
                                            BroadcasterSuggerisciMaty.broadcast(mess, operazione,account.getNome(),false);
                                            box.removeAll();
                                            wrapper.removeAll();
                                            button.setEnabled(false);
                                            sendParola.setEnabled(true);
                                            int num = Integer.parseInt(mess);
                                            BroadcasterMaty.addIntegers(
                                                    BroadcasterMaty.getIntegers().get(BroadcasterMaty.getIntegers().size() - 1) + num);
                                            box.removeAll();
                                            wrapper.removeAll();
                                            Div circle1 = new Div();
                                            circle1.addClassName("circle");
                                            circle1.setId("colorpad1");
                                            Paragraph paragraph1 = new Paragraph();
                                            paragraph1.addClassName("parag1");
                                            Span span1 = new Span("0");
                                            paragraph1.add(span1);
                                            circle1.add(paragraph1);
                                            box.add(circle1);
                                            add(box);
                                            Div d1 = new Div();
                                            d1.setWidth(null);
                                            Paragraph p1 = new Paragraph("0");
                                            p1.addClassName("parag2");
                                            d1.addClassName("paer");
                                            d1.setId("colorpad");
                                            d1.add(p1);
                                            wrapper.add(d1);
                                            wrapper.addClassName("box1");
                                            add(wrapper);
                                            getElement().executeJavaScript("setRandomColor()");
                                            checkIfWin();


                                        });
                                        //deleteButtonStyle();
                                        div.add(label, button);
                                        messageList.add(div);
                                        parolaLayout.add(messageList);
                                        /*------FINE----------------------------------------------------------------------------------------------------------------------------------*/


                                        suggertisci.setValue("");
                                        sendParola.setEnabled(false);
                                        if (BroadcasterMaty.getContClick().size() == 5) {
                                            String indizio = BroadcasterSuggerisciMaty.getItems().get(i).getIndizio(0);
                                            BroadcasterMaty.riceveIndizio(indizio);
                                            BroadcasterMaty.setIndiziRicevuti(1);
                                            System.out.println("Trovato");
                                        } else if (BroadcasterMaty.getContClick().size() == 10) {
                                            String indizio = BroadcasterSuggerisciMaty.getItems().get(i).getIndizio(1);
                                            BroadcasterMaty.riceveIndizio(indizio);
                                            BroadcasterMaty.setIndiziRicevuti(2);
                                            System.out.println("Trovato");
                                        } else if (BroadcasterMaty.getContClick().size() == 15) {
                                            String indizio = BroadcasterSuggerisciMaty.getItems().get(i).getIndizio(2);
                                            BroadcasterMaty.riceveIndizio(indizio);
                                            BroadcasterMaty.setIndiziRicevuti(3);
                                            System.out.println("Trovato");
                                        } else if (BroadcasterMaty.getContClick().size() == 20) {
                                            String indizio = BroadcasterSuggerisciMaty.getItems().get(i).getIndizio(3);
                                            BroadcasterMaty.riceveIndizio(indizio);
                                            BroadcasterMaty.setIndiziRicevuti(4);
                                            System.out.println("Trovato");
                                        }
                                        checkIfWin();
                                    }

                                } catch (Exception e) {
                                }
                            }
                        }catch (Exception e){
                            suggertisci.setValue("");
                        }
                    }
                }else {
                    suggertisci.setValue("");
                }
            });

            paroleVotate.addClassName("parolevotatelabel");
            cronologiaMosse.addClassName("cronologiaMosse");
            cronologiaNUmeri.setWidth(null);
            add(paroleVotate);
            sendParola.getStyle().set("cursor", "pointer");
            sendParola.setWidth(null);
            parolaLayout.addClassName("suggerisciParolaLayout");

            cronologiaNUmeri.addClassName("cronologiaNumeri");

            parolaLayout.setWidth("250px");
            horizontalLayout.add(suggertisci, divC, parolaLayout,cronologiaMosse,cronologiaNUmeri);
            add(horizontalLayout);

        } catch (Exception e) {
            removeAll();
            ErrorPage errorPage = new ErrorPage();
            add(errorPage);
            e.printStackTrace();
        }

    }

    @Override
    public void operazione(String message, String operazione,String nome,boolean operation) {
        getUI().get().access(() -> {
            MessageList messageList = new MessageList("message-list");
            Div div = new Div();
            Button label = null;

            if (operation == true) {
                label = new Button(nome + " ha inserito " + message);
            }else {
                label = new Button(nome + " ha eliminato " + message);
            }
            //div.addClassName("labelCronologia");
            //label.getStyle().set("margin-right", "15px");
            label.setEnabled(false);
            label.setId("button");
            div.add(label);
            messageList.add(div);
            if (!account.getNome().equals(nome)) {
                cronologiaNUmeri.add(messageList);
            }

        });








        /*getUI().get().access(() -> {
            BroadcasterMaty.numeroInserito(operazione);
            try {
                MessageList messageList = new MessageList("message-list");
                Div div = new Div();
                Label label = new Label(message);
                label.getStyle().set("margin-right","15px");
                button = new Button("Elimina ", VaadinIcon.MINUS.create());
                button.setId("button");
                button.addClickListener(buttonClickEvent -> {
                    button.setEnabled(false);
                    sendParola.setEnabled(true);
                    int num = Integer.parseInt(message);
                    if (operazione.equals("somma")) {
                        BroadcasterMaty.addIntegers(
                                BroadcasterMaty.getIntegers().get(BroadcasterMaty.getIntegers().size() - 1) - num);
                        BroadcasterMaty.numeroInserito(operazione);
                        checkIfWin();
                    } else {
                        BroadcasterMaty.addIntegers(
                                BroadcasterMaty.getIntegers().get(BroadcasterMaty.getIntegers().size() - 1) + num);
                        BroadcasterMaty.numeroInserito(operazione);
                        checkIfWin();
                    }

                });

                //deleteButtonStyle();
                div.add(label, button);
                messageList.add(div);
                parolaLayout.add(messageList);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
*/

    }

    @Override
    public void setOperazione() {
        for (int i = 0; i < BroadcasterSuggerisciMaty.getItems().size(); i++) {
            try {
                //System.out.println(BroadcasterSuggerisciMaty.getItems().get(i).getOperazione());
                String operazione = BroadcasterSuggerisciMaty.getItems().get(i).getOperazione();
                if (operazione.equalsIgnoreCase("somma")) {
                    operazioneLabel = "Somma un numero";
                    paroleVotateLabel = "Il tuo numero è: ";
                    sendParolaButton = "Somma";
                    label.setText(operazioneLabel);
                    paroleVotate.setText(paroleVotateLabel);
                    sendParola.setText(sendParolaButton);
                } else {
                    operazioneLabel = "Sottrai un numero";
                    paroleVotateLabel = "Il tuo numero è: ";
                    sendParolaButton = "Sottrai";
                    label.setText(operazioneLabel);
                    paroleVotate.setText(paroleVotateLabel);
                    sendParola.setText(sendParolaButton);
                }
            } catch (Exception e) {
                // System.out.println(e.getMessage());
            }
        }
    }

    void checkIfWin() {
        for (int i = 0; i < BroadcasterMaty.getPartiteThread().size(); i++) {
            if (i == 0) {
                flag = false;
            }
            try {
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                int punteggio = 0;
                if (BroadcasterMaty.getIndiziRicevuti() == 0) {
                    punteggio = 120;
                } else if (BroadcasterMaty.getIndiziRicevuti() == 1) {
                    punteggio = 100;
                } else if (BroadcasterMaty.getIndiziRicevuti() == 2) {
                    punteggio = 60;
                } else if (BroadcasterMaty.getIndiziRicevuti() == 3) {
                    punteggio = 30;
                } else if (BroadcasterMaty.getIndiziRicevuti() == 4) {
                    punteggio = 10;
                }
                vincente = matyController.partitaVincente("" + BroadcasterMaty.getIntegers().get(BroadcasterMaty.getIntegers().size() - 1),
                        BroadcasterMaty.getItems().get(i));
                if (vincente == false && flag == false) {
                } else if (vincente == true && flag == false) {
                    BroadcasterMaty.getPartiteThread().get(i).interrupt();
                    BroadcasterMaty.getPartiteThread().get(i).stopTimer();
                    BroadcasterMaty.partitaVincente("" + BroadcasterMaty.getIntegers().get(BroadcasterMaty.getIntegers().size() - 1)
                            ,punteggio);
                }
            }

        }
    }

    void deleteButtonStyle() {
        button.getStyle().set("margin-left", "30px");
        button.getStyle().set("cursor", "pointer");
        button.getStyle().set("color", "white");
    }
}
