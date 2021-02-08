package ru.job4j.io;

import java.io.*;

public class ParseFile {

    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) > 0) {
                output.append(data);
            }
        }
        return output.toString();
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    output.append(data);
                }
            }
        }
        return output.toString();
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            o.write(content.getBytes());
        }
    }
}