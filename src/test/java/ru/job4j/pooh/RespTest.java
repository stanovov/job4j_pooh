package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RespTest {
    @Test
    public void whenGetText() {
        Resp resp = new Resp("TEST", 200);
        String expected = "TEST";
        String result = resp.text();
        assertThat(result, is(expected));
    }

    @Test
    public void whenGetStatus() {
        Resp resp = new Resp("TEST", 200);
        int expected = 200;
        int result = resp.status();
        assertThat(result, is(expected));
    }
}