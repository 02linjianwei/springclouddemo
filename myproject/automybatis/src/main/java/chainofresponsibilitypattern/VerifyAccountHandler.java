package chainofresponsibilitypattern;

public class VerifyAccountHandler extends  BuildHandler {
    @Override
    public void doHandler(LoginUser loginUser) {
        System.out.println("====VerifyAccountHandler======");
        next.doHandler(loginUser);
    }
}
