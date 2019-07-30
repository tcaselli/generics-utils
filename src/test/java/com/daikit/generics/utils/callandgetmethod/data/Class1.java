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

public class Class1
{

	public Result1 test(String param1)
	{
		return new Result1();
	}

	public Result2 test(int param1)
	{
		return new Result2();
	}

	public ResultNotUsed test(String param1, String param2)
	{
		return new ResultNotUsed();
	}

	public Result3 test(int param1, String param2)
	{
		return new Result3();
	}

	public Result5 test(String param1, String param2, String... param3)
	{
		return new Result5();
	}

	public Result6 test(int param1, Object param2)
	{
		return new Result6();
	}

	public Result7 test(InterfaceParam1 param2)
	{
		return new Result7();
	}

	public Result8 test(InterfaceParam2 param2)
	{
		return new Result8();
	}

	public Result9 test(InterfaceParam3 param3)
	{
		return new Result9();
	}

	public Result10 test(ClassParam1 param2)
	{
		return new Result10();
	}

	public Result11 test(ClassParam2 param2)
	{
		return new Result11();
	}

	public Result12 test(ClassParam3 param3)
	{
		return new Result12();
	}

	public Result14 test(String param1, String param2, Integer... param3)
	{
		return new Result14();
	}

	public Result15 test(String param1, String param2, Object... param3)
	{
		return new Result15();
	}

	public Result16 test(String param1, String param2, InterfaceParam1... param3)
	{
		return new Result16();
	}

	public Result17 test(String param1, String param2, InterfaceParam2... param3)
	{
		return new Result17();
	}

	public Result18 test(String param1, String param2, ClassParam1... param3)
	{
		return new Result18();
	}

	public Result19 test(String param1, String param2, ClassParam2... param3)
	{
		return new Result19();
	}
}
