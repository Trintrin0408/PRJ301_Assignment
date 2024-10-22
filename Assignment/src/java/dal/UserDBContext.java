package dal;

import entity.accesscontrol.Feature;
import entity.accesscontrol.Role;
import entity.accesscontrol.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDBContext extends DBContext<User> {
    
    public UserDBContext() {
        super(); // Gọi constructor của DBContext để khởi tạo connection
    }

    public ArrayList<Role> getRoles(String username) {
        String sql = "SELECT r.RoleID, r.RoleName, f.FeatureID, f.FeatureName, f.url FROM [User] u \n"
                + "    INNER JOIN UserRole ur ON ur.UserName = u.UserName\n"
                + "    INNER JOIN [Role] r ON r.RoleID = ur.RoleID\n"
                + "    INNER JOIN RoleFeature rf ON r.RoleID = rf.RoleID\n"
                + "    INNER JOIN Feature f ON f.FeatureID = rf.FeatureID\n"
                + "WHERE u.UserName = ?\n"
                + "ORDER BY r.RoleID, f.FeatureID ASC";
        
        PreparedStatement stm = null;
        ArrayList<Role> roles = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            Role currentRole = new Role();
            currentRole.setId(-1);
            while(rs.next()) {
                int roleID = rs.getInt("RoleID");
                if(roleID != currentRole.getId()) {
                    currentRole = new Role();
                    currentRole.setId(roleID);
                    currentRole.setName(rs.getString("RoleName"));
                    roles.add(currentRole);
                }
                
                Feature feature = new Feature();
                feature.setId(rs.getInt("FeatureID"));
                feature.setName(rs.getString("FeatureName"));
                feature.setUrl(rs.getString("url"));
                currentRole.getFeatures().add(feature);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stm != null) stm.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return roles;
    }

    public User get(String username, String password) {
        String sql = "SELECT UserName FROM [User] \n"
                + "WHERE UserName = ? AND [password] = ?";
        PreparedStatement stm = null;
        User user = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("UserName"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stm != null) stm.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }

    // Các phương thức khác...

    @Override
    public void insert(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<User> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
