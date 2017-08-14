package ph.com.jeffreyvcabrera.iamiloilorecode.models;

/**
 * Created by Jeffrey on 4/7/2017.
 */

public class HotelRoomsModel {

    Integer id, hotel_id;
    String name, image;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setHotelId(Integer hotel_id) {
        this.id = hotel_id;
    }

    public Integer getHotelId() {
        return hotel_id;
    }
}
