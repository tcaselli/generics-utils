package com.daikit.generics.utils.gettypeargs;

import org.junit.Assert;
import org.junit.Test;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.generics.utils.gettypeargs.data.Type1;
import com.daikit.generics.utils.gettypeargs.data.Type2;
import com.daikit.generics.utils.gettypeargs.data.Type3;
import com.daikit.generics.utils.gettypeargs.data.scenario.interfaceA.ClassA1;
import com.daikit.generics.utils.gettypeargs.data.scenario.interfaceA.InterfaceA1;
import com.daikit.generics.utils.gettypeargs.data.scenario.interfaceB.ClassB2;
import com.daikit.generics.utils.gettypeargs.data.scenario.interfaceB.InterfaceB1;
import com.daikit.generics.utils.gettypeargs.data.scenario.interfaceC.ClassC2;
import com.daikit.generics.utils.gettypeargs.data.scenario.interfaceC.InterfaceC1;
import com.daikit.generics.utils.gettypeargs.data.scenario.interfaceD.ClassD2;
import com.daikit.generics.utils.gettypeargs.data.scenario.interfaceD.InterfaceD1;
import com.daikit.generics.utils.gettypeargs.data.scenario.interfaceE.ClassE2;
import com.daikit.generics.utils.gettypeargs.data.scenario.interfaceE.InterfaceE1;

public class GetTypeArgumentsFromInterfaceTest
{

	@Test
	public void testGetTypeArgumentsFromInterfaceA()
	{
		Assert.assertArrayEquals(new Object[] { Type1.class }, GenericsUtils.getTypeArguments(ClassA1.class, InterfaceA1.class).toArray());
	}

	@Test
	public void testGetTypeArgumentsFromInterfaceB()
	{
		Assert.assertArrayEquals(new Object[] { Type1.class }, GenericsUtils.getTypeArguments(ClassB2.class, InterfaceB1.class).toArray());
	}

	@Test
	public void testGetTypeArgumentsFromInterfaceC()
	{
		Assert.assertArrayEquals(new Object[] { Type1.class }, GenericsUtils.getTypeArguments(ClassC2.class, InterfaceC1.class).toArray());
	}

	@Test
	public void testGetTypeArgumentsFromInterfaceD()
	{
		Assert.assertArrayEquals(new Object[] { Type1.class }, GenericsUtils.getTypeArguments(ClassD2.class, InterfaceD1.class).toArray());
	}

	@Test
	public void testGetTypeArgumentsFromInterfaceE()
	{
		Assert.assertArrayEquals(new Object[] { Type1.class, Type2.class, Type3.class },
				GenericsUtils.getTypeArguments(ClassE2.class, InterfaceE1.class).toArray());
	}

}
