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
import java.util.Comparator;
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

            String sql_insert_plan = "INSERT INTO [dbo].[Plan]\n"
                    + "           ([PlanName]\n"
                    + "           ,[StartDate]\n"
                    + "           ,[EndDate]\n"
                    + "           ,[DepartmentID]\n"
                    + "           ,[Status]\n"
                    + "           ,[deleted])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,0)";
            PreparedStatement stm_insert_plan = connection.prepareStatement(sql_insert_plan);
            stm_insert_plan.setString(1, entity.getName());
            stm_insert_plan.setDate(2, entity.getStart());
            stm_insert_plan.setDate(3, entity.getEnd());
            stm_insert_plan.setInt(4, entity.getDept().getId());
            stm_insert_plan.setString(5, entity.getStatus());
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

    public void update(Plan entity) {
        PreparedStatement stm_update_plan = null;
        PreparedStatement stm_update_campain = null;

        try {
            connection.setAutoCommit(false);

            // Update Plan
            String sql_update_plan = "UPDATE [dbo].[Plan] SET [PlanName] = ?, [StartDate] = ?, [EndDate] = ?, [DepartmentID] = ? WHERE [PlanID] = ?";
            stm_update_plan = connection.prepareStatement(sql_update_plan);
            stm_update_plan.setString(1, entity.getName());
            stm_update_plan.setDate(2, entity.getStart());
            stm_update_plan.setDate(3, entity.getEnd());
            stm_update_plan.setInt(4, entity.getDept().getId());
            stm_update_plan.setInt(5, entity.getId());
            stm_update_plan.executeUpdate();

            // Update PlanCampaign
            String sql_update_campain = "UPDATE [dbo].[PlanCampain] SET [Quantity] = ?, [Estimate] = ? WHERE [PlanCampnID] = ?";
            stm_update_campain = connection.prepareStatement(sql_update_campain);

            for (PlanCampain campain : entity.getCampains()) {
                stm_update_campain.setInt(1, campain.getQuantity());
                stm_update_campain.setFloat(2, campain.getCost());
                stm_update_campain.setInt(3, campain.getId());
                stm_update_campain.executeUpdate();
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
                if (stm_update_plan != null) {
                    stm_update_plan.close();
                }
                if (stm_update_campain != null) {
                    stm_update_campain.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
            String sql = "SELECT [dbo].[Plan].[PlanID], [PlanName], [StartDate], [EndDate], "
                    + "[dbo].[Department].[DepartmentID], [DepartmentName], [Status], "
                    + "[dbo].[PlanCampain].[PlanCampnID], [dbo].[PlanCampain].[ProductID], "
                    + "[ProductName], [dbo].[PlanCampain].Quantity AS PlanCampainQuantity, "
                    + "[ScID], [dbo].[SchedualCampaign].Quantity AS SchedualCampaignQuantity, "
                    + "[dbo].[SchedualCampaign].[Date], [dbo].[SchedualCampaign].[Shift] "
                    + "FROM [dbo].[Plan] "
                    + "JOIN [dbo].[PlanCampain] ON [dbo].[Plan].[PlanID] = [dbo].[PlanCampain].[PlanID] "
                    + "JOIN [dbo].[Product] ON [dbo].[Product].ProductID = [dbo].[PlanCampain].ProductID "
                    + "JOIN [dbo].[Department] ON [dbo].[Department].DepartmentID = [dbo].[Plan].DepartmentID "
                    + "JOIN [dbo].[SchedualCampaign] ON [dbo].[SchedualCampaign].PlanCampnID = [dbo].[PlanCampain].PlanCampnID "
                    + "ORDER BY [dbo].[SchedualCampaign].[Date] ASC"; // Đảm bảo sắp xếp theo ngày ở đây

            command = connection.prepareStatement(sql);
            resultSet = command.executeQuery();

            while (resultSet.next()) {
                int planID = resultSet.getInt("PlanID");

                // Check if the plan already exists in the plans list
                Plan plan = plans.stream()
                        .filter(p -> p.getId() == planID)
                        .findFirst()
                        .orElse(null);
                if (plan == null) {
                    // Create a new plan if it doesn't exist in the list
                    plan = new Plan();
                    plan.setId(planID);
                    plan.setName(resultSet.getString("PlanName"));
                    plan.setStart(resultSet.getDate("StartDate"));
                    plan.setEnd(resultSet.getDate("EndDate"));

                    Department department = new Department();
                    department.setId(resultSet.getInt("DepartmentID"));
                    department.setName(resultSet.getString("DepartmentName"));
                    plan.setDept(department);

                    plan.setStatus(resultSet.getString("Status"));
                    plan.setCampains(new ArrayList<>()); // Initialize the campains list
                    plans.add(plan);
                }

                int planCampnID = resultSet.getInt("PlanCampnID");

                // Check if the PlanCampain already exists in the plan's campains
                PlanCampain planCampain = plan.getCampains().stream()
                        .filter(pc -> pc.getId() == planCampnID)
                        .findFirst()
                        .orElse(null);
                if (planCampain == null) {
                    // Create PlanCampain and add to plan's campains
                    planCampain = new PlanCampain();
                    planCampain.setId(planCampnID);
                    planCampain.setQuantity(resultSet.getInt("PlanCampainQuantity"));

                    Product product = new Product();
                    product.setId(resultSet.getInt("ProductID"));
                    product.setName(resultSet.getString("ProductName"));
                    planCampain.setProduct(product);

                    planCampain.setSchedualCampains(new ArrayList<>()); // Initialize schedualCampains list

                    plan.getCampains().add(planCampain);
                }

                // Create SchedualCampain
                SchedualCampain schedualCampain = new SchedualCampain();
                schedualCampain.setId(resultSet.getInt("ScID"));
                schedualCampain.setQuantity(resultSet.getInt("SchedualCampaignQuantity"));
                schedualCampain.setDate(resultSet.getDate("Date"));
                schedualCampain.setShift(resultSet.getString("Shift"));

                // Add SchedualCampain to the existing PlanCampain
                planCampain.getSchedualCampains().add(schedualCampain);
            }

            // **Sắp xếp các schedualCampains trong mỗi planCampain theo ngày**
            // Sau khi sắp xếp các schedualCampains trong mỗi planCampain theo ngày
            for (Plan plan : plans) {
                for (PlanCampain campain : plan.getCampains()) {
                    campain.getSchedualCampains().sort(Comparator.comparing(SchedualCampain::getDate));

                    // Debug: In ra thứ tự của các SchedualCampain để kiểm tra
                    System.out.println("Plan ID: " + plan.getId());
                    System.out.println("PlanCampain ID: " + campain.getId());
                    for (SchedualCampain schedual : campain.getSchedualCampains()) {
                        System.out.println("Date: " + schedual.getDate() + " | Shift: " + schedual.getShift());
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Clean up resources here if necessary
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
                + "[dbo].[Department].[DepartmentID], [DepartmentName], [Status], "
                + "[dbo].[PlanCampain].[PlanCampnID], [dbo].[PlanCampain].[ProductID], "
                + "[ProductName], [dbo].[PlanCampain].Quantity AS PlanCampainQuantity, "
                + "[dbo].[SchedualCampaign].[ScID], [dbo].[SchedualCampaign].Quantity AS SchedualCampaignQuantity, "
                + "[dbo].[SchedualCampaign].[Date], [dbo].[SchedualCampaign].[Shift] "
                + "FROM [dbo].[Plan] "
                + "JOIN [dbo].[PlanCampain] ON [dbo].[Plan].[PlanID] = [dbo].[PlanCampain].[PlanID] "
                + "JOIN [dbo].[Product] ON [dbo].[Product].ProductID = [dbo].[PlanCampain].ProductID "
                + "JOIN [dbo].[Department] ON [dbo].[Department].DepartmentID = [dbo].[Plan].DepartmentID "
                + "JOIN [dbo].[SchedualCampaign] ON [dbo].[SchedualCampaign].PlanCampnID = [dbo].[PlanCampain].PlanCampnID "
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

                Department dept = new Department();
                dept.setId(rs.getInt("DepartmentID"));
                dept.setName(rs.getNString("DepartmentName"));
                plan.setDept(dept);
            }

            int planCampainId = rs.getInt("PlanCampnID");
            PlanCampain planCampain = campains.stream()
                    .filter(pc -> pc.getId() == planCampainId)
                    .findFirst()
                    .orElse(null);

            if (planCampain == null) {
                planCampain = new PlanCampain();
                planCampain.setId(planCampainId);
                planCampain.setQuantity(rs.getInt("PlanCampainQuantity"));

                Product product = new Product();
                product.setId(rs.getInt("ProductID"));
                product.setName(rs.getNString("ProductName"));
                planCampain.setProduct(product);

                planCampain.setPlan(plan);
                campains.add(planCampain);
            }

            SchedualCampain schedualCampain = new SchedualCampain();
            schedualCampain.setId(rs.getInt("ScID"));
            schedualCampain.setDate(rs.getDate("Date"));
            schedualCampain.setShift(rs.getString("Shift"));
            schedualCampain.setQuantity(rs.getInt("SchedualCampaignQuantity"));
            planCampain.getSchedualCampains().add(schedualCampain);
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
