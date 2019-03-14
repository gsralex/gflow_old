package com.gsralex.gflow.pub.context;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gsralex
 * @version 2019/3/12
 */
public class IpManager {

    public IpManager(String ips) {
        if (ips != null) {
            list = new ArrayList<>();
            String[] ipArr = ips.split(",");
            for (String ip : ipArr) {
                list.add(new IpAddr(ip));
            }
        }
    }

    public IpManager(IpAddr ip) {
        list = new ArrayList<>();
        if (ip != null) {
            list.add(ip);
        }
    }

    public IpManager(List<IpAddr> ipList) {
        if (ipList != null) {
            this.list = ipList;
        }
    }

    private List<IpAddr> list = new ArrayList<>();
    private int index;

    public IpAddr getIp() {
        if (list.size() == 0) {
            return null;
        }
        synchronized (list) {
            if (index >= list.size()) {
                index = 0;
            }
            IpAddr ip = list.get(index);
            index++;
            return ip;
        }
    }

    public void setIp(final List<IpAddr> ipList) {
        if (ipList == null) {
            return;
        }
        if (!(list.size() == ipList.size() && list.containsAll(ipList))) {
            synchronized (list) {
                list = ipList;
            }
        }
    }

    public void removeIp(final IpAddr ip) {
        synchronized (list) {
            list.remove(ip);
        }
    }

    public void addIp(final IpAddr ip) {
        synchronized (list) {
            if (!list.contains(ip)) {
                list.add(ip);
            }
        }
    }

    public List<IpAddr> listIp() {
        return new ArrayList<>(list);
    }

    public static void main(String[] args) throws InterruptedException, UnknownHostException {

        InetAddress ips = InetAddress.getByName("dev.gsralex.com");

        System.out.println(ips);
        long begin = System.currentTimeMillis();
        List<IpAddr> list = new ArrayList<>();
        list.add(new IpAddr("192.168.1.1", 2001));
        list.add(new IpAddr("192.168.1.2", 2001));
        list.add(new IpAddr("192.168.1.3", 2001));
        list.add(new IpAddr("192.168.1.4", 2001));
        list.add(new IpAddr("192.168.1.5", 2001));
        list.add(new IpAddr("192.168.1.6", 2001));
        list.add(new IpAddr("192.168.1.7", 2001));
        list.add(new IpAddr("192.168.1.8", 2001));
        list.add(new IpAddr("192.168.1.9", 2001));
        list.add(new IpAddr("192.168.1.10", 2001));
        list.add(new IpAddr("192.168.1.11", 2001));
        list.add(new IpAddr("192.168.1.12", 2001));
        list.add(new IpAddr("192.168.1.13", 2001));
        list.add(new IpAddr("192.168.1.14", 2001));
        list.add(new IpAddr("192.168.1.15", 2001));
        list.add(new IpAddr("192.168.1.16", 2001));
        list.add(new IpAddr("192.168.1.17", 2001));
        list.add(new IpAddr("192.168.1.18", 2001));

        IpManager ipManager = new IpManager(list);
        int threadCnt = 100;
        Thread[] threads = new Thread[threadCnt];
        for (int i = 0; i < threadCnt; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100000; j++) {
//                        ipManager.getIp();
                        ipManager.setIp(list);
                    }
                }
            });
            thread.start();
            threads[i] = thread;
        }
        for (Thread thread : threads) {
            thread.join();
        }
        long end = System.currentTimeMillis();
        System.out.print(end - begin);
    }
}
