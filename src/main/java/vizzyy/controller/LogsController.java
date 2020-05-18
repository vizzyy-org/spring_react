package vizzyy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vizzyy.service.LoggingService;

import java.util.List;

@RestController
public class LogsController {

    @Autowired
    LoggingService loggingService;

    @Value("${logging.file.path}")
    private String logPath;

    @RequestMapping(value = "/log")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER')")
    public String logs(){
        return loggingService.printLogs();
    }

    @RequestMapping(value = "/log/paged")
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER')")
    public @ResponseBody List<String> getLogPage(@RequestParam int page) {
        return loggingService.pagedLog(page);
    }

    @RequestMapping(value = "/log/append")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('ROLE_POWER', 'ROLE_ADMIN', 'ROLE_OWNER')")
    public void remoteAppend(@RequestParam String entry){
        loggingService.addEntry(entry);
    }

}
