package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class MultiThreadResourceLoader {
    public static void main(String[] args) {
        int limit = 1000;
        AtomicInteger succeeded = new AtomicInteger();
        String classResourceName = MultiThreadResourceLoader.class.getName().replace(".", "/") + ".class";
        URL resourceUrl = MultiThreadResourceLoader.class.getClassLoader().getResource(classResourceName);
        List<Thread> threads = Stream.iterate(0, i -> i + 1).limit(limit).map(i -> new Thread(() -> {
            try (URLClassLoader classLoader = new URLClassLoader(new URL[] { resourceUrl })) {
                InputStream in = classLoader.getResourceAsStream(classResourceName);
                if (in == null) {
                    throw new IllegalStateException(classResourceName + " not found");
                }
                in.readAllBytes();
                succeeded.incrementAndGet();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        })).toList();
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }
        if (succeeded.get() != limit) {
            throw new IllegalStateException("Only " + succeeded.get() + " threads succeeded");
        } else {
            System.out.println("Success");
        }
    }
}
