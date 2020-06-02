package vizzyy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vizzyy.service.S3ResourceService;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Autowired
    public S3ResourceService s3ResourceService;

    private final String dbUser = (String) s3ResourceService.loadFileFromS3(s3ResourceService.getCredentialsBucket(), "db.user").toArray()[0];
    private final String dbPass = (String) s3ResourceService.loadFileFromS3(s3ResourceService.getCredentialsBucket(), "db.password").toArray()[0];
    private final String dbURL = (String) s3ResourceService.loadFileFromS3(s3ResourceService.getCredentialsBucket(), "db.url").toArray()[0];

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url(dbURL);
        dataSourceBuilder.username(dbUser);
        dataSourceBuilder.password(dbPass);
        return dataSourceBuilder.build();
    }
}