package tm;

public class CollectReportTM {

    private String collectId;
    private String memberId;
    private String borrowId;
    private String collectedDate;
    private String description;
    
    public CollectReportTM() {
    }

    public CollectReportTM(String collectId, String memberId, String borrowId, String collectedDate) {
        this.collectId = collectId;
        this.memberId = memberId;
        this.borrowId = borrowId;
        this.collectedDate = collectedDate;
    }

    public CollectReportTM(String collectId, String memberId, String borrowId, String collectedDate,
            String description) {
        this.collectId = collectId;
        this.memberId = memberId;
        this.borrowId = borrowId;
        this.collectedDate = collectedDate;
        this.description = description;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getCollectedDate() {
        return collectedDate;
    }

    public void setCollectedDate(String collectedDate) {
        this.collectedDate = collectedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
