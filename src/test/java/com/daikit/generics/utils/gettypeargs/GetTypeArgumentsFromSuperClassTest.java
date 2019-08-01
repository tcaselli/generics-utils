package com.daikit.generics.utils.gettypeargs;

import org.junit.Assert;
import org.junit.Test;

import com.daikit.generics.utils.GenericsUtils;
import com.daikit.generics.utils.gettypeargs.data.Type1;
import com.daikit.generics.utils.gettypeargs.data.Type2;
import com.daikit.generics.utils.gettypeargs.data.Type3;
import com.daikit.generics.utils.gettypeargs.data.scenario.classA.ClassA1;
import com.daikit.generics.utils.gettypeargs.data.scenario.classA.ClassA2;
import com.daikit.generics.utils.gettypeargs.data.scenario.classB.ClassB1;
import com.daikit.generics.utils.gettypeargs.data.scenario.classB.ClassB3;
import com.daikit.generics.utils.gettypeargs.data.scenario.classC.ClassC1;
import com.daikit.generics.utils.gettypeargs.data.scenario.classC.ClassC3;

public class GetTypeArgumentsFromSuperClassTest {

	@Test
	public void testGetTypeArgumentsFromSuperclassA() {
		Assert.assertArrayEquals(new Object[]{Type1.class},
				GenericsUtils.getTypeArguments(ClassA2.class, ClassA1.class).toArray());
	}

	@Test
	public void testGetTypeArgumentsFromSuperclassB() {
		Assert.assertArrayEquals(new Object[]{Type1.class},
				GenericsUtils.getTypeArguments(ClassB3.class, ClassB1.class).toArray());
	}

	@Test
	public void testGetTypeArgumentsFromSuperclassC() {
		Assert.assertArrayEquals(new Object[]{Type1.class, Type2.class, Type3.class},
				GenericsUtils.getTypeArguments(ClassC3.class, ClassC1.class).toArray());
	}
}
