package TestClass;

import Annotation.CreateOnFly;

public class AnotherBean extends SuperAnotherBean{
    @CreateOnFly
    private AnotherDependency anotherDependency;

    public AnotherBean() {
    }

    public AnotherDependency getAnotherDependency() {
        return this.anotherDependency;
    }
}
