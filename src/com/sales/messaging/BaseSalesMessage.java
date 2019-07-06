package com.sales.messaging;

import com.sales.common.SalesType;
import com.sales.common.ProductType;

public abstract class BaseSalesMessage {
	
	protected SalesType msgTyp;
	protected ProductType prodTyp;
	protected double value;
	
	public BaseSalesMessage(SalesType msgTyp, ProductType salesTyp, double value) {
		this.msgTyp = msgTyp;
		this.prodTyp = salesTyp;
		this.value = value;
	}
	
	public SalesType getMsgTyp() {
		return this.msgTyp;
	}
	
	public ProductType getProdTyp() {
		return this.prodTyp;
	}
	
	public double getValue() {
		return this.value;
	}

}
