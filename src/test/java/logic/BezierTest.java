package logic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import model.Bezier;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author mloda
 */
public class BezierTest {

    @Test
    public void binomialTheorem4() {
        Bezier bezier = new Bezier();
        assertEquals(1, bezier.binomialTheorem(4, 0));
        assertEquals(4, bezier.binomialTheorem(4, 1));
        assertEquals(6, bezier.binomialTheorem(4, 2));
        assertEquals(4, bezier.binomialTheorem(4, 3));
        assertEquals(1, bezier.binomialTheorem(4, 4));
    }

    @Test
    public void factorialTest() {
        Bezier bezier = new Bezier();
        assertEquals(6, bezier.factorial(3));
        assertEquals(24, bezier.factorial(4));
        assertEquals(120, bezier.factorial(5));
        assertEquals(720, bezier.factorial(6));
    }
}
