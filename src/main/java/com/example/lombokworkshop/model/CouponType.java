package com.example.lombokworkshop.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CouponType implements BaseEnum {
	AMOUNT_COUPON, PERCENTAGE_COUPON;

	@Override
	public String getMessagePrefix() {
		return "coupon";
	}

	@JsonCreator
	public static CouponType fromString(String key) {
		return key == null
				? null
				: CouponType.valueOf(key.toUpperCase());
	}

}
