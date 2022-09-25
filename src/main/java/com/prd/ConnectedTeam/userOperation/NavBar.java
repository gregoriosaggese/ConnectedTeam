package com.prd.ConnectedTeam.userOperation;

import com.prd.ConnectedTeam.mainView.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@HtmlImport("style.html")
@StyleSheet("frontend://stile/stile.css")
public class NavBar extends HorizontalLayout {


    public NavBar (){

        getStyle().set("background-color","#84c0c9");
        getStyle().set("padding","0px");
        getElement().getStyle().set("width", "100%");
        getStyle().set("border-radius","10%");
        UnorderedList unorderedList = new UnorderedList();
        HorizontalLayout main = new HorizontalLayout();
        main.getStyle().set("padding","10px");
        Div uno = new Div();
        Icon homeIcon = new Icon(VaadinIcon.HOME);
        homeIcon.setSize("30px");
        homeIcon.setColor("#007d99");
        RouterLink routerLink = new RouterLink("Home", HomeView.class);
        routerLink.getStyle().set("text-decoration", "none");
        routerLink.addClassName("router-link");
        unorderedList.add(routerLink);
        Div space = new Div();
        space.setWidth("100%");
        uno.add(homeIcon,routerLink);

        Div due = new Div();
        Icon settingIcon = new Icon(VaadinIcon.COGS);
        settingIcon.setSize("30px");
        settingIcon.setColor("#007d99");
        RouterLink routerLink1 = new RouterLink("Impostazioni", SettingsUser.class);
        routerLink1.getStyle().set("text-decoration", "none");
        routerLink1.addClassName("router-link");
        Div space1 = new Div();
        space1.setWidth("100%");
        due.add(settingIcon,routerLink1);

        Div tre = new Div();
        Icon statisticheIcon = new Icon(VaadinIcon.PIE_BAR_CHART);
        statisticheIcon.setSize("30px");
        statisticheIcon.setSize("30px");
        statisticheIcon.setColor("#007d99");

        RouterLink routerLink2 = new RouterLink("Statistiche", StatisticUser.class);
        routerLink2.getStyle().set("text-decoration", "none");
        routerLink2.addClassName("router-link");
        Div space2 = new Div();
        space2.setWidth("100%");
        tre.add(statisticheIcon,routerLink2);

        Div quattro = new Div();
        Icon logoutIcon = new Icon(VaadinIcon.SIGN_OUT);
        logoutIcon.setSize("30px");
        logoutIcon.setColor("#007d99");
        quattro.getStyle().set("position","absolute");
        quattro.getStyle().set("left","89%");

        Button routerLink3 = new Button("Logout");
        routerLink3.addClickListener(buttonClickEvent -> {
            VaadinSession.getCurrent().getSession().invalidate();
            UI.getCurrent().navigate(MainView.class);
            System.out.println(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user"));
        });

        routerLink3.addClassName("buttonLogOut");
        Div space3 = new Div();
        space3.setWidth("100%");
        quattro.add(logoutIcon,routerLink3);
        String styles = ".router-link { "
                + "margin-left: 10px;"
                + "font-size: 20px;"
                + " }";

        StreamRegistration resource = UI.getCurrent().getSession()
                .getResourceRegistry()
                .registerResource(new StreamResource("styles.css", () -> {
                    byte[] bytes = styles.getBytes(StandardCharsets.UTF_8);
                    return new ByteArrayInputStream(bytes);
                }));
        UI.getCurrent().getPage().addStyleSheet(
                "base://" + resource.getResourceUri().toString());

        main.add(uno,due,tre,quattro);
        add(main);
    }

}
