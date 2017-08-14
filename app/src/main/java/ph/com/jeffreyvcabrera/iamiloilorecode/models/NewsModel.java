package ph.com.jeffreyvcabrera.iamiloilorecode.models;

/**
 * Created by Jeffrey on 2/22/2017.
 */

public class NewsModel {
    Integer id;
    String name, description, post_date, image;

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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_date() {
        return post_date;
    }
}
