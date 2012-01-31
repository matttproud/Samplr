package org.samplr.client;

import java.util.ArrayList;
import java.util.List;

import org.samplr.shared.model.SampleSourceType;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Samplr implements EntryPoint {
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";

  private final SamplrServiceAsync samplrService = GWT.create(SamplrService.class);

  @Override
  public void onModuleLoad() {
    final TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(1.5, Unit.EM);
    final VerticalPanel sampleSourceTypePanel = new VerticalPanel();
    tabLayoutPanel.add(sampleSourceTypePanel, "SampleSourceType");

    final CellList<String> sampleSourceTypeCellList = new CellList<String>(new TextCell());
    sampleSourceTypePanel.add(sampleSourceTypeCellList);

    samplrService.createSampleSourceType(new AsyncCallback<Boolean>() {

      @Override
      public void onFailure(final Throwable caught) {
        Window.alert("f " + caught);
      }

      @Override
      public void onSuccess(final Boolean result) {
        // TODO Auto-generated method stub

      }
    });
    samplrService.getSampleSourceTypes(new AsyncCallback<List<SampleSourceType>>() {

      @Override
      public void onSuccess(final List<SampleSourceType> result) {
        final List<String> emissions = new ArrayList<String>(result.size());
        //        final ImmutableList.Builder<String> b = ImmutableList.builder();

        for (final SampleSourceType i: result) {
          emissions.add(i.getTitle());
          //          b.add(i.getTitle());
        }

        sampleSourceTypeCellList.setRowData(emissions);
        //        b.build();
      }

      @Override
      public void onFailure(final Throwable caught) {
        Window.alert(caught.toString());
      }
    });

    final VerticalPanel sampleSourcePanel = new VerticalPanel();
    tabLayoutPanel.add(sampleSourcePanel, "SampleSource");
    RootPanel.get("widgetContainer").add(tabLayoutPanel);
  }
}
