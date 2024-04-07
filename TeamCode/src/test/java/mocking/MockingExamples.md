# Mockito Examples (All of these Pass)
~~~ java
@ExtendWith(MockitoExtension.class)
public class MockingExample {

    @Test
    public void whenNotUseMockAnnotation_thenCorrect() {
        // Creates an ArrayList manually without using @Mock annotation
        List mockList = mock(ArrayList.class);

        mockList.add("one");
        Mockito.verify(mockList).add("one");
        assertEquals(0, mockList.size());

        when(mockList.size()).thenReturn(100);
        assertEquals(100, mockList.size());
    }

    @Mock
    List<String> mockedList;

    @Test
    public void whenUseMockAnnotation_thenMockIsInjected() {
        // The same, we'll inject the mock using @Mock annotation
        mockedList.add("one");
        verify(mockedList).add("one");
        assertEquals(0, mockedList.size());

        when(mockedList.size()).thenReturn(100);
        assertEquals(100, mockedList.size());
    }

    @Test
    public void whenNotUseSpyAnnotation_thenCorrect() {
        // Creates a spy of List without @Spy annotation
        List<String> spyList = spy(new ArrayList<String>());

        spyList.add("one");
        spyList.add("two");

        verify(spyList).add("one");
        verify(spyList).add("two");

        assertEquals(2, spyList.size());

        doReturn(100).when(spyList).size();
        assertEquals(100, spyList.size());
    }

    @Spy
    List<String> spiedList = new ArrayList<String>();

    @Test
    public void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
        // Use the real method spiedList.add() to add elements to the spiedList
        // Stubbed method spiedList.size() to return 100 instead of 2 using Mockito.doReturn()
        spiedList.add("one");
        spiedList.add("two");

        verify(spiedList).add("one");
        verify(spiedList).add("two");

        assertEquals(2, spiedList.size());

        doReturn(100).when(spiedList).size();
        assertEquals(100, spiedList.size());
    }

    @Test
    public void whenNotUseCaptorAnnotation_thenCorrect() {
        List mockList = mock(List.class);
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);

        mockList.add("one");
        verify(mockList).add(arg.capture());

        assertEquals("one", arg.getValue());
    }

    @Captor
    ArgumentCaptor argCaptor;

    @Test
    public void whenUseCaptorAnnotation_thenTheSame() {
        // Test becomes simpler and more readable when we remove config logic
        mockedList.add("one");
        verify(mockedList).add((String) argCaptor.capture());

        assertEquals("one", argCaptor.getValue());
    }

    // @InjectMocks annotation injects mocks into a concrete class that has a mock as an instance variable
    // Inject a Mock into a spy using the constructor

}
~~~
