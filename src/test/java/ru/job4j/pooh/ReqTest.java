package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ReqTest {
    @Test
    public void whenCreatePostQueueReq() {
        String content = "POST /queue/something HTTP/1.1" + System.lineSeparator()
                       + System.lineSeparator()
                       + System.lineSeparator()
                       + System.lineSeparator()
                       + System.lineSeparator()
                       + System.lineSeparator()
                       + System.lineSeparator()
                       + "some text";
        Req req = Req.of(content);
        assertThat(req.method(), is("POST"));
        assertThat(req.mode(), is("queue"));
        assertThat(req.text(), is("some text"));
        assertThat(req.name(), is("something"));
        assertThat(req.id(), is(0));
    }

    @Test
    public void whenCreatePostTopicReq() {
        String content = "POST /topic/something HTTP/1.1" + System.lineSeparator()
                       + System.lineSeparator()
                       + System.lineSeparator()
                       + System.lineSeparator()
                       + System.lineSeparator()
                       + System.lineSeparator()
                       + System.lineSeparator()
                       + "some text";
        Req req = Req.of(content);
        assertThat(req.method(), is("POST"));
        assertThat(req.mode(), is("topic"));
        assertThat(req.text(), is("some text"));
        assertThat(req.name(), is("something"));
        assertThat(req.id(), is(0));
    }

    @Test
    public void whenCreateGetQueueReq() {
        String content = "GET /queue/something HTTP/1.1";
        Req req = Req.of(content);
        assertThat(req.method(), is("GET"));
        assertThat(req.mode(), is("queue"));
        assertThat(req.text(), is(""));
        assertThat(req.name(), is("something"));
        assertThat(req.id(), is(0));
    }

    @Test
    public void whenCreateGetTopicReq() {
        String content = "GET /topic/something/1 HTTP/1.1";
        Req req = Req.of(content);
        assertThat(req.method(), is("GET"));
        assertThat(req.mode(), is("topic"));
        assertThat(req.text(), is(""));
        assertThat(req.name(), is("something"));
        assertThat(req.id(), is(1));
    }

    @Test
    public void whenEquals2Req() {
        String content = "GET /topic/something/1 HTTP/1.1";
        Req req1 = Req.of(content);
        Req req2 = Req.of(content);
        assertEquals(req1, req2);
    }

    @Test
    public void whenHashcode2Req() {
        String content = "GET /topic/something/1 HTTP/1.1";
        Req req1 = Req.of(content);
        Req req2 = Req.of(content);
        assertEquals(req1.hashCode(), req2.hashCode());
    }
}