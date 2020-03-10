package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vizzyy.service.LoggingService;

@RestController
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class LogsController {

    @Autowired
    LoggingService loggingService;

    @RequestMapping(value = "/logs")
    @ResponseBody
    public String logs(){
//        loggingService.addEntry("Called /logs...");

        return loggingService.printLogs();
    }

}
