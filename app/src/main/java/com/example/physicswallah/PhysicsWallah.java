package com.example.physicswallah;

import java.util.ArrayList;
import java.util.List;

public class PhysicsWallah {
    String name,profileImageUrl;
    int id;
    List<String> subjects, qualifications;
    PhysicsWallah(int id,String name,List<String> subjects,List<String> qualifications,String profileImageUrl){
        this.id=id;
        this.name=name;
        this.qualifications=new ArrayList<>(qualifications);
        this.subjects=new ArrayList<>(subjects);
        this.profileImageUrl=profileImageUrl;
    }
}
