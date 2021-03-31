package product.com.productyw.Entity;

public class ProductComment {
    private Long id;
    private Long pid;
    private String comment;
    private String servicePort;

    public ProductComment() {

    }

    public String getServicePort() {
        return servicePort;
    }

    public void setServicePort(String servicePort) {
        this.servicePort = servicePort;
    }

    public ProductComment(Long id, Long pid, String comment,String servicePort) {
        this.id = id;
        this.pid = pid;
        this.comment = comment;
        this.servicePort = servicePort;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
