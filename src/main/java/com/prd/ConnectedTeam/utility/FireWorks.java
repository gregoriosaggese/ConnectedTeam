package com.prd.ConnectedTeam.utility;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;


@StyleSheet("frontend://stile/fireworks.css")
public class FireWorks extends Div {

    public FireWorks() {
        addClassName("pyro");
        Div before = new Div();
        before.addClassName("before");
        Div after = new Div();
        after.addClassName("after");
        add(before,after);
    }
}
