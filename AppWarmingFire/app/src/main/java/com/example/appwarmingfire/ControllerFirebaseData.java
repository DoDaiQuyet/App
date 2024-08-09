package com.example.appwarmingfire;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ControllerFirebaseData {

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private final IDataListener iDataListener ;
    public ControllerFirebaseData(IDataListener dataListener){
        iDataListener = dataListener ;
    }

    public void fetchHumidity(){
        firebaseDatabase.getReference().child("Humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() ){
                    float data = snapshot.getValue(Float.class);
                    iDataListener.getHumidity(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fetchTemperature(){
        firebaseDatabase.getReference().child("Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    float data = snapshot.getValue(Float.class);
                    iDataListener.getTemperature(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fetchGas(){
        firebaseDatabase.getReference().child("Gas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int data = snapshot.getValue(Integer.class);
                    iDataListener.getGas(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
