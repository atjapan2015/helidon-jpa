package io.helidon.workshop.ds;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

@ApplicationScoped
public class WorkshopDataSource {

    @Inject
    @Named("workshopDataSource")
    private DataSource workshopDataSource;

    private final DataSource ds;

    @Inject
    public WorkshopDataSource(@Named("workshopDataSource") DataSource ds) {
        super();
        this.ds = ds;
    }
}
