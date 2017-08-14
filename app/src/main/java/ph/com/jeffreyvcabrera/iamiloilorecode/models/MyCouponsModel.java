package ph.com.jeffreyvcabrera.iamiloilorecode.models;

/**
 * Created by Jeffrey on 2/19/2017.
 */

public class MyCouponsModel {

    Integer id, price, discount, order_count, status;
    String name, description, date_added, image, short_description;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setOrder_count(Integer order_count) {
        this.order_count = order_count;
    }

    public Integer getOrder_count() {
        return order_count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

}
