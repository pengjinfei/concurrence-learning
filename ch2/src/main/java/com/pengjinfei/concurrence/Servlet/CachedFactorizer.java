package com.pengjinfei.concurrence.Servlet;

import com.pengjinfei.concurrence.annotation.ThreadSafe;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by Pengjinfei on 16/9/24.
 * Description: 安全性和性能之间的平衡
 */
@ThreadSafe
public class CachedFactorizer extends AbstractFactorizerServlet {

    private BigInteger lastNumber;
    private BigInteger[] lastFactos;
    private long hits;
    private long cacheHits;

    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheHitsRatio() {
        return (double)cacheHits/(double)hits;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        BigInteger bigInteger = extractFromRequest(req);
        BigInteger[] factors=null;
        synchronized (this){
            hits++;
            if (bigInteger.equals(lastNumber)) {
                cacheHits++;
                factors=lastFactos.clone();
            }
        }
        if (factors == null) {
            factors = factor(bigInteger);
            synchronized (this){
                lastNumber=bigInteger;
                lastFactos=factors;
            }
        }
        encodeIntoResponse(res,lastFactos);
    }
}
