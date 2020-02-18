package vizzyy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class LoggingService {

    private ArrayList<String> arr = new java.util.ArrayList<>();
    private Logger log = LoggerFactory.getLogger(LoggingService.class);

    public void addEntry(String entry){
        String timeStamp = new SimpleDateFormat().format( new Date() );
        log.info(entry);
        arr.add(timeStamp +" -- "+entry);
    }

    private ArrayList<String> getLogs(){
        return arr;
    }

    public String printLogs(){

        StringBuilder output = new StringBuilder();
        for(String line : getLogs()){
            output.append(line).append("\n");
        }

        return output.toString();
    }

}
