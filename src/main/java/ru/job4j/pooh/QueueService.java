package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp;
        queue.putIfAbsent(req.name(), new ConcurrentLinkedQueue<>());
        if (req.method().equals("POST")) {
            queue.get(req.name()).add(req.text());
            resp = new Resp("Message created", 201);
        } else {
            String text = queue.get(req.name()).poll();
            int status = 200;
            if (text == null) {
                status = 409;
                text = "Message not found";
            }
            resp = new Resp(text, status);
        }
        return resp;
    }
}