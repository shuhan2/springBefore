package TestClass;

import Annotation.CreateOnFly;

public class SuperMyBean implements AutoCloseable {
    public SuperMyDependency getSuperMyDependency() {
        return superMyDependency;
    }

    public SuperMyBean() {
    }

    @CreateOnFly
    private SuperMyDependency superMyDependency;


    @Override
    public void close() throws Exception {

    }
}
