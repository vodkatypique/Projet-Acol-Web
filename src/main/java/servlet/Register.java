/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.jar.Attributes.Name;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.websocket.Session;
import org.apache.commons.codec.digest.DigestUtils;
/**
 *
 * @author cleme
 */
@WebServlet(name = "Register", urlPatterns = {"/register"})
public class Register extends HttpServlet {
    @Resource(name = "jdbc/UserTable")
    private DataSource ds;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CheckUser</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckUser at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String login = request.getParameter("login");
            String mdp = request.getParameter("password");
            if(login != "" && mdp != ""){
            inscription(login, mdp);
            }
            request.getRequestDispatcher("checkuser").forward(request,response);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public boolean inscription(String login, String mdp) throws SQLException{
        try (Connection c = ds.getConnection()) {
            /* Un PreparedStatement évite les injections SQL */
            try(PreparedStatement s = c.prepareStatement(
                "INSERT INTO UserTable (idUser, login, password) VALUES (?, ?, ?)"
            )){
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM UserTable");
                int count = 0;
                while(rs.next()){
                    count = rs.getInt("total") + 1;
                }
                s.setInt(1, count);
                s.setString(2, login);
                mdp = new DigestUtils("SHA3-256").digestAsHex(mdp);
                System.out.println(mdp);
                s.setString(3, mdp);
                ResultSet r = s.executeQuery();
                /* r.next() renvoie vrai si et seulement si la réponse contient au moins 1 ligne */
                return r.next();
            }
        }

        /* Remarque : ici le bloc "try" a l’effet suivant :
         * 1) r.next() est évalué
         * 2) c.close() est appelé (car on va sortir du bloc "try" par l’instruction return)
         * 3) la méthode renvoie la valeur de r.next() évaluée précédemment
         */
    }
}
