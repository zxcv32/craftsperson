package com.seacreeper.craftsperson.views.httpcreeper;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.seacreeper.craftsperson.views.MainLayout;

@Route(value = "creeper/http", layout = MainLayout.class)
@PageTitle("HTTP Creeper")
public class HTTPCreeperView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HTTPCreeperView() {
        addClassName("h-ttp-creeper-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }

}
