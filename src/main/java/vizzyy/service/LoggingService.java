package vizzyy.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class LoggingService {

    private Logger log = LoggerFactory.getLogger(LoggingService.class);

    @Value("${logging.file.path}")
    private String logPath;

    @Value("${logging.file.pageSize}")
    private int logPageSize;

    public void addEntry(String entry){
        String user = " " + AuthenticationService.getUserName();
        log.info(entry);
    }

    public String printLogs(){

        StringBuilder output = new StringBuilder();
        for(String line : readLogFile()){
            output.append(line).append("\n");
        }

        return output.toString();
    }

    public List<String> readLogFile() {
        List<String> lines;
        try {
            File file = new File(logPath);
            lines = FileUtils.readLines(file, "UTF-8");
        } catch (Exception e) {
            log.error(e.getMessage());
            lines = Collections.emptyList();
        }
        return lines;
    }

    public List<String> pagedLog(int page) {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(logPath);
            int pageStartLine = (page-1)*logPageSize;
            int pageEndLine = page * logPageSize;
            addEntry(String.format("Calling log pages %d to %d", pageStartLine, pageEndLine));
            ReversedLinesFileReader fileReader = new ReversedLinesFileReader(file);

            for(int i=0; i<pageEndLine; i++) {
                if(i < pageStartLine)
                    fileReader.readLine();
                else
                    lines.add(fileReader.readLine());
            }
        } catch (Exception e){
            addEntry("Could not return logging page due to: "+e.getLocalizedMessage());
        }

        Collections.reverse(lines);
        return lines;
    }

}
