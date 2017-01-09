package kr.co.teampierce.forfitproject.data;

import java.io.Serializable;
import java.util.Vector;

public class DataSet implements Serializable {

    public final static int MAX_NUM_OF_DATA = 300;

    private final static String fileName = "data_set.ser";

    private static DataSet instance = new DataSet();

    private Vector<UserData> userData;

    private DataSet(){

        userData = new Vector<>();
    }

    public static DataSet getInstance(){

        return instance;
    }

    public Vector<UserData> getUserData(){

        return userData;
    }
}
