package kr.co.ttinfo.bookManager.book;

/**
 * Created by ttinfo on 2017-03-12.
 */
public class Books {
    private String name;
    private String id;
    private String price;

    public Books(String name, String id, String price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
