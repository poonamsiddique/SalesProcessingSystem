package com.sales.messaging;

import com.sales.common.SalesType;
import com.sales.common.ProductType;

public class MultiSalesMessage extends BaseSalesMessage {
	
	protected int occurrences;
	
	public MultiSalesMessage(ProductType type, int numSales, double salesValue) {
		super(SalesType.MultipleSales, type, salesValue);
		
		this.occurrences = numSales;
	}

	// Getters
	public int getOccurrences() {
		return occurrences;
	}

}
