package com.example.lombokworkshop;

import com.example.lombokworkshop.model.Amount;
import com.example.lombokworkshop.model.AmountCoupon;
import com.example.lombokworkshop.model.DecimalValue;
import com.example.lombokworkshop.model.ExternalRef;
import com.example.lombokworkshop.model.InventoryItem;
import com.example.lombokworkshop.model.LocalizedString;
import com.example.lombokworkshop.model.SupportedCurrency;
import com.example.lombokworkshop.repository.AmountCouponRepository;
import com.example.lombokworkshop.repository.InventoryItemRepository;
import java.util.Locale;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.lombokworkshop.repository")
public class LombokWorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(LombokWorkshopApplication.class, args);
	}

	@Autowired
	private AmountCouponRepository amountCouponRepository;

	@Autowired
	private InventoryItemRepository inventoryItemRepository;

	@Bean
	CommandLineRunner runner(){
		return args -> {
			LocalizedString banner = new LocalizedString();
			banner.with(Locale.ENGLISH, "banner");
			banner.with(Locale.FRENCH, "banière");

			AmountCoupon amountCoupon = AmountCoupon.builder()
					.amountValue(Amount.builder()
							.decimalValue(DecimalValue.ONE_HUNDRED_PERCENT)
							.currencyCode(SupportedCurrency.CAD)
							.build())
					.banner(banner)
          .amountValue(Amount.builder()
              .decimalValue(DecimalValue.ONE_HUNDRED_PERCENT)
              .currencyCode(SupportedCurrency.CAD)
              .build())
          .updatedAt(DateTime.now())
					.id(new ObjectId("5733a8c6b958895728f2ba66")).build();

      amountCouponRepository.deleteAll();
			amountCouponRepository.save(amountCoupon);


			InventoryItem chair = InventoryItem.builder()
          .name("Kitchen Chair")
          .updatedAt(DateTime.now())
          .externalRef(ExternalRef.builder().id("25").source("facebook").build())
          .quantity(89)
          .build();

      inventoryItemRepository.deleteAll();
			inventoryItemRepository.save(chair);
		};
	}
}
