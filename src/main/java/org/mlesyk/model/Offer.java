package org.mlesyk.model;

import org.openqa.selenium.WebElement;

/**
 * Created by Maks.
 */
public class Offer {

    private String shopName;
    private int commentsAmount;
    private int warranty;
    private double price;
    private WebElement goToShopButton;

    public Offer(String shopName, int commentsAmount, int warranty, double price, WebElement goToShopButton) {
        this.shopName = shopName;
        this.commentsAmount = commentsAmount;
        this.warranty = warranty;
        this.price = price;
        this.goToShopButton = goToShopButton;
    }

    public Offer() {    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getCommentsAmount() {
        return commentsAmount;
    }

    public void setCommentsAmount(int commentsAmount) {
        this.commentsAmount = commentsAmount;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public WebElement getGoToShopButton() {
        return goToShopButton;
    }

    public void setGoToShopButton(WebElement goToShopButton) {
        this.goToShopButton = goToShopButton;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "shopName='" + shopName + '\'' +
                ", commentsAmount=" + commentsAmount +
                ", warranty='" + warranty + '\'' +
                ", price=" + price +
                '}';
    }
}
