package ru.kpfu.itis.gr201.ponomarev.dao;

import ru.kpfu.itis.gr201.ponomarev.model.Master;
import ru.kpfu.itis.gr201.ponomarev.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MasterDao {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    public Master get(int id) {
        String sql = "SELECT * FROM masters WHERE id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Master(
                        set.getInt("id"),
                        set.getString("name"),
                        set.getInt("experience_years")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Master> getAll() {
        String sql = "SELECT * FROM masters;";
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            List<Master> masters = new ArrayList<>();
            while (set.next()) {
                masters.add(
                        new Master(
                                set.getInt("id"),
                                set.getString("name"),
                                set.getInt("experience_years")
                        )
                );
            }
            return masters;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
