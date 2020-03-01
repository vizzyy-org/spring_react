package vizzyy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vizzyy.domain.UserRepository;

import java.io.IOException;

@Service
public class KeyService {

    @Autowired
    UserRepository userRepository;

    private static String caPass = (String) S3ResourceService.loadFileFromS3("vizzyy", "credentials/ca.password").toArray()[0];

    public void generatePair(String CN) throws IOException {
        String command =
                "openssl req -nodes -new -x509 -days 365 -sha256 " +
                "-newkey rsa:2048 -keyout " + CN +".key " +
                "-subj /CN=" + CN + "/OU=" + CN;
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        Process process = processBuilder.start();
        System.out.println(process.getOutputStream());
        System.out.println(process.getErrorStream());
    }

    public void createSigningRequest(String CN) throws IOException {
        String command =
                "openssl req -new -key "+CN+".key " +
                "-out "+CN+".csr -sha256 -subj /CN=" + CN + "/OU=" + CN;
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        Process process = processBuilder.start();
        System.out.println(process.getOutputStream());
        System.out.println(process.getErrorStream());
    }

    public void signWithCA(String CN) throws IOException {
        String command =
                "openssl ca -config $CAROOT/ca.conf -batch " +
                "-in "+CN+".csr -cert $CAROOT/ca.crt -keyfile $CAROOT/ca.key " +
                "-out "+CN+".crt -subj /CN="+CN+"/OU="+CN+" -passin pass:" + caPass;
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        Process process = processBuilder.start();
        System.out.println(process.getOutputStream());
        System.out.println(process.getErrorStream());
    }

    public void combine(String CN, String password) throws IOException {
        String command =
                "openssl pkcs12 -export -passout pass:" + password +
                " -in "+CN+".crt -inkey "+CN+".key -certfile "+CN+".crt " +
                "-out "+CN+".p12";
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        Process process = processBuilder.start();
        System.out.println(process.getOutputStream());
        System.out.println(process.getErrorStream());
    }

    public void export(String CN) throws IOException {
        String command = "python emailCert.py " + CN +".p12";
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        Process process = processBuilder.start();
        System.out.println(process.getOutputStream());
        System.out.println(process.getErrorStream());
    }

}
