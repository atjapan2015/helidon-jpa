package io.helidon.workshop.service.impl;


import io.helidon.workshop.service.WorkshopService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Dependent
@Transactional(Transactional.TxType.REQUIRED)
public class WorkshopServiceImpl implements WorkshopService {

    @Inject
    @Named("workshop")
    private DataSource workshopDataSource;

    @Override
    public void doSomething() throws SQLException {
        StringBuilder sb = new StringBuilder();
        try (Connection connection = this.workshopDataSource.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(" SELECT TABLE_NAME"
                             + " FROM INFORMATION_SCHEMA.TABLES "
                             + "ORDER BY TABLE_NAME ASC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                sb.append(rs.getString(1)).append("\n");
            }
        }
        System.out.println(sb.toString());
    }
}
