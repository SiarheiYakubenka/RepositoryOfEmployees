import javax.swing.text.html.parser.Parser;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtRepository implements EmployeeStorable {


    File openedFile = new File("Repository.txt");


    @Override
    public void addEmployee(Employee employee)  {
        File openedFile = new File("Repository.txt");
        try {
            FileWriter writer = new FileWriter(openedFile, true);
            writer.write(employee.toString() + "\r\n");
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.getStackTrace();
        }
    }

    @Override
    public void delEmployee(Employee employee) {

        StringBuilder newFile = new StringBuilder();
        List<String> employees = new ArrayList<>();
        File file = new File("Repository.txt");

        try {

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();

            Pattern pattern = Pattern.compile("Employee[^}]*}");
            Pattern pattern1 = Pattern.compile(employee.getLastName() );
            Matcher matcher =  pattern.matcher(line);
            Matcher matcher1;


            while (line != null){

                while (matcher.find()){
                    matcher1 = pattern1.matcher(matcher.group());
                    if (!matcher1.find()){
                        employees.add(matcher.group());
                    }
                }
                line = bufferedReader.readLine();
                if (line != null) {
                    matcher = pattern.matcher(line);
                }
            }
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        for (String item : employees) {
            newFile.append(item + "\r\n");
        }

        try {
            FileWriter fileWriter = new FileWriter("Repository.txt");
            fileWriter.write(String.valueOf(newFile));
            fileWriter.flush();
            fileWriter.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void changeInfo(Employee employee) {


        StringBuilder newFile = new StringBuilder();
        List<String> employees = new ArrayList<>();
        File file = new File("Repository.txt");

        try {

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();

            Pattern pattern = Pattern.compile("Employee[^}]*}");
            Pattern pattern1 = Pattern.compile(employee.getLastName() );
            Matcher matcher =  pattern.matcher(line);
            Matcher matcher1;


            while (line != null){

                while (matcher.find()){
                    matcher1 = pattern1.matcher(matcher.group());
                    if (matcher1.find()){
                        employees.add(employee.toString());
                    }else {
                    employees.add(matcher.group());
                    }
                }
                line = bufferedReader.readLine();
                if (line != null) {
                    matcher = pattern.matcher(line);
                }
            }
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        for (String item : employees) {
            newFile.append(item + "\r\n");
        }

        try {
            FileWriter fileWriter = new FileWriter("Repository.txt");
            fileWriter.write(String.valueOf(newFile));
            fileWriter.flush();
            fileWriter.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public Employee findEmployeeByLastName(String lastName) {

        Employee employee = new Employee();
        employee.setLastName(lastName);
        try {
            FileReader fileReader = new FileReader(openedFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            String newline;

            Pattern pattern = Pattern.compile("Employee[^}]*}");
            Pattern pattern1 = Pattern.compile(lastName);
            Pattern pattern2 = Pattern.compile("firstName='[^']*'");
            Pattern pattern3 = Pattern.compile("(?>age=)\\d+");
            Pattern pattern4 = Pattern.compile("experience=[\\d]*'");
            Matcher matcher =  pattern.matcher(line);
            Matcher matcher1, matcher2, matcher3, matcher4;



            while (line != null){

                while (matcher.find()){
                    matcher1 = pattern1.matcher(matcher.group());
                    if (matcher1.find()){
                        newline = matcher.group();
                        matcher2 = pattern2.matcher(newline);
                        matcher3 = pattern3.matcher(newline);
                        matcher4 = pattern4.matcher(newline);
                        if (matcher3.find()){
                            employee.setAge(Integer.parseInt(matcher3.group()));
                        }




                    }
                }
                line = bufferedReader.readLine();
                if (line != null) {
                    matcher = pattern.matcher(line);
                }
            }
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
