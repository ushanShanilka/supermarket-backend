package com.supermarket.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "shopping_bag_has_product_has_product_size")
public class ShoppingBagHasProductHasProductSize implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "quantity")
    private Short quantity;
    @Column(name = "discounted_unit_price")
    private Double discountedUnitPrice;
    @JoinColumn(name = "product_has_product_size_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProductHasProductSize productHasProductSizeId;
    @JoinColumn(name = "shopping_bag_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ShoppingBag shoppingBagId;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status statusId;
}
