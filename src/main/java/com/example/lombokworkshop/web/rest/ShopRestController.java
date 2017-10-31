package com.example.lombokworkshop.web.rest;

import com.example.lombokworkshop.model.AmountCoupon;
import com.example.lombokworkshop.model.InventoryItem;
import com.example.lombokworkshop.service.ShopService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShopRestController {

  @Autowired
  private ShopService shopService;

  @GetMapping("/amount-coupons")
  public ResponseEntity retrieveAllAmountCoupons(){
    List<AmountCoupon> coupons =  shopService.findAllAmountCoupons();
    return ResponseEntity.ok(coupons.toString());
  }

  @GetMapping("/inventory-items")
  public ResponseEntity retrieveAllInventoryItems(){
    List<InventoryItem> items = shopService.findAllInventoryItems();
    return ResponseEntity.ok(items.toString());
  }
}
