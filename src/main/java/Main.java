import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException, XPathExpressionException {

        XsdValidation.validateAgainstXSD(new FileInputStream("Employees.xml")
                ,new FileInputStream("Employee.xsd"));
        XmlRepository parser = new XmlRepository("Employees.xml");

//        SaxParser parser = new SaxParser("employees.xml");

//        Human human = new Employee("Svetlana", "Petrova", 19, 3);
//        parser.create(human);

     // List<Employee> employees = parser.read();
//        parser.delete(employees.get(5));

//        employees.get(4).setFirstName("Elena");
//        ((Student)employees.get(4)).setGroup("T-3");
//        parser.update(employees.get(4));

        TxtRepository txtRepository = new TxtRepository(new File("Repository.txt"));
        Employee employee = txtRepository.findEmployeeByLastName("Ivanov");
        if (employee != null){
            System.out.println(employee);
        }else {
            System.out.println("Рабочего с такой фамилией нет");
        }

//        Employee employee = new Employee();
//        employee.setLastName("Petrova");

//        txtRepository.delEmployee(employee);

       // Employee addemployee = new Employee("Elena", "Petrova", 24, 1);
       // txtRepository.changeInfo(addemployee);
       // for (Employee item : employees){
       //     try {
       //         txtRepository.addEmployee(item);
       //     } catch (Exception e) {
       //         System.out.println(e.getMessage());
       //     }
       //}

        //Employee employee = new Employee("Ivan", "Ivanov", 25, 7);
        //repository.changeInfo(employee);
        //System.out.println(repository.findEmployeeByLastName("Ivanov"));
        //System.out.println();
    }
}
