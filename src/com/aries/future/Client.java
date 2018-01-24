package com.aries.future;

public class Client {

    public Data request(final String requestStr) {
        final FutureData futureData = new FutureData();
        new Thread(() -> {
            RealData realData = new RealData(requestStr);
            futureData.setRealData(realData);
        }).start();
        return
                futureData;
    }

    public static void main(String[] args) {
        Client client = new Client();
        Data data = client.request("name");
        System.out.println("请求完毕");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("数据:" + data.getResult());
    }
}
