package com.crypto_shopping.cryptoshopping.Objects;

public class ProductInCart extends Product{

    private String image,title,desc,productID;
    private int price;


    private int amount;

    public String  getProductID() {
        return productID;
    }

    public void setProductID(String id) {
        this.productID= id;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDescription(String description) {
        this.desc = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
