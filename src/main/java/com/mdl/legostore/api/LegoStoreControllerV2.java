package com.mdl.legostore.api;

import com.mdl.legostore.model.LegoSet;
import com.mdl.legostore.model.LegoSetDifficulty;
import com.mdl.legostore.model.QLegoSet;
import com.mdl.legostore.persistence.LegoSetRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("legostore/v2/api")
public class LegoStoreControllerV2 {
    private LegoSetRepository legoSetRepository;

    public LegoStoreControllerV2(LegoSetRepository legoSetRepository) {
        this.legoSetRepository = legoSetRepository;
    }

    @PostMapping
    public void insert(@RequestBody LegoSet legoSet) {
        this.legoSetRepository.insert(legoSet);
    }

    @PutMapping
    public void update(@RequestBody LegoSet legoSet) {
        this.legoSetRepository.save(legoSet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.legoSetRepository.deleteById(id);
    }

    @GetMapping("/all")
    public Collection<LegoSet> all() {
        Sort sortByThemeAsc = Sort.by("theme").ascending();
        Collection<LegoSet> legoSets = this.legoSetRepository.findAll(sortByThemeAsc);
        return legoSets;
    }

    @GetMapping("/{id}")
    public LegoSet findById(@PathVariable String id) {
        LegoSet legoset = this.legoSetRepository.findById(id).orElse(null);
        return legoset;
    }

    @GetMapping("/findByTheme/{theme}")
    public Collection<LegoSet> findByTheme(@PathVariable String theme) {
        Sort sortByThemeAsc = Sort.by("theme").ascending();
        Collection<LegoSet> legoSets =  this.legoSetRepository.findAllByThemeContains(theme, sortByThemeAsc);
        return legoSets;
    }

    @GetMapping("hardThatStartsWithM")
    public Collection<LegoSet> hardThatStartsWithM() {
        Collection<LegoSet> legoSets =  this.legoSetRepository.findAllByDifficultyAndNameStartsWith(LegoSetDifficulty.HARD, "M");
        return legoSets;
    }

    @GetMapping("findAllByDeliveryPriceLessThan/{price}")
    public Collection<LegoSet> findAllByDeliveryPriceLessThan(@PathVariable int price) {
        Collection<LegoSet> legoSets =  this.legoSetRepository.findAllByDeliveryPriceLessThan(price);
        return legoSets;
    }

    @GetMapping("findAllByGreatReviews")
    public Collection<LegoSet> findAllByGreatReviews() {
        Collection<LegoSet> legoSets =  this.legoSetRepository.findAllByGreatReviews();
        return legoSets;
    }

    @GetMapping("bestBuys")
    public Collection<LegoSet> bestBuys() {
        QLegoSet query = new QLegoSet("query");
        BooleanExpression inStockFilter = query.deliveryInfo.inStock.isTrue();
        BooleanExpression smallDeliveryFeeFilter = query.deliveryInfo.deliveryFee.lt(50);
        BooleanExpression hasGreatReviews = query.reviews.any().rating.eq(10);

        Predicate bestBuysFilter = inStockFilter
                .and(smallDeliveryFeeFilter)
                .and(hasGreatReviews);

        return (Collection<LegoSet>) this.legoSetRepository.findAll(bestBuysFilter);
    }
}
