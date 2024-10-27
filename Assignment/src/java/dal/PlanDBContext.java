/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import dal.DBContext;
import entity.Department;
import entity.Plan;
import entity.PlanCampain;
import entity.Product;
import entity.SchedualCampain;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

/**
 *
 * @author sonnt-local
 */
public class PlanDBContext extends DBContext<Plan> {

    @Override
    public void insert(Plan entity) {
        try {
            connection.setAutoCommit(false);

            String sql_insert_plan = "INSERT INTO [Plan]\n"
                    + "           ([PlanName]\n"
                    + "           ,[StartDate]\n"
                    + "           ,[EndDate]\n"
                    + "           ,[DepartmentID])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm_insert_plan = connection.prepareStatement(sql_insert_plan);
            stm_insert_plan.setString(1, entity.getName());
            stm_insert_plan.setDate(2, entity.getStart());
            stm_insert_plan.setDate(3, entity.getEnd());
            stm_insert_plan.setInt(4, entity.getDept().getId());
            stm_insert_plan.executeUpdate();

            String sql_select_plan = "SELECT @@IDENTITY as PlanID";
            PreparedStatement stm_select_plan = connection.prepareStatement(sql_select_plan);
            ResultSet rs = stm_select_plan.executeQuery();
            if (rs.next()) {
                entity.setId(rs.getInt("PlanID"));
            }

            for (PlanCampain campain : entity.getCampains()) {
                String sql_insert_campain = "INSERT INTO [PlanCampain]\n"
                        + "           ([PlanID]\n"
                        + "           ,[ProductID]\n"
                        + "           ,[Quantity]\n"
                        + "           ,[Estimate])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?)";

                PreparedStatement stm_insert_campain = connection.prepareStatement(sql_insert_campain);
                stm_insert_campain.setInt(1, entity.getId());
                stm_insert_campain.setInt(2, campain.getProduct().getId());
                stm_insert_campain.setInt(3, campain.getQuantity());
                stm_insert_campain.setFloat(4, campain.getCost());
                stm_insert_campain.executeUpdate();
            }

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void update(Plan entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Plan entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  
    public ArrayList<Plan> list() {
        ArrayList<Plan> plans = new ArrayList<>();
        PreparedStatement command = null;
        ResultSet resultSet = null;
        PlanDBContext db = new PlanDBContext();
        try {
            String sql = "SELECT [dbo].[Plan].[PlanID], [PlanName], [StartDate], [EndDate], \n"
                    + "                    [dbo].[Department].[DepartmentID], [DepartmentName], [Status], \n"
                    + "                    [dbo].[PlanCampain].[PlanCampnID], [dbo].[PlanCampain].[ProductID], \n"
                    + "                    [ProductName], [dbo].[PlanCampain].Quantity AS PlanCampainQuantity, \n"
                    + "                    [ScID], [dbo].[SchedualCampaign].Quantity AS SchedualCampaignQuantity, \n"
                    + "                    [dbo].[SchedualCampaign].[Date], [dbo].[SchedualCampaign].[Shift] \n"
                    + "                    FROM [dbo].[Plan] \n"
                    + "                    JOIN [dbo].[PlanCampain] ON [dbo].[Plan].[PlanID] = [dbo].[PlanCampain].[PlanID] \n"
                    + "                    JOIN [dbo].[Product] ON [dbo].[Product].ProductID = [dbo].[PlanCampain].ProductID \n"
                    + "                    JOIN [dbo].[Department] ON [dbo].[Department].DepartmentID = [dbo].[Plan].DepartmentID \n"
                    + "                    JOIN [dbo].[SchedualCampaign] ON [dbo].[SchedualCampaign].PlanCampnID = [dbo].[PlanCampain].PlanCampnID";
            
       
            command = connection.prepareStatement(sql);
            resultSet = command.executeQuery();
            while (resultSet.next()) {
                Plan plan = new Plan();
                plan.setId(resultSet.getInt("PlanID"));
                plan.setName(resultSet.getString("PlanName"));
                plan.setStart(resultSet.getDate("StartDate"));
                plan.setEnd(resultSet.getDate("EndDate"));

                Department department = new Department();
                department.setId(resultSet.getInt("DepartmentID"));
                department.setName(resultSet.getString("DepartmentName"));
                plan.setDept(department);

                plan.setStatus(resultSet.getString("Status"));

                
                ArrayList<PlanCampain> planCampains = new ArrayList<>();
                PlanCampain planCampain = new PlanCampain();
                planCampain.setId(resultSet.getInt("PlanCampnID"));
                planCampain.setId(resultSet.getInt("ProductID"));
                planCampain.setQuantity(resultSet.getInt("PlanCampainQuantity"));

                Product product = new Product();
                product.setId(resultSet.getInt("ProductID"));
                product.setName(resultSet.getString("ProductName"));
                planCampain.setProduct(product);

                ArrayList<SchedualCampain> schedualCampains = new ArrayList<>();
                SchedualCampain schedualCampain = new SchedualCampain();
                schedualCampain.setId(resultSet.getInt("ScID"));
                schedualCampain.setQuantity(resultSet.getInt("SchedualCampaignQuantity"));
                schedualCampain.setDate(resultSet.getDate("Date"));
                schedualCampain.setShift(resultSet.getString("Shift"));
                schedualCampains.add(schedualCampain);

                planCampain.setSchedualCampains(schedualCampains);
                planCampains.add(planCampain);
                plan.setCampains(planCampains);
                plans.add(plan);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
//            if (resultSet != null) {
//                try {
//                    resultSet.close();
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
//            if (command != null) {
//                try {
//                    command.close();
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
        }
        return plans;
    }


    public ArrayList<Plan> getPlansByStatus(String status) {
        Logger logger = Logger.getLogger(PlanDBContext.class.getName());
        logger.info("Status: ");
        logger.info(status);

        ArrayList<Plan> plans = new ArrayList<>();
        PreparedStatement command = null;
        ResultSet rs = null;  // Add ResultSet variable so it can be closed properly
        try {
            // Remove the quotes around '?'
            String sql = "select [PlanID], [PlanName], [Status]\n"
                    + "from [dbo].[Plan]\n"
                    + "where [Status] = ?";
            String executedSQL = sql.replaceFirst("\\?", "'" + status + "'");
            logger.info("SQL:");
            logger.info(executedSQL);

            command = connection.prepareStatement(sql);
            command.setString(1, status);
            rs = command.executeQuery();

            while (rs.next()) {
                Plan plan = new Plan();
                plan.setId(rs.getInt("PlanID"));
                plan.setName(rs.getString("PlanName"));
                logger.info("PlanName:");
                logger.info(rs.getString("PlanName"));

                plan.setStatus(status);
                // You can retrieve other fields as needed

                plans.add(plan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//        // Close resources properly, with null checks
//        try {
//            if (rs != null) {
//                rs.close();
//            }
//            if (command != null) {
//                command.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        }
        }
        return plans;  // Return the list of plans (it will be empty if there was an error)
//        ArrayList<Plan> plans = new ArrayList<>();
//        PreparedStatement command = null;
//        try {
//            String sql = "select [PlanID], [PlanName], [Status]\n"
//                    + "from [dbo].[Plan]\n"
//                    + "where [Status] = ?";
//
//            command = connection.prepareStatement(sql);
//            command.setString(1, status);
//            ResultSet rs = command.executeQuery();
//            while (rs.next()) {
//                Plan d = new Plan();
//                d.setId(rs.getInt("PlanID"));
//                d.setName(rs.getString("PlanName"));
//                d.setStatus(status);
//                plans.add(d);
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(dal.DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                command.close();
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(dal.DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

    }

    @Override
    public Plan get(int id) {
        Plan plan = null;
        PreparedStatement command = null;
        ArrayList<PlanCampain> campains = new ArrayList<>();
        try {
            String sql = "SELECT [dbo].[Plan].[PlanID], [PlanName], [StartDate], [EndDate], "
                    + "[dbo].[Department].[DepartmentID], [Status], [PlanCampnID], "
                    + "[dbo].[PlanCampain].[ProductID], [ProductName], [dbo].[PlanCampain].Quantity, [DepartmentName] "
                    + "FROM [dbo].[Plan] "
                    + "JOIN [dbo].[PlanCampain] ON [dbo].[Plan].[PlanID] = [dbo].[PlanCampain].[PlanID] "
                    + "JOIN [dbo].[Product] ON [dbo].[Product].ProductID = [dbo].[PlanCampain].ProductID "
                    + "JOIN [dbo].[Department] ON [dbo].[Department].DepartmentID = [dbo].[Plan].DepartmentID "
                    + "WHERE [dbo].[Plan].[PlanID] = ?";

            command = connection.prepareStatement(sql);
            command.setInt(1, id);
            ResultSet rs = command.executeQuery();

            while (rs.next()) {
                if (plan == null) {
                    plan = new Plan();
                    plan.setId(rs.getInt("PlanID"));
                    plan.setName(rs.getNString("PlanName"));
                    plan.setStart(rs.getDate("StartDate"));
                    plan.setEnd(rs.getDate("EndDate"));
                    plan.setStatus(rs.getString("Status"));

                    // Gán Department vào Plan
                    Department dept = new Department();
                    dept.setId(rs.getInt("DepartmentID"));
                    dept.setName(rs.getNString("DepartmentName"));
                    plan.setDept(dept);
                }

                // Tạo PlanCampain mới và thêm vào danh sách campains
                PlanCampain planCampain = new PlanCampain();
                planCampain.setId(rs.getInt("PlanCampnID"));
                planCampain.setQuantity(rs.getInt("Quantity"));

                // Tạo đối tượng Product và thiết lập các thuộc tính
                Product product = new Product();
                product.setId(rs.getInt("ProductID"));
                product.setName(rs.getNString("ProductName"));

                // Gán Product vào PlanCampain
                planCampain.setProduct(product);

                // Gán Plan vào PlanCampain
                planCampain.setPlan(plan);

                // Thêm PlanCampain vào danh sách của Plan
                campains.add(planCampain);
            }
            if (plan != null) {
                plan.setCampains(campains);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (command != null) {
                try {
                    command.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return plan;
    }

}
