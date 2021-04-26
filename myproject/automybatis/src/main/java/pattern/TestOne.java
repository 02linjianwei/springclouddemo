package pattern;

public class TestOne extends BuildHandler{
    @Override
    public void doHandler(LoginUser loginUser) {
        next.doHandler(loginUser);
    }

    public static void main(String[] args) {
        LoginUser  loginUser = new LoginUser();
        loginUser.setLoginName("aaaaaaaaaaaa");
        loginUser.setPassword("111111111111");
        loginUser.setPermission("admin");
        loginUser.setRoleName("admin");
        BuildHandler.Builder builder = new BuildHandler.Builder();
        builder.addHanlder(new TestOne());
        builder.build().doHandler(loginUser);
    }
}
