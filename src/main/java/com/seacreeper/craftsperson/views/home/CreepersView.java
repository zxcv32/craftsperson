package com.seacreeper.craftsperson.views.home;

import com.seacreeper.craftsperson.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import lombok.val;

@Route(value = "creepers", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Creepers")
@Tag("creepers-view")
@JsModule("./views/creepers/creepers-view.ts")
public class CreepersView extends LitTemplate implements HasComponents, HasStyle {

  @Id private Select<String> sortBy;

  public CreepersView() {
    addClassNames("creepers-view", "flex", "flex-col", "h-full");
    sortBy.setItems("Popularity", "Newest first", "Oldest first");
    sortBy.setValue("Popularity");

    val http =
        new ImageCard(
            "HTTP",
            "https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80",
            true);
    http.setSubTitle("HTTP requests");
    http.setBody("Send and automate HTTP operations");
    http.setBadge("Classic");
    add(http);

    val postmanNewman =
        new ImageCard(
            "POSTMAN-NEWMAN",
            "https://images.unsplash.com/photo-1562832135-14a35d25edef?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=815&q=80",
            true);
    postmanNewman.setSubTitle("Run postman collections");
    postmanNewman.setBody("Run and automate postman collections");
    postmanNewman.setBadge("Popular");
    add(postmanNewman);

    val file =
        new ImageCard(
            "FILE",
            "https://images.unsplash.com/photo-1512273222628-4daea6e55abb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80",
            false);
    file.setSubTitle("File operations");
    file.setBody("Operate on files");
    file.setBadge("Coming soon");
    add(file);

    val selenium =
        new ImageCard(
            "SELENIUM",
            "https://images.unsplash.com/photo-1512273222628-4daea6e55abb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80",
            false);
    selenium.setSubTitle("Frontend tests");
    selenium.setBody("Run selenium");
    selenium.setBadge("Coming soon");
    add(selenium);

    val tor =
        new ImageCard(
            "TOR",
            "https://images.unsplash.com/photo-1536048810607-3dc7f86981cb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=375&q=80",
            false);
    tor.setSubTitle("Operate on Tor");
    tor.setBody("View, collect, monitor, and more");
    tor.setBadge("Coming soon");
    add(tor);

    val dns =
        new ImageCard(
            "DNS",
            "https://images.unsplash.com/photo-1513147122760-ad1d5bf68cdb?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1000&q=80",
            false);
    dns.setSubTitle("DNS collection and monitor");
    dns.setBody("View, collect, monitor, and more");
    dns.setBadge("Coming soon");
    add(dns);

    val gpu =
        new ImageCard(
            "GPU",
            "https://images.unsplash.com/photo-1515705576963-95cad62945b6?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=750&q=80",
            false);
    gpu.setSubTitle("GPU processing");
    gpu.setBody("Run on GPU");
    gpu.setBadge("Coming soon");
    add(gpu);

    val k6 =
        new ImageCard(
            "K6",
            "https://images.unsplash.com/photo-1562832135-14a35d25edef?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=815&q=80",
            false);
    k6.setSubTitle("Run k6 tests");
    k6.setBody("Run K6 tests in an scalable and easy to setup environment");
    k6.setBadge("Coming soon");
    add(k6);
  }
}
