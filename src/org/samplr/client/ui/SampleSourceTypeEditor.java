///**
// *
// */
//package org.samplr.client.ui;
//
//import org.samplr.shared.model.SampleSourceType;
//
//import com.google.gwt.event.dom.client.ChangeEvent;
//import com.google.gwt.event.dom.client.ChangeHandler;
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.event.logical.shared.ValueChangeEvent;
//import com.google.gwt.event.logical.shared.ValueChangeHandler;
//import com.google.gwt.user.client.Window;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.HasHorizontalAlignment;
//import com.google.gwt.user.client.ui.HasVerticalAlignment;
//import com.google.gwt.user.client.ui.HorizontalPanel;
//import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.TextBox;
//import com.google.gwt.user.client.ui.VerticalPanel;
//
///**
// * @author mtp
// *
// */
//public class SampleSourceTypeEditor extends VerticalPanel {
//  private final MutationHandler commitHandler;
//  private final Button commitButton;
//
//  SampleSourceTypeEditor(final boolean commitable, final String title, final MutationHandler commitHandler) {
//    setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
//    setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
//
//    final HorizontalPanel titleHorizontalPanel = new HorizontalPanel();
//    titleHorizontalPanel.setSpacing(4);
//    add(titleHorizontalPanel);
//    setCellVerticalAlignment(titleHorizontalPanel, HasVerticalAlignment.ALIGN_MIDDLE);
//    setCellHorizontalAlignment(titleHorizontalPanel, HasHorizontalAlignment.ALIGN_CENTER);
//
//    final Label titleLabel = new Label("Title:");
//    titleHorizontalPanel.add(titleLabel);
//    titleHorizontalPanel.setCellVerticalAlignment(titleLabel, HasVerticalAlignment.ALIGN_MIDDLE);
//    titleHorizontalPanel.setCellHorizontalAlignment(titleLabel, HasHorizontalAlignment.ALIGN_CENTER);
//
//    final TextBox titleTextBox = new TextBox();
//    titleHorizontalPanel.add(titleTextBox);
//    titleHorizontalPanel.setCellVerticalAlignment(titleTextBox, HasVerticalAlignment.ALIGN_MIDDLE);
//    titleHorizontalPanel.setCellHorizontalAlignment(titleTextBox, HasHorizontalAlignment.ALIGN_CENTER);
//    titleTextBox.setText(title);
//    titleTextBox.addValueChangeHandler(new ValueChangeHandler<String>() {
//      @Override
//      public void onValueChange(final ValueChangeEvent<String> event) {
//        final String v = event.getValue();
//        Window.alert("vc " + v);
//
//        commitButton.setEnabled(!"".equals(v));
//      }
//    });
//    titleTextBox.addChangeHandler(new ChangeHandler() {
//
//      @Override
//      public void onChange(final ChangeEvent event) {
//        Window.alert("oc");
//        final String v = titleTextBox.getValue();
//        Window.alert("oc " + v);
//        commitButton.setEnabled(!"".equals(v));
//      }
//
//    });
//
//    final HorizontalPanel buttonHorizontalPanel = new HorizontalPanel();
//    buttonHorizontalPanel.setSpacing(4);
//    add(buttonHorizontalPanel);
//    setCellVerticalAlignment(buttonHorizontalPanel, HasVerticalAlignment.ALIGN_MIDDLE);
//    setCellHorizontalAlignment(buttonHorizontalPanel, HasHorizontalAlignment.ALIGN_CENTER);
//
//    commitButton = new Button("Commit");
//    commitButton.setEnabled(commitable);
//    commitButton.addClickHandler(new ClickHandler() {
//      @Override
//      public void onClick(final ClickEvent event) {
//        final SampleSourceType future = new SampleSourceType(titleTextBox.getText());
//
//        if (!"".equals(titleTextBox.getText())) {
//          commitHandler.create(future);
//        } else {
//          final SampleSourceType original = new SampleSourceType(title);
//
//          commitHandler.replace(original, future);
//        }
//      }
//    });
//    buttonHorizontalPanel.add(commitButton);
//
//    final Button cancelButton = new Button("Cancel");
//    buttonHorizontalPanel.add(cancelButton);
//
//    this.commitHandler = commitHandler;
//  }
//
//  public static class Factory {
//    public SampleSourceTypeEditor nonexistent(final MutationHandler mutationHandler) {
//      return new SampleSourceTypeEditor(false, "", mutationHandler);
//    }
//
//    public SampleSourceTypeEditor existing(final SampleSourceType entity, final MutationHandler commitHandler) {
//      return new SampleSourceTypeEditor(true, entity.getTitle(), commitHandler);
//    }
//  }
//
//  public static interface MutationHandler {
//    boolean replace(SampleSourceType original, SampleSourceType future);
//    boolean create(SampleSourceType future);
//    boolean delete(SampleSourceType original);
//  }
//}
