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

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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

        /*
        //打开连接
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取Mapper实例
        AddressMapper addressMapper = sqlSession.getMapper(AddressMapper.class);
        //执行查询
        Address address = addressMapper.selectByPrimaryKey(6378815391019302912L);
        logger.info("address detail：*****************" + address.getDetail());

        parameterNameTest("uuuu");
*/

        interceptorTest(sqlSessionFactory);
/*
        sql$Test(sqlSessionFactory);
        sqlSessionSelectTest(sqlSessionFactory);
        paramResolverTest(sqlSessionFactory);
        pageHelperTest(sqlSessionFactory);
        jdbcTest();
        */
    }

    public static void paramResolverTest(SqlSessionFactory sqlSessionFactory){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AddressMapper addressMapper = sqlSession.getMapper(AddressMapper.class);

//        Address addr = addressMapper.selectByPrimaryKey(6378815391019302912L);
//
//        Address param = new Address();
//        param.setAddressId(6378815391019302912L);
//
//        List<Address> addrs = addressMapper.selectBySelective(param);
//
//        addressMapper.selectByIds(Arrays.asList(6378815391019302912L));

        List<Address> addrs2 = addressMapper.selectByCityAndStatus("110100", 1);

        List<Address> addrs3 = addressMapper.selectByAddrIds(Arrays.asList(6378815391019302912L));


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
        logger.info("CountInterceptor:*****************" + RollbackCountInterceptor.getCount());
        SqlSession sqlSessionAutoCommitFalse = sqlSessionFactory.openSession(false);
        sqlSessionAutoCommitFalse.update(MAPPER_PACKAGE + ".AddressMapper.removeAddressByPrimaryKey");
        sqlSessionAutoCommitFalse.rollback();
//        sqlSessionAutoCommitFalse.update(MAPPER_PACKAGE + ".AddressMapper.removeAddressByPrimaryKey");
//        sqlSessionAutoCommitFalse.rollback();
        logger.info("CountInterceptor:*****************" + RollbackCountInterceptor.getCount());
    }

    private static void pageHelperTest(SqlSessionFactory sqlSessionFactory){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AddressMapper addressMapper = sqlSession.getMapper(AddressMapper.class);
        PageHelper.startPage(2,10);

        List<Address> addressList =  addressMapper.selectByCity("110100");
        logger.info("addressList size:***************** " + addressList.size());
        PageHelper.clearPage();
        List<Address> addressList2 =  addressMapper.selectByCity("110100");
        logger.info("addressList2 size:***************** " + addressList2.size());
    }

    private static void sql$Test(SqlSessionFactory sqlSessionFactory){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AddressMapper addressMapper = sqlSession.getMapper(AddressMapper.class);
        addressMapper.selectBy$City("00000");
        Address address =  addressMapper.selectBy$CityAndId("110100",6378815391019302912L);
        logger.info("address.getDetail:***************** " + address.getDetail());
    }
    private static void jdbcTest() throws Exception{
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://192.168.241.164:8306/mall_admin?useUnicode=true&characterEncoding=utf-8";
        String userName = "ad";
        String pwd = "ad";
        String sql = "SELECT * FROM address where address_id=?";
        //加载驱动
        Class.forName(driver);
        //获取连接
        Connection conn = DriverManager.getConnection(url,userName,pwd);
        //创建会话
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setLong(1,6378815391019302912L);
        //执行sql
        statement.executeQuery();
        //获取结果集
        ResultSet resultSet = statement.getResultSet();
        List<Address> list = new ArrayList<>();
        //封装结果
        while (resultSet.next()){
            Address address = new Address();
            address.setAddressId(resultSet.getLong("address_id"));
            address.setDetail(resultSet.getString("detail"));
            logger.info("jdbc result: *****************" + address.getDetail());
            list.add(address);
        }
        //todo 关闭会话
    }

    public static void parameterNameTest(String djdj) throws Exception{
        logger.info("----" +
                TestMain.class.getMethod("parameterNameTest",new Class[]{String.class}).getParameters()[0].getName());
    }
}
