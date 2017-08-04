package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.useCase.UnknownProductException;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class OrderItem {
    private Product product;
    private int quantity;
    private BigDecimal taxedAmount;
    private BigDecimal tax;

    public OrderItem() {

    }

    public OrderItem(Product product, int quantity) {
        if(product == null ){
           throw new UnknownProductException();
        }
        this.product = product;
        this.quantity = quantity;

        this.taxedAmount = product.getUnitaryTaxedAmount().multiply(BigDecimal.valueOf(this.quantity)).setScale(2, HALF_UP);
        this.tax = product.getUnitaryTax().multiply(BigDecimal.valueOf(this.quantity));

    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTaxedAmount() {
        return taxedAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }
}
