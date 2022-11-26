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
@Entity(name = "product")
public class Product implements Serializable {

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
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "descriptions")
    private String descriptions;
    @Column(name = "video_url")
    private String videoUrl;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "rating_count")
    private Long ratingCount;
    @JoinColumn(name = "product_category_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private ProductCategory productCategoryId;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status statusId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<ProductHasImage> productHasImageCollection;
}
