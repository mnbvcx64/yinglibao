package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.model.productInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface productInfoMapper {
    //利率平均值
    BigDecimal selectAvgRate();

    //按产品类型分页查询
    List<productInfo> selectByTypeLimit(@Param("ptype")Integer ptype,
                                        @Param("offset")Integer office,
                                        @Param("rows")Integer rows);

    //某个产品类型的记录总数
    Integer selectCountByType(@Param("ptype") Integer pType);

    int deleteByPrimaryKey(Integer id);

    int insert(productInfo record);

    int insertSelective(productInfo record);

    productInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(productInfo record);

    int updateByPrimaryKey(productInfo record);

    int updateLeftProductMoney(@Param("id") Integer productId, @Param("money") BigDecimal money);

    productInfo selectByPrimaryId(@Param("id") Integer id);

    int updateSellde(@Param("id") Integer productId);

    List<productInfo> selectFullTimeProducts(@Param("beginTime") Date beginTime,
                                             @Param("endTime") Date endTime);

    int updateStatus(@Param("id") Integer id, @Param("productStatusPlan") int productStatusPlan);

    productInfo selectProductName(@Param("id") Integer id);
}