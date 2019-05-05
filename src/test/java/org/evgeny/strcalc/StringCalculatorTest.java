package org.evgeny.strcalc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringCalculatorTest {

    private StringCalculator calculator;

    @Before
    public void before(){
        calculator = new StringCalculator();
    }

    @Test
    public void testAddEmptyString(){
        Assert.assertEquals(0, calculator.add(""));
    }

    @Test
    public void testAddOneNumber(){
        Assert.assertEquals(1, calculator.add("1"));
    }

    @Test
    public void testAddTwoNumbers(){
        Assert.assertEquals(3, calculator.add("1,2"));
    }

    @Test
    public void testAddNewLine(){
        Assert.assertEquals(6, calculator.add("1\n2,3"));
    }

    @Test
    public void testAddWithDelimiter(){
        Assert.assertEquals(3, calculator.add("//;\n1;2"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddWithNegatives(){
        calculator.add("-5,-10");
    }

    @Test
    public void testAddWithMoreThan1000(){
        Assert.assertEquals(5, calculator.add("5,1001"));
    }

    @Test
    public void testAddWithLongerDelimiters(){
        Assert.assertEquals(33, calculator.add("//***\n1***2***30"));
    }

    @Test
    public void testAddWithMultipleDelimiters(){
        Assert.assertEquals(6, calculator.add("//*|%\n1*2%3"));
    }

    @Test(expected=NumberFormatException.class)
    public void testAddUnparseableInput(){
        calculator.add("5,abc");
    }
}
