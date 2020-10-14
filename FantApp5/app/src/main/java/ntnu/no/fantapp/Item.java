package ntnu.no.fantapp;

public class Item {
    private Long id;

    private String title;

    private String created;

    private String price;

    private String description;

    private boolean sold;

    private int userid;

    public Item(String title, String price, String description)
    {
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public Item(Long id, String title, String created, String price, String description, boolean sold, int userid)
    {
        this.id = id;
        this.title = title;
        this.created = created;
        this.price = price;
        this.description = description;
        this.sold = sold;
        this.userid = userid;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCreated()
    {
        return created;
    }

    public void setCreated(String created)
    {
        this.created = created;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isSold()
    {
        return sold;
    }

    public void setSold(boolean sold)
    {
        this.sold = sold;
    }

    public int getUserid()
    {
        return userid;
    }

    public void setUserid(int userid)
    {
        this.userid = userid;
    }

    @Override
    public String toString()
    {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", created='" + created + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
               // ", sold=" + sold +
                ", userid=" + userid +
                '}';
    }
}

