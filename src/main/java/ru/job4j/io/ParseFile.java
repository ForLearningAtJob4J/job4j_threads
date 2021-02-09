package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {

    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent(Predicate<Integer> condition) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) > 0) {
                if (condition.test(data)) {
                    output.append(data);
                }
            }
        }
        return output.toString();
    }

    public String getContent() throws IOException {
        return getContent(all -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent((i) -> i < 0x80);
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            o.write(content.getBytes());
        }
    }
}