package com.sales.producer;


import java.util.Random;

import com.sales.common.AdjustmentOperation;
import com.sales.common.DoubleFormatter;
import com.sales.common.ProductType;
import com.sales.messaging.AdjSalesMessage;
import com.sales.messaging.BaseSalesMessage;
import com.sales.messaging.MultiSalesMessage;
import com.sales.messaging.SingleSalesMessage;

public class MockMessageProducer {

	public static BaseSalesMessage generateRandomMessage() {
		
		BaseSalesMessage message=null;
		Random rand = new Random();
		
		int mesgClassSelector = rand.nextInt(3);
		
		if (mesgClassSelector == 0) {
			message = new SingleSalesMessage(getRandomProductType(rand), getRandomSalesValue(rand));
		} else if (mesgClassSelector == 1) {
			message = new MultiSalesMessage(getRandomProductType(rand), getRandomOccurrence(rand), getRandomSalesValue(rand));
		} else if (mesgClassSelector == 2) {
			message = new AdjSalesMessage(getRandomProductType(rand), getRandomAdjType(rand), getRandomAdjValue(rand));
		} else {
			System.err.println("Unable to produce a message!!!");
		}
		
		return message;
	}

	// This would be used for a MultiSales message for which there should always be more than 1 sales.
	private static int getRandomOccurrence(Random rand) {
		int i = rand.nextInt(100);
		return (i>1?i:2);
	}

	private static double getRandomAdjValue(Random rand) {
		double d = rand.nextInt(10)*rand.nextDouble();
		while (d <= 0.0) {
			d = rand.nextInt(10)*rand.nextDouble();
		}
		return DoubleFormatter.format(d);
	}

	private static AdjustmentOperation getRandomAdjType(Random rand) {
		AdjustmentOperation[] allAdjTypes = AdjustmentOperation.values();
		return allAdjTypes[rand.nextInt(allAdjTypes.length)];
	}

	private static double getRandomSalesValue(Random rand) {
		double d = rand.nextDouble()+(rand.nextDouble()*10);
		while (d <= 0.0) {
			d = rand.nextDouble()+(rand.nextDouble()*10);
		}
		return DoubleFormatter.format(d);
	}

	private static ProductType getRandomProductType(Random rand) {
		ProductType[] allProductTypes = ProductType.values();
		return allProductTypes[rand.nextInt(allProductTypes.length)];
	}
}
