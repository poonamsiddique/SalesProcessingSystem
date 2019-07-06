package com.sales.messaging;

import com.sales.common.SalesType;
import com.sales.common.ProductType;

public class SingleSalesMessage extends BaseSalesMessage {
	
	public SingleSalesMessage(ProductType type, double salesValue) {
		super(SalesType.SingleSales, type, salesValue);
	}
}
