package com.prd.ConnectedTeam.utility;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

public class MessageList extends Div {

  public MessageList(String className) {
    addClassName(className);
  }

  @Override
  public void add(Component... components) {
    super.add(components);
    components[components.length-1].getElement().callFunction("scrollIntoView");
  }

}

