package chainofresponsibilitypattern;

public class VerifyPermissionHandlder extends BuildHandler {
    @Override
    public void doHandler(LoginUser loginUser) {
        System.out.println("===========VerifyPermissionHandlder=================");
        //next.doHandler(loginUser);
    }
}
