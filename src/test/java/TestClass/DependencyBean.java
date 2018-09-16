package TestClass;

import Annotation.CreateOnFly;

public class DependencyBean {
    @CreateOnFly
    private ValidDependency validDependency;

    public DependencyBean() {
    }

    public ValidDependency getMyDependency() {
        return this.validDependency;
    }


}
