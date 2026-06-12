```java
package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import java.io.IOException;
import java.io.PrintWriter;

import java.security.MessageDigest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/vulnerable")

public class VulnerableApp extends HttpServlet {

    // Hardcoded Credentials
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "password123";

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {

            // Weak Cryptography (MD5)
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            // Hardcoded Database Connection
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/testdb",
                    DB_USER,
                    DB_PASSWORD
            );

            Statement stmt = conn.createStatement();

            // SQL Injection Vulnerability
            String query =
                    "SELECT * FROM users WHERE username='"
                            + username +
                            "' AND password='"
                            + password + "'";

            ResultSet rs = stmt.executeQuery(query);

            if(rs.next()) {

                // Cross-Site Scripting (XSS)
                out.println("<h2>Welcome " + username + "</h2>");

            } else {

                out.println("<h2>Invalid User</h2>");
            }

            // Sensitive Information Disclosure
            out.println("Database Password: " + DB_PASSWORD);

            // Command Injection Risk
            Runtime.getRuntime().exec("ping " + username);

            // Insecure Random
            java.util.Random random = new java.util.Random();
            int token = random.nextInt();

            out.println("Generated Token: " + token);

            conn.close();

        } catch (Exception e) {

            // Stack Trace Disclosure
            e.printStackTrace();

            out.println(e);

        }
    }
}
```
