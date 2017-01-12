package kr.co.teampierce.forfitproject.data;

import java.io.Serializable;
import java.util.Calendar;

public class UserData implements Serializable{

    Calendar timeAndDate;

    private int picture;
    //TODO: temporary value at this time
    private String name;
    private String email;
    private float height;
    private float weight;
    private float waist;
    private float hip;
    private float thigh;
    private float calf;
    private float brachialMuscle;
    private float neck;

    private float bmi;
    private float postureScore;

    public UserData(int picture,
                    float height,
                    float weight,
                    float waist,
                    float hip,
                    float thigh,
                    float calf,
                    float brachialMuscle,
                    float neck,
                    float bmi,
                    float postureScore) {

        timeAndDate = Calendar.getInstance();

        this.picture = picture;
        this.height = height;
        this.weight = weight;
        this.waist = waist;
        this.hip = hip;
        this.thigh = thigh;
        this.calf = calf;
        this.brachialMuscle = brachialMuscle;
        this.neck = neck;
        this.bmi = bmi;
        this.postureScore = postureScore;
    }
}
