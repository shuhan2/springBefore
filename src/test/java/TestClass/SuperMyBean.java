package TestClass;

public class SuperMyBean {
    public SuperMyDependency getSuperMyDependency() {
        return superMyDependency;
    }

    SuperMyBean() {
    }

    @CreateOnFly
    private SuperMyDependency superMyDependency;


}
