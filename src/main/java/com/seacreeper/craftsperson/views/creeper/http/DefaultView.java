package com.seacreeper.craftsperson.views.creeper.http;

import com.seacreeper.craftsperson.model.influxdb.HttpScribe;
import com.seacreeper.craftsperson.service.queen.QueenTalker;
import com.seacreeper.craftsperson.service.scribe.ScribeTalker;
import com.seacreeper.craftsperson.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.PostConstruct;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "creeper/http", layout = MainLayout.class)
@PageTitle("Creeper: HTTP")
public class DefaultView extends SplitLayout {

  @Autowired private ScribeTalker scribeTalker;
  @Autowired private QueenTalker queenTalker;
  private Grid grid;

  public DefaultView() {
    this.grid = new Grid<>(HttpScribe.class);
    configureHistoryGrid();
    addClassName("creeper-http-view");
    addToPrimary(getPrimary());
    addToSecondary(getSecondary());
  }

  private void configureHistoryGrid() {
    grid.setColumns("dateTime", "data");
    grid.recalculateColumnWidths();
    grid.setHeightFull();
    setOrientation(Orientation.VERTICAL);
    setSplitterPosition(40);
    setHeightFull();
  }

  private Component getPrimary() {
    val vertical = new VerticalLayout(getTop(), getConfigurator());
    return vertical;
  }

  private Component getTop() {
    val view = new HorizontalLayout();
    view.setPadding(true);
    view.setWidthFull();
    Select<String> requestType = new Select<>();
    requestType.setItems("GET", "POST");
    requestType.setValue("GET");
    val urlInput = new TextField();
    urlInput.setPlaceholder("URL");
    urlInput.setRequired(true);
    urlInput.setWidthFull();
    urlInput.setAutofocus(true);
    val testRun = new Button("Dry run");
    testRun.setMinWidth("5em");
    val submit = new Button("Submit");
    submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    submit.addClickListener(
        e -> {
          Notification.show("Submitted: " + urlInput.getValue());
          try {
            val result = queenTalker.send(urlInput.getValue());
            checkResult(result);
          } catch (IOException ex) {
            ex.printStackTrace();
            Notification.show("Error: " + ex.getMessage());
          }
        });
    view.add(requestType, urlInput, testRun, submit);
    view.setWidthFull();
    view.setAlignItems(Alignment.CENTER);
    view.setJustifyContentMode(JustifyContentMode.CENTER);
    return view;
  }

  private Component getConfigurator() {
    Tab parameters = new Tab("Parameters");
    Tab header = new Tab("Header");
    Tab configure = new Tab("Configure");
    Tabs tabs = new Tabs(parameters, header, configure);
    tabs.setWidthFull();
    return tabs;
  }

  private Component getDetails() {
    val details =
        new Details(
            "About HTTP Creeper",
            new Text("HTTP Creeper sends the HTTP request to the target and stores the result"));
    return details;
  }

  private Component getSecondary() {
    val interactiveSection = new SplitLayout(grid, getDetails());
    interactiveSection.setSplitterPosition(75);
    return interactiveSection;
  }

  private void checkResult(Future<String> result) {
    while (true) {
      if (result.isDone()) {
        try {
          Notification.show(String.format("Executed: %s", result.get()));
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
        recent();
        break;
      }
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @PostConstruct
  private void recent() {
    try {
      val httpScribe = new HttpScribe();
      val future = scribeTalker.readRecent(Optional.empty());
      val result = future.get();
      grid.setItems(result);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
