package com.apress.springbootrecipes.demo;

import com.apress.springbootrecipes.demo.calculator.Calculator;
import com.apress.springbootrecipes.demo.calculator.Operation;
import com.apress.springbootrecipes.demo.calculator.operation.Addition;
import com.apress.springbootrecipes.demo.calculator.operation.Multiplication;
import com.apress.springbootrecipes.demo.calculator.operation.Subtraction;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalculatorApplication.class)
public class CalculatorApplicationTests {
    @Rule
    public OutputCapture capture = new OutputCapture();

    @Autowired
    private Calculator calculator;

    @MockBean(name = "division")
    private Operation mockOperation;

    @Test
    public void doingMultiplicationShouldSucceed() {
        calculator.calculate(12, 13, '*');
        capture.expect(Matchers.containsString("12 * 13 = 156"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doingDivisionShouldFall() {
        calculator.calculate(12, 13, '/');
    }

    @Test
    public void calculatorShouldHave3Operation() {
        Object operations = ReflectionTestUtils.getField(calculator, "operations");
        assertThat((Collection) operations).hasSize(4);
    }

    @Test
    public void mockDivision() {
        when(mockOperation.handles('/')).thenReturn(true);
        when(mockOperation.apply(14, 7)).thenReturn(2);

        calculator.calculate(14, 7, '/');
        capture.expect(Matchers.containsString("14 / 7 = 2"));
    }

}
