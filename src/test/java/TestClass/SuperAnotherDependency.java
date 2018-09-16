package TestClass;

import java.util.Date;

public class SuperAnotherDependency implements AutoCloseable{
    private Date time;
    private boolean isClosed;

    public Date getTime() {
        return time;
    }
    public boolean isClosed() {
        return isClosed;
    }
    @Override
    public void close() throws Exception {
        time = new Date();
        isClosed = true;

        Thread.sleep(10);

        throw new IllegalArgumentException();
    }
}
