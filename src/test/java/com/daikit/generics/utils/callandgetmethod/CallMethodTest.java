package com.daikit.generics.utils.callandgetmethod;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.generics.utils.callandgetmethod.data.Class1;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result1;
import com.daikit.generics.utils.callandgetmethod.data.Results.Result5;

public class CallMethodTest {

	private final Class1 object = new Class1();

	@Test
	public void testCall()
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Assert.assertEquals(Result1.class, GenericsUtils.callMethod(object, "test", "param").getClass());
	}

	@Test
	public void testCallWithVarargsEmpty()
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Assert.assertEquals(Result5.class,
				GenericsUtils.callMethod(object, "test", "param", "param", new String[]{}).getClass());
	}

	@Test
	public void testCallWithVarargsOnly1()
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Assert.assertEquals(Result5.class,
				GenericsUtils.callMethod(object, "test", "param", "param", new String[]{"vararg1"}).getClass());
	}

	@Test
	public void testCallWithVarargs()
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Assert.assertEquals(Result5.class, GenericsUtils
				.callMethod(object, "test", "param", "param", new String[]{"vararg1", "vararg2"}).getClass());
	}

	@Test
	public void testCallWithParamTypes()
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Assert.assertEquals(Result1.class,
				GenericsUtils.callMethod(object, "test", new Class<?>[]{String.class}, "param").getClass());
	}

	@Test
	public void testCallWithParamTypesAndVarargsEmpty()
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Assert.assertEquals(Result5.class, GenericsUtils.callMethod(object, "test",
				new Class<?>[]{String.class, String.class, String[].class}, "param", "param").getClass());
	}

	@Test
	public void testCallWithParamTypesAndVarargsOnly1()
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Assert.assertEquals(Result5.class, GenericsUtils.callMethod(object, "test",
				new Class<?>[]{String.class, String.class, String[].class}, "param", "param", "vararg1").getClass());
	}

	@Test
	public void testCallWithParamTypesAndVarargs()
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Assert.assertEquals(Result5.class,
				GenericsUtils.callMethod(object, "test", new Class<?>[]{String.class, String.class, String[].class},
						"param", "param", "vararg1", "vararg2").getClass());
	}

}
