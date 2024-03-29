/*
 * Copyright (c) 2023 Tim Vaughan
 *
 * This file is part of feast.
 *
 * feast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * feast is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with feast. If not, see <https://www.gnu.org/licenses/>.
 */

package feast.app;

import beast.base.core.BEASTObject;
import beast.base.core.Input;
import beast.pkgmgmt.BEASTClassLoader;
import beast.pkgmgmt.PackageManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * A basic GUI app for exploring the BEAST classes made available
 * by the packages currently installed on your system.
 * (This is essentially equivalent to the information presented by
 * DocMaker, but this app avoids having to generate temporary HTML files,
 * and the information is presented in a slightly more compact way.)
 */
public class FeastQuery extends Application {

    /**
     * Map from package names (really just the root package of the service
     * FQCNs) to lists containing the processed BOInfo objects.
     */
    SortedMap<String, List<BOInfo>> beastObjectsByPackage;
    List<String> allBeastObjectNames;

    /**
     * Populate the beastObjects map.
     * @throws IOException thrown by PackageManager.loadExternalJars() when
     *                     something goes wrong.
     */
    void loadBeastObjects() throws IOException {
        beastObjectsByPackage = new TreeMap<>();
        allBeastObjectNames = new ArrayList<>();

        PackageManager.loadExternalJars();

        for (String service : PackageManager.listServices("beast.base.core.BEASTInterface")) {
            BOInfo boInfo = new BOInfo(service);
            if (boInfo.loadingError)
                continue;

            if (!beastObjectsByPackage.containsKey(boInfo.packageName))
                beastObjectsByPackage.put(boInfo.packageName, new ArrayList<>());

            beastObjectsByPackage.get(boInfo.packageName).add(boInfo);
            allBeastObjectNames.add(boInfo.classNameFQ);
        }

        // Add some special BEASTObjects: (Is there an easy way to populate this list automatically?)
        allBeastObjectNames.add("beast.base.core.Function");
        allBeastObjectNames.add("beast.base.evolution.TreeInterface");
        allBeastObjectNames.add("beast.base.inference.StateNode");
        allBeastObjectNames.add("beast.base.inference.CalculationNode");
        allBeastObjectNames.add("beast.base.inference.Distribution");
        allBeastObjectNames.add("beast.base.inference.distribution.ParametricDistribution");
        allBeastObjectNames.add("beast.base.inference.StateNodeInitialiser");
        allBeastObjectNames.add("beast.base.evolution.substitionmodel.SubtitutionModel");
        allBeastObjectNames.add("beast.base.evolution.branchratemodel.BranchRateModel");

        // Ensure objects are sorted lexicographically
        for (String packageName : beastObjectsByPackage.keySet()) {
            beastObjectsByPackage.get(packageName).sort(Comparator.comparing(o -> o.classNameFQ));
        }
        allBeastObjectNames.sort(String::compareTo);
    }

    TextField searchBox;
    ComboBox<String> assignableToBox;
    WebEngine objectInfoContent;

    Stack<BOInfo> pageHistory;

    private Parent initializeUI() {

        BorderPane bp = new BorderPane();
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().add(createTreeView());
        splitPane.setDividerPositions(0.33);

        VBox vBox = new VBox();
        HBox hBox = new HBox();
        Button backButton = new Button("<- Previous page");
        hBox.getChildren().add(backButton);
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        vBox.getChildren().add(hBox);

        WebView webView = new WebView();
        objectInfoContent = webView.getEngine();
        vBox.getChildren().add(webView);
        VBox.setVgrow(webView, Priority.ALWAYS);

        splitPane.getItems().add(vBox);
        bp.setCenter(splitPane);

        hBox = new HBox();
        hBox.getChildren().add(new Label("Filter: "));

        searchBox = new TextField();
        hBox.getChildren().add(searchBox);

        hBox.getChildren().add(new Label(" Assignable to:"));
        assignableToBox = new ComboBox<>(FXCollections.observableList(allBeastObjectNames));
        assignableToBox.setMaxWidth(300);
        hBox.getChildren().add(assignableToBox);

        Button clearButton = new Button("Clear filters");
        hBox.getChildren().add(clearButton);

        HBox.setHgrow(searchBox, Priority.ALWAYS);
        bp.setBottom(hBox);

        displayObjectInfo(null);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> filterObjectTree());
        assignableToBox.setOnAction(event -> filterObjectTree());

        objectInfoContent.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == Worker.State.SUCCEEDED) {
                EventListener listener = evt -> {
                    String href = ((Element) evt.getTarget()).getAttribute("href");
                    assignableToBox.setValue(href);
                };

                Document doc = objectInfoContent.getDocument();
                NodeList links = doc.getElementsByTagName("a");
                for (int i=0; i<links.getLength(); i++) {
                    ((EventTarget) links.item(i)).addEventListener("click", listener, true);
                }
            }

        });

        clearButton.setOnAction(event -> {
            searchBox.setText("");
            assignableToBox.setValue("");
        });

        backButton.setOnAction(event -> {
            if (pageHistory.size()>1) {
                pageHistory.pop();
                displayObjectInfo(pageHistory.peek());
            }
        });

        pageHistory = new Stack<>();

        return bp;
    }

    TreeItem<BOTreeEntry> objectTreeRoot, objectTreeRootCopy;

    private Parent createTreeView() {
        objectTreeRoot = new TreeItem<> (new BOTreeEntry("Installed Packages"));
        objectTreeRootCopy = new TreeItem<> (objectTreeRoot.getValue());

        for (String packageName : beastObjectsByPackage.keySet()) {
            TreeItem<BOTreeEntry> packageItem = new TreeItem<> (new BOTreeEntry(packageName));
            TreeItem<BOTreeEntry> packageItemCopy = new TreeItem<>(packageItem.getValue());

            for (BOInfo boInfo : beastObjectsByPackage.get(packageName)) {
                TreeItem<BOTreeEntry> boItem = new TreeItem<>(new BOTreeEntry(boInfo.classNameFQ, boInfo));
                packageItem.getChildren().add(boItem);
                packageItemCopy.getChildren().add(boItem);
            }

            packageItem.getValue().size = packageItem.getChildren().size();

            objectTreeRoot.getChildren().add(packageItem);
            objectTreeRootCopy.getChildren().add(packageItemCopy);
        }

        objectTreeRoot.getValue().size = objectTreeRoot.getChildren().size();
        objectTreeRoot.expandedProperty().setValue(true);

        TreeView<BOTreeEntry> treeView = new TreeView<> (objectTreeRoot);
        treeView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        BOTreeEntry itemLabel = newValue.getValue();
                        if (itemLabel.boInfo != null) {
                            displayObjectInfo(itemLabel.boInfo);
                            if (pageHistory.isEmpty() || !pageHistory.peek().equals(itemLabel.boInfo))
                                pageHistory.push(itemLabel.boInfo);
                        }
                    }
        });

        StackPane treePane = new StackPane();
        treePane.getChildren().add(treeView);

        return treePane;
    }

    /**
     * Replaces the object tree with a tree containing
     * only those services containing a specific substring.
     */
    private void filterObjectTree() {

        // Extract assignable to class and search string from UI elements.
        // (It's ugly to have UI code polluting this method, but easy
        // alternatives were uglier!)

        String assignableToClassName = assignableToBox.getSelectionModel().getSelectedItem();
        if (assignableToClassName == null)
            assignableToClassName = "";
        else
            assignableToClassName = assignableToClassName.trim();

        if (assignableToClassName.isEmpty())
            assignableToClassName = "beast.base.core.BEASTInterface";
        Class<?> assignableToClass;
        try {
            assignableToClass = BEASTClassLoader.forName(assignableToClassName);
        } catch (ClassNotFoundException e) {
            return; // Abort filtering
        }

        String searchString = searchBox.getText().trim().toLowerCase();

        // Do the filtering:

        objectTreeRoot.getChildren().clear();
        for (TreeItem<BOTreeEntry> packageItemCopy : objectTreeRootCopy.getChildren()) {
            TreeItem<BOTreeEntry> packageItem = new TreeItem<>(packageItemCopy.getValue());

            for (TreeItem<BOTreeEntry> objectItem : packageItemCopy.getChildren()) {
                if (objectItem.getValue().boInfo.classNameFQ.toLowerCase().contains(searchString)
                        && assignableToClass.isAssignableFrom(objectItem.getValue().boInfo.beastClass))
                    packageItem.getChildren().add(objectItem);
            }

            if (!packageItem.getChildren().isEmpty())
                objectTreeRoot.getChildren().add(packageItem);

            packageItem.getValue().size = packageItem.getChildren().size();
        }

        objectTreeRoot.getValue().size = objectTreeRoot.getChildren().size();
    }

    /**
     * Display documentation for the chosen object in the right-hand panel.
     *
     * @param boInfo determines which BEASTObject to show documentation for.
     */
    private void displayObjectInfo(BOInfo boInfo) {
        if (boInfo == null) {
            objectInfoContent.loadContent("<i>Select an object from the tree on the left.</i>");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><style>")
                .append("table { width: 95%; }\n")
                .append("th { text-align: left; padding-right: 15px; background: black; color: white}\n")
                .append("table,tr,td {")
                .append("border: 1px solid gray;")
                .append("border-collapse: collapse;")
                .append(")}\n")
                .append(".req { background: #fbb; }\n")
                .append("</style></head><body>");

        sb.append("<p><b>Object Name:</b> ").append(boInfo.classNameFQ).append("</p>");
        sb.append("<p><b>Package:</b> ").append(boInfo.packageName).append("</p>");
        sb.append("<p><b>Description:</b><br><i>").append(boInfo.description).append("</i></p>");

        if (boInfo.inputInfos.isEmpty())
        sb.append("<b>Object defines no inputs.</b>");
        else {
            sb.append("<p><b>Inputs:</b> (Required inputs are highlighted in " +
                    "<span class=\"req\">red</span>.)<br>");
            sb.append("<table><tr>")
                    .append("<th>Input</th>")
                    .append("<th>Type</th>")
                    .append("<th>Default</th>")
                    .append("<th>Description</th>")
                    .append("</tr>");
            for (InputInfo inputInfo : boInfo.inputInfos) {
                String typeLink;
                if (inputInfo.inputClassNameFQ == null) {
                    typeLink = "-";
                } else {
                    if (allBeastObjectNames.contains(inputInfo.inputClassNameFQ)) {
                        typeLink = "<a href=\"" + inputInfo.inputClassNameFQ + "\">" +
                                inputInfo.inputClassName + "</a>";
                    } else {
                        typeLink = inputInfo.inputClassName;
                    }
                    if (inputInfo.isList)
                        typeLink = "[" + typeLink + "]";
                }
                sb.append(inputInfo.required ? "<tr class=\"req\">" : "<tr>")
                        .append("<td>").append(inputInfo.inputName).append("</td>")
                        .append("<td>").append(typeLink).append("</td>")
                        .append("<td>").append(inputInfo.defaultValue == null ? "-" : inputInfo.defaultValue).append("</td>")
                        .append("<td>").append(inputInfo.tipText).append("</td></tr>");
            }
            sb.append("</table></p>");


        }

        if (!boInfo.citations.trim().isEmpty())
            sb.append("<p><b>Citation:</b><br><i>").append(boInfo.citations).append("</i></p>");

        sb.append("</body></html>");

        objectInfoContent.loadContent(sb.toString());

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Feast Query");

        loadBeastObjects();

        primaryStage.setScene(new Scene(initializeUI(), 1024, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            launch(FeastQuery.class, args);
        } catch (IllegalStateException e) {
            Platform.runLater(() -> {
                try {
                    FeastQuery feastQuery = new FeastQuery();
                    Stage primaryStage = new Stage();
                    feastQuery.start(primaryStage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    /**
     * Class of objects representing individual "services", i.e.
     * children of BEASTObject.
     */
    public static class BOInfo {
        String packageName;
        String className, classNameFQ;
        String description;
        String citations;

        Class<?> beastClass;

        List<InputInfo> inputInfos;

        boolean loadingError;


        /**
         * Create a new BOInfo object.  Involves lots of introspection
         * of the specified service in order to determine information
         * relevant to documentation.
         *
         * @param service fully qualified class name of the service.
         */
        public BOInfo(String service) {
            classNameFQ = service;
            className = service.substring(service.lastIndexOf('.')+1);
            packageName = service.substring(0, service.indexOf('.'));

            loadingError = false;

            try {
                beastClass = BEASTClassLoader.forName(
                        classNameFQ,
                        "beast.base.core.BEASTInterface");
                BEASTObject o = (BEASTObject) beastClass.newInstance();

                description = o.getDescription();
                citations = o.getCitations();

                inputInfos = new ArrayList<>();
                for (Field field : beastClass.getFields()) {
                    if (!field.getType().isAssignableFrom(Input.class))
                        continue;

                    inputInfos.add(new InputInfo(o, field));
                }

            } catch (NoClassDefFoundError | ClassNotFoundException | InstantiationException |
                     ClassCastException | IllegalAccessException e) {
                loadingError = true;
            }
        }

        public String toString() {
            return classNameFQ;
        }
    }

    /**
     * Class of objects representing documentation-relevant information
     * about individual inputs.
     */
    public static class InputInfo {
        String inputName;
        String tipText;
        String inputClassNameFQ;
        String inputClassName;
        Boolean isList;
        Boolean required;
        Object defaultValue;


        List<BOInfo> assignableFrom;

        /**
         * Create a new InputInfo object.  Both the BEASTObject associated with
         * the input as well as the field corresponding to the actual input
         * are required in order to extract all of the documentation-relevant
         * information.
         *
         * @param beastObject BEASTObject possessing the input
         * @param inputField field corresponding to the input
         * @throws IllegalAccessException
         */
        public InputInfo(BEASTObject beastObject, Field inputField) throws IllegalAccessException {
            Input<?> input = (Input<?>) inputField.get(beastObject);

            inputName = input.getName();
            tipText = input.getTipText();

            try {
                // Awful do-si-do required to extract type of input.
                Type[] types = ((ParameterizedType) inputField.getGenericType()).getActualTypeArguments();
                Class<?> inputClass;

                isList = input.get() instanceof List;

                if (isList)
                    inputClass = (Class<?>) ((ParameterizedType) types[0]).getActualTypeArguments()[0];
                else
                    inputClass = (Class<?>) types[0];

                inputClassNameFQ = inputClass.getName();
                if (inputClassNameFQ.contains("$"))
                    inputClassNameFQ = inputClassNameFQ.substring(0, inputClassNameFQ.lastIndexOf('$'));
                inputClassName = inputClassNameFQ.substring(inputClass.getName().lastIndexOf('.') + 1);

            } catch (ClassCastException e) {
                // Nothing to do.
            }

            required = input.getRule().equals(Input.Validate.REQUIRED);
            defaultValue = input.defaultValue;
        }

    }

    /**
     * Class of objects representing individual entries in the beast object
     * tree.  This is only necessary because I want to mix both BOInfo objects
     * and non-BOInfo objects (packages names, the tree root) in the same tree,
     * while also being able to retrieve the BOInfo objects for specific
     * tree elements when available.
     */
    public static class BOTreeEntry {
        String display;
        BOInfo boInfo;
        Integer size;

        public BOTreeEntry(String display, BOInfo boInfo) {
            this.display = display;
            this.boInfo = boInfo;
        }

        public BOTreeEntry(String display) {
            this(display, null);
        }

        @Override
        public String toString() {
            return display + (size == null ? "" : " (" + size + ")");
        }
    }
}