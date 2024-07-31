package dto;

import java.util.ArrayList;

public class BorrowDto {

    private String borrowId;
    private String memberId;
    private String librarianId;
    private String issueDate;
    private int dueDate;
    private String returnDate;
    private int status;
    private ArrayList<BorrowDetailDto> borrowDetailDtos;
    
    public BorrowDto() {
    }

    public BorrowDto(String borrowId, String memberId, String librarianId, String issueDate, int dueDate, String returnDate, int status, ArrayList<BorrowDetailDto> borrowDetailDtos) {
        this.borrowId = borrowId;
        this.memberId = memberId;
        this.librarianId = librarianId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.borrowDetailDtos = borrowDetailDtos;
    }

    public BorrowDto(String borrowId, String memberId, String librarianId, String issueDate, int dueDate, String returnDate, int status) {
        this.borrowId = borrowId;
        this.memberId = memberId;
        this.librarianId = librarianId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(String librarianId) {
        this.librarianId = librarianId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public int getDueDate() {
        return dueDate;
    }

    public void setDueDate(int dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public ArrayList<BorrowDetailDto> getBorrowDetailDtos() {
        return borrowDetailDtos;
    }

    public void setBorrowDetailDtos(ArrayList<BorrowDetailDto> borrowDetailDtos) {
        this.borrowDetailDtos = borrowDetailDtos;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
}
