import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlRepository implements EmployeeStorable {
    private static final String XSD_PATH = "Employee.xsd";

    private String path;
    private Document document;

    private XPath xPath;

    public XmlRepository(String path) throws IOException, ParserConfigurationException, SAXException {
        if (XsdValidation.validateAgainstXSD(new FileInputStream(path), new FileInputStream(XSD_PATH))) {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(inputFile);
            xPath = XPathFactory.newInstance().newXPath();
            this.path = path;
        }

    }


    public List<Employee> read() {
        List<Employee> items = new ArrayList<Employee>();
        NodeList list = document.getElementsByTagName("employee");
        for (int i = 0; i < list.getLength(); i++) {
            Node item = list.item(i);
            Element element = (Element) item;
            items.add(getHumanByElement(element));
        }
        return items;
    }

    private Employee getHumanByElement(Element element) {
        String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
        String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
        int age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());
        int experience = Integer.parseInt(element.getElementsByTagName("experience").item(0).getTextContent());

        return new Employee(firstName, lastName, age, experience);
    }

    private Element setElementByHuman(Employee employee) {
        Element element = document.createElement("employee");

        Element firstName = document.createElement("firstName");
        firstName.setTextContent(employee.getFirstName());
        element.appendChild(firstName);

        Element lastName = document.createElement("lastName");
        lastName.setTextContent(employee.getLastName());
        element.appendChild(lastName);

        Element age = document.createElement("age");
        age.setTextContent(String.valueOf(employee.getAge()));
        element.appendChild(age);

        Element experience = document.createElement("experience");
        experience.setTextContent(String.valueOf(employee.getExperience()));
        element.appendChild(experience);

        return element;
    }
    @Override
    public void addEmployee(Employee employee) {
        Element root = (Element) document.getFirstChild();
        root.appendChild(setElementByHuman(employee));
        save();
    }
    @Override
    public void delEmployee(Employee employee) {
        NodeList list = document.getElementsByTagName("employee");
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
            if (employee.getLastName().equals(lastName))
                element.getParentNode().removeChild(element);
        }
        save();
    }
    @Override
    public void changeInfo(Employee employee) {
        NodeList list = document.getElementsByTagName("employee");
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
            if (employee.getLastName().equals(lastName))
                element.getParentNode().replaceChild(setElementByHuman(employee), element);
        }
        save();
    }
    @Override
    public Employee findEmployeeByLastName(String lastName) {
        Employee employee = null;
        NodeList list = document.getElementsByTagName("employee");
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            String foundLastName = element.getElementsByTagName("lastName").item(0).getTextContent();
            if (lastName.equals(foundLastName))
                employee = getHumanByElement(element);
        }
        return employee;
    }

    private void save() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "4");
            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
