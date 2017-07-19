package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.useCase.ApprovedOrderCannotBeRejectedException;
import it.gabrieletondi.telldontaskkata.useCase.RejectedOrderCannotBeApprovedException;
import it.gabrieletondi.telldontaskkata.useCase.ShippedOrdersCannotBeChangedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private BigDecimal total;
    private String currency;
    private List<OrderItem> items;
    private BigDecimal tax;
    private OrderStatus status;
    private int id;

    public Order(String currency) {
        this.status = OrderStatus.CREATED;
        this.items = new ArrayList<>();
        this.currency = currency;
        this.total = new BigDecimal("0.00");
        this.tax = new BigDecimal("0.00");
    }

    public Order() {
        new Order("EUR");
    }

    public BigDecimal getTotal() {
        return total;
    }


    public String getCurrency() {
        return currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {

        if (this.status != null) {
            if (this.status.equals(OrderStatus.SHIPPED)) {
                throw new ShippedOrdersCannotBeChangedException();
            }
        }

        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void reject() {
        if(this.status.equals(OrderStatus.APPROVED)) {
            throw new ApprovedOrderCannotBeRejectedException();
        }
        this.setStatus(OrderStatus.REJECTED);

    }

    public void approve() {
        if(this.status.equals(OrderStatus.REJECTED)) {
            throw new RejectedOrderCannotBeApprovedException();
        }
        this.setStatus(OrderStatus.APPROVED);
    }

    public void addItems(OrderItem orderItem) {
        this.items.add(orderItem);
        this.total = this.total.add(orderItem.getTaxedAmount());
        this.tax = this.tax.add(orderItem.getTax());

    }
}
