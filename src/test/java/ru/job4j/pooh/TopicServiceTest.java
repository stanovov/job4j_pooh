package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TopicServiceTest {
    @Test
    public void whenProcessPostReq() {
        String content = "POST /topic/something HTTP/1.1" + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "some text";
        Req req = Req.of(content);
        TopicService topic = new TopicService();
        Resp expected = new Resp("Topic created", 201);
        Resp result = topic.process(req);
        assertThat(result, is(expected));
    }

    @Test
    public void whenProcessPostReqThenProcess2SubGetReqAndSuccessfulResp() {
        String postContent = "POST /queue/something HTTP/1.1" + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "some text";
        String getContent1 = "GET /topic/something/1 HTTP/1.1";
        String getContent2 = "GET /topic/something/2 HTTP/1.1";
        Req postReq = Req.of(postContent);
        Req getReq1 = Req.of(getContent1);
        Req getReq2 = Req.of(getContent2);
        TopicService topic = new TopicService();
        topic.process(postReq);
        Resp expected = new Resp("some text", 200);
        assertThat(topic.process(getReq1), is(expected));
        assertThat(topic.process(getReq2), is(expected));
    }

    @Test
    public void whenProcessPostReqThenProcess1Sub2GetReqAndNotSuccessfulResp() {
        String postContent = "POST /queue/something HTTP/1.1" + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "some text";
        String getContent = "GET /topic/something/1 HTTP/1.1";
        Req postReq = Req.of(postContent);
        Req getReq = Req.of(getContent);
        TopicService topic = new TopicService();
        topic.process(postReq);
        topic.process(getReq);
        Resp expected = new Resp("Message not found", 409);
        assertThat(topic.process(getReq), is(expected));
    }

    @Test
    public void whenProcessGetReqAndNotSuccessfulResp() {
        String getContent = "GET /topic/something/1 HTTP/1.1";
        Req getReq = Req.of(getContent);
        TopicService topic = new TopicService();
        Resp expected = new Resp("Message not found", 409);
        assertThat(topic.process(getReq), is(expected));
    }
}