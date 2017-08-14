package ph.com.jeffreyvcabrera.iamiloilorecode.models;

/**
 * Created by Jeffrey on 2/19/2017.
 */

public class CatalogueModel {

    Integer id, price, discount, coupons_left;
    String name, description, promo_date, image;

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

    public void setCoupons_left(Integer coupons_left) {
        this.coupons_left = coupons_left;
    }

    public Integer getCoupons_left() {
        return coupons_left;
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

    public void setPromo_date(String promo_date) {
        this.promo_date = promo_date;
    }

    public String getPromo_date() {
        return promo_date;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

}
