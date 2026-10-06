package com.example.customermanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Customer Manager classroom lab (ICT261, Lecture 3, slide 29).
 *
 * 1. Build a form with a name field and province list.
 * 2. Create Customer and an ObservableList<Customer>.
 * 3. Add a TableView with name and province columns.
 * 4. Validate input, then add the customer.
 * 5. Confirm deletion of a selected customer.
 * 6. Test invalid input and keyboard access.
 */
public class CustomerManagerApp extends Application {

    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {

        // ----- Step 1: form with a name field and province list -----
        Label nameLabel = new Label("Customer name");
        TextField nameField = new TextField();
        nameField.setPromptText("e.g., Mary Banda");
        nameLabel.setLabelFor(nameField);

        Label provinceLabel = new Label("Province");
        ComboBox<String> provinceBox = new ComboBox<>();
        provinceBox.getItems().addAll(
                "Central", "Lusaka", "Copperbelt",
                "Eastern", "Northern", "Southern",
                "Western", "North-Western", "Luapula", "Muchinga");
        provinceBox.setPromptText("Choose a province");
        provinceLabel.setLabelFor(provinceBox);

        Label status = new Label();

        Button addButton = new Button("Add customer");
        // Step 6: Enter activates this button from anywhere on the form.
        addButton.setDefaultButton(true);

        Button deleteButton = new Button("Delete selected");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(15));
        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);
        form.add(provinceLabel, 0, 1);
        form.add(provinceBox, 1, 1);

        // ----- Step 3: TableView with name and province columns -----
        TableView<Customer> table = new TableView<>();
        table.setItems(customers);
        table.setPlaceholder(new Label("No customers yet. Add one above."));

        TableColumn<Customer, String> nameCol = new TableColumn<>("Customer name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);

        TableColumn<Customer, String> provinceCol = new TableColumn<>("Province");
        provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));
        provinceCol.setPrefWidth(150);

        table.getColumns().add(nameCol);
        table.getColumns().add(provinceCol);

        // ----- Step 4: validate input, then add the customer -----
        addButton.setOnAction(event -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                status.setText("Enter the customer name.");
                nameField.requestFocus();
                return;
            }

            String province = provinceBox.getValue();
            if (province == null) {
                status.setText("Choose a province.");
                provinceBox.requestFocus();
                return;
            }

            customers.add(new Customer(name, province));
            status.setText("Customer saved.");

            // Clear the fields only after the save succeeds.
            nameField.clear();
            provinceBox.setValue(null);
            nameField.requestFocus();
        });

        // ----- Step 5: confirm deletion of a selected customer -----
        deleteButton.setOnAction(event -> {
            Customer selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                status.setText("Select a customer to delete.");
                return;
            }

            ButtonType delete = new ButtonType("Delete");
            Alert ask = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete the selected customer?",
                    delete, ButtonType.CANCEL);
            ask.setHeaderText("Confirm deletion");

            if (ask.showAndWait().orElse(ButtonType.CANCEL) == delete) {
                customers.remove(selected);
                status.setText("Customer deleted.");
            } else {
                status.setText("Deletion cancelled.");
            }
        });

        HBox buttons = new HBox(10, addButton, deleteButton);
        buttons.setAlignment(Pos.CENTER_LEFT);
        buttons.setPadding(new Insets(0, 15, 10, 15));

        VBox root = new VBox(10, form, buttons, table, status);
        root.setPadding(new Insets(10));
        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);
        status.setPadding(new Insets(0, 15, 10, 15));

        Scene scene = new Scene(root, 600, 450);
        stage.setTitle("Customer Manager");
        stage.setScene(scene);
        stage.show();

        // Step 6: start with focus on the first field in a logical order.
        nameField.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
