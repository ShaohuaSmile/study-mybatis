package self.shaohua.study.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import self.shaohua.study.model.Address;

import java.util.List;

public interface AddressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table address
     *
     * @mbg.generated Mon Mar 04 16:00:56 CST 2019
     */
    int deleteByPrimaryKey(Long addressId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table address
     *
     * @mbg.generated Mon Mar 04 16:00:56 CST 2019
     */
    int insert(Address record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table address
     *
     * @mbg.generated Mon Mar 04 16:00:56 CST 2019
     */
    int insertSelective(Address record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table address
     *
     * @mbg.generated Mon Mar 04 16:00:56 CST 2019
     */
    Address selectByPrimaryKey(Long addressId);

    List<Address> selectBySelective(Address address);

    List<Address> selectByIds(List<Long> addrIds);

    List<Address> selectByCityAndStatus(String city, Integer status);

    List<Address> selectByAddrIds(@Param("addrIds") List<Long> addrIds);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table address
     *
     * @mbg.generated Mon Mar 04 16:00:56 CST 2019
     */
    int updateByPrimaryKeySelective(Address record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table address
     *
     * @mbg.generated Mon Mar 04 16:00:56 CST 2019
     */
    int updateByPrimaryKey(Address record);

    @Update("update address set detail='' where address_id = #{addressId} ")
    int removeAddressByPrimaryKey(Long addressId);

    List<Address> selectByCity(String city);







    List<Address> selectBy$City(@Param("city") String city);
    Address selectByCityAndId(@Param("city")String city,@Param("addressId")Long addressId);
    Address selectBy$CityAndId(@Param("city") String city,@Param("addressId")Long addressId);
    Address selectBy$CityAnd$Id(@Param("city")String city,@Param("addressId")Long addressId);
    Address selectByCityAndIfId(@Param("city")String city,@Param("addressId")Long addressId);

}