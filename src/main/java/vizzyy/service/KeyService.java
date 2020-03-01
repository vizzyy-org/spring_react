package vizzyy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vizzyy.domain.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class KeyService {

    @Autowired
    UserRepository userRepository;

    private static String caPass = (String) S3ResourceService.loadFileFromS3("vizzyy", "credentials/ca.password").toArray()[0];

    public void generatePair(String CN) throws IOException, InterruptedException {
        String command =
                "openssl req -nodes -new -x509 -days 365 -sha256 " +
                "-newkey rsa:2048 -keyout " + CN +".key " +
                "-subj /CN=" + CN + "/OU=" + CN;
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        process.waitFor();
        System.out.println("ok!");

        in.close();
    }

    public void createSigningRequest(String CN) throws IOException, InterruptedException {
        String command =
                "openssl req -new -key "+CN+".key " +
                "-out "+CN+".csr -sha256 -subj /CN=" + CN + "/OU=" + CN;
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        process.waitFor();
        System.out.println("ok!");

        in.close();
    }

    public void signWithCA(String CN) throws IOException, InterruptedException {
        String command =
                "openssl ca -config /home/ec2-user/ca/ca.conf -batch " +
                "-in "+CN+".csr -cert /home/ec2-user/ca/ca.crt -keyfile /home/ec2-user/ca/ca.key " +
                "-out "+CN+".crt -subj /CN="+CN+"/OU="+CN+" -passin pass:" + caPass;
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        process.waitFor();
        System.out.println("ok!");

        in.close();
    }

    public void combine(String CN, String password) throws IOException, InterruptedException {
        String command =
                "openssl pkcs12 -export -passout pass:" + password +
                " -in "+CN+".crt -inkey "+CN+".key -certfile "+CN+".crt " +
                "-out "+CN+".p12";
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        process.waitFor();
        System.out.println("ok!");

        in.close();
    }

    public void export(String CN) throws IOException, InterruptedException {
        String command = "python emailCert.py " + CN +".p12";
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        process.waitFor();
        System.out.println("ok!");

        in.close();
    }

}
