package vizzyy.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import vizzyy.service.S3ResourceService;

import javax.sql.DataSource;

@Configuration
@Profile("enableAuth")
public class DataSourceConfig {

    private static String dbUser = (String) S3ResourceService.loadFileFromS3("vizzyy-credentials", "db.user").toArray()[0];
    private static String dbPass = (String) S3ResourceService.loadFileFromS3("vizzyy-credentials", "db.password").toArray()[0];
    private static String dbURL = (String) S3ResourceService.loadFileFromS3("vizzyy-credentials", "db.url").toArray()[0];

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