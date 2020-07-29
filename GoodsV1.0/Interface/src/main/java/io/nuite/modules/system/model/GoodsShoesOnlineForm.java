package io.nuite.modules.system.model;

import java.math.BigDecimal;
import java.util.List;

public class GoodsShoesOnlineForm {
    private BigDecimal tagPrice;
    private BigDecimal salePrice;
    private Boolean onSale;

    private List<GoodsShoesStockForm> stock;

    public BigDecimal getTagPrice() {
        return tagPrice;
    }

    public void setTagPrice(BigDecimal tagPrice) {
        this.tagPrice = tagPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public List<GoodsShoesStockForm> getStock() {
        return stock;
    }

    public void setStock(List<GoodsShoesStockForm> stock) {
        this.stock = stock;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }
}
