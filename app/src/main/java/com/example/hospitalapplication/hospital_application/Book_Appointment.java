package com.example.hospitalapplication.hospital_application;

public class Book_Appointment {
    String patientname;
    String patientDiagnosis;
    String diagnosisdescription;
    Boolean accept = false;

    Book_Appointment(){

    }

    public Book_Appointment(String patientname, String patientDiagnosis, String diagnosisdescription, Boolean accept) {
        this.patientname = patientname;
        this.patientDiagnosis = patientDiagnosis;
        this.diagnosisdescription = diagnosisdescription;
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

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }
}
