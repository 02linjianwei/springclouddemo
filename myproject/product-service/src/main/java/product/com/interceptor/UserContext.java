package product.com.interceptor;

public class UserContext {
    public static final String AUTH_TOKEN="Authorization";
    public static final String USER_ID = "scd-user-id";
    public static final String SHOP_ID="scd-shop-id";
    private static   ThreadLocal<String> authToken ;
    private static  ThreadLocal<String> userId ;
    private static  ThreadLocal<String> shopId ;
    public static void setAuthToken(ThreadLocal<String> authToken) {
        authToken = authToken;
    }
    public static void setUserId(ThreadLocal<String> userId) {
        userId = userId;
    }
    public static void setShopId(ThreadLocal<String> shopId) {
        shopId = shopId;
    }
    public static ThreadLocal<String> getAuthToken() {
        return authToken;
    }

    public static ThreadLocal<String> getUserId() {
        return userId;
    }

    public static ThreadLocal<String> getShopId() {
        return shopId;
    }
}
