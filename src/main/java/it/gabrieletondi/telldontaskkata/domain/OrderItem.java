package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class OrderItem {
    private Product product;
    private int quantity;
    private BigDecimal taxedAmount;
    private BigDecimal tax;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.taxedAmount = this.calculateTaxedAmount();
        this.tax = this.calculateTaxAmount();
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

    private BigDecimal calculateTaxedAmount() {
        BigDecimal quantity = BigDecimal.valueOf(this.getQuantity());
        BigDecimal price = this.product.getPrice();

        final BigDecimal unitaryTax = this.calculateUnitaryTax();
        final BigDecimal unitaryTaxedAmount = price.add(unitaryTax).setScale(2, HALF_UP);
        return unitaryTaxedAmount.multiply(quantity).setScale(2, HALF_UP);
    }

    private BigDecimal calculateTaxAmount() {
        BigDecimal quantity = BigDecimal.valueOf(this.getQuantity());

        final BigDecimal unitaryTax = this.calculateUnitaryTax();
        return unitaryTax.multiply(quantity);
    }

    private BigDecimal calculateUnitaryTax() {
        BigDecimal taxPercentage = this.product.getCategory().getTaxPercentage();
        return product.getPrice().divide(valueOf(100)).multiply(taxPercentage).setScale(2, HALF_UP);
    }


}
