package com.example.lombokworkshop.web.rest;

import com.example.lombokworkshop.model.AmountCoupon;
import com.example.lombokworkshop.model.InventoryItem;
import com.example.lombokworkshop.service.ShopService;
import com.google.gson.Gson;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShopRestController {

  private final ShopService shopService;

  @GetMapping("/amount-coupons")
  public ResponseEntity retrieveAllAmountCoupons(){
    List<AmountCoupon> coupons =  shopService.findAllAmountCoupons();
    Gson gson = new Gson();
    return ResponseEntity.ok(gson.toJson(coupons));
  }

  @GetMapping("/inventory-items/string")
  public ResponseEntity retrieveAllInventoryItemsString(){
    List<InventoryItem> items = shopService.findAllInventoryItems();
    return ResponseEntity.ok(items.toString());
  }

  @GetMapping("/inventory-items")
  public ResponseEntity retrieveAllInventoryItems(){
    List<InventoryItem> items = shopService.findAllInventoryItems();
    Gson gson = new Gson();
    return ResponseEntity.ok(gson.toJson(items));
  }
}
