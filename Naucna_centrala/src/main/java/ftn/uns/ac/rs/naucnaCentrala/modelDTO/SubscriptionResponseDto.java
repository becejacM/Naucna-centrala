package ftn.uns.ac.rs.naucnaCentrala.modelDTO;

public class SubscriptionResponseDto {

    private String redirectUrl;
    private String id;

    public SubscriptionResponseDto() {
        // TODO Auto-generated constructor stub
    }

    public SubscriptionResponseDto(String redirectUrl, String id) {
        super();
        this.redirectUrl = redirectUrl;
        this.id = id;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
