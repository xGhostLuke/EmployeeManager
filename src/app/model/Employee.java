package app.model;

public class Employee {

    private double employeeID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private double worktimePerWeek;

    public Employee(double _employeeID, String _firstName, String _lastName, String _email, String _phone, double _worktimePerWeek) {
        setEmployeeID(_employeeID);
        setFirstName(_firstName);
        setLastName(_lastName);
        setEmail(_email);
        setPhone(_phone);
        setWorktimePerWeek(_worktimePerWeek);
    }

    public double getWorktimePerWeek() {
        return worktimePerWeek;
    }

    public void setWorktimePerWeek(double _worktimePerWeek) {
        this.worktimePerWeek = _worktimePerWeek;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String _phone) {
        this.phone = _phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String _email) {
        this.email = _email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String _lastName) {
        this.lastName = _lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String _firstName) {
        this.firstName = _firstName;
    }

    public double getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(double _employeeID) {
        this.employeeID = _employeeID;
    }
}
