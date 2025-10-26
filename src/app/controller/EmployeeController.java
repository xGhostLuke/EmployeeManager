package app.controller;

import app.model.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;

/**
 * Controller Class to manage the Employees
 *
 * @author Lukas Reißland
 */
public class EmployeeController {

    @FXML
    private Label AlertLabel;
    @FXML
    private TextField worktimeInputField;
    @FXML
    private TextField phoneInputField;
    @FXML
    private TextField lastNameInputField;
    @FXML
    private TextField emailInputField;
    @FXML
    private TextField firstNameInputField;
    @FXML
    private TextField numberInputField;
    @FXML
    private Button showEmployeeButton;
    @FXML
    private Button saveEmployeeButton;
    @FXML
    private Button deleteEmployeeButton;
    @FXML
    private Button dontReplaceEmployeeButton;
    @FXML
    private ComboBox<String> employeeComboBox;

    private ArrayList<Employee> employeesList = new ArrayList<>();

    public EmployeeController() {}

    boolean replaceBool = false;

    /**
     * Method to show Employee Information on Button Press
     * Also ask the user if he wants to edit the selected Employee
     */
    @FXML
    private void ShowEmployee() {
        String employeeName = employeeComboBox.getSelectionModel().getSelectedItem();
        String[] name = employeeName.split(" ");

        for (Employee employee : employeesList) {
            if(employee.getEmployeeID() == Double.parseDouble(name[0])) {
                numberInputField.setText(employee.getEmployeeID() + "");
                lastNameInputField.setText(employee.getLastName());
                firstNameInputField.setText(employee.getFirstName());
                phoneInputField.setText(employee.getPhone());
                emailInputField.setText(employee.getEmail());
                worktimeInputField.setText(employee.getWorktimePerWeek() + "");

                SetSaveButtonToReplace();
                AlertLabel.setText("Mitarbeiter bearbeiten?");
            }
        }
    }

    /**
     * Method to save an Employee with given Information
     * Prints out an aller to AlertLabel if given Information is in invalid format
     * If the set Number from the user is already taken he can replace Employee with the certain number
     */
    @FXML
    private void saveEmployee() {
        double employeeInput = ValidateNumberInput(numberInputField.getText().trim());

        Employee newEmployee = new Employee(
                ValidateNumberInput(numberInputField.getText().trim()),
                firstNameInputField.getText().trim(),
                lastNameInputField.getText().trim(),
                emailInputField.getText().trim(),
                phoneInputField.getText().trim(),
                ValidateNumberInput(worktimeInputField.getText().trim())
        );

        if(!IsValidEmployee(newEmployee)) {
            AlertLabel.setText("Ungültige Eingaben beim erstellen/ersetzen des Mitarbeiters!");
            return;
        }

        if(replaceBool){
            for (Employee employee : employeesList) {
                if(employee.getEmployeeID() == newEmployee.getEmployeeID()) {
                    ReplaceEmployee(employee);
                    AlertLabel.setText("Mitarbeiter wurde bearbeitet!");
                    SetSaveButtonToSave();
                    ClearInputFields();
                    refreshComboBox();
                    return;
                }
            }
            AlertLabel.setText("Fehler beim bearbeiten des Mitarbeiters!");
            SetSaveButtonToSave();
            return;
        }

        for (Employee employee : employeesList) {
            if (employee.getEmployeeID() == employeeInput) {
                AlertLabel.setText("Nummer bereits vergeben. Informationen ersetzen?");
                SetSaveButtonToReplace();
                return;
            }
        }

        AddEmployeeToList(newEmployee);
        AlertLabel.setText("Mitarbeiter wurde erstellt!");
    }

    /**
     * Sets the UI to the "create Employee" state
     */
    @FXML
    private void dontReplaceEmployee() {
        SetSaveButtonToSave();
    }

    /**
     * Deletes an Employee
     */
    @FXML
    private void deleteEmployee() {
        String employeeName = employeeComboBox.getSelectionModel().getSelectedItem();
        String[] name = employeeName.split(" ");

        for (Employee employee : employeesList) {
            if(employee.getEmployeeID() == Double.parseDouble(name[0])) {
                employeesList.remove(employee);
                refreshComboBox();
                SetSaveButtonToSave();
                ClearInputFields();
                AlertLabel.setText("Mitarbeiter gelöscht!");
                return;
            }
        }
    }

    /**
     * Adds an Employee to the Employee List. Also refreshes the ComboBox
     *
     * @param employee Employee to add
     */
    private void AddEmployeeToList(Employee employee) {

        refreshComboBox();
        employeesList.add(employee);
        employeeComboBox.getItems().add(employee.getEmployeeID() + " " + employee.getFirstName() + " " + employee.getLastName());
        ClearInputFields();
    }

    /**
     * Replaces Employee Information
     *
     * @param _employee Employee to replace
     */
    private void ReplaceEmployee(Employee _employee){
        _employee.setFirstName(firstNameInputField.getText().trim());
        _employee.setLastName(lastNameInputField.getText().trim());
        _employee.setEmail(emailInputField.getText().trim());
        _employee.setPhone(phoneInputField.getText().trim());
        _employee.setWorktimePerWeek(ValidateNumberInput(worktimeInputField.getText().trim()));
    }

    /**
     * Validates if a String holds a valid Number input
     *
     * @param _input the string to validate
     * @return Number value holded by the string
     */
    private double ValidateNumberInput(String _input){
        double out;

        try{
            out = Double.parseDouble(_input);
            return out;
        }catch(NumberFormatException e){
            AlertLabel.setText("Bitte eine gültige Nummer eingeben!");
        }
        return -1;
    }

    /**
     * checks if an Employee Object holds valid information
     *
     * @param _employee Employee that should get validated
     * @return True if Employee is valid, false if he isn´t
     */
    private boolean IsValidEmployee(Employee _employee){
        if(_employee.getEmployeeID() == -1){
            return false;
        }
        if(_employee.getEmail() == null || _employee.getEmail().isEmpty()){
            return false;
        }
        if(_employee.getFirstName() == null || _employee.getFirstName().isEmpty()){
            return false;
        }
        if(_employee.getLastName() == null || _employee.getLastName().isEmpty()){
            return false;
        }
        if(_employee.getPhone() == null || _employee.getPhone().isEmpty()){
            return false;
        }
        if(_employee.getWorktimePerWeek() == -1){
            return false;
        }

        return true;
    }

    /**
     * Clears all InputFields
     */
    private void ClearInputFields(){
        worktimeInputField.clear();
        phoneInputField.clear();
        lastNameInputField.clear();
        firstNameInputField.clear();
        emailInputField.clear();
        numberInputField.clear();
    }

    /**
     * Switches UI state to Replace State
     */
    private void SetSaveButtonToReplace(){
        saveEmployeeButton.setText("Ersetzen");
        dontReplaceEmployeeButton.setVisible(true);
        deleteEmployeeButton.setVisible(true);
        replaceBool = true;
        numberInputField.setEditable(false);
    }

    /**
     * Switches UI state to Save State
     */
    private void SetSaveButtonToSave(){
        saveEmployeeButton.setText("Speichern");
        dontReplaceEmployeeButton.setVisible(false);
        deleteEmployeeButton.setVisible(false);
        replaceBool = false;
        numberInputField.setEditable(true);
    }

    /**
     * Refreshes the Employee Combobox
     */
    private void refreshComboBox(){
        employeeComboBox.getItems().clear();

        for(Employee employee : employeesList){
            employeeComboBox.getItems().add(employee.getEmployeeID() + " " + employee.getFirstName() + " " + employee.getLastName());
        }
    }
}
