package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class SubscriptionRequestDto {

    private String sellerUuid;

    private String successUrl;

    private String failUrl;

    private String kpSubscribeId;


    public SubscriptionRequestDto() {
        // TODO Auto-generated constructor stub
    }

    public SubscriptionRequestDto(String sellerUuid, String successUrl, String failUrl,String kpSubscribeId) {
        this.sellerUuid = sellerUuid;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
        this.kpSubscribeId = kpSubscribeId;
    }

    public String getSellerUuid() {
        return sellerUuid;
    }

    public void setSellerUuid(String sellerUuid) {
        this.sellerUuid = sellerUuid;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailUrl() {
        return failUrl;
    }

    public void setFailUrl(String failUrl) {
        this.failUrl = failUrl;
    }

    public String getKpSubscribeId() {
        return kpSubscribeId;
    }

    public void setKpSubscribeId(String kpSubscribeId) {
        this.kpSubscribeId = kpSubscribeId;
    }
}
