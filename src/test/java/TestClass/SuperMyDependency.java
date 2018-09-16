package TestClass;

import java.util.Date;

public class SuperMyDependency implements AutoCloseable {
    public SuperMyDependency() {
    }

    public Date getTime() {
        return time;
    }

    public boolean isClosed() {
        return isClosed;
    }

    private Date time;
    private boolean isClosed;
    @Override
    public void close() throws Exception {
        time = new Date();
        isClosed = true;
        Thread.sleep(10);
    }


}
