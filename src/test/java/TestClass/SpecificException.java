package TestClass;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;

public class SpecificException extends GetOptsException {
    static final long serialVersionUID = 8736874967183039899L;

    SpecificException(String msg) {
        super(msg);
    }
}
