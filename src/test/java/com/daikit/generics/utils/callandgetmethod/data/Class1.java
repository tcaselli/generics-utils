package com.daikit.generics.utils.callandgetmethod.data;

import com.daikit.generics.utils.callandgetmethod.data.Results.Result1;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result10;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result11;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result12;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result14;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result15;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result16;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result17;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result18;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result19;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result2;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result3;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result5;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result6;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result7;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result8;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result9;
import com.daikit.generics.utils.callandgetmethod.data.Results.ResultNotUsed;

public class Class1 {

	public Result1 test(final String param1) {
		return new Result1();
	}

	public Result2 test(final int param1) {
		return new Result2();
	}

	public ResultNotUsed test(final String param1, final String param2) {
		return new ResultNotUsed();
	}

	public Result3 test(final int param1, final String param2) {
		return new Result3();
	}

	public Result5 test(final String param1, final String param2, final String... param3) {
		return new Result5();
	}

	public Result6 test(final int param1, final Object param2) {
		return new Result6();
	}

	public Result7 test(final InterfaceParam1 param2) {
		return new Result7();
	}

	public Result8 test(final InterfaceParam2 param2) {
		return new Result8();
	}

	public Result9 test(final InterfaceParam3 param3) {
		return new Result9();
	}

	public Result10 test(final ClassParam1 param2) {
		return new Result10();
	}

	public Result11 test(final ClassParam2 param2) {
		return new Result11();
	}

	public Result12 test(final ClassParam3 param3) {
		return new Result12();
	}

	public Result14 test(final String param1, final String param2, final Integer... param3) {
		return new Result14();
	}

	public Result15 test(final String param1, final String param2, final Object... param3) {
		return new Result15();
	}

	public Result16 test(final String param1, final String param2, final InterfaceParam1... param3) {
		return new Result16();
	}

	public Result17 test(final String param1, final String param2, final InterfaceParam2... param3) {
		return new Result17();
	}

	public Result18 test(final String param1, final String param2, final ClassParam1... param3) {
		return new Result18();
	}

	public Result19 test(final String param1, final String param2, final ClassParam2... param3) {
		return new Result19();
	}
}
