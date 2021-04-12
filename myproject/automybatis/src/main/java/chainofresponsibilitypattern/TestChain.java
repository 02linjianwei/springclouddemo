package chainofresponsibilitypattern;

public class TestChain {
    public static void main(String[] args) {
        LoginUser  loginUser = new LoginUser();
        loginUser.setLoginName("aaaaaaaaaaaa");
        loginUser.setPassword("111111111111");
        loginUser.setPermission("admin");
        loginUser.setRoleName("admin");
        BuildHandler.Builder builder = new BuildHandler.Builder();
        builder.addHanlder(new VerifyAccountHandler())
                .addHanlder(new VerifyRoleHanlder())
                .addHanlder(new VerifyPermissionHandlder());
        builder.build().doHandler(loginUser);
    }
}
