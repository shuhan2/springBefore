package TestClass;

import Annotation.CreateOnFly;

public class SuperAnotherBean {
    @CreateOnFly
    private SuperAnotherDependency superAnotherDependency;

    public SuperAnotherBean() {
    }

    public SuperAnotherDependency getSuperAnotherDependency() {
        return this.superAnotherDependency;
    }
}
