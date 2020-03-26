package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vizzyy.service.LoggingService;

@RestController
public class LogsController {

    @Autowired
    LoggingService loggingService;

    @RequestMapping(value = "/logs")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String logs(){
        return loggingService.printLogs();
    }

    @RequestMapping(value = "/log/append")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_POWER')")
    public void remoteAppend(@RequestParam String entry){
        loggingService.addEntry(entry);
    }

}
