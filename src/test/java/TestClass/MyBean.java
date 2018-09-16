package TestClass;

import Annotation.CreateOnFly;

public class MyBean extends SuperMyBean {
    @CreateOnFly
    private MyDependency myDependency;

    public MyBean() {
    }

    public MyDependency getMyDependency() {
        return this.myDependency;
    }
}

