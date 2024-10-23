/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.productionplan;

import controller.accesscontrol.BaseRBACController;
import entity.accesscontrol.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author ASUS
 */
public class HomePage extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
         req.getRequestDispatcher("../view/home /home.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
