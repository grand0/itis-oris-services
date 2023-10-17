package ru.kpfu.itis.gr201.ponomarev.servlet;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.dao.AppointmentDao;
import ru.kpfu.itis.gr201.ponomarev.dao.MasterDao;
import ru.kpfu.itis.gr201.ponomarev.dao.ServiceDao;
import ru.kpfu.itis.gr201.ponomarev.model.Appointment;
import ru.kpfu.itis.gr201.ponomarev.model.Master;
import ru.kpfu.itis.gr201.ponomarev.model.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "servicesServlet", urlPatterns = "/services")
public class ServicesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("master_id") != null) {
            // services for specified master were requested
            int masterId = Integer.parseInt(req.getParameter("master_id"));
            ServiceDao serviceDao = new ServiceDao();
            List<Service> services = serviceDao.getAllByMaster(masterId);
            resp.setContentType("application/json");
            resp.getWriter().write(new JSONArray(services).toString());
        } else {
            // show page
            MasterDao masterDao = new MasterDao();
            List<Master> masters = masterDao.getAll();
            req.setAttribute("masters", masters);
            req.getRequestDispatcher("services.ftl").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println();

        int masterId = Integer.parseInt(req.getParameter("masterId"));
        int serviceId = Integer.parseInt(req.getParameter("serviceId"));
        String date = req.getParameter("date");
        String time = req.getParameter("time");
        String phone = req.getParameter("phone");
        String dateTime = date + " " + time + ":00";

        JSONObject obj = new JSONObject();

        Appointment appointment = new Appointment(
                masterId,
                serviceId,
                phone,
                Timestamp.valueOf(dateTime)
        );

        AppointmentDao appointmentDao = new AppointmentDao();
        try {
            if (appointmentDao.checkTime(appointment)) {
                appointmentDao.save(appointment);
                obj.put("success", 1);
            } else {
                obj.put("success", 0);
                obj.put("masterBusy", 1);
            }
        } catch (Exception e) {
            obj.put("success", 0);
            obj.put("unknownError", 1);
            Logger.getLogger(getClass().getName()).severe(e.toString());
        }

        resp.setContentType("application/json");
        resp.getWriter().write(obj.toString());
    }
}
