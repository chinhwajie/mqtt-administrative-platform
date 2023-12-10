package cn.edu.zju.cs.bs;

import java.util.List;
import java.util.Vector;

public class IOTClient {
    public static void main(String[] args) {
        String mqttServer = "tcp://10.73.103.130:1883";
        List<String> devices = Dummies.getRandomDeviceList();
        try {
            Vector<WorkerThread> threadVector = new Vector<>();
            for (String d : devices) {
                WorkerThread thread = new WorkerThread();
                thread.setIotId(d);
                thread.setMqttServer(mqttServer);
                thread.setTopic(Dummies.getRandomTopic());
                threadVector.add(thread);
                thread.start();
            }
            for (WorkerThread thread : threadVector) {
                thread.join();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
