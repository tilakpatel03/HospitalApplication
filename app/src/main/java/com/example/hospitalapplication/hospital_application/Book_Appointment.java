package com.example.hospitalapplication.hospital_application;

import java.io.Serializable;

public class Book_Appointment implements Serializable {
    String patientname;
    String patientDiagnosis;
    String diagnosisdescription;
    String time;
    String date;
    Boolean accept = false;

    Book_Appointment(){

    }

    public Book_Appointment(String patientname, String patientDiagnosis, String diagnosisdescription, String time, String date, Boolean accept) {
        this.patientname = patientname;
        this.patientDiagnosis = patientDiagnosis;
        this.diagnosisdescription = diagnosisdescription;
        this.time = time;
        this.date = date;
        this.accept = accept;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getPatientDiagnosis() {
        return patientDiagnosis;
    }

    public void setPatientDiagnosis(String patientDiagnosis) {
        this.patientDiagnosis = patientDiagnosis;
    }

    public String getDiagnosisdescription() {
        return diagnosisdescription;
    }

    public void setDiagnosisdescription(String diagnosisdescription) {
        this.diagnosisdescription = diagnosisdescription;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }
}
