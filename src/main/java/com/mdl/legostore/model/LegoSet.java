package com.mdl.legostore.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Collection;

@Document(collection = "legosets")
public class LegoSet {
    @Id
    private String id;
    private String name;
    private LegoSetDifficulty difficulty;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String theme;
    private Collection<ProductReview> reviews = new ArrayList<>();
    @Field("delivery")
    private DeliveryInfo deliveryInfo;

    public LegoSet(String name, String theme, LegoSetDifficulty difficulty, DeliveryInfo deliveryInfo, Collection<ProductReview> reviews ) {
        this.name = name;
        this.difficulty = difficulty;
        this.theme = theme;
        this.deliveryInfo = deliveryInfo;
        if (reviews != null) {
            this.reviews = reviews;
        }
    }

    private int nbParts;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LegoSetDifficulty getDifficulty() {
        return difficulty;
    }

    public String getTheme() {
        return theme;
    }

    public Collection<ProductReview> getReviews() {
        return reviews;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public int getNbParts() {
        return nbParts;
    }
}
