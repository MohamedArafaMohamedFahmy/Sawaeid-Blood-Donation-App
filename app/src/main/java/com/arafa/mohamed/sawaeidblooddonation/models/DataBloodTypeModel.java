package com.arafa.mohamed.sawaeidblooddonation.models;

import com.arafa.mohamed.sawaeidblooddonation.R;

import java.util.ArrayList;

public class DataBloodTypeModel {

    public static ArrayList<BloodTypeModel>  getBloodType(){

        ArrayList <BloodTypeModel> listBloodType = new ArrayList<>();
        BloodTypeModel aPositive = new BloodTypeModel();
        aPositive.name = "A Positive";
        aPositive.image = R.drawable.a_pos;
        listBloodType.add(aPositive);

        BloodTypeModel aNegative = new BloodTypeModel();
        aNegative.name = "A Negative";
        aNegative.image = R.drawable.a_neg;
        listBloodType.add(aNegative);

        BloodTypeModel bPositive = new BloodTypeModel();
        bPositive.name = "B Positive";
        bPositive.image = R.drawable.b_pos;
        listBloodType.add(bPositive);

        BloodTypeModel bNegative = new BloodTypeModel();
        bNegative.name = "B Negative";
        bNegative.image = R.drawable.b_neg;
        listBloodType.add(bNegative);

        BloodTypeModel abPositive = new BloodTypeModel();
        abPositive.name = "AB Positive";
        abPositive.image = R.drawable.ab_pos;
        listBloodType.add(abPositive);

        BloodTypeModel abNegative = new BloodTypeModel();
        abNegative.name = "AB Negative";
        abNegative.image = R.drawable.ab_neg;
        listBloodType.add(abNegative);

        BloodTypeModel oPositive = new BloodTypeModel();
        oPositive.name = "O Positive";
        oPositive.image = R.drawable.o_pos;
        listBloodType.add(oPositive);

        BloodTypeModel oNegative = new BloodTypeModel();
        oNegative.name = "O Negative";
        oNegative.image = R.drawable.o_neg;
        listBloodType.add(oNegative);

        BloodTypeModel unknownBloodType = new BloodTypeModel();
        unknownBloodType.name = "UnKnown";
        unknownBloodType.image = R.drawable.unknown_blood;
        listBloodType.add(unknownBloodType);

        return listBloodType;
    }
}
