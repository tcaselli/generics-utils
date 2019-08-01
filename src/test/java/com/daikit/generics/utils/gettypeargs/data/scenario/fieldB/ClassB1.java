package com.daikit.generics.utils.gettypeargs.data.scenario.fieldB;

import java.util.List;

import com.daikit.generics.utils.gettypeargs.data.Type3;

@SuppressWarnings("unused")
public class ClassB1<GENERIC1, GENERIC2> {

	private List<GENERIC1> test1;

	private ClassB3<GENERIC1, GENERIC2, Type3> test2;

	private GENERIC2 test3;

}
