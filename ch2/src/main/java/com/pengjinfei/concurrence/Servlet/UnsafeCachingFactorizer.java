package com.pengjinfei.concurrence.Servlet;

import com.pengjinfei.concurrence.annotation.NoThreadSafe;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Pengjinfei on 16/9/24.
 * Description:
 */
@NoThreadSafe
public class UnsafeCachingFactorizer extends AbstractFactorizerServlet {

    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();

    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        BigInteger bigInteger = extractFromRequest(req);
        /*
          UnsafeCachingFactorizer的不变性条件之一是：在lastFactors中缓存的因数之积应该等于lastNumber中缓存的数组。
          虽然AtomicReference是线程安全类，但是在获取lastNumber的同时可能另一线程修改了lastFactors
         */
        if (bigInteger.equals(lastNumber.get())) {
            encodeIntoResponse(res, lastFactors.get());
        } else {
            /*
              无法保证同时修改lastNumber和lastFactors
             */
            BigInteger[] factor = factor(bigInteger);
            lastNumber.set(bigInteger);
            lastFactors.set(factor);
            encodeIntoResponse(res,factor);
        }
    }
}
