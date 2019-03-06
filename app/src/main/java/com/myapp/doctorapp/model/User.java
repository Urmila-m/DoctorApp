package com.myapp.doctorapp.model;

import java.io.Serializable;

public class User implements Serializable {
    private String MobileNumber;

    private String Email;

    private String DOB;

    private String Height;

    private String id;

    private String Gender;

    private String Image;

    private String Weight;

    private String Name;

    private String Password;

    public String getMobileNumber ()
    {
        return MobileNumber;
    }

    public void setMobileNumber (String MobileNumber)
    {
        this.MobileNumber = MobileNumber;
    }

    public String getEmail ()
    {
        return Email;
    }

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public String getDOB ()
    {
        return DOB;
    }

    public void setDOB (String DOB)
    {
        this.DOB = DOB;
    }

    public String getHeight ()
    {
        return Height;
    }

    public void setHeight (String Height)
    {
        this.Height = Height;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getGender ()
    {
        return Gender;
    }

    public void setGender (String Gender)
    {
        this.Gender = Gender;
    }

    public String getImage ()
    {
        return Image;
    }

    public void setImage (String Image)
    {
        this.Image = Image;
    }

    public String getWeight ()
    {
        return Weight;
    }

    public void setWeight (String Weight)
    {
        this.Weight = Weight;
    }

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getPassword ()
    {
        return Password;
    }

    public void setPassword (String Password)
    {
        this.Password = Password;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [MobileNumber = "+MobileNumber+", Email = "+Email+", DOB = "+DOB+", Height = "+Height+", id = "+id+", Gender = "+Gender+", Image = "+Image+", Weight = "+Weight+", Name = "+Name+", Password = "+Password+"]";
    }
}