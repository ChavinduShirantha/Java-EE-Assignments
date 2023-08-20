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

@WebServlet(urlPatterns = "/pages/item")
public class ItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webPos", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("select * from Item");
            ResultSet rst = pstm.executeQuery();

            resp.addHeader("Content-Type", "application/json");

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();
            while (rst.next()) {
                String code = rst.getString(1);
                String description = rst.getString(2);
                int qtyOnHand = rst.getInt(3);
                double unitPrice = rst.getDouble(4);

                JsonObjectBuilder customerObject = Json.createObjectBuilder();
                customerObject.add("code", code);
                customerObject.add("description", description);
                customerObject.add("qtyOnHand", qtyOnHand);
                customerObject.add("unitPrice", unitPrice);

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
        String code = req.getParameter("code");
        String itemName = req.getParameter("description");
        String qty = req.getParameter("qty");
        String unitPrice = req.getParameter("unitPrice");

        resp.addHeader("Content-Type", "application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webPos", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("insert into item values(?,?,?,?)");
            pstm.setObject(1, code);
            pstm.setObject(2, itemName);
            pstm.setObject(3, qty);
            pstm.setObject(4, unitPrice);
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
        String code = req.getParameter("code");

        resp.addHeader("Content-Type", "application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webPos", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("DELETE from Item where code=?");
            pstm.setObject(1, code);

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
        String code = req.getParameter("code");
        String name = req.getParameter("description");
        String qty = req.getParameter("qty");
        String price = req.getParameter("unitPrice");

        resp.addHeader("Content-Type", "application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webPos", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("update Item set description=?,qtyOnHand=?,unitPrice=? where code=?");
            pstm.setObject(4, code);
            pstm.setObject(1, name);
            pstm.setObject(2, qty);
            pstm.setObject(3, price);
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
