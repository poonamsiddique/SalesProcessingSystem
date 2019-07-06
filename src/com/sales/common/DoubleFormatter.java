package com.sales.common;

import java.text.DecimalFormat;

public class DoubleFormatter {
	public static double format(double val) {
		DecimalFormat df = new DecimalFormat("#0.00");
		return Double.valueOf(df.format(val));
	}
}
