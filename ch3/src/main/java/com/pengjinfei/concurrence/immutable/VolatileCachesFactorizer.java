package com.pengjinfei.concurrence.immutable;

import com.pengjinfei.concurrence.Servlet.AbstractFactorizerServlet;
import com.pengjinfei.concurrence.annotation.ThreadSafe;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by Pengjinfei on 16/9/25.
 * Description: 使用包含多个状态变量的容器对象来维持不变性条件，并使用一个volatile类型的引用来确保可见性
 */
@ThreadSafe
public class VolatileCachesFactorizer extends AbstractFactorizerServlet {

    private volatile OneValueCache cache = new OneValueCache(null, null);

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        BigInteger bigInteger = extractFromRequest(req);
        BigInteger[] factors = cache.getFactors(bigInteger);
        if (factors == null) {
            factors = factor(bigInteger);
            cache = new OneValueCache(bigInteger, factors);
        }
        encodeIntoResponse(res,factors);
    }
}
