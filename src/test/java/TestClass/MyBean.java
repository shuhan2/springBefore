package TestClass;

public class MyBean {
    @CreateOnFly
    private MyDependency myDependency;

    public MyBean() {

    }

    public Object getMyDependency() {
        return this.myDependency;
    }
}

