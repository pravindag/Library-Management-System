package tm;

import javafx.scene.control.Button;

public class MemberTM {

    private String memberId;
    private String firstName;
    private String lastName;
    private String memberFullName;
    private String address;
    private String phoneNumber;
    private Button btnDelete;
    private Button btnUpdate;
    
    public MemberTM() {
    }

    public MemberTM(String memberId, String firstName, String lastName, String address, String phoneNumber) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberFullName = firstName + " " + lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public MemberTM(String memberId, String firstName, String lastName, String address, String phoneNumber,
            Button btnDelete, Button btnUpdate) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberFullName = firstName + " " + lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.btnDelete = btnDelete;
        this.btnUpdate = btnUpdate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    public Button getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(Button btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public String getMemberFullName() {
        return memberFullName;
    }

    public void setMemberFullName(String memberFullName) {
        this.memberFullName = memberFullName;
    }

    
}
