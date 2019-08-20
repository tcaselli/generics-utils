package com.daikit.generics.utils.gettypeclass;

import org.junit.Assert;
import org.junit.Test;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.generics.utils.gettypeclass.data.Class1;
import com.daikit.generics.utils.gettypeclass.data.Class2;

public class GetTypeClassTest {

	@Test
	public void testTypeClass() {
		Assert.assertEquals(Class1.class, GenericsUtils.getTypeClass(Class2.class.getGenericSuperclass()));
	}

}
