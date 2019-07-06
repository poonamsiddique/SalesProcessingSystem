package com.sales.consumer;

import com.sales.common.AdjustmentOperation;
import com.sales.common.ProductType;

public class AdjustmentLog {
	
	private ProductType productType;
	private long recsAdjusted;
	private String logMessage;
	private AdjustmentOperation adjOp;
	private double adjVal;
	
	// Getters
	public ProductType getProductType() {
		return this.productType;
	}
	
	public long getRecsAdjusted() {
		return this.recsAdjusted;
	}
	
	public String getLogMessage() {
		return this.logMessage;
	}
	
	public AdjustmentOperation getAdjOp() {
		return this.adjOp;
	}

	public double getAdjVal() {
		return this.adjVal;
	}

	// Setters
	public AdjustmentLog setProductType(ProductType productType) {
		this.productType = productType;
		return this;
	}

	public AdjustmentLog setRecsAdjusted(long recsAdjusted) {
		this.recsAdjusted = recsAdjusted;
		return this;
	}

	public AdjustmentLog setAdjOp(AdjustmentOperation adjOp) {
		this.adjOp = adjOp;
		return this;
	}
	
	public AdjustmentLog setAdjVal(double adjVal) {
		this.adjVal = adjVal;
		return this;
	}

	public AdjustmentLog formatLogMessage() {
		this.logMessage = this.adjOp + " " + this.getAdjVal() + " " + this.getProductType() + " : "
				+ this.getRecsAdjusted() + " sales records adjusted";
		return this;
	}
}
