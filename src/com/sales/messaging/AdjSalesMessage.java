package com.sales.messaging;

import com.sales.common.AdjustmentOperation;
import com.sales.common.SalesType;
import com.sales.common.ProductType;

public class AdjSalesMessage extends BaseSalesMessage {
	
	protected AdjustmentOperation adjType;
	
	public AdjSalesMessage(ProductType type, AdjustmentOperation adjType, double adjValue) {
		super(SalesType.AdjSales, type, adjValue);
		this.adjType = adjType;
	}

	//Getters
	public AdjustmentOperation getAdjType() {
		return this.adjType;
	}

}
