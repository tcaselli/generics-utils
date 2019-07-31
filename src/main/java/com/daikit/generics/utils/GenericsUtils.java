package com.daikit.generics.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Utility methods for retrieving actual implementing generic types from generic class and fields
 *
 * @author tcaselli
 */
public class GenericsUtils
{

	/**
	 * Cache for {@link Class#getDeclaredMethods()}, allowing for fast resolution.
	 */
	private static final Map<Class<?>, Method[]> declaredMethodsCache = new HashMap<>(256);

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// METHODS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	/**
	 * Get type arguments of a generic base type "baseType" in the context of extending/implementing class or interface "actualType"
	 *
	 * @param actualType the extending/implementing class or interface
	 * @param baseType   the interface or super class where are defined the generic types from which we are looking for actual types
	 * @return a generic type list of interface
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Type> getTypeArguments(final Class<?> actualType, final Class<?> baseType)
	{
		return baseType.isInterface() ? getTypeArgumentsFromInterface(actualType, baseType) : getTypeArgumentsFromSuperclass((Class) actualType, baseType);
	}

	private static List<Type> getTypeArgumentsFromInterface(final Class<?> actualType, final Class<?> baseType)
	{
		final Map<Type, Type> resolvedTypes = new HashMap<>();
		Type type = actualType;
		while (!getRawClass(type).equals(Object.class))
		{
			final Class<?> rawType = getRawClass(type);

			// Store type parameter (from class) to actual type for later mapping
			if (type instanceof ParameterizedType)
			{
				final Type[] actualClassTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
				final TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
				for (int i = 0; i < actualClassTypeArguments.length; i++)
				{
					resolvedTypes.put(typeParameters[i], actualClassTypeArguments[i]);
				}
			}

			// Get generic interfaces for current type
			final Type[] genericInterfaces = rawType.getGenericInterfaces();
			final List<Type> allSuperInterfaces = new ArrayList<>();
			Collections.addAll(allSuperInterfaces, genericInterfaces);
			for (final Type genericInterface : genericInterfaces)
			{
				Type superInterface = getSuperInterface(genericInterface);
				while (superInterface != null)
				{
					allSuperInterfaces.add(superInterface);
					superInterface = getSuperInterface(superInterface);
				}
			}

			// Store type parameter (from implemented interfaces) to actual type for later mapping
			for (final Type superInterfaceType : allSuperInterfaces)
			{
				if (superInterfaceType instanceof ParameterizedType)
				{
					final Type[] actualClassTypeArguments = ((ParameterizedType) superInterfaceType).getActualTypeArguments();
					final Class<?> rawInterfaceype = getRawClass(superInterfaceType);
					final TypeVariable<?>[] typeParameters = rawInterfaceype.getTypeParameters();
					for (int i = 0; i < actualClassTypeArguments.length; i++)
					{
						resolvedTypes.put(typeParameters[i], actualClassTypeArguments[i]);
					}
				}
			}

			// Find searched interface in interfaces for current type
			for (final Type genericInterface : allSuperInterfaces)
			{
				if (genericInterface instanceof ParameterizedType)
				{
					final Class<?> rawInterfaceType = getRawClass(genericInterface);
					if (baseType.equals(rawInterfaceType))
					{
						// Returned list
						final List<Type> typeArguments = new ArrayList<>();

						final Type[] actualTypeArguments = ((ParameterizedType) genericInterface).getActualTypeArguments();

						for (Type actualTypeArgument : actualTypeArguments)
						{
							if (actualTypeArgument instanceof TypeVariable)
							{
								while (resolvedTypes.containsKey(actualTypeArgument))
								{
									actualTypeArgument = resolvedTypes.get(actualTypeArgument);
								}
							}
							typeArguments.add(actualTypeArgument);
						}
						return typeArguments;
					}
				}
			}
			type = rawType.getGenericSuperclass();
		}
		return null;
	}

	private static <T> List<Type> getTypeArgumentsFromSuperclass(final Class<? extends T> actualType, final Class<T> baseType)
	{
		final TypeAndResolvedTypes resolvedTypes = getTypeAndResolvedTypes(actualType, baseType);
		// for each actual type argument provided to baseClass, determine (if possible) the raw class for that type argument.
		final Type[] genericTypes = getActualTypeArguments(resolvedTypes.type);
		return getTypeArguments(resolvedTypes.resolvedTypes, resolvedTypes.bounds, genericTypes);
	}

	/**
	 * Get direct generic types for given class
	 *
	 * @param clazz the clazz
	 * @param       <T> The input class actual type
	 * @return a list of the raw classes for the actual type arguments.
	 */
	public static <T> List<Type> getTypeArguments(final Class<T> clazz)
	{
		return getTypeArguments(clazz, clazz);
	}

	/**
	 * Get the underlying class for a type, or null if the type is a variable type.
	 *
	 * @param type the type
	 * @return the underlying class
	 */
	@SuppressWarnings("rawtypes")
	public static final Class<?> getRawClass(final Type type)
	{
		if (type instanceof Class)
		{
			return (Class) type;
		}
		else if (type instanceof ParameterizedType)
		{
			return getRawClass(((ParameterizedType) type).getRawType());
		}
		else if (type instanceof GenericArrayType)
		{
			final Type componentType = ((GenericArrayType) type).getGenericComponentType();
			final Class<?> componentClass = getRawClass(componentType);
			if (componentClass != null)
			{
				return Array.newInstance(componentClass, 0).getClass();
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * Get the underlying classes for a list of types, (if type is a variable type then its class will be null).
	 *
	 * @param types the type list
	 * @return the underlying class
	 */
	public static List<Class<?>> getRawClasses(final List<Type> types)
	{
		return types.stream().map(type -> getRawClass(type)).collect(Collectors.toList());
	}

	/**
	 * Get generic type arguments for the given field type. The field must have a parameterized type, these parameters can be generics from class.
	 *
	 * @param contextConcreteClass the actual concrete class holding the field. This is useful to retrieve generic types for super class field.
	 * @param field                the {@link Field}
	 * @return a {@link List} of the generic types of given field type
	 */
	public static List<Type> getFieldTypeArguments(final Class<?> contextConcreteClass, final Field field)
	{
		final TypeAndResolvedTypes resolvedTypes = getTypeAndResolvedTypes(contextConcreteClass, field.getDeclaringClass());
		final ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
		return getTypeArguments(resolvedTypes.resolvedTypes, resolvedTypes.bounds, parameterizedType.getActualTypeArguments());
	}

	/**
	 * Get generic type arguments for the given field type. The field must have a parameterized type, these parameters must not be generic.
	 *
	 * @param field the {@link Field}
	 * @return a {@link List} of the generic types of given field type
	 */
	public static List<Type> getFieldTypeArguments(final Field field)
	{
		final ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
		final List<Type> clazzList = new ArrayList<>();
		for (final Type type : parameterizedType.getActualTypeArguments())
		{
			clazzList.add(type);
		}
		return clazzList;
	}

	/**
	 * Get first generic type argument for given field type. The field must have a parameterized type with only 1 parameter, this parameter can be generics from
	 * class.
	 *
	 * @param contextConcreteClass the actual concrete class holding the field. This is useful to retrieve generic types for super class field.
	 * @param field                the {@link Field}
	 * @return the first generic type of given field type
	 */
	public static Type getFieldTypeArgument(final Class<?> contextConcreteClass, final Field field)
	{
		final ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
		return parameterizedType.getActualTypeArguments().length == 0 ? null : getFieldTypeArguments(contextConcreteClass, field).get(0);
	}

	/**
	 * Get first generic type argument for given field type. The field must have a parameterized type with only 1 parameter, this parameter cant be generics
	 * from class.
	 *
	 * @param field the {@link Field}
	 * @return the first generic type of given field type
	 */
	public static Class<?> getFieldTypeArgument(final Field field)
	{
		final ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
		return parameterizedType.getActualTypeArguments().length == 0 ? null : getRawClass(parameterizedType.getActualTypeArguments()[0]);
	}

	/**
	 * Get first generic type argument for given field type. The field must have a parameterized type with only 1 parameter, this parameter cant be generics
	 * from class.
	 *
	 * @param contextConcreteClass the actual concrete class holding the field. This is useful to retrieve generic types for super class field.
	 * @param field                the {@link Field}
	 * @return the first generic type of given field type
	 */
	public static Type getFieldType(final Class<?> contextConcreteClass, final Field field)
	{
		if (field.getGenericType() instanceof TypeVariable)
		{
			final TypeVariable<?> typeVariable = (TypeVariable<?>) field.getGenericType();
			final TypeAndResolvedTypes resolvedTypes = getTypeAndResolvedTypes(contextConcreteClass, field.getDeclaringClass());
			final Entry<Type, Type> genericResolvedType = resolvedTypes.resolvedTypes.entrySet().stream()
					.filter(entry -> typeVariable.getName().equals(entry.getKey().getTypeName())).findFirst().orElse(null);
			// getTypeArgumentsFromGenerics(typeVariable.get, field);
			return genericResolvedType == null || genericResolvedType.getValue() instanceof TypeVariable ? resolvedTypes.bounds.get(typeVariable.getName())
					: genericResolvedType.getValue();
		}
		else
		{
			return field.getGenericType();
		}
	}

	/**
	 * This is a helper method to call a method on an Object with the given parameters.
	 *
	 * @param object     the object holding the method and on which the method must be called
	 * @param methodName the method name
	 * @param args       arguments for the method
	 * @return an Object
	 * @throws NoSuchMethodException     if there is no such method in object
	 * @throws IllegalArgumentException  if arguments for the method are not correct
	 * @throws IllegalAccessException    if the method is not accessible
	 * @throws InvocationTargetException if an error happened during method call
	 */
	public static Object callMethod(final Object object, final String methodName, final Object... args)
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		final Class<?>[] paramTypes = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++)
		{
			if (args[i] == null)
			{
				throw new NullPointerException(
						"No arguments may be null when using callMethod(Object, String, Object...) because every argument is needed in order to determine the parameter types. Use callMethod(Object, String, Class<?>[], Object...) instead and specify parameter types.");
			}
			paramTypes[i] = args[i].getClass();
		}
		return callMethod(object, methodName, paramTypes, args);
	}

	/**
	 * This is a helper method to call a method on an Object with the given parameters.
	 *
	 * @param object     the object holding the method and on which the method must be called
	 * @param methodName the method name
	 * @param argsTypes  types for method arguments
	 * @param args       arguments for the method
	 * @return an Object
	 * @throws NoSuchMethodException     if there is no such method in object
	 * @throws IllegalArgumentException  if arguments for the method are not correct
	 * @throws IllegalAccessException    if the method is not accessible
	 * @throws InvocationTargetException if an error happened during method call
	 */
	public static Object callMethod(final Object object, final String methodName, final Class<?>[] argsTypes, final Object... args)
			throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		final Method method = getMethod(object.getClass(), methodName, argsTypes);
		if (method == null)
		{
			throw new NoSuchMethodException("Method: " + methodName + " not found on Class: " + object.getClass());
		}

		if (method.isVarArgs())
		{
			// put variable arguments into array as last parameter
			final Object[] allargs = new Object[method.getParameterTypes().length];
			for (int i = 0; i < method.getParameterTypes().length - 1; i++)
			{
				allargs[i] = args[i];
			}

			Object[] vargs;
			if (args.length == method.getParameterTypes().length && args[args.length - 1].getClass().isArray()
					&& args[args.length - 1].getClass().equals(method.getParameterTypes()[method.getParameterTypes().length - 1]))
			{
				vargs = (Object[]) args[args.length - 1];
			}
			else
			{
				vargs = (Object[]) Array.newInstance(method.getParameterTypes()[method.getParameterTypes().length - 1].getComponentType(),
						args.length - method.getParameterTypes().length + 1);
				for (int i = 0; i < args.length - method.getParameterTypes().length + 1; i++)
				{
					vargs[i] = args[method.getParameterTypes().length - 1 + i];
				}
			}
			allargs[method.getParameterTypes().length - 1] = vargs;

			return method.invoke(object, allargs);
		}
		else
		{
			return method.invoke(object, args);
		}
	}

	/**
	 * Get a method on a class with given name and parameters.
	 *
	 * @param clazz      the class holding the method
	 * @param methodName the method name
	 * @param argsTypes  types for method arguments
	 * @return a {@link Method}
	 */
	public static Method getMethod(final Class<?> clazz, final String methodName, final Class<?>... argsTypes)
	{
		final List<Method> candidates = new ArrayList<>();

		Class<?> searchType = clazz;
		while (searchType != null)
		{
			final Method[] methods = searchType.isInterface() ? searchType.getMethods() : getDeclaredMethods(searchType);
			outer: for (final Method method : methods)
			{
				if (method.getName().equals(methodName))
				{
					final Class<?>[] methodParamTypes = method.getParameterTypes();
					if (argsTypes.length == methodParamTypes.length || method.isVarArgs() && argsTypes.length >= methodParamTypes.length - 1)
					{
						// method has correct name and # of parameters
						if (method.isVarArgs())
						{
							for (int i = 0; i < methodParamTypes.length - 1; i++)
							{
								if (argsTypes[i] != null && !methodParamTypes[i].isAssignableFrom(argsTypes[i]))
								{
									continue outer;
								}
							}
							if (methodParamTypes.length == argsTypes.length + 1)
							{
								// no param is specified for the optional vararg
								// spot
							}
							else if (methodParamTypes.length == argsTypes.length
									&& methodParamTypes[argsTypes.length - 1].isAssignableFrom(argsTypes[argsTypes.length - 1]))
							{
								// an array is specified for the last param
							}
							else
							{
								final Class<?> varClass = methodParamTypes[methodParamTypes.length - 1].getComponentType();
								for (int i = methodParamTypes.length - 1; i < argsTypes.length; i++)
								{
									if (argsTypes[i] != null && !varClass.isAssignableFrom(argsTypes[i]))
									{
										continue outer;
									}
								}
							}
						}
						else
						{
							for (int i = 0; i < methodParamTypes.length; i++)
							{
								if (argsTypes[i] != null && !methodParamTypes[i].isAssignableFrom(argsTypes[i]))
								{
									continue outer;
								}
							}
						}
						candidates.add(method);
					}
				}
			}

			// Do not search in super classes if there is a candidate in sub class.
			// This may be a problem but increases performances
			searchType = candidates.isEmpty() ? null : searchType.getSuperclass();
		}

		if (candidates.size() == 0)
		{
			return null;
		}
		else if (candidates.size() == 1)
		{
			return candidates.get(0);
		}
		else
		{
			// There are several possible methods. Choose the most specific.

			// Throw away any var-args options.
			// Non var-args methods always beat var-args methods and we're going
			// to say that if we have two var-args
			// methods, we cannot choose between the two.
			final Iterator<Method> itr = candidates.iterator();
			while (itr.hasNext())
			{
				final Method m = itr.next();
				if (m.isVarArgs())
				{
					// the exception is if an array is actually specified as the
					// last parameter
					if (m.getParameterTypes().length != argsTypes.length
							|| !m.getParameterTypes()[argsTypes.length - 1].isAssignableFrom(argsTypes[argsTypes.length - 1]))
					{
						itr.remove();
					}
				}
			}

			// If there are no candidates left, that means we had only var-args
			// methods, which we can't choose
			// between.
			if (candidates.size() == 0)
			{
				return null;
			}

			// Here we have several candidates because of type inheritance for parameters
			// For example

			Method a = candidates.get(0);
			boolean ambiguous = false;

			// For each methods and then for each parameters we will compute score
			for (int j = 1; j < candidates.size(); j++)
			{
				final Method b = candidates.get(j);

				final Class<?>[] aTypes = a.getParameterTypes();
				final Class<?>[] bTypes = b.getParameterTypes();

				int aScore = 0, bScore = 0;
				// increment score if distance is greater for a given parameter
				for (int i = 0; i < aTypes.length; i++)
				{
					if (aTypes[i] != null)
					{
						final int distA = getDistance(aTypes[i], argsTypes[i]);
						final int distB = getDistance(bTypes[i], argsTypes[i]);
						if (distA > distB)
						{
							bScore++;
						}
						else if (distA < distB)
						{
							aScore++;
						}
						else if (distA == 1000)
						{
							// both are interfaces
							// if one interface extends the other, that
							// interface is lower in the hierarchy (more
							// specific) and wins
							if (!aTypes[i].equals(bTypes[i]))
							{
								if (aTypes[i].isAssignableFrom(bTypes[i]))
								{
									bScore++;
								}
								else if (bTypes[i].isAssignableFrom(aTypes[i]))
								{
									aScore++;
								}
							}
						}
					}
				}

				// lower score wins
				if (aScore == bScore)
				{
					ambiguous = true;
				}
				else if (bScore > aScore)
				{
					a = b; // b wins
					ambiguous = false;
				}
			}

			if (ambiguous)
			{
				// Returns the one in the class the closest as possible to input clazz
				Method c = candidates.get(0);

				// For each methods and then for each parameters we will compute score
				for (int j = 1; j < candidates.size(); j++)
				{
					final Method d = candidates.get(j);
					if (!c.getDeclaringClass().equals(d.getDeclaringClass())
							&& (d.getDeclaringClass().equals(clazz) || c.getDeclaringClass().isAssignableFrom(d.getDeclaringClass())))
					{
						c = d;
					}
				}

//				// For example in spring beans , methods from parents are added to proxied bean instance before methods from children.
//				// So returning the last one will give the right method.
//				return candidates.get(candidates.size() - 1);

				return c;
			}

			return a;
		}
	}

	/**
	 * Get methods annotated with given annotation within given type class
	 *
	 * @param type       the class on which annotated methods have to be retrieved
	 * @param annotation the annotation method
	 * @param recursive  whether to look for annotated method in super classes recursively
	 * @return a {@link List} of {@link Method}
	 */
	public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation, final boolean recursive)
	{
		final List<Method> methods = new ArrayList<>();
		Class<?> clazz = type;
		// need to iterated thought hierarchy in order to retrieve methods from above the current instance
		loop: while (!Object.class.equals(clazz))
		{
			// iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
			final List<Method> allMethods = Arrays.asList(clazz.getDeclaredMethods());
			for (final Method method : allMethods)
			{
				final Annotation annotInstance = method.getAnnotation(annotation);
				if (annotInstance != null)
				{
					methods.add(method);
				}
			}
			// move to the upper class in the hierarchy in search for more methods
			clazz = clazz.getSuperclass();
			if (!recursive)
			{
				break loop;
			}
		}
		return methods;
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
	// PRIVATE UTILS
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

	private static Type getSuperInterface(final Type genericInterface)
	{
		final Type[] genericSuperInterfaces = (genericInterface instanceof ParameterizedType ? getRawClass(genericInterface) : (Class<?>) genericInterface)
				.getGenericInterfaces();
		return genericSuperInterfaces.length == 0 ? null : genericSuperInterfaces[0];
	}

	private static List<Type> getTypeArguments(final Map<Type, Type> genericTypeMappings, final Map<String, Type> bounds, final Type[] genericTypes)
	{
		final List<Type> typeArguments = new ArrayList<>();
		// resolve types by chasing up type variables.
		if (genericTypes != null)
		{
			for (Type baseType : genericTypes)
			{
				while (genericTypeMappings.containsKey(baseType))
				{
					baseType = genericTypeMappings.get(baseType);
				}
				if (baseType instanceof TypeVariable)
				{
					baseType = bounds.get(((TypeVariable<?>) baseType).getName());
				}
				typeArguments.add(baseType);
			}
		}
		return typeArguments;
	}

	@SuppressWarnings("rawtypes")
	private static TypeAndResolvedTypes getTypeAndResolvedTypes(final Class<?> childClass, final Class<?> baseClass)
	{
		final TypeAndResolvedTypes holder = new TypeAndResolvedTypes();
		holder.type = childClass;

		boolean stop = baseClass.equals(childClass);
		// start walking up the inheritance hierarchy until we hit baseClass
		loop: while (holder.type != null)
		{
			if (holder.type instanceof Class)
			{
				if (!stop)
				{
					// there is no useful information for us in raw types, so just
					// keep going.
					holder.type = ((Class) holder.type).getGenericSuperclass();
				}
			}
			else
			{
				final ParameterizedType parameterizedType = (ParameterizedType) holder.type;
				final Class<?> rawType = (Class<?>) parameterizedType.getRawType();

				final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
				final TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
				for (int i = 0; i < actualTypeArguments.length; i++)
				{
					holder.resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
				}

				if (!stop && !rawType.equals(baseClass))
				{
					holder.type = rawType.getGenericSuperclass();
				}
			}
			if (stop)
			{
				final Class<?> rawType = holder.type instanceof Class ? (Class<?>) holder.type : (Class<?>) ((ParameterizedType) holder.type).getRawType();
				for (final TypeVariable typeVariable : rawType.getTypeParameters())
				{
					holder.bounds.put(typeVariable.getName(), typeVariable.getBounds()[0]);
				}
				break loop;
			}
			if (baseClass.equals(getRawClass(holder.type)))
			{
				stop = true;
			}
		}
		return holder;
	}

	private static class TypeAndResolvedTypes
	{
		protected Type type;
		protected Map<Type, Type> resolvedTypes = new HashMap<>();
		protected Map<String, Type> bounds = new HashMap<>();

	}

	/**
	 * Get name of generci type arguments for given type class.
	 *
	 * @param type the type class
	 * @return For example for class X&lt;TYPE, TYPE2&gt; this will return [TYPE, TYPE2]
	 */
	@SuppressWarnings("rawtypes")
	private static Type[] getActualTypeArguments(final Type type)
	{
		Type[] actualTypeArguments = null;
		if (type instanceof Class)
		{
			actualTypeArguments = ((Class) type).getTypeParameters();
		}
		else if (type != null)
		{
			actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
		}
		return actualTypeArguments;
	}

	/**
	 * Greater dist is worse:
	 * <ol>
	 * <li>superClass = Object loses to all
	 * <li>If klass is not an interface, superClass is interface loses to all other classes
	 * <li>Closest inheritance wins
	 * </ol>
	 */
	private static int getDistance(Class<?> superClass, Class<?> clazz)
	{
		if (clazz.isArray())
		{
			if (superClass.isArray())
			{
				superClass = superClass.getComponentType();
				clazz = clazz.getComponentType();
			}
			else
			{
				// superClass must be Object. An array fitting into an Object
				// must be more general than an array fitting into an Object[]
				// array.
				return 3000;
			}
		}

		if (superClass.equals(clazz))
		{
			return 0;
		}
		if (superClass.equals(Object.class))
		{
			return 2000; // specifying Object is always the most general
		}
		if (superClass.isInterface())
		{
			return 1000;
		}

		int dist = 0;
		while (true)
		{
			dist++;
			clazz = clazz.getSuperclass();
			if (superClass.equals(clazz))
			{
				return dist;
			}
		}
	}

	/**
	 * This method retrieves {@link Class#getDeclaredMethods()} from a local cache in order to avoid the JVM's SecurityManager check and defensive array
	 * copying.
	 */
	private static Method[] getDeclaredMethods(final Class<?> clazz)
	{
		Method[] result = declaredMethodsCache.get(clazz);
		if (result == null)
		{
			result = clazz.getDeclaredMethods();
			declaredMethodsCache.put(clazz, result);
		}
		return result;
	}
}
