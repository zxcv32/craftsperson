package com.seacreeper.craftsperson.views.home;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import lombok.val;

@JsModule("./views/creepers/image-card.ts")
@Tag("image-card")
public class ImageCard extends LitTemplate {

  @Id private Image image;

  @Id private Span header;

  @Id private Span subtitle;

  @Id private Paragraph text;

  @Id private Span badge;

  public ImageCard(String creeperName, String imgUrl, boolean enable) {
    this.header.setText(creeperName);
    this.image.setSrc(imgUrl);
    this.image.setAlt(creeperName.toLowerCase());
    if (enable) {
      val url = creeperName.toLowerCase().replaceAll("[^a-z0-9]?", "");
      this.getElement().addEventListener("click", e -> UI.getCurrent().navigate("creeper/" + url));
    } else {
      this.getElement()
          .addEventListener("click", event -> Notification.show("Creeper unavailable"));
    }
  }

  public void setSubTitle(String subTitle) {
    this.subtitle.setText(subTitle);
  }

  public void setBody(String body) {
    this.text.setText(body);
  }

  public void setBadge(String badge) {
    this.badge.setText(badge);
  }
}
