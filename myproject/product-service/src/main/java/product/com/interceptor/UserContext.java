package product.com.interceptor;

public class UserContext {
    public static final String AUTH_TOKEN="Authorization";
    public static final String USER_ID = "scd-user-id";
    public static final String SHOP_ID="scd-shop-id";
    private static final ThreadLocal<String> authToken = new ThreadLocal<String>();
    private static final  ThreadLocal<String> userId = new ThreadLocal<>();
    private static final  ThreadLocal<String> shopId = new ThreadLocal<>();
    public static void setAuthToken(String authTokenparam) {
        authToken.set(authTokenparam);
    }
    public static void setUserId(String userIdparam) {
        userId.set(userIdparam);
    }
    public static void setShopId(String shopIdparam) {
        shopId.set(shopIdparam);
    }
    public static String getAuthToken() {
        return authToken.get();
    }

    public static String getUserId() {
        return userId.get();
    }

    public static String getShopId() {
        return shopId.get();
    }
}
