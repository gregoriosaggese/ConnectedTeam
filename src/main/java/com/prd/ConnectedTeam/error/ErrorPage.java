package com.prd.ConnectedTeam.error;

import com.prd.ConnectedTeam.mainView.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@HtmlImport("style.html")
@Route("ErrorPage")
@PageTitle("Connecteam")
@StyleSheet("frontend://stile/style.css")
public class ErrorPage extends VerticalLayout {


    public ErrorPage(){

        getStyle().set("padding","100px");
        HorizontalLayout uno = new HorizontalLayout();
        uno.setHeight("160px");
        Div div = new Div();
        Image image = new Image("frontend/img/logocompleto.png", "txt");
        image.setHeight("200px");
        image.setWidth("600px");
        div.add(image);
        setHorizontalComponentAlignment(Alignment.CENTER,uno);
        uno.add(div);

        HorizontalLayout due = new HorizontalLayout();
        due.setHeight("90px");
        Div div2 = new Div();
        Label oops = new Label("Ooooops");
        oops.getStyle().set("font-size","70px");
        div2.add(oops);
        setHorizontalComponentAlignment(Alignment.CENTER,due);
        due.add(div2);

        HorizontalLayout tre = new HorizontalLayout();
        tre.setHeight("40px");
        Div div3 = new Div();
        Label typeError = new Label("404"/*+VaadinRequest.getCurrent().getWrappedSession().getAttribute("error")*/);
        typeError.getStyle().set("font-size","50px");
        typeError.getStyle().set("font-weight","100");
        div3.add(typeError);
        setHorizontalComponentAlignment(Alignment.CENTER,tre);
        tre.add(div3);

        HorizontalLayout quattro = new HorizontalLayout();
        Div div4 = new Div();
        Label info = new Label("Pagina web non trovata");
        info.getStyle().set("font-size","60px");
        info.getStyle().set("font-weight","400");
        div4.add(info);
        setHorizontalComponentAlignment(Alignment.CENTER,quattro);
        quattro.add(div4);

        HorizontalLayout cinque = new HorizontalLayout();
        Div div5 = new Div();
        Button home = new Button("Home");
        home.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate(MainView.class);
        });
        div5.add(home);
        setHorizontalComponentAlignment(Alignment.CENTER,cinque);
        cinque.add(div5);

        add(uno,due,tre,quattro,cinque);
    }

}
