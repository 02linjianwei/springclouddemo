package chainofresponsibilitypattern;

public class VerifyRoleHanlder extends BuildHandler {
    @Override
    public void doHandler(LoginUser loginUser) {
        System.out.println("========VerifyRoleHanlder============");
        next.doHandler(loginUser);
    }
}
