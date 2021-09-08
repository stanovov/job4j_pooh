package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> topics = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, ConcurrentHashMap<Integer, ConcurrentLinkedQueue<String>>> subscriptions =
            new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp;
        topics.putIfAbsent(req.name(), new ConcurrentLinkedQueue<>());
        subscriptions.putIfAbsent(req.name(), new ConcurrentHashMap<>());
        if (req.method().equals("POST")) {
            topics.get(req.name()).add(req.text());
            subscriptions.get(req.name()).values().forEach(strings -> strings.add(req.text()));
            resp = new Resp("Topic created", 201);
        } else {
            subscriptions.get(req.name()).putIfAbsent(req.id(), new ConcurrentLinkedQueue<>(topics.get(req.name())));
            String text = subscriptions.get(req.name()).get(req.id()).poll();
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