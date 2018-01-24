package com.aries.future;

public class RealData implements Data {

    protected final String result;

    public RealData(String param) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(param);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }

        this.result = stringBuilder.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}
