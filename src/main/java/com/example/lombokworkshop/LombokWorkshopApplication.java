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
					.withAmountValue(Amount.builder()
							.withDecimalValue(DecimalValue.ONE_HUNDRED_PERCENT)
							.withCurrency(SupportedCurrency.CAD)
							.build())
					.withBanner(banner)
          .withMinAmountValue(Amount.builder()
              .withDecimalValue(DecimalValue.ONE_HUNDRED_PERCENT)
              .withCurrency(SupportedCurrency.CAD)
              .build())
					.withId(new ObjectId("5733a8c6b958895728f2ba66").toString()).build();
      amountCouponRepository.deleteAll();
			amountCouponRepository.save(amountCoupon);

			InventoryItem inventoryItem = new InventoryItem();
			inventoryItem.setExternalRef(ExternalRef.builder().withId("25").withSource("facebook").build());
			inventoryItem.setName("Kitchen Table");
			inventoryItem.setQuantity(25);
      inventoryItemRepository.deleteAll();
			inventoryItemRepository.save(inventoryItem);
		};
	}
}
