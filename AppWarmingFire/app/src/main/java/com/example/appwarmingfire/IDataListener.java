package com.example.appwarmingfire;

public interface IDataListener {
    void getHumidity(float data);
    void getTemperature(float data);
    void getGas(int data);
}
