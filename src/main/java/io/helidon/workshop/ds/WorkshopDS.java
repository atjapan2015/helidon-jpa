package io.helidon.workshop.ds;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

@ApplicationScoped
public class WorkshopDS {

    @Inject
    @Named("workshop")
    private DataSource workshopDataSource;

    private final DataSource ds;

    @Inject
    public WorkshopDS(@Named("workshop") DataSource ds) {
        super();
        this.ds = ds;
    }
}
