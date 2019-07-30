package com.daikit.generics.utils.getrawclass;

import org.junit.Assert;
import org.junit.Test;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.generics.utils.getrawclass.data.Class1;
import com.daikit.generics.utils.getrawclass.data.Class2;

public class GetRawClassTest
{

	@Test
	public void testGetRawClass()
	{
		Assert.assertEquals(Class1.class, GenericsUtils.getRawClass(Class2.class.getGenericSuperclass()));
	}

}
