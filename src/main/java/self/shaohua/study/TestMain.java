package self.shaohua.study;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import self.shaohua.study.interceptor.RollbackCountInterceptor;
import self.shaohua.study.mapper.AddressMapper;
import self.shaohua.study.model.Address;

import java.util.List;


public class TestMain {
    /** package of mapper interfaces*/
    private static final String MAPPER_PACKAGE = "self.shaohua.study.mapper";

    private static Logger logger = Logger.getLogger(TestMain.class);

    public static void main(String[] args) throws Exception {
        logger.info("main started:");
        //构建一个SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(
                Resources.getResourceAsStream("mybatis.xml"));
        //打开连接
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取Mapper实例
        AddressMapper addressMapper = sqlSession.getMapper(AddressMapper.class);
        //执行查询
        Address address = addressMapper.selectByPrimaryKey(6378815391019302912L);
        logger.info("address detail：" + address.getDetail());

        sqlSessionSelectTest(sqlSessionFactory);
        interceptorTest(sqlSessionFactory);
        pageHelperTest(sqlSessionFactory);
    }

    /** 直接select测试*/
    public static void sqlSessionSelectTest(SqlSessionFactory sqlSessionFactory){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Address address = sqlSession.selectOne(
                MAPPER_PACKAGE + ".AddressMapper.selectByPrimaryKey",
                6378824903616888832L);
        logger.info("address detail: " + address.getDetail());
    }
    /** 拦截器测试 */
    private static void interceptorTest(SqlSessionFactory sqlSessionFactory){
        logger.info("CountInterceptor:" + RollbackCountInterceptor.getCount());
        SqlSession sqlSessionAutoCommitFalse = sqlSessionFactory.openSession(false);
        sqlSessionAutoCommitFalse.update(MAPPER_PACKAGE + ".AddressMapper.removeAddressByPrimaryKey");
        sqlSessionAutoCommitFalse.rollback();
        logger.info("CountInterceptor:" + RollbackCountInterceptor.getCount());
    }

    private static void pageHelperTest(SqlSessionFactory sqlSessionFactory){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AddressMapper addressMapper = sqlSession.getMapper(AddressMapper.class);
        PageHelper.startPage(2,10);
        List<Address> addressList =  addressMapper.selectByCity("110100");
        logger.info("addressList size: " + addressList.size());

        List<Address> addressList2 =  addressMapper.selectByCity("110100");
        logger.info("addressList2 size: " + addressList2.size());



    }
}
