package grepp.shop.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchSchemaInitializerConfig {
    private final DataSource dataSource;

    @PostConstruct
    public void initializeBatchSchema() throws SQLException {
        if (batchTablesExist()) {
            log.info("Spring Batch tables already exist. Skipping schema initialization.");
            return;
        }
        log.info("Spring Batch tables not found. Executing schema-postgresql.sql");
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                new ClassPathResource("org/springframework/batch/core/schema-postgresql.sql")
        );
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

    private boolean batchTablesExist() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String schema = connection.getSchema();
            if (schema == null || schema.isBlank()) {
                schema = "public";
            }

            DatabaseMetaData metaData = connection.getMetaData();
            String tableName = "batch_job_instance";
            if (metaData.storesUpperCaseIdentifiers()) {
                tableName = tableName.toUpperCase();
            } else if (metaData.storesMixedCaseIdentifiers()) {
                tableName = "Batch_Job_Instance";
            }

            try (ResultSet tables = metaData.getTables(connection.getCatalog(), schema, tableName, new String[]{"TABLE"})) {
                return tables.next();
            }
        }
    }
}
