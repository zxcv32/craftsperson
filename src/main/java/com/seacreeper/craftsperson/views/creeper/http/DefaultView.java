package com.seacreeper.craftsperson.views.creeper.http;

import com.seacreeper.craftsperson.model.influxdb.HttpScribe;
import com.seacreeper.craftsperson.service.QueenTalker;
import com.seacreeper.craftsperson.service.ScribeTalker;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.seacreeper.craftsperson.views.MainLayout;
import java.util.concurrent.Future;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Route(value = "creeper/http", layout = MainLayout.class)
@PageTitle("HTTP Creeper")
public class DefaultView extends SplitLayout {

  @Autowired private ScribeTalker scribeTalker;
  @Autowired private QueenTalker queenTalker;

  public DefaultView() {
    addClassName("creeper-http-view");
    val urlInput = new TextField();
    urlInput.setPlaceholder("URL");
    urlInput.setRequired(true);
    val submitUrl = new Button("Submit");
    val submitLayout = new HorizontalLayout();
    submitLayout.setWidthFull();
    submitLayout.setAlignItems(Alignment.CENTER);
    submitLayout.setJustifyContentMode(JustifyContentMode.CENTER);
    submitLayout.add(urlInput, submitUrl);

    val details =
        new Details(
            "About HTTP Creeper",
            new Text("HTTP Creeper sends the HTTP request to the target and stores the result"));
    val interactiveSection = new SplitLayout(submitLayout, details);
    interactiveSection.setSplitterPosition(50);
    addToPrimary(interactiveSection);
    setHeightFull();
    submitUrl.addClickListener(
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
  }

  private void checkResult(Future<String> result) {
    while (true) {
      if (result.isDone()) {
        try {
          Notification.show(result.get());
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
      val grid = new Grid<>(HttpScribe.class);
      val future = scribeTalker.readRecent();
      val result = future.get();
      grid.setItems(result);
      grid.setColumns("dateTime", "data");
      grid.recalculateColumnWidths();
      grid.setHeightFull();
      setOrientation(Orientation.VERTICAL);
      setSplitterPosition(50);
      addToSecondary(grid);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
