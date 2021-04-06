package user.com.usercomment.Entity;

import com.google.common.base.MoreObjects;

public class UserMsg {
    public static final String UA_UPDATE = "update";
    public static final String UA_DELETE = "delete";
    private String action;
    private Long userId;
    private String traceId;

    public UserMsg() {

    }

    public UserMsg(String action, Long userId, String traceId) {
        this.action = action;
        this.userId = userId;
        this.traceId = traceId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return this.toStringHelper().toString();
    }

    protected MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(this)
                .add("action",this.getAction())
                .add("userId",this.getUserId())
                .add("traceId",this.getTraceId());
    }
}
