package com.ce216.projectce216;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TreeView<String> treeView;

    @FXML
    private TableView<Item> tableView;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addItemButton;

    @FXML
    private Button addTypeButton;

    @FXML
    private Button valueButton;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField itemTextField;

    @FXML
    private TextField valueTextField;

    @FXML
    private TextField keyTextField;

    Item item = null;
    List<Item> items = Arrays.<Item>asList();
    TreeItem<String> rootNode = new TreeItem<>("Types of My Collection");

    ObservableList<Item> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootNode.setExpanded(true);

        for (Item item : items) {
            TreeItem<String> itemLeaf = new TreeItem<>(item.getName());
            boolean found = false;
            for (TreeItem<String> typeNode : rootNode.getChildren()) {
                if (typeNode.getValue().contentEquals(item.getType())) {
                    typeNode.getChildren().add(itemLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> typeNode = new TreeItem<>(item.getType());
                rootNode.getChildren().add(typeNode);
                typeNode.getChildren().add(itemLeaf);
            }
        }
        treeView.setRoot(rootNode);
        treeView.setEditable(true);

        tableView.setEditable(true);

        TableColumn type = new TableColumn("Type");
        TableColumn name = new TableColumn("Item");

        tableView.getColumns().addAll(type, name);

        name.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        type.setCellValueFactory(new PropertyValueFactory<Item, String>("type"));

        tableView.setItems(list);

    }

    @FXML
    void selectItem() {
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
        if (item != null) {
        }
    }

    @FXML
    void delete(ActionEvent e) {
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TreeItem c = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                if (c == null) {
                    return;
                }
                if (c.getParent() != null) {
                    boolean remove = c.getParent().getChildren().remove(c);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR!");
                    alert.setHeaderText("Something went wrong:/");
                    alert.setContentText("You cannot delete the root!");
                    alert.showAndWait();
                }
            }
        });
    }
    // delete ilk tıklandığında çalışmıyor

    @FXML
    void createType() {
        if(typeTextField.getText().length() != 0) {
            TreeItem<String> newType = new TreeItem<>(typeTextField.getText());
            rootNode.getChildren().add(newType);
            typeTextField.setText("");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION!");
            alert.setHeaderText("Something went wrong:/");
            alert.setContentText("Please enter something.");
            alert.showAndWait();
        }
    }

    @FXML
    void createItem() {
        if (itemTextField.getText().length() != 0) {
            TreeItem<String> newItem = new TreeItem<>(itemTextField.getText());
            TreeItem<String> itemNode = treeView.getSelectionModel().getSelectedItem();
            if (itemNode != null) {
                itemNode.getChildren().add(newItem);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Item Creation");
                alert.setHeaderText("You have not selected which type you want to add the item to!");
                alert.setContentText("Please try again by selecting the type you want to add the item to.");
                alert.showAndWait();
            }
            itemTextField.setText("");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION!");
            alert.setHeaderText("Something went wrong:/");
            alert.setContentText("Please enter something.");
            alert.showAndWait();
        }
    }

    @FXML
    void createKey() {
        TableColumn key = new TableColumn(keyTextField.getText());
        tableView.getColumns().add(key);
        keyTextField.setText("");
    }

    void createValue() {
    }

}