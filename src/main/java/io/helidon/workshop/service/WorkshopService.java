package io.helidon.workshop.service;

import io.helidon.workshop.entity.Items;
import io.helidon.workshop.jpa.JPAItems;

import java.sql.SQLException;
import java.util.List;

public interface WorkshopService {

    List<Items> selectAllItems() throws SQLException;

    List<JPAItems> selectAllJPAItems();
}
