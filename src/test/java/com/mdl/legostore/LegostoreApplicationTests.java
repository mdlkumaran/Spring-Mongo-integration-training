package com.mdl.legostore;

import com.mdl.legostore.model.DeliveryInfo;
import com.mdl.legostore.model.LegoSet;
import com.mdl.legostore.model.LegoSetDifficulty;
import com.mdl.legostore.model.ProductReview;
import com.mdl.legostore.persistence.LegoSetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest
class LegostoreDBTests {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private LegoSetRepository legoSetRepository;

	@BeforeEach
	public void before() {
		this.legoSetRepository.deleteAll();

		LegoSet milleniumFalcon = new LegoSet(
				"Millennium Falcon",
				"Star Wars",
				LegoSetDifficulty.HARD,
				new DeliveryInfo(LocalDate.now().plusDays(1), 30, true),
				Arrays.asList(
						new ProductReview("Dan", 7),
						new ProductReview("Anna", 10),
						new ProductReview("John", 8)
				)
		);

		LegoSet skyPolice = new LegoSet(
				"Sky Police Air Base",
				"City",
				LegoSetDifficulty.MEDIUM,
				new DeliveryInfo(LocalDate.now().plusDays(3), 50, true),
				Arrays.asList(
						new ProductReview("Dan", 5),
						new ProductReview("Andrew", 8)
				)
		);

		this.legoSetRepository.insert(milleniumFalcon);
		this.legoSetRepository.insert(skyPolice);
	}

	@Test
	public void findAllByGreatReviews() {
		List<LegoSet> results = (List<LegoSet>) this.legoSetRepository.findAllByGreatReviews();
		Assertions.assertEquals(1, results.size());
		Assertions.assertEquals("Millennium Falcon", results.get(0).getName());
	}

}
