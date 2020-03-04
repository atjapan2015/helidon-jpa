package io.helidon.workshop.entity;

import java.util.Date;

public class Items {

    private Long itemId;

    private String itemTitle;

    private String itemDesc;

    private Date itemPostDate;

    private Long itemPostedBy;

    private Long itemBoughtBy;

    private Short itemPrice;

    private String itemStatus;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
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