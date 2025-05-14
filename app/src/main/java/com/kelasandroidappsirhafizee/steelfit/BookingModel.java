package com.kelasandroidappsirhafizee.steelfit;

import java.util.SplittableRandom;

public class BookingModel {

    private int id, age;
    private String name, specialization, experiences;

    private byte [] profileImage;

    public BookingModel (int id, String name, int age, String specialization, String experiences, byte [] profile_Image){
        this.id = id;
        this.name = name;
        this.age = age;
        this.specialization = specialization;
        this.experiences = experiences;
        this.profileImage = profile_Image;
    }
    public int getId()  {return id;}
    public String getName()  {return name;}
    public int getAge()  {return age;}
    public String getSpecialization()  {return specialization;}
    public String getExperiences()  {return experiences;}
    public byte [] getProfileImage() {return profileImage;}

}
