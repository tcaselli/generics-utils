package com.daikit.generics.utils.callandgetmethod;

import org.junit.Assert;
import org.junit.Test;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.generics.utils.callandgetmethod.data.Class1;
import com.daikit.generics.utils.callandgetmethod.data.Class2;
import com.daikit.generics.utils.callandgetmethod.data.ClassParam1;
import com.daikit.generics.utils.callandgetmethod.data.ClassParam2;
import com.daikit.generics.utils.callandgetmethod.data.ClassParam3;
import com.daikit.generics.utils.callandgetmethod.data.ClassParam4;
import com.daikit.generics.utils.callandgetmethod.data.InterfaceParam1;
import com.daikit.generics.utils.callandgetmethod.data.InterfaceParam2;
import com.daikit.generics.utils.callandgetmethod.data.InterfaceParam3;
import com.daikit.generics.utils.callandgetmethod.data.InterfaceParam4;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result1;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result10;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result11;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result12;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result13;
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

public class GetMethodTest
{

	@Test
	public void testGet1Arg()
	{
		Assert.assertEquals(Result1.class, GenericsUtils.getMethod(Class1.class, "test", String.class).getReturnType());
	}

	@Test
	public void testGet1ArgPrimitive()
	{
		Assert.assertEquals(Result2.class, GenericsUtils.getMethod(Class1.class, "test", int.class).getReturnType());
	}

	@Test
	public void testGet2Args()
	{
		Assert.assertEquals(Result3.class, GenericsUtils.getMethod(Class1.class, "test", int.class, String.class).getReturnType());
	}

	@Test
	public void testGet3ArgsWithVarargs()
	{
		Assert.assertEquals(Result5.class, GenericsUtils.getMethod(Class1.class, "test", String.class, String.class, String[].class).getReturnType());
	}

	@Test
	public void testGet2ArgsWithInheritance()
	{
		Assert.assertEquals(Result6.class, GenericsUtils.getMethod(Class1.class, "test", int.class, Double.class).getReturnType());
	}

	@Test
	public void testGet1ArgsWithInterface()
	{
		Assert.assertEquals(Result7.class, GenericsUtils.getMethod(Class1.class, "test", InterfaceParam1.class).getReturnType());
	}

	@Test
	public void testGet1ArgsWithInterfaceAndInheritance()
	{
		Assert.assertEquals(Result8.class, GenericsUtils.getMethod(Class1.class, "test", InterfaceParam2.class).getReturnType());
	}

	@Test
	public void testGet1ArgsWithInterfaceAndInheritanceAndAmbiguity()
	{
		Assert.assertEquals(Result9.class, GenericsUtils.getMethod(Class1.class, "test", InterfaceParam4.class).getReturnType());
	}

	@Test
	public void testGet1ArgsWithClass()
	{
		Assert.assertEquals(Result10.class, GenericsUtils.getMethod(Class1.class, "test", ClassParam1.class).getReturnType());
	}

	@Test
	public void testGet1ArgsWithClassAndInheritance()
	{
		Assert.assertEquals(Result11.class, GenericsUtils.getMethod(Class1.class, "test", ClassParam2.class).getReturnType());
	}

	@Test
	public void testGet1ArgsWithClassAndInheritanceAndAmbiguity()
	{
		Assert.assertEquals(Result12.class, GenericsUtils.getMethod(Class1.class, "test", ClassParam4.class).getReturnType());
	}

	@Test
	public void testGet1ArgWithExtension()
	{
		Assert.assertEquals(Result13.class, GenericsUtils.getMethod(Class2.class, "test", String.class).getReturnType());
	}

	@Test
	public void testGet3ArgWithVarargsAndAmbiguity()
	{
		Assert.assertEquals(Result14.class, GenericsUtils.getMethod(Class1.class, "test", String.class, String.class, Integer[].class).getReturnType());
	}

	@Test
	public void testGet3ArgWithSuperclassAndVarargsAndAmbiguity()
	{
		Assert.assertEquals(Result15.class, GenericsUtils.getMethod(Class1.class, "test", String.class, String.class, Object[].class).getReturnType());
	}

	@Test
	public void testGet3ArgWithInterfaceAndVarargs()
	{
		Assert.assertEquals(Result16.class,
				GenericsUtils.getMethod(Class1.class, "test", String.class, String.class, InterfaceParam1[].class).getReturnType());
	}

	@Test
	public void testGet3ArgWithInterfaceAndVarargsAndAmbiguity()
	{
		Assert.assertEquals(Result17.class,
				GenericsUtils.getMethod(Class1.class, "test", String.class, String.class, InterfaceParam2[].class).getReturnType());
	}

	@Test
	public void testGet3ArgWithInterfaceAndVarargsAndAmbiguity2()
	{
		Assert.assertEquals(Result17.class,
				GenericsUtils.getMethod(Class1.class, "test", String.class, String.class, InterfaceParam3[].class).getReturnType());
	}

	@Test
	public void testGet3ArgWithCustomClassAndVarargs()
	{
		Assert.assertEquals(Result18.class, GenericsUtils.getMethod(Class1.class, "test", String.class, String.class, ClassParam1[].class).getReturnType());
	}

	@Test
	public void testGet3ArgWithCustomClassAndVarargsAndAmbiguity()
	{
		Assert.assertEquals(Result19.class, GenericsUtils.getMethod(Class1.class, "test", String.class, String.class, ClassParam2[].class).getReturnType());
	}

	@Test
	public void testGet3ArgWithCustomClassAndVarargsAndAmbiguity2()
	{
		Assert.assertEquals(Result19.class, GenericsUtils.getMethod(Class1.class, "test", String.class, String.class, ClassParam3[].class).getReturnType());
	}

}
