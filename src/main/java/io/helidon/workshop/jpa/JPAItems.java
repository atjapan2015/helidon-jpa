package io.helidon.workshop.jpa;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Access(value = AccessType.FIELD)
@Entity(name = "JPAItems")
@Table(name = "ITEMS")
public class JPAItems implements Serializable {

    @Column(
            insertable = true,
            name = "ITEM_ID",
            nullable = false,
            updatable = false
    )
    @Id
    private Integer itemId;

    @Basic(optional = false)
    @Column(
            insertable = true,
            name = "ITEM_TITLE",
            nullable = false,
            updatable = true
    )
    private String itemTitle;

    @Basic(optional = false)
    @Column(
            insertable = true,
            name = "ITEM_DESC",
            nullable = false,
            updatable = true
    )
    private String itemDesc;

    @Basic(optional = false)
    @Column(
            insertable = true,
            name = "ITEM_POST_DATE",
            nullable = false,
            updatable = true
    )
    private Date itemPostDate;

    @Basic(optional = false)
    @Column(
            insertable = true,
            name = "ITEM_POSTED_BY",
            nullable = false,
            updatable = true
    )
    private Long itemPostedBy;

    @Basic(optional = false)
    @Column(
            insertable = true,
            name = "ITEM_BOUGHT_BY",
            nullable = false,
            updatable = true
    )
    private Long itemBoughtBy;

    @Basic(optional = false)
    @Column(
            insertable = true,
            name = "ITEM_PRICE",
            nullable = false,
            updatable = true
    )
    private Short itemPrice;

    @Basic(optional = false)
    @Column(
            insertable = true,
            name = "ITEM_STATUS",
            nullable = false,
            updatable = true
    )
    private String itemStatus;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Date getItemPostDate() {
        return itemPostDate;
    }

    public void setItemPostDate(Date itemPostDate) {
        this.itemPostDate = itemPostDate;
    }

    public Long getItemPostedBy() {
        return itemPostedBy;
    }

    public void setItemPostedBy(Long itemPostedBy) {
        this.itemPostedBy = itemPostedBy;
    }

    public Long getItemBoughtBy() {
        return itemBoughtBy;
    }

    public void setItemBoughtBy(Long itemBoughtBy) {
        this.itemBoughtBy = itemBoughtBy;
    }

    public Short getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Short itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }
}
