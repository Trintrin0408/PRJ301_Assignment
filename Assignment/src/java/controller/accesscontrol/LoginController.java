/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.accesscontrol;

import dal.UserDBContext;
import entity.accesscontrol.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author sonng
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        
        UserDBContext db = new UserDBContext();
        User account = db.get(user, pass);
        
        if(account!=null)
        {
            req.getSession().setAttribute("account", account);
            
            resp.getWriter().println("login successful!");
        }
        else
        {
            resp.getWriter().println("login failed!");
        }
        
        String url = this.getInitParameter("url");
        resp.getWriter().println(url);
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //pre-processing
        req.getRequestDispatcher("login.html").forward(req, resp);
        //post-processing
    }
    
}
