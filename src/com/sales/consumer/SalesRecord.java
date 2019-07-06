package com.sales.consumer;

import com.sales.common.ProductType;

public class SalesRecord {

	private ProductType type;
	private double value;
	
	// Getters
	public ProductType getType() {
		return this.type;
	}

	public double getValue() {
		return this.value;
	}

	// Setters
	public SalesRecord setType(ProductType type) {
		this.type = type;
		return this;
	}

	public SalesRecord setValue(double value) {
		this.value = value;
		return this;
	}
}
