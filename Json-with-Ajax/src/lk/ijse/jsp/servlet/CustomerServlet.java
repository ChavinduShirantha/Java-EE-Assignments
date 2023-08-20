package lk.ijse.jsp.servlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author : Chavindu
 * created : 8/19/2023-9:25 AM
 **/

@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webPos", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("select * from Customer");
            ResultSet rst = pstm.executeQuery();

            resp.addHeader("Content-Type", "application/json");

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                String salary = rst.getString(4);

                JsonObjectBuilder customerObject = Json.createObjectBuilder();
                customerObject.add("id", id);
                customerObject.add("name", name);
                customerObject.add("address", address);
                customerObject.add("salary", salary);

                allCustomers.add(customerObject.build());
            }

            resp.getWriter().print(allCustomers.build());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");

        resp.addHeader("Content-Type", "application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webPos", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("insert into Customer values(?,?,?,?)");
            pstm.setObject(1, cusID);
            pstm.setObject(2, cusName);
            pstm.setObject(3, cusAddress);
            pstm.setObject(4, cusSalary);
            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("state", "OK");
                response.add("message", "Successfully Added ! ");
                response.add("data", "");
                resp.setStatus(200);
                resp.getWriter().print(response.build());
            }


        } catch (ClassNotFoundException | SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state", "Error");
            objectBuilder.add("message", e.getMessage());
            objectBuilder.add("data", "");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("cusID");

        resp.addHeader("Content-Type", "application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webPos", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("DELETE from customer where id=?");
            pstm.setObject(1, id);

            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("state", "OK");
                response.add("message", "Successfully Deleted ! ");
                response.add("data", "");
                resp.setStatus(200);
                resp.getWriter().print(response.build());
            }
        } catch (ClassNotFoundException | SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state", "Error");
            objectBuilder.add("message", e.getMessage());
            objectBuilder.add("data", "");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");

        resp.addHeader("Content-Type", "application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webPos", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("update Customer set name=?,address=?,salary=? where id=?");
            pstm.setObject(4, cusID);
            pstm.setObject(1, cusName);
            pstm.setObject(2, cusAddress);
            pstm.setObject(3, cusSalary);
            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("state", "OK");
                response.add("message", "Successfully Updated ! ");
                response.add("data", "");
                resp.setStatus(200);
                resp.getWriter().print(response.build());
            }
        } catch (ClassNotFoundException | SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state", "Error");
            objectBuilder.add("message", e.getMessage());
            objectBuilder.add("data", "");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());
        }
    }
}
