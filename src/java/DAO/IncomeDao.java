package DAO;

import Model.Income;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class IncomeDao extends DBContext {

    TutorDAO tdao = new TutorDAO();

    public ArrayList<Income> GetAllIncomeWithDate(Date startDate, Date endDate, int tutorID) {
        ArrayList<Income> list = new ArrayList<>();
        String query = "SELECT * \n"
                + "FROM Income \n"
                + "WHERE CAST(createdAt AS DATE) BETWEEN ? AND ?\n"
                + "  AND tutorID = ?; ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(endDate.getTime()));
            preparedStatement.setInt(3, tutorID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Income income = new Income();
                income.setId(resultSet.getInt("id"));
                income.setTax(resultSet.getFloat("tax"));
                income.setAmount(resultSet.getFloat("amount"));
                income.setDayPaid(resultSet.getDate("DayPaid"));
                income.setTotal(resultSet.getFloat("Total")); // Corrected this line
                income.setCreatedAt(resultSet.getDate("createdAt"));
                income.setStatus(resultSet.getString("status"));
                int tutorid = resultSet.getInt("tutorID");
                income.setTutor(tdao.getTutorById(tutorid));
                list.add(income);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        IncomeDao incomeDAO = new IncomeDao();

        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;

        try {
            // Parse the start and end dates
            startDate = dateFormat.parse("2023-06-25 00:00:00.000");
            endDate = dateFormat.parse("2024-07-01 00:00:00.000");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (startDate != null && endDate != null) {
            ArrayList<Income> incomes = incomeDAO.GetAllIncomeWithDate(startDate, endDate, 7);
            for (Income income : incomes) {
                System.out.println(income.getTotal());
            }
        }
    }
}
