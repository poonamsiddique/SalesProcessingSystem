package com.sales.main;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.sales.common.DoubleFormatter;
import com.sales.common.ProductType;
import com.sales.consumer.SalesRecord;
import com.sales.consumer.SalesSession;
import com.sales.producer.MockMessageProducer;

public class SalesApp {

	public static void main(String[] args) {
		
		SalesSession mockSalesSession = new SalesSession();
		
		while (true) {
		
			mockSalesSession.recordSales(MockMessageProducer.generateRandomMessage());
			
			if(mockSalesSession.getMessagesProcessed()%10 == 0) {
				showTotalSalesReport(mockSalesSession);
			}
			
			if (mockSalesSession.getMessagesProcessed()%50 ==0) {
				System.out.println();
				System.out.println("Pausing application.. No new messages being consumed.. generating adjustments report");
				showAdjustmentReport(mockSalesSession);
				pause();
			}
		}
	}

	private static void showAdjustmentReport(SalesSession mockSalesSession) {
		System.out.println();
		System.out.println("################ SHOWING ADJUSTMENTS REPORT ################");
		mockSalesSession.getAllAdjLogs().stream().forEach(adj -> {
			System.out.println(adj.getLogMessage());
		});
		System.out.println("############################################################");
	}

	private static void pause() {

		System.out.println();

		Scanner sc = new Scanner(System.in);
		try {
			while (true) {
				System.out.println("Type 'cont' to resume message processing and 'exit' to quit the application? ");
				String response = sc.nextLine();

				if ("exit".equals(response)) {
					sc.close();
					System.exit(0);
				}

				if ("cont".equals(response)) {
					sc.close();
					return;
				}
			}
		} catch (NoSuchElementException nsee) {
			System.err.println("Invalid input, stopping application...");
			sc.close();
			System.exit(1);
		}
	}

	private static void showTotalSalesReport(SalesSession mockSalesSession) {
		System.out.println();
		System.out.println("~~~~~~~~~~~~~~~~ SHOWING TOTAL SALES REPORT ~~~~~~~~~~~~~~~~");
		System.out.println("Total Messages Processed: " + mockSalesSession.getMessagesProcessed());
		for (ProductType type : ProductType.values()) {
			List<Double> valList = mockSalesSession.getAllSalesRecords().stream()
					.filter(salesRec -> type.equals(salesRec.getType())).map(SalesRecord::getValue)
					.collect(Collectors.toList());
			if (valList.isEmpty())
				continue;
			double totalVal = 0.0;
			for (double val : valList) {
				totalVal += val;
			}
			System.out.println("ProductType=" + type + ", TotalNumberOfSales=" + valList.size()
					+ ", TotalValueOfAllSales=" + DoubleFormatter.format(totalVal));
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
}
