package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class QueueServiceTest {
    @Test
    public void whenProcessPostReq() {
        String content = "POST /queue/something HTTP/1.1" + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + "some text";
        Req req = Req.of(content);
        QueueService queue = new QueueService();
        Resp expected = new Resp("Message created", 201);
        Resp result = queue.process(req);
        assertThat(result, is(expected));
    }

    @Test
    public void whenProcessPostReqThenProcessGetReqAndSuccessfulResp() {
        String postContent = "POST /queue/something HTTP/1.1" + System.lineSeparator()
                           + System.lineSeparator()
                           + System.lineSeparator()
                           + System.lineSeparator()
                           + System.lineSeparator()
                           + System.lineSeparator()
                           + System.lineSeparator()
                           + "some text";
        String getContent = "GET /queue/something HTTP/1.1";
        Req postReq = Req.of(postContent);
        Req getReq = Req.of(getContent);
        QueueService queue = new QueueService();
        queue.process(postReq);
        Resp expected = new Resp("some text", 200);
        Resp result = queue.process(getReq);
        assertThat(result, is(expected));
    }

    @Test
    public void whenProcessPostReqThenProcess2GetReqAndNotSuccessfulResp() {
        String postContent = "POST /queue/something HTTP/1.1" + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "some text";
        String getContent = "GET /queue/something HTTP/1.1";
        Req postReq = Req.of(postContent);
        Req getReq = Req.of(getContent);
        QueueService queue = new QueueService();
        queue.process(postReq);
        queue.process(getReq);
        Resp expected = new Resp("Message not found", 409);
        Resp result = queue.process(getReq);
        assertThat(result, is(expected));
    }

    @Test
    public void whenProcessGetReqAndNotSuccessfulResp() {
        String getContent = "GET /queue/something HTTP/1.1";
        Req getReq = Req.of(getContent);
        QueueService queue = new QueueService();
        Resp expected = new Resp("Message not found", 409);
        Resp result = queue.process(getReq);
        assertThat(result, is(expected));
    }
}