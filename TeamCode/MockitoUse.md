# Mockito
- import static org.mockito.Mockito.*;

## General Syntax
- mock()/@Mock: create mock
  - optionally specify how it should behave via Answer/MockSettings
  - when()/given() to specify how a mock should behave
  - If the provided answers don't fit your needs, write one yourself extending Answer interface
- spy()/@Spy: partial mocking, real methods invoked but can still be verified and stubbed
- @InjectMocks: automatically inject mocks/spies fields annotated with @Spy or @Mock
- verify(): to check methods were called with given arguments
  - can use flexible argument matching, for example any expression via the any()
  - or capture what arguments were called using @Captor instead

## Remember
- Do not mock types you don't own
- Don't mock value objects 
- Don't mock everything
- Show love with your tests!
- Stubs are FAKES, Spy's use the real methods, Mocks implement the methods 

## Mocking
~~~ java
 //mock creation
 List mockedList = mock(List.class);

 //using mock object
 mockedList.add("one");
 mockedList.clear();

 //verification
 verify(mockedList).add("one");
 verify(mockedList).clear();
~~~
- Once created, a mock will remember all interactions, which means you can selectively verify 
whatever interactions you're interested in
- A mock is a fake class that can be examined after the test is finished for its interactions
with the class under test (Focus: behavior)

## Stubbing
- A stub is a fake class that comes with pre-programmed values, it's injected into the class under 
test to give you absolute control over what's being tested as input. (Focus: outcomes)
~~~ java
 //You can mock concrete classes, not just interfaces
 LinkedList mockedList = mock(LinkedList.class);

 //stubbing
 when(mockedList.get(0)).thenReturn("first");
 when(mockedList.get(1)).thenThrow(new RuntimeException());

 //following prints "first"
 System.out.println(mockedList.get(0));

 //following throws runtime exception
 System.out.println(mockedList.get(1));

 //following prints "null" because get(999) was not stubbed
 System.out.println(mockedList.get(999));

 //Although it is possible to verify a stubbed invocation, usually it's just redundant
 //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
 //If your code doesn't care what get(0) returns, then it should not be stubbed.
 verify(mockedList).get(0);
~~~
- By default, for all methods that return a value, a mock will either return null, a primitive/wrapper value,
or an empty collection
- Stubbing can be overriden: for example, stubbing can go to fixture setup, but the test methods can override it.
Overriding stubbing is usually a sign of too much stubbing
- Once stubbed, the method will always return a stubbed value, regardless of how many times it is called
- Last stubbing is more important - when you stubbed the same method with the same arguments many times.
Aka: the order of stubbing matters, but it's usually only meaningful when stubbing exactly the same method calls
or sometimes when argument matchers are used

## Argument Matchers
- Mockito verifies argument values in natural java style, using equals(). Argument matchers allow additional flexibility.
~~~ java
 //stubbing using built-in anyInt() argument matcher
 when(mockedList.get(anyInt())).thenReturn("element");

 //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
 when(mockedList.contains(argThat(isValid()))).thenReturn(true);

 //following prints "element"
 System.out.println(mockedList.get(999));

 //you can also verify using an argument matcher
 verify(mockedList).get(anyInt());

 //argument matchers can also be written as Java 8 Lambdas
 verify(mockedList).add(argThat(someString -> someString.length() > 5));
~~~
- Argument matchers allow flexible verification or stubbing, you can make your own matchers (see documentation for examples)
- **Warning** If you are using argument matchers, **all** arguments have to be provided by matchers
~~~ java
 verify(mock).someMethod(anyInt(), anyString(), eq("third argument"));
 //above is correct - eq() is also an argument matcher

 verify(mock).someMethod(anyInt(), anyString(), "third argument");
 //above is incorrect - exception will be thrown because third argument is given without an argument matcher.
~~~
- Matcher methods like any(), eq() do not return matchers. Internally, they record a matcher a stack and return a dummy
value (usually null), this happens to to static type safety. This means you cannot use any(), eq() methods outside of verified/stubbed methods

## Verifying exact number of invocations/ at least x / never
~~~ java
//using mock
 mockedList.add("once");

 mockedList.add("twice");
 mockedList.add("twice");

 mockedList.add("three times");
 mockedList.add("three times");
 mockedList.add("three times");

 //following two verifications work exactly the same - times(1) is used by default
 verify(mockedList).add("once");
 verify(mockedList, times(1)).add("once");

 //exact number of invocations verification
 verify(mockedList, times(2)).add("twice");
 verify(mockedList, times(3)).add("three times");

 //verification using never(). never() is an alias to times(0)
 verify(mockedList, never()).add("never happened");

 //verification using atLeast()/atMost()
 verify(mockedList, atMostOnce()).add("once");
 verify(mockedList, atLeastOnce()).add("three times");
 verify(mockedList, atLeast(2)).add("three times");
 verify(mockedList, atMost(5)).add("three times");
~~~
- times(1) is default, so it can be omitted

## Stubbing void methods with exceptions
~~~ java
 doThrow(new RuntimeException()).when(mockedList).clear();

 //following throws RuntimeException:
 mockedList.clear();
~~~

## Verification in Order
~~~ java
// A. Single mock whose methods must be invoked in a particular order
List singleMock = mock(List.class);

//using a single mock
singleMock.add("was added first");
singleMock.add("was added second");

//create an inOrder verifier for a single mock
InOrder inOrder = inOrder(singleMock);

//following will make sure that add is first called with "was added first", then with "was added second"
inOrder.verify(singleMock).add("was added first");
inOrder.verify(singleMock).add("was added second");

// B. Multiple mocks that must be used in a particular order
List firstMock = mock(List.class);
List secondMock = mock(List.class);

//using mocks
firstMock.add("was called first");
secondMock.add("was called second");

//create inOrder object passing any mocks that need to be verified in order
InOrder inOrder = inOrder(firstMock, secondMock);

//following will make sure that firstMock was called before secondMock
inOrder.verify(firstMock).add("was called first");
inOrder.verify(secondMock).add("was called second");

// Oh, and A + B can be mixed together at will
~~~
- verification in order is flexible, you don't have to verify all interactions one-by-one,
but only those you are interested in testing in order
- You can also create an InOrder object passing only the mocks that are relevant for in-order verification

## Making Sure Interaction(s) Never Happened On Mock
~~~ java
 //using mocks - only mockOne is interacted
 mockOne.add("one");

 //ordinary verification
 verify(mockOne).add("one");

 //verify that method was never called on a mock
 verify(mockOne, never()).add("two");
~~~

## Finding Redundant Invocations
~~~ java
 //using mocks
 mockedList.add("one");
 mockedList.add("two");

 verify(mockedList).add("one");

 //following verification will fail
 verifyNoMoreInteractions(mockedList);
~~~
- **Warning**: verifyNoMoreInteractions() is not recommended to use in every test method; use
only when relevant. Abusing it leads to over-specified, less maintainable tests
- never() is more explicit and communicates intent well

## Shorthand For Mocks Creation - @Mock Annotation
- Minimizes repetitive mock creation code; makes test class more readable; makes verification error easier
because the field name is used to identify the mock
~~~ java
public class ArticleManagerTest {

       @Mock private ArticleCalculator calculator;
       @Mock private ArticleDatabase database;
       @Mock private UserProvider userProvider;

       private ArticleManager manager;

       @org.junit.jupiter.api.Test
       void testSomethingInJunit5(@Mock ArticleDatabase database) {
       // **IMPORTANT** This needs to be somewhere in the base class or a test runner
       MockitoAnnotations.openMocks(testClass)
~~~
- You can use built-in runner: MockitoJUnitRunner or a rule: MockitoRule

## Stubbing Consecutive Calls (Iterator-Style Stubbing)
- Sometimes a need to stub with different return value/exception for the same method call
~~~ java
when(mock.someMethod("some arg"))
   .thenThrow(new RuntimeException())
   .thenReturn("foo");

 //First call: throws runtime exception:
 mock.someMethod("some arg");

 //Second call: prints "foo"
 System.out.println(mock.someMethod("some arg"));

 //Any consecutive call: prints "foo" as well (last stubbing wins).
 System.out.println(mock.someMethod("some arg"));
 
 // Or shorter way:
 when(mock.someMethod("some arg"))
   .thenReturn("one", "two", "three");
   
 // If instead of chaining .thenReturn() calls, multiple stubbing with the same matchers
 // or arguments is used, then each stubbing will override the previous one
 //All mock.someMethod("some arg") calls will return "two"
 when(mock.someMethod("some arg"))
   .thenReturn("one")
 when(mock.someMethod("some arg"))
   .thenReturn("two")
~~~

## doReturn()|doThrow()|doAnswer()|doNothing()|doCallRealMethod() Family of Methods
- Stubbing void methods requires a different approach from when(Object) 
because compiler does not like void methods inside brackets
- Use doThrow() when you want to stub a void method with an exception:
~~~ java
 doThrow(new RunTimeException()).when(mockedList).clear();
 // following throws RuntimeException
 mockedList.clear();
~~~
- You can use doThrow(), doAnswer(), doNothing(), doReturn(), and doCallRealMethod() in place
of the corresponding call with when(), for any method. It is necessary when you:
  - stub void methods
  - stub methods on spy objects
  - stub same method more than once, to change behavior of mock in middle of a test

## Spying on Real Objects
- You can create spies of real objects, when you use the spy then the REAL methods are called (unless method was stubbed)
- Real spies should be used CAREFULLY and OCCASIONALLY, for example, when dealing with legacy code
- Spying on real objects is like partial mocking
~~~ java
 List list = new LinkedList();
 List spy = spy(list);

 //optionally, you can stub out some methods:
 when(spy.size()).thenReturn(100);

 //using the spy calls *real* methods
 spy.add("one");
 spy.add("two");

 //prints "one" - the first element of a list
 System.out.println(spy.get(0));

 //size() method was stubbed - 100 is printed
 System.out.println(spy.size());

 //optionally, you can verify
 verify(spy).add("one");
 verify(spy).add("two");
~~~
- Important gotcha on spying real objects
1) Sometimes it's impossible or impractical to use when(Object) for stubbing spies. 
Therefore, consider doReturn()|Answer|Throw() family of methods for stubbing
~~~ java
 List list = new LinkedList();
 List spy = spy(list);

 //Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
 when(spy.get(0)).thenReturn("foo");

 //You have to use doReturn() for stubbing
 doReturn("foo").when(spy).get(0);
~~~
2) Mockito **does not** delegate calls to the passed real instance, instead it creates a copy.
Therefore, if you keep the real instance and interact with it, don't expect the spied to be aware 
of those interaction and their effect on real instance state. (Un-stubbed method called on spy but not real
instance, you won't see any effects on the real instance)
3) Watch out for final methods. Mockito doesn't mock final methods so the bottom line is: when you 
spy on teal objects and try to stub a final method, you've got trouble. You won't be able to verify these methods too.

## Capturing Arguments For Further Assertions
- Mockito verifies argument values by using equals(). Also the recommended way of matching arguments
because it makes tests clean and simple. In some situations though, it is useful to assert on certain arguments after verification
~~~ java
 ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
 verify(mock).doSomething(argument.capture());
 assertEquals("John", argument.getValue().getName());
~~~
- It is recommended to use ArgumentCaptor with verification but NOT with stubbing. 
Using ArgumentCaptor with stubbing may decrease test readability
- ArgumentCaptor may be a better fit that ArgumentMatcher if custom argument matcher is not likely to be reused
or you just need to assert on argument values to complete verification
- Custom argument matchers via ArgumentMatcher are usually better for stubbing

## Real Partial Mocks
- Use sparingly, it is NOT Object Oriented; use only when dealing with code that can't be changed easily,
like 3rd party interfaces, legacy code, etc.
~~~ java
 //you can create partial mock with spy() method:
 List list = spy(new LinkedList());

 //you can enable partial mock capabilities selectively on mocks:
 Foo mock = mock(Foo.class);
 //Be sure the real implementation is 'safe'.
 //If real implementation throws exceptions or depends on specific state of the object then you're in trouble.
 when(mock.someMethod()).thenCallRealMethod();
~~~

## Annotations: @Captor, @Spy, @InjectMocks
- @Captor: simplifies creation of ArgumentCaptor - useful when argument to 
capture is a nasty generic class and you want to avoid compiler warnings
- @Spy: you can use it instead of spy(Object)
- @InjectMocks: injects mock or spy fields into tested object automatically
- Note that @InjectMocks can also be used in combination with @Spy, it means that
Mockito will inject mocks into the partial mock under test (another good reason why you should use partial mocks as a last resort)
- Processed on MockitoAnnotations.openMocks(Object), you can use in the built in runner: MockitoJUnitRunner or rule: MockitoRule

## Spying or Mocking Abstract Classes
- You can spy on abstract classes, but note that spy overuse hints at design smells
- You can use the constructor when creating an instance of the mock; this is useful for abstract classes
because the user is not required to provide an instance of the abstract class
~~~ java
 //convenience API, new overloaded spy() method:
 SomeAbstract spy = spy(SomeAbstract.class);

 //Mocking abstract methods, spying default methods of an interface (only available since 2.7.13)
 Function<Foo, Bar> function = spy(Function.class);

 //Robust API, via settings builder:
 OtherAbstract spy = mock(OtherAbstract.class, withSettings()
    .useConstructor().defaultAnswer(CALLS_REAL_METHODS));

 //Mocking an abstract class with constructor arguments (only available since 2.7.14)
 SomeAbstract spy = mock(SomeAbstract.class, withSettings()
   .useConstructor("arg1", 123).defaultAnswer(CALLS_REAL_METHODS));

 //Mocking a non-static inner abstract class:
 InnerAbstract spy = mock(InnerAbstract.class, withSettings()
    .useConstructor().outerInstance(outerInstance).defaultAnswer(CALLS_REAL_METHODS));
~~~

## Mockito JUnit Rule
- Annotate JUnit test class with @ExtendWith(MockitoExtension.class)
- OR Invoking MockitoAnnotations.openMocks(Object) in @Before
- OR @Rule public MockitoRule mockito = MockitoJUnit.rule();

## Custom Verification Failure Message
- Allows specifying a custom message to be printed if verification fails
~~~ java
 // will print a custom message on verification failure
 verify(mock, description("This will print on failure")).someMethod();

 // will work with any verification mode
 verify(mock, times(2).description("someMethod should be called twice")).someMethod();
~~~

## Java 8 Lambda Matcher Support
- You can use lambda expressions with ArgumentMatcher to reduce dependency on ArgumentCaptor
- If you need to verify that the input to a function call on a mock is correct, then you would
normally use the ArgumentCaptor to find the operands used and then do assertions on them (good for complex examples)
- For simple examples, you can write a lambda to express the match. The argument to your function, when 
used in conjunction with argThat, will be passed to the ArgumentMatcher as a strongly typed object, so you can 
do pretty much anything with it
~~~ java
 // verify a list only had strings of a certain length added to it
 // note - this will only compile under Java 8
 verify(list, times(2)).add(argThat(string -> string.length() < 5));

 // Java 7 equivalent - not as neat
 verify(list, times(2)).add(argThat(new ArgumentMatcher<String>(){
     public boolean matches(String arg) {
         return arg.length() < 5;
     }
 }));

 // more complex Java 8 example - where you can specify complex verification behaviour functionally
 verify(target, times(1)).receiveComplexObject(argThat(obj -> obj.getSubObject().get(0).equals("expected")));

 // this can also be used when defining the behaviour of a mock under different inputs
 // in this case if the input list was fewer than 3 items the mock returns null
 when(mock.someMethod(argThat(list -> list.size()<3))).thenReturn(null);
~~~

## Verification with Assertions
- To validate arguments during verification, instead of capturing them with ArgumentCaptor, you can use
ArgumentMatchers.assertArg(Consumer)
~~~ java
 verify(serviceMock).doStuff(assertArg(param -> 
    assertThat(param.getField1()).isEqualTo("foo");
    assertThat(param.getField2()).isEqualTo("bar");
   }));
                                
~~~