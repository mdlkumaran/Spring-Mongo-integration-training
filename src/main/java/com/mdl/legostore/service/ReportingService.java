package com.mdl.legostore.service;

import com.mdl.legostore.model.AvgRatingModel;
import com.mdl.legostore.model.LegoSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Service
public class ReportingService {
    private MongoTemplate mongoTemplate;

    public ReportingService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<AvgRatingModel> getAvgRatingReport() {
        ProjectionOperation projectionMatchModel = project()
                .andExpression("name").as("productName")
                .andExpression("{$avg : '$reviews.rating'}").as("avgRating");
        Aggregation avgRatingAggregation = newAggregation(LegoSet.class, projectionMatchModel);
        return this.mongoTemplate.aggregate(avgRatingAggregation, LegoSet.class, AvgRatingModel.class).getMappedResults();
    }

}
