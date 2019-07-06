package com.sales.consumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.sales.common.AdjustmentOperation;
import com.sales.common.DoubleFormatter;
import com.sales.common.ProductType;
import com.sales.common.SalesType;
import com.sales.messaging.AdjSalesMessage;
import com.sales.messaging.BaseSalesMessage;
import com.sales.messaging.MultiSalesMessage;

public class SalesSession {
	private Collection<SalesRecord> allSalesRecords;
	private Collection<AdjustmentLog> allAdjLogs;
	private int messagesProcessed;
	
	public SalesSession() {
		this.allSalesRecords = new ArrayList<>();
		this.allAdjLogs = new ArrayList<>();
		this.messagesProcessed = 0;
	}
	
	public boolean recordSales(BaseSalesMessage message) {
		
		SalesType sType = message.getMsgTyp();
		ProductType pType = message.getProdTyp();
		double val = message.getValue();
		
		switch (sType) {
		case SingleSales:
			this.allSalesRecords.add(new SalesRecord().setType(pType).setValue(DoubleFormatter.format(val)));
			System.out.println("Message Processed: added 1 sales record");
			break;
		case MultipleSales:
			int numSales = ((MultiSalesMessage) message).getOccurrences();
			for (int x=0; x<numSales; x++) {
				this.allSalesRecords.add(new SalesRecord().setType(pType).setValue(DoubleFormatter.format(val)));
			}
			System.out.println("Message Processed: added " + numSales + " sales records");
			break;
		case AdjSales:
			AdjustmentOperation adjOp = ((AdjSalesMessage) message).getAdjType();
			adjustSales(adjOp, pType, val);
			break;
		default: System.err.println("Un-implemented, message not understood by the application!!!");
			return false;
		}
		this.messagesProcessed++;
		return true;
	}

	private void adjustSales(AdjustmentOperation adjOp, ProductType pType, double val) {
		
		Optional<SalesRecord> record = this.allSalesRecords.stream().filter(z -> z.getType().equals(pType)).findAny();
		
		if(!record.isPresent()) {
			System.err.println("Message Not Processed: No sales found matching given product type of " + pType + " for adjustment");
			return;
		}
		
		// For ADD & MULTIPLY
		long recordsAffected = this.allSalesRecords.stream().filter(x -> x.getType().equals(pType)).count();

		switch (adjOp) {
		case ADD:
			this.allSalesRecords.stream().filter(salesRecord -> pType.equals(salesRecord.getType()))
					.forEach(filtered -> filtered.setValue(DoubleFormatter.format(filtered.getValue()+val)));
			break;
		case SUBTRACT:
			recordsAffected = this.allSalesRecords.stream().filter(x -> x.getType().equals(pType))
					.filter(y -> y.getValue() > val).count();
			this.allSalesRecords.stream().filter(salesRecord -> pType.equals(salesRecord.getType()))
					.forEach(filtered -> {
						double newVal = DoubleFormatter.format(filtered.getValue() - val);
						if (newVal > 0) {
							filtered.setValue(newVal);
						}
					});
			break;
		case MULTIPLY:
			this.allSalesRecords.stream().filter(salesRecord -> pType.equals(salesRecord.getType()))
					.forEach(filtered -> filtered.setValue(DoubleFormatter.format(filtered.getValue() * val)));
			break;
		default:
			System.out.println("Adjustment Operation Not Implemented: " + adjOp);
		}
		
		System.out.println("Message Processed: adjusted " + recordsAffected + " sales records");

		this.allAdjLogs.add(new AdjustmentLog().setProductType(pType).setRecsAdjusted(recordsAffected).setAdjOp(adjOp)
				.setAdjVal(val).formatLogMessage());
	}

	// Getters
	public int getMessagesProcessed() {
		return this.messagesProcessed;
	}

	public Collection<SalesRecord> getAllSalesRecords() {
		return this.allSalesRecords;
	}

	public Collection<AdjustmentLog> getAllAdjLogs() {
		return this.allAdjLogs;
	}
}
