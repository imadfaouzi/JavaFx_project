package ma.emsi.firstfx.logic.dao.impl;

import ma.emsi.firstfx.logic.dao.UserDao;
import ma.emsi.firstfx.logic.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UserDaoImp implements UserDao {
    private Connection conn = DB.getConnection();

    @Override
    public boolean isValidate(String username, String password) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error validating user: " + e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }

        return false;
    }
}
