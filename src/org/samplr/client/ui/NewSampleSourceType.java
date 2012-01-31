//package org.samplr.client.ui;
//
//import com.google.gwt.user.client.ui.DialogBox;
//import com.google.gwt.user.client.ui.VerticalPanel;
//import com.google.gwt.user.client.ui.HasVerticalAlignment;
//import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.HorizontalPanel;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.HasHorizontalAlignment;
//import com.google.gwt.user.client.ui.SuggestBox;
//
//public class NewSampleSourceType extends DialogBox {
//
//  public NewSampleSourceType() {
//
//    VerticalPanel verticalPanel = new VerticalPanel();
//    setWidget(verticalPanel);
//
//    HorizontalPanel titleHorizontalPanel = new HorizontalPanel();
//    titleHorizontalPanel.setSpacing(4);
//    verticalPanel.add(titleHorizontalPanel);
//    verticalPanel.setCellVerticalAlignment(titleHorizontalPanel, HasVerticalAlignment.ALIGN_MIDDLE);
//    verticalPanel.setCellHorizontalAlignment(titleHorizontalPanel, HasHorizontalAlignment.ALIGN_CENTER);
//
//    Label titleLabel = new Label("Title:");
//    titleHorizontalPanel.add(titleLabel);
//    titleHorizontalPanel.setCellVerticalAlignment(titleLabel, HasVerticalAlignment.ALIGN_MIDDLE);
//    titleHorizontalPanel.setCellHorizontalAlignment(titleLabel, HasHorizontalAlignment.ALIGN_CENTER);
//
//    SuggestBox titleSuggestBox = new SuggestBox();
//    titleHorizontalPanel.add(titleSuggestBox);
//    titleHorizontalPanel.setCellVerticalAlignment(titleSuggestBox, HasVerticalAlignment.ALIGN_MIDDLE);
//    titleHorizontalPanel.setCellHorizontalAlignment(titleSuggestBox, HasHorizontalAlignment.ALIGN_CENTER);
//
//    HorizontalPanel buttonHorizontalPanel = new HorizontalPanel();
//    buttonHorizontalPanel.setSpacing(4);
//    verticalPanel.add(buttonHorizontalPanel);
//    verticalPanel.setCellVerticalAlignment(buttonHorizontalPanel, HasVerticalAlignment.ALIGN_MIDDLE);
//    verticalPanel.setCellHorizontalAlignment(buttonHorizontalPanel, HasHorizontalAlignment.ALIGN_CENTER);
//
//    Button createButton = new Button("Create");
//    createButton.setEnabled(false);
//    buttonHorizontalPanel.add(createButton);
//
//    Button cancelButton = new Button("Cancel");
//    buttonHorizontalPanel.add(cancelButton);
//  }
//
//}
