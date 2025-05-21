package org.cisu.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    // use application.properties values
    @Value("${spring.application.name}")
    private String appName;

    // @RequestMapping ->
    @RequestMapping("/")
    public String index() {
        System.out.println("appName: "+appName);
        String viewName = getViewName();
        return viewName;
    }

    private String getViewName() {
        return "index.html";
    }
}
