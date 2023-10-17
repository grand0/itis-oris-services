package ru.kpfu.itis.gr201.ponomarev.dao;

import ru.kpfu.itis.gr201.ponomarev.model.Appointment;
import ru.kpfu.itis.gr201.ponomarev.util.DatabaseConnectionUtil;

import java.sql.*;

public class AppointmentDao {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    public void save(Appointment appointment) {
        String sql = "INSERT INTO appointments(master_id, service_id, client_phone, time) values (?, ?, ?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, appointment.getMasterId());
            statement.setInt(2, appointment.getServiceId());
            statement.setString(3, appointment.getClientPhone());
            statement.setTimestamp(4, appointment.getTime());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkTime(Appointment appointment) {
        Timestamp thisAppointmentStart = appointment.getTime();
        int thisAppointmentDuration = new ServiceDao().get(appointment.getServiceId()).getDuration();
        Timestamp thisAppointmentEnd = Timestamp.valueOf(thisAppointmentStart.toLocalDateTime().plusMinutes(thisAppointmentDuration));
        System.out.println(thisAppointmentStart);
        System.out.println(thisAppointmentEnd);
        String sql = "SELECT a.time AS time, s.duration_m AS duration FROM appointments a INNER JOIN services s ON a.service_id = s.id WHERE master_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, appointment.getMasterId());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Timestamp start = set.getTimestamp("time");
                Timestamp end = Timestamp.valueOf(start.toLocalDateTime().plusMinutes(set.getInt("duration")));
                System.out.println(start);
                System.out.println(end);
                if (!(end.before(thisAppointmentStart) || end.equals(thisAppointmentStart) || start.after(thisAppointmentEnd) || start.equals(thisAppointmentEnd))) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
