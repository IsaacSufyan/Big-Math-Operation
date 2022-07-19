package com.isaac.sufyan.pk.example.stream;


import com.isaac.sufyan.pk.big.BigFloat;
import com.isaac.sufyan.pk.big.BigFloat.Context;
import com.isaac.sufyan.pk.big.stream.BigFloatStream;

public class BigFloatStreamExample {

	public static void main(String[] args) {
		Context context = BigFloat.context(20);

		System.out.println("Range [0, 10) step 1 (using BigDecimal as input parameters)");
		BigFloatStream.range(context.valueOf(0), context.valueOf(10), context.valueOf(1))
			.forEach(System.out::println);
		System.out.println();

		System.out.println("Range [0, 12] step 3 (using long as input parameters)");
		BigFloatStream.rangeClosed(0, 12, 3, context)
			.forEach(System.out::println);
		System.out.println();

		System.out.println("Range [0, 12] step 3 (using double as input parameters)");
		BigFloatStream.rangeClosed(0.0, 12.0, 3.0, context)
			.forEach(System.out::println);
		System.out.println();

		System.out.println("Range [10, 0) step -1");
		BigFloatStream.range(10, 0, -1, context)
			.forEach(System.out::println);
		System.out.println();

		System.out.println("Range [10, 0] step -1");
		BigFloatStream.rangeClosed(10, 0, -1, context)
			.forEach(System.out::println);
		System.out.println();

		System.out.println("Range [0, 10] step 1 parallel");
		BigFloatStream.rangeClosed(0, 10, 1, context)
			.parallel()
			.forEach(System.out::println);
		System.out.println();
	}
}
