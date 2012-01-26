package org.samplr.server.utility;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.samplr.server.utility.Ranker;

import com.google.common.collect.Lists;

public class RankerTest {
  private Ranker matcher;

  @Before
  public void setUp() throws Exception {
    matcher = new Ranker("foo");
  }

  @Test
  public void chooseBestChoice_SingleWordEquivalent() {
    assertEquals(Arrays.asList("foo"), matcher.rank("foo", Arrays.asList("foo")));
  }
  
  @Test
  public void chooseBestChoice_SingleWordEquivalent_DifferentCase() {
    assertEquals(Arrays.asList("Foo"), matcher.rank("foo", Arrays.asList("Foo")));
  }
  
  @Test
  public void chooseBestChoice_TwoWords_DifferentCases() {
    assertEquals(Arrays.asList("foo", "Foo"), matcher.rank("foo", Arrays.asList("foo", "Foo")));
  }
  
  @Test
  public void chooseBestChoice_TwoWords_DifferentCases_Swapped() {
    assertEquals(Arrays.asList("foo", "Foo"), matcher.rank("foo", Arrays.asList("Foo", "foo")));
  }
  
  @Test
  public void chooseBestChoice_TwoWords_OneEquivalentOneGarbage() {
    assertEquals(Arrays.asList("foo", "bar"), matcher.rank("foo", Arrays.asList("bar", "foo")));
  }
  
  @Test
  public void chooseBestChoice_TwoWords_OneSimilarOneGarbage() {
    assertEquals(Arrays.asList("Foo", "bar"), matcher.rank("foo", Arrays.asList("bar", "Foo")));
  }
  
  @Test
  public void chooseBestChoice_ThreeWords_OneEquivalentOneSimilarOneGarbage() {
    assertEquals(Arrays.asList("foo", "Foo", "bar"), matcher.rank("foo", Arrays.asList("bar", "Foo", "foo")));
  }
  
  @Test
  public void chooseBestChoice_SingleWord_Whitespace() {
    assertEquals(Arrays.asList("foo "), matcher.rank("foo", Arrays.asList("foo ")));
  }
  
  @Test
  public void chooseBestChoice_TwoWords_OneEquivalentOneWhitespace() {
    assertEquals(Arrays.asList("foo", "foo "), matcher.rank("foo", Arrays.asList("foo ", "foo")));
  }
  
  @Test
  public void chooseBestChoice_TwoWords_OneWhitespaceOneGarbage() {
    assertEquals(Arrays.asList("foo ", "bar"), matcher.rank("foo", Arrays.asList("bar", "foo ")));
  }

  @Test
  public void chooseBestChoice_A() {
    matcher = new Ranker("Hi, mom!");
    
    assertEquals(Arrays.asList("Hi, mom!", "Hi,  mom!"), matcher.rank("Hi, mom!", Arrays.asList("Hi,  mom!", "Hi, mom!")));
  }
  
  @Test
  public void chooseBestChoice_C() {
    matcher = new Ranker("Die you motherfucker!!!");
    
    assertEquals(Arrays.asList("Die you, motherfucker!!"), matcher.rank("", Arrays.asList("Die you, motherfucker!!", "Die you, motherfucker!!!!", "Die you,, motherfucker.!!", "Die you, motherfucker!!!!!!!", "Die you!!")));
  }
  
  public void chooseBestChoice_B() {
    matcher = new Ranker("Hi, mom!");
    
    assertEquals(Arrays.asList("Hi, mom!", "Hi,  mom!", "Hi, Fred!"), matcher.rank("Hi, mom!", Arrays.asList("Hi, Fred!", "Hi,  mom!", "Hi, mom!")));
  }
  
  @Test
  public void chooseBestChoice_D() {
    final int count = 1000;
    
    final List<String> foo = new ArrayList<String>(count);
    for (int i = 0; i < count; i++) {
      foo.add(String.valueOf(i));
    }
    matcher = new Ranker("-2"); 
    
    final List<String> bar = matcher.rank("", foo);
    
    System.out.println(Lists.partition(bar, 10));
  }
}
