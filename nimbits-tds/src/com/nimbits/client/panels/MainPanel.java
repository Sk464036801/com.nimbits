/*
 * Copyright (c) 2010 Tonic Solutions LLC.
 *
 * http://www.nimbits.com
 *
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the license is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.nimbits.client.panels;

import com.extjs.gxt.ui.client.Style.*;
import com.extjs.gxt.ui.client.util.*;
import com.extjs.gxt.ui.client.widget.*;
import com.extjs.gxt.ui.client.widget.layout.*;
import com.nimbits.client.enums.*;
import com.nimbits.client.model.*;
import com.nimbits.client.model.category.*;
import com.nimbits.client.model.diagram.*;
import com.nimbits.client.model.email.*;
import com.nimbits.client.model.point.Point;

import java.util.*;

public class MainPanel extends NavigationEventProvider {

    private final CenterPanel center = new CenterPanel();
    private EmailAddress emailAddress;
    private ContentPanel west;
    private NavigationPanel navigationPanel;
    private Map<String, String> settings;
    final NavigationPanel createNavigationPanel(ClientType clientType) {
        final NavigationPanel navTree = new NavigationPanel(emailAddress, false, clientType, settings);


        navTree.addCategoryClickedListeners(new CategoryClickedListener() {

            @Override
            public void onCategoryClicked(final Category c, boolean readOnly)  {

                notifyCategoryClickedListener(c, readOnly);

            }

        });

        navTree.addPointClickedListeners(new PointClickedListener() {

            @Override
            public void onPointClicked(final Point p) {

                notifyPointClickedListener(p);
            }

        });

        navTree.addDiagramClickedListeners(new DiagramClickedListener() {

            @Override
            public void onDiagramClicked(final Diagram p) {

                notifyDiagramClickedListener(p);
            }

        });

        navTree.addPointDeletedListeners(new PointDeletedListener() {

            @Override
            public void onPointDeleted(final Point c)  {
                notifyPointDeletedListener(c);
                center.removePoint(c);
            }

        });

        navTree.addDiagramDeletedListeners(new DiagramDeletedListener() {

            @Override
            public void onDiagramDeleted(final Diagram c, final boolean readOnly) {
                notifyDiagramDeletedListener(c, readOnly);
            }

        });
        return navTree;

    }


    public void addPoint(final Point point)  {
        center.addPoint(point);
    }
    public void addPointToTree(final Point point) {
        navigationPanel.addNewlyCreatedPointToTree(point);
    }

    public MainPanel(final LoginInfo loginInfo,
                     final boolean doAndroid,
                     final boolean loadConnections,
                     final Map<String, String> settings)   {

        this.settings = settings;

        if (doAndroid) {
            loadAndroidLayout(loginInfo);
        } else {
            loadBorderLayout(loginInfo, loadConnections);
        }


    }

    private void loadAndroidLayout(LoginInfo loginInfo)  {
        final FillLayout layout = new FillLayout();
        this.emailAddress = loginInfo.getEmailAddress();
        setLayout(layout);


        final ContentPanel west = new ContentPanel();
        final NavigationPanel navigationPanel = createNavigationPanel(ClientType.android);
        navigationPanel.setLayout(new FillLayout());
        navigationPanel.setHeight(1280);
        //  navigationPanel.setAutoHeight(true);
        west.setHeaderVisible(false);
        west.add(navigationPanel);
        west.setHeight("100%");
        west.setHeading("Navigator");

        add(west);


    }
    public void loadTree() {
        west.removeAll();
        navigationPanel = createNavigationPanel(ClientType.other);
        navigationPanel.setLayout(new FillLayout());

        west.add(navigationPanel);
    }

    private void loadBorderLayout(final LoginInfo loginInfo,final boolean loadConnections)   {
        final BorderLayout layout = new BorderLayout();
        this.emailAddress = loginInfo.getEmailAddress();
        setLayout(layout);


        west = new ContentPanel();
        loadTree();
        west.setHeight("100%");
        west.setHeading("Navigator");

        final ContentPanel east = new ContentPanel();

        east.setHeading("Connections");
        if (loadConnections) {
            ConnectionPanel connections = createConnections(loginInfo.getEmailAddress());
            east.add(connections);
        }


        final BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST, 250);
        westData.setSplit(true);
        westData.setCollapsible(true);
        westData.setMargins(new Margins(5));

        final BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
        centerData.setMargins(new Margins(5, 5, 5, 0));

        final BorderLayoutData eastData = new BorderLayoutData(LayoutRegion.EAST, 175);
        eastData.setSplit(true);
        eastData.setCollapsible(true);
        eastData.setMargins(new Margins(5));

        final BorderLayoutData southData = new BorderLayoutData(LayoutRegion.SOUTH,
                125);
        southData.setSplit(true);
        southData.setCollapsible(true);
        southData.setMargins(new Margins(0, 0, 0, 0));
        west.setLayout(new FillLayout(Orientation.VERTICAL));

        add(west, westData);
        if (loadConnections) {
            add(east, eastData);
        }

        center.setLayout(new FillLayout());
        add(center, centerData);
    }

    private ConnectionPanel createConnections(final EmailAddress email)   {
        final ConnectionPanel connections = new ConnectionPanel(email, settings);

        connections.addCategoryClickedListeners(new CategoryClickedListener() {

            @Override
            public void onCategoryClicked(final Category c, boolean readOnly)  {

                notifyCategoryClickedListener(c, readOnly);

            }

        });

        connections.addPointClickedListeners(new PointClickedListener() {

            @Override
            public void onPointClicked(final Point p){

                notifyPointClickedListener(p);
            }

        });
        connections.addDiagramClickedListeners(new DiagramClickedListener() {

            @Override
            public void onDiagramClicked(final Diagram p) {

                notifyDiagramClickedListener(p);
            }

        });

        return connections;
    }

    public void addDiagram(final Diagram d) {
        center.addDiagram(d);
    }
}
