package com.example.lombokworkshop.service;

import com.example.lombokworkshop.model.AmountCoupon;
import com.example.lombokworkshop.model.InventoryItem;
import com.example.lombokworkshop.repository.AmountCouponRepository;
import com.example.lombokworkshop.repository.InventoryItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {

  private final AmountCouponRepository amountCouponRepository;
  private final InventoryItemRepository inventoryItemRepository;

  public List<AmountCoupon> findAllAmountCoupons(){
    return amountCouponRepository.findAll();
  }

  public List<InventoryItem> findAllInventoryItems(){
    return inventoryItemRepository.findAll();
  }

}
