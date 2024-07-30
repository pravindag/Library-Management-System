package dto;

public class FineDto {

    private String fineId;
    private String borrowId;
    private String paidDate;
    private Double amount;
    
    public FineDto() {
    }

    public FineDto(String fineId, String borrowId, String paidDate, Double amount) {
        this.fineId = fineId;
        this.borrowId = borrowId;
        this.paidDate = paidDate;
        this.amount = amount;
    }

    public String getFineId() {
        return fineId;
    }

    public void setFineId(String fineId) {
        this.fineId = fineId;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    
}
