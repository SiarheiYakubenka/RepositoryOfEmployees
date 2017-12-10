import entities.Employee;
import entities.EmployeeStorable;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException, XPathExpressionException {

        System.out.println(XsdValidation.validateAgainstXSD(new FileInputStream("Employees.xml")
                ,new FileInputStream("Employee.xsd")));
        DomParser parser = new DomParser("Employees.xml");

//        SaxParser parser = new SaxParser("employees.xml");

//        Human human = new Employee("Svetlana", "Petrova", 19, 3);
//        parser.create(human);

      List<Employee> employees = parser.read();
//        parser.delete(employees.get(5));

//        employees.get(4).setFirstName("Elena");
//        ((Student)employees.get(4)).setGroup("T-3");
//        parser.update(employees.get(4));

        for (Employee item : employees){
            System.out.println(item);
        }

        EmployeeStorable repository = parser;
        System.out.println();
        //Employee employee = new Employee("Ivan", "Ivanov", 25, 7);
        //repository.changeInfo(employee);
        //System.out.println(repository.findEmployeeByLastName("Ivanov"));
        //System.out.println();
    }
}
