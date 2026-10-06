# Customer Manager classroom lab (ICT261, Lecture 3)

A JavaFX desktop app that matches the six steps on the lab slide:

1. **Form** — `nameField` (TextField) and `provinceBox` (ComboBox) in `CustomerManagerApp.java`,
   each with a label linked via `setLabelFor`.
2. **Customer + ObservableList** — `Customer.java` is the model; `customers` is an
   `ObservableList<Customer>` created with `FXCollections.observableArrayList()`.
3. **TableView** — two columns (`nameCol`, `provinceCol`) use `PropertyValueFactory` to read
   `getName()` / `getProvince()`, and `table.setItems(customers)` connects the table to the list.
4. **Validation** — `addButton`'s handler trims the name, checks it isn't empty, checks a
   province is chosen, and only then adds the `Customer` and clears the fields. Invalid input
   keeps what the user typed and moves focus to the field that needs fixing.
5. **Delete confirmation** — `deleteButton`'s handler needs a selected row, then shows a
   `CONFIRMATION` `Alert` with **Delete** / **Cancel** buttons; the row is removed only if
   **Delete** is chosen.
6. **Keyboard access** — `addButton.setDefaultButton(true)` lets Enter trigger Add from
   anywhere on the form; `nameField.requestFocus()` sets the starting focus; Tab order follows
   the layout order (name → province → Add → Delete → table).

## How to run

1. Open the `CustomerManager` folder as a project in IntelliJ IDEA (File → Open).
2. Let Gradle finish syncing.
3. Open the IntelliJ terminal (View → Tool Windows → Terminal) and run:

   ```
   ./gradlew run
   ```

4. Try it out:
   - Click **Add customer** with the name blank → should show "Enter the customer name."
     and focus the name field.
   - Fill in a name but leave province unselected → should show "Choose a province." and
     focus the ComboBox.
   - Fill in both and click **Add customer** (or press Enter) → row appears in the table,
     fields clear.
   - Select a row and click **Delete selected** → confirmation dialog appears; **Cancel**
     leaves the row, **Delete** removes it.
   - Click **Delete selected** with nothing selected → "Select a customer to delete."
   - Tab through the controls to check the focus order, and confirm Enter submits the form.
