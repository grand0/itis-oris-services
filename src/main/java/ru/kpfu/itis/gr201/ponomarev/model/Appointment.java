package ru.kpfu.itis.gr201.ponomarev.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Appointment {
    private int id;
    private int masterId;
    private int serviceId;
    private String clientPhone;
    private Timestamp time;

    public Appointment(int id, int masterId, int serviceId, String clientPhone, Timestamp time) {
        this.id = id;
        this.masterId = masterId;
        this.serviceId = serviceId;
        this.clientPhone = clientPhone;
        this.time = time;
    }

    public Appointment(int masterId, int serviceId, String clientPhone, Timestamp time) {
        this.masterId = masterId;
        this.serviceId = serviceId;
        this.clientPhone = clientPhone;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", masterId=" + masterId +
                ", serviceId=" + serviceId +
                ", clientPhone='" + clientPhone + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return getId() == that.getId() && getMasterId() == that.getMasterId() && getServiceId() == that.getServiceId() && Objects.equals(getClientPhone(), that.getClientPhone()) && Objects.equals(getTime(), that.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMasterId(), getServiceId(), getClientPhone(), getTime());
    }
}
