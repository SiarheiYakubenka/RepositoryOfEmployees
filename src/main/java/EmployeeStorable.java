public interface EmployeeStorable {
    void addEmployee(Employee employee);
    void delEmployee(Employee employee);
    void changeInfo(Employee employee);
    Employee findEmployeeByLastName(String lastName);
}
