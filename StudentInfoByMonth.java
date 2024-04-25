package Lab.Lab5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentInfoByMonth {
    private static final String DATABASE_URL = "jdbc:h2:tcp://localhost/~/studentsDB";
    private static final String USERNAME = "Daniel";
    private static final String PASSWORD = "MyPassword123";

    public static void main(String[] args) {
        try {
            // Establishing the database connection
            Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

            // Creating a Scanner object to read user input
            Scanner scanner = new Scanner(System.in);
            int birthMonth;

            // Loop until a valid month is entered
            do {
                System.out.print("Enter the birth month (1-12): ");
                birthMonth = scanner.nextInt();

                // Check if the entered month is valid
                if (birthMonth < 1 || birthMonth > 12) {
                    System.out.println("Invalid month! Please enter a number between 1 and 12.");
                }
            } while (birthMonth < 1 || birthMonth > 12);

            // SQL query
            String query = "SELECT * FROM students WHERE MONTH(\"Date of birth\") = ?\n";

            // Creating a prepared statement
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, birthMonth);

            // Executing the query
            ResultSet resultSet = statement.executeQuery();

            // Processing the result
            boolean found = false;
            while (resultSet.next()) {
                found = true;
                String id = resultSet.getString("ID");
                String firstName = resultSet.getString("First name");
                String lastName = resultSet.getString("Second name");
                String patronymic = resultSet.getString("Patronymic");
                String birthdate = resultSet.getString("Date of birth");
                String bookNumber = resultSet.getString("Book number");
                System.out.println("ID: " + id);
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Patronymic: " + patronymic);
                System.out.println("Birthdate: " + birthdate);
                System.out.println("Book Number: " + bookNumber);
                System.out.println("-----------------------");
            }

            // If no students found
            if (!found) {
                System.out.println("No students found for the specified birth month.");
            }

            // Closing resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
