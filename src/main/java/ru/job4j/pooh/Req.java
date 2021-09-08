package ru.job4j.pooh;

import java.util.Objects;

public class Req {

    private final String method;

    private final String mode;

    private final String text;

    private final String name;

    private final int id;

    public Req(String method, String mode, String text, String name, int id) {
        this.method = method;
        this.mode = mode;
        this.text = text;
        this.name = name;
        this.id = id;
    }

    public static Req of(String content) {
        String[] splitContent = content.split(System.lineSeparator());
        boolean isPost = splitContent[0].startsWith("POST");
        String method = isPost ? "POST" : "GET";
        String[] items = splitContent[0].substring(
                isPost ? 6 : 5,
                splitContent[0].lastIndexOf(" HTTP/1.1")
        ).split("/");
        String mode = items[0];
        String name = items[1];
        String text = isPost ? splitContent[7] : "";
        int id = !isPost && mode.equals("topic")
                ? Integer.parseInt(items[2])
                : 0;
        return new Req(method, mode, text, name, id);
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String text() {
        return text;
    }

    public String name() {
        return name;
    }

    public int id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Req req = (Req) o;
        return id == req.id
                && Objects.equals(method, req.method)
                && Objects.equals(mode, req.mode)
                && Objects.equals(text, req.text)
                && Objects.equals(name, req.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, mode, text, name, id);
    }
}