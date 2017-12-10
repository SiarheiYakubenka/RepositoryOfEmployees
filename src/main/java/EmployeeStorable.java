
public interface EmployeeStorable {
    void addEmployee(Employee employee) throws Exception;
    void delEmployee(Employee employee);
    void changeInfo(Employee employee);
    Employee findEmployeeByLastName(String lastName);
}
