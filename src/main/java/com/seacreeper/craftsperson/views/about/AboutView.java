package com.seacreeper.craftsperson.views.about;

import com.moandjiezana.toml.Toml;
import com.seacreeper.craftsperson.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
@Route(value = "about", layout = MainLayout.class)
@PageTitle("About")
public class AboutView extends Div {

  public AboutView() {
    addClassName("about-view");
    add(new Text("Sea Creeper"));

    add(new Hr());
    add(new H2("Attribution"));
    Toml toml = null;
    try {
      toml = new Toml().read(ClassLoader.getSystemResourceAsStream("attribution.toml"));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    val imageName = toml.getString("Image.name");
    val imageUrl = toml.getString("Image.url");
    val attributionList = new OrderedList();
    val anchor = new Anchor();
    anchor.setText(imageName);
    anchor.setHref(imageUrl);
    anchor.setTarget("_blank");
    attributionList.add(anchor);
    add(attributionList);
  }
}
