package com.prd.ConnectedTeam.utility;

import com.prd.ConnectedTeam.mainView.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class InfoEventUtility {

    public void infoEvent(String info, String px) {

        Icon close = VaadinIcon.CLOSE.create();
        close.setColor("black");
        Button close1 = new Button(close);

        Button content = new Button(info);
        close1.getElement().getStyle().set("background-color", "transparent");
        close1.getElement().getStyle().set("cursor", "pointer");
        content.getElement().getStyle().set("background-color", "transparent");
        content.getElement().getStyle().set("margin-left", px+"px");
        content.setEnabled(false);
        content.addClassName("my-style");
        Notification notification = new Notification();
        String styles =".my-style { "
                + "  color: red;"
                + " }";
        notification.add(content, close1);
        close1.addClickListener(buttonClickEvent -> notification.close());
        notification.setPosition(Notification.Position.MIDDLE);
        StreamRegistration resource = UI.getCurrent().getSession()
                .getResourceRegistry()
                .registerResource(new StreamResource("styles.css", () -> {
                    byte[] bytes = styles.getBytes(StandardCharsets.UTF_8);
                    return new ByteArrayInputStream(bytes);
                }));
        UI.getCurrent().getPage().addStyleSheet(
                "base://" + resource.getResourceUri().toString());

        if(info.equalsIgnoreCase("C'è una partita in corso aspetta che finisca")){
            close1.getElement().getStyle().set("margin-left", "140px");
            close1.getElement().getStyle().set("cursor", "pointer");
            notification.add(content);
        }else if (info.equals("Aspetta almeno un altro giocatore")){
            close1.getElement().getStyle().set("margin-left", "120px");
            close1.getElement().getStyle().set("cursor", "pointer");
        }else if (info.equals("Account eliminato con successo")){
            content.getElement().getStyle().set("color","green");
            close1.addClickListener(buttonClickEvent -> UI.getCurrent().navigate(MainView.class));
        }else if (info.contains("Ti abbiamo inviato un'e-mail all'indirizzo")){
            content.getElement().getStyle().set("color","green");
            if (info.contains("gmail.com")){
                String url = "https://accounts.google.com/ServiceLogin/" +
                        "signinchooser?service=mail&passive=true&rm=false&continue=" +
                        "https%3A%2F%2Fmail.google.com%2Fmail%2F%3Ftab%3Drm0%26ogbl&scc=1&ltmpl=" +
                        "default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
                close1.addClickListener(buttonClickEvent ->
                        UI.getCurrent().getPage().executeJavaScript("window.location.replace(\"" + url + "\");"));
            }else
                close1.addClickListener(buttonClickEvent -> UI.getCurrent().getPage().reload());
        }else if (info.equals("Password cambiata con successo")){
            content.getElement().getStyle().set("color","green");
        }else if (info.equals("Immagine cambiata con successo")){
            content.getElement().getStyle().set("color","green");
        }else if (info.equals("Email Inviata con successo")){
            content.getElement().getStyle().set("color","green");
        }else{
            close1.addClickListener(buttonClickEvent -> UI.getCurrent().getPage().reload());
        }
        notification.open();
    }
}
