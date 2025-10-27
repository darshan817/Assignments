package Assignment4.Java.models;
public class Address {
    private int id;
    private String pincode;
    private String city;
    private int studentId;
    public Address(int id, String pincode, String city, int studentId) {
        this.id=id; this.pincode=pincode; this.city=city; this.studentId=studentId;
    }
    public int getId(){return id;} public String getPincode(){return pincode;}
    public String getCity(){return city;} public int getStudentId(){return studentId;}
    public String toString(){return String.format("Address{id=%d, pincode='%s', city='%s', studentId=%d}",id,pincode,city,studentId);}
}
