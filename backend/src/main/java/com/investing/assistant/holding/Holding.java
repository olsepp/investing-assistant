package com.investing.assistant.holding;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

/**
 * One position in the portfolio, e.g. "15 shares of AAPL bought for 2400.00 USD in total".
 * Each instance corresponds to one row in the "holding" table.
 */
@Entity
@Table(name = "holding")
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String ticker;

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

    /** Everything paid for the position, fees included. */
    @Column(name = "total_cost", nullable = false, precision = 19, scale = 6)
    private BigDecimal totalCost;

    @Column(nullable = false, length = 3)
    private String currency;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /** Required by JPA, which creates objects this way when loading rows. Not for our own code. */
    protected Holding() {
    }

    public Holding(String ticker, BigDecimal quantity, BigDecimal totalCost, String currency) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public String getTicker() {
        return ticker;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public String getCurrency() {
        return currency;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /** Price paid per share: total cost / quantity, rounded to 6 decimals. Calculated, not stored. */
    public BigDecimal getAverageBuyPrice() {
        return totalCost.divide(quantity, 6, RoundingMode.HALF_UP);
    }

    /** The ticker is the holding's identity, so only the position details can change. */
    public void updatePosition(BigDecimal quantity, BigDecimal totalCost, String currency) {
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.currency = currency;
    }
}
