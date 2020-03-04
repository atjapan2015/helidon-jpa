package io.helidon.workshop.service.impl;


import io.helidon.workshop.entity.Items;
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
import java.util.ArrayList;
import java.util.List;

@Dependent
@Transactional(Transactional.TxType.REQUIRED)
public class WorkshopServiceImpl implements WorkshopService {

    @Inject
    @Named("workshop")
    private DataSource workshopDataSource;

    @Override
    public List<Items> selectAllItems() throws SQLException {
        List<Items> items = new ArrayList<>();
        try (Connection connection = this.workshopDataSource.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(" SELECT ITEM_ID, ITEM_TITLE, ITEM_DESC, ITEM_POST_DATE, ITEM_POSTED_BY, ITEM_BOUGHT_BY, ITEM_PRICE, ITEM_STATUS"
                             + " FROM ITEMS"
                             + " ORDER BY ITEM_ID ASC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Items item = new Items();
                item.setItemId(rs.getLong(1));
                item.setItemTitle(rs.getString(2));
                item.setItemDesc(rs.getString(3));
                item.setItemPostDate(rs.getDate(4));
                item.setItemPostedBy(rs.getLong(5));
                item.setItemBoughtBy(rs.getLong(6));
                item.setItemPrice(rs.getShort(7));
                item.setItemStatus(rs.getString(8));
                items.add(item);
            }
        }
        return items;
    }
}
