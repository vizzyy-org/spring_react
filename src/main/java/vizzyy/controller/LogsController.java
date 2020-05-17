package vizzyy.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vizzyy.service.LoggingService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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

    @RequestMapping(
            value = "/log/stream",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody byte[] getFile() throws IOException {
        InputStream in = new FileInputStream(logPath);
        return IOUtils.toByteArray(in);
    }

    @RequestMapping(value = "/log/append")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('ROLE_POWER', 'ROLE_ADMIN', 'ROLE_OWNER')")
    public void remoteAppend(@RequestParam String entry){
        loggingService.addEntry(entry);
    }

}
