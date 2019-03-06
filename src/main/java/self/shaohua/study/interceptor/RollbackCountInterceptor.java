package self.shaohua.study.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.plugin.*;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

@Intercepts({@Signature(type= Executor.class, method = "rollback", args = {boolean.class})})
public class RollbackCountInterceptor implements Interceptor {
    //全局统计回滚次数
    private static AtomicInteger count = new AtomicInteger(0);
    public Object intercept(Invocation invocation) throws Throwable {
        count.getAndIncrement();
        return invocation.proceed();
    }
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    public void setProperties(Properties properties) {
    }
    public static int getCount(){
        return count.get();
    }
}
