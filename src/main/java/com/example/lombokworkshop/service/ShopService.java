package com.example.lombokworkshop.service;

import com.example.lombokworkshop.model.AmountCoupon;
import com.example.lombokworkshop.model.InventoryItem;
import com.example.lombokworkshop.repository.AmountCouponRepository;
import com.example.lombokworkshop.repository.InventoryItemRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

  @Autowired
  private AmountCouponRepository amountCouponRepository;

  @Autowired
  private InventoryItemRepository inventoryItemRepository;

  public List<AmountCoupon> findAllAmountCoupons(){
    return amountCouponRepository.findAll();
  }

  public List<InventoryItem> findAllInventoryItems(){
    return inventoryItemRepository.findAll();
  }

}
