package ru.kpfu.itis.gr201.ponomarev.dao;

import ru.kpfu.itis.gr201.ponomarev.model.Service;
import ru.kpfu.itis.gr201.ponomarev.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    public Service get(int id) {
        String sql = "SELECT * FROM services WHERE id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Service(
                        set.getInt("id"),
                        set.getString("name"),
                        set.getInt("duration_m"),
                        set.getInt("price")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Service> getAll() {
        String sql = "SELECT * FROM services;";
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            List<Service> services = new ArrayList<>();
            while (set.next()) {
                services.add(
                        new Service(
                                set.getInt("id"),
                                set.getString("name"),
                                set.getInt("duration_m"),
                                set.getInt("price")
                        )
                );
            }
            return services;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Service> getAllByMaster(int masterId) {
        String sql = "SELECT * FROM services s INNER JOIN masters_services ms on s.id = ms.service_id WHERE master_id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, masterId);
            ResultSet set = statement.executeQuery();
            List<Service> services = new ArrayList<>();
            while (set.next()) {
                services.add(
                        new Service(
                                set.getInt("id"),
                                set.getString("name"),
                                set.getInt("duration_m"),
                                set.getInt("price")
                        )
                );
            }
            return services;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
