package ru.kpfu.itis.gr201.ponomarev;

import org.json.JSONArray;
import ru.kpfu.itis.gr201.ponomarev.model.Service;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Service> services = new ArrayList<>();
        services.add(new Service(1, "name", 12, 12));
        JSONArray arr = new JSONArray(services);
        System.out.println(arr);
    }
}
