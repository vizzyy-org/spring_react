package vizzyy.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class S3ResourceService {

    @Autowired
    static LoggingService loggingService;

    @Value("${s3.bucket.credentials}")
    public String credentialsBucket;

    private final AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard().build();

    public Collection<String> loadFileFromS3(String BUCKET_NAME, String FILE_NAME) {
        try (final S3Object s3Object = amazonS3Client.getObject(BUCKET_NAME, FILE_NAME);
             final InputStreamReader streamReader = new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.UTF_8);
             final BufferedReader reader = new BufferedReader(streamReader)) {
            return reader.lines().collect(Collectors.toSet());
        } catch (final IOException e) {
            loggingService.addEntry("ERROR loading S3 resource "+FILE_NAME+" - " + e.getMessage());
            return Collections.emptySet();
        }
    }

    public String getCredentialsBucket() {
        return credentialsBucket;
    }

    public void setCredentialsBucket(String credentialsBucket) {
        this.credentialsBucket = credentialsBucket;
    }
}
