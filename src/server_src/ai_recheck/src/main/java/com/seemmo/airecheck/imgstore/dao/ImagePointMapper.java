//package com.seemmo.airecheck.imgstore.dao;
//
//import com.seemmo.aitraffic.imgstore.entity.ImageAndPointEntity;
//import org.apache.ibatis.annotations.Delete;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
//import org.springframework.stereotype.Repository;
//
///**
// * @program: ai_traffic
// * @description: 图片对应车辆坐标和信号灯坐标mapper
// * @author: 张乐
// * @create: 2018-12-20 11:41
// **/
//@Repository
//public interface ImagePointMapper {
//
//    @Select("SELECT image_id as imageId,box_point as boxPoint,red_light_point as redLightPoint FROM tb_image_points WHERE image_id = #{imageId}")
//    ImageAndPointEntity selectImagePointByImageId(@Param("imageId") String imageId);
//
//    @Insert("INSERT INTO `tb_image_points`(image_id,box_point,red_light_point) VALUES(#{imageId},#{boxPoint},#{redLightPoint}) ON DUPLICATE KEY UPDATE  box_point = #{boxPoint} , red_light_point = #{redLightPoint}")
//    void save(ImageAndPointEntity imageAndPointEntity);
//
//    @Delete("DELETE FROM `tb_image_points` WHERE image_id in (${imageIds})")
//    void deleteImagePointByImageIds(@Param("imageIds") String imageIds);
//}
