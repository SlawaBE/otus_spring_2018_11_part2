package ru.otus.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MyInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String,String> data= new HashMap<>();
        data.put("developer", "SlawaBE");
        data.put("application", "Library");
        data.put("version", "1.0");
        builder.withDetail("AppInfo", data);
    }

}