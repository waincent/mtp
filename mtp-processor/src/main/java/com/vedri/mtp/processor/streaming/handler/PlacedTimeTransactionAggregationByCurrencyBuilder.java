package com.vedri.mtp.processor.streaming.handler;

import com.vedri.mtp.processor.transaction.TableName;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.joda.time.DateTime;

import com.google.common.collect.Sets;
import com.vedri.mtp.core.transaction.Transaction;
import com.vedri.mtp.processor.transaction.TransactionAggregationByCurrency;

public class PlacedTimeTransactionAggregationByCurrencyBuilder
		extends TimeTransactionAggregationByCurrencyBuilderTemplate {

	public PlacedTimeTransactionAggregationByCurrencyBuilder(StreamBuilder<?, JavaDStream<Transaction>> prevBuilder,
			String keyspace) {
		super(prevBuilder, keyspace, TableName.PT_AGGREGATION_BY_CURRENCY);
	}

	@Override
	protected FlatMapFunction<Transaction, TransactionAggregationByCurrency> mapFunction() {
		return transaction -> {
			final DateTime time = transaction.getPlacedTime();
			return Sets.newHashSet(
					new TransactionAggregationByCurrency(transaction.getCurrencyFrom(),
							time.getYear(), time.getMonthOfYear(), time.getDayOfMonth(), time.getHourOfDay(),
							1, 0),
					new TransactionAggregationByCurrency(transaction.getCurrencyTo(),
							time.getYear(), time.getMonthOfYear(), time.getDayOfMonth(), time.getHourOfDay(),
							0, 1));
		};
	}
}
