# Generics utils

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

## Documentation

Generics utils consists in a unique utility class for dealing with java generics. Here are its capabilities :

### Get actual generic types a class has used to implement a given interface

Example, super simple but it works with any hierarchy of classes provided that ClassA1 implements InterfaceA1 somehow :
```java
public class Type1 {}
public class Type2 {}
public interface InterfaceA1<GENERIC1, GENERIC2> {}
public class ClassA1 implements InterfaceA1<Type1, Type2> {}

GenericsUtils.getTypeArguments(ClassA1.class, InterfaceA1.class) 
// Will return List[Type1.class, Type2.class]
```

### Get actual generic types a class has used to extend a given super class

Example, super simple but it works with any hierarchy of classes provided that ClassA1 extends ClassA2 somehow :
```java
public class Type1 {}
public class Type2 {}
public class ClassA1<GENERIC1, GENERIC2> {}
public class ClassA2 extends ClassA1<Type1, Type2> {}

GenericsUtils.getTypeArguments(ClassA1.class, ClassA2.class) 
// Will return List[Type1.class, Type2.class]
```

### Get actual type for a class field typed with declaring class generic

Example :

```java
public class Type1 {}
public class ClassA1<GENERIC1> { private GENERIC1 test; }
public class ClassA2 extends ClassA1<Type1> {}

GenericsUtils.getFieldType(ClassA1.class, ClassA1.class.getDeclaredField("test")) 
// Will return Object.class
GenericsUtils.getFieldType(ClassA2.class, ClassA1.class.getDeclaredField("test")) 
// Will return Type1.class
```

### Get actual generic types for a class generic field

Example :

```java
public class Type1 {}
public class Type2 {}
public class ClassA1 { private ClassA3<Type1, Type2> test; }
public class ClassA2 extends ClassA1 {}
public class ClassA3<GENERIC1, GENERIC2> {}

GenericsUtils.getFieldTypeArguments(ClassA2.class, ClassA1.class.getDeclaredField("test")) 
// Will return List[Type1.class, Type2.class]
```

### Get a method on a given object providing its name and argument types

Example, super simple but it works with any kind of hierrarchy, with varargs and overriding :

```java
public class Class1 { public void test(String param1, String... param2) }

GenericsUtils.getMethod(Class1.class, "test", String.class, String[].class) 
// Will return the method "test"
```

### Call a method on a given object providing its name and argument types

Example :

```java
public class Class1 { public void test(String param1, String... param2) }

GenericsUtils.callMethod(object, "test", "param", new String[] { "vararg1", "vararg2" }) 
// Will call the test method with arguments ("param", "vararg1", "vararg2")
```

### Get type(s) extending class(es)

Example :

```java
public class Class1<GENERIC> {}
public class Class2 extends Class1<Type1> {}
GenericsUtils.getTypeClass(Class2.class.getGenericSuperclass())
// Will return Class1
```

## Where can I get the latest release?

You can check latest version and pull it from the [central Maven repositories](https://mvnrepository.com/artifact/com.daikit/generics-utils):

With maven

```xml
<dependency>
    <groupId>com.daikit</groupId>
    <artifactId>generics-utils</artifactId>
    <version>x.x</version>
</dependency>
```

Or with gradle 

```gradle
compile group: 'com.daikit', name: 'generics-utils', version: 'x.x'
```

## Contributing

We accept Pull Requests via GitHub. There are some guidelines which will make applying PRs easier for us:
+ No spaces :) Please use tabs for indentation.
+ Respect the code style.
+ Create minimal diffs - disable on save actions like reformat source code or organize imports. If you feel the source code should be reformatted create a separate PR for this change.
+ Provide JUnit tests for your changes and make sure your changes don't break any existing tests by running ```mvn clean test```.

## License

This code is under the [Apache Licence v2](https://www.apache.org/licenses/LICENSE-2.0).