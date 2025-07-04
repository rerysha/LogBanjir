package controller;

import model.Banjir;
import utils.DataStore;
import java.util.ArrayList;

public class BanjirController {
    public void tambahData(Banjir b) {
        DataStore.dataBanjir.add(b);
    }

    public void hapusData(int index) {
        DataStore.dataBanjir.remove(index);
    }

    public void updateData(int index, Banjir b) {
        DataStore.dataBanjir.set(index, b);
    }

    public ArrayList<Banjir> getAllData() {
        return DataStore.dataBanjir;
    }
}