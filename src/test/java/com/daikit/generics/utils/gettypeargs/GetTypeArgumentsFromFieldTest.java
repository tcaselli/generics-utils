package com.daikit.generics.utils.gettypeargs;

import java.lang.reflect.Type;

import org.junit.Assert;
import org.junit.Test;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.generics.utils.gettypeargs.data.Type1;
import com.daikit.generics.utils.gettypeargs.data.Type2;
import com.daikit.generics.utils.gettypeargs.data.Type3;
import com.daikit.generics.utils.gettypeargs.data.scenario.fieldA.ClassA1;
import com.daikit.generics.utils.gettypeargs.data.scenario.fieldA.ClassA2;
import com.daikit.generics.utils.gettypeargs.data.scenario.fieldB.ClassB1;
import com.daikit.generics.utils.gettypeargs.data.scenario.fieldB.ClassB2;

public class GetTypeArgumentsFromFieldTest
{

	@Test
	public void testGetTypeArgumentsFromFieldA1() throws NoSuchFieldException, SecurityException
	{
		Assert.assertArrayEquals(new Type[] { Type1.class },
				GenericsUtils.getFieldTypeArguments(ClassA1.class, ClassA1.class.getDeclaredField("test1")).toArray());
	}

	@Test
	public void testGetTypeArgumentsFromFieldA1_2() throws NoSuchFieldException, SecurityException
	{
		Assert.assertArrayEquals(new Type[] { Type1.class },
				GenericsUtils.getFieldTypeArguments(ClassA2.class, ClassA1.class.getDeclaredField("test1")).toArray());
	}

	@Test
	public void testGetTypeArgumentsFromFieldA1OneResult() throws NoSuchFieldException, SecurityException
	{
		Assert.assertEquals(Type1.class, GenericsUtils.getFieldTypeArgument(ClassA1.class, ClassA1.class.getDeclaredField("test1")));
	}

	@Test
	public void testGetTypeArgumentsFromFieldA2() throws NoSuchFieldException, SecurityException
	{
		Assert.assertArrayEquals(new Type[] { Type1.class, Type2.class },
				GenericsUtils.getFieldTypeArguments(ClassA1.class, ClassA1.class.getDeclaredField("test2")).toArray());
	}

	@Test
	public void testGetTypeArgumentsFromFieldB1() throws NoSuchFieldException, SecurityException
	{
		Assert.assertArrayEquals(new Type[] { Object.class },
				GenericsUtils.getFieldTypeArguments(ClassB1.class, ClassB1.class.getDeclaredField("test1")).toArray());
	}

	@Test
	public void testGetTypeArgumentsFromFieldB1_2() throws NoSuchFieldException, SecurityException
	{
		Assert.assertArrayEquals(new Type[] { Object.class, Object.class, Type3.class },
				GenericsUtils.getFieldTypeArguments(ClassB1.class, ClassB1.class.getDeclaredField("test2")).toArray());
	}

	@Test
	public void testGetTypeArgumentsFromFieldB2() throws NoSuchFieldException, SecurityException
	{
		Assert.assertArrayEquals(new Type[] { Type1.class },
				GenericsUtils.getFieldTypeArguments(ClassB2.class, ClassB1.class.getDeclaredField("test1")).toArray());
	}

	@Test
	public void testGetTypeArgumentsFromFieldB2_2() throws NoSuchFieldException, SecurityException
	{
		Assert.assertArrayEquals(new Type[] { Type1.class, Type2.class, Type3.class },
				GenericsUtils.getFieldTypeArguments(ClassB2.class, ClassB1.class.getDeclaredField("test2")).toArray());
	}

	@Test
	public void testGetTypeArgumentsFromFieldB2_3() throws NoSuchFieldException, SecurityException
	{
		Assert.assertEquals(Object.class, GenericsUtils.getFieldType(ClassB1.class, ClassB1.class.getDeclaredField("test3")));
	}

	@Test
	public void testGetTypeArgumentsFromFieldB2_4() throws NoSuchFieldException, SecurityException
	{
		Assert.assertEquals(Type2.class, GenericsUtils.getFieldType(ClassB2.class, ClassB1.class.getDeclaredField("test3")));
	}
}
