import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtRepository implements EmployeeStorable {


    File file;

    public TxtRepository(File file){
        if (file == null){
            throw new IllegalArgumentException("Отсутствует ссылка на файл");
        }

        if (file.isDirectory() || !file.exists()){
            throw new IllegalArgumentException("Файл отсутствует или является пакой");
        }
        this.file = file;
    }

    @Override
    public void addEmployee(Employee employee)  {
        try {
            FileWriter writer = new FileWriter(file, true);
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
            FileWriter fileWriter = new FileWriter(file);
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
            FileWriter fileWriter = new FileWriter(file);
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
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            Pattern pattern = Pattern.compile("Employee\\{firstName='([^']*)', lastName='([^']*)', age=([^']*)" +
                    ", experience=([^']*)}");
            Matcher matcher =  pattern.matcher(line);

            while (line != null){

                while (matcher.find()){
                    if (matcher.group(2).equals(lastName)){
                        employee.setFirstName(matcher.group(1));
                        employee.setAge(Integer.parseInt(matcher.group(3)));
                        employee.setExperience(Integer.parseInt(matcher.group(4)));

                        return employee;
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
