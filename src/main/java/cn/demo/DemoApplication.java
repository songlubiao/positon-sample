package cn.demo;

import cn.demo.model.Currency;
import cn.demo.thread.FileThread;
import cn.demo.thread.ShellThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 76732
 */
public class DemoApplication {

    static Lock lock = new ReentrantLock();
    static List<Currency> currencies = new ArrayList<>();
    static ThreadLocal<List<Currency>> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set(currencies);

        String fileName = "position.data";
        if (args.length == 1) {
            fileName = args[0];
        }
        if (args.length > 1) {
            System.exit(-1);
        }

        ShellThread shellThread = new ShellThread(lock, currencies);

        FileThread fileThread = new FileThread(lock, fileName);

        new Thread(fileThread).start();

        new Thread(shellThread).start();
    }
}
