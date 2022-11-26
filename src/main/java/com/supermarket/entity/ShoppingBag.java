package com.supermarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "shopping_bag")
public class ShoppingBag implements Serializable {

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
    @Column(name = "total")
    private Double total;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status statusId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;
    @JoinColumn(name = "shopping_bag_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ShoppingBagStatus shoppingBagStatusId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingBagId")
    private Collection<ShoppingBagHasProductHasProductSize> shoppingBagHasProductHasProductSizeCollection;

}
