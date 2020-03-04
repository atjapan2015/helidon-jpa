package io.helidon.workshop.ds;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

@ApplicationScoped
public class WorkshopDataSource {

    @Inject
    @Named("workshopDS")
    private DataSource workshopDS;

    private final DataSource ds;

    @Inject
    public WorkshopDS(@Named("workshopDS") DataSource ds) {
        super();
        this.ds = ds;
    }
}
