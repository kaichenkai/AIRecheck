package com.seemmo.airecheck.dataGate.service.impl;

import com.seemmo.airecheck.core.ExceptionCode;
import com.seemmo.airecheck.core.Result;
import com.seemmo.airecheck.core.ResultGenerator;
import com.seemmo.airecheck.dataGate.mapper.TrafficWfRecordAccessMapper;
import com.seemmo.airecheck.dataGate.web.AccessController;
import com.seemmo.airecheck.dataGate.web.dto.TrafficIllegalRecordCreateDto;
import com.seemmo.airecheck.dataGate.service.AccessService;
import com.seemmo.airecheck.utils.DateUtils;
import com.seemmo.airecheck.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author: kaichenkai
 * @create: 6/2/2020 17:12
 */
@Service("accessService")
public class AccessServiceImpl implements AccessService {
    static Logger logger = LoggerFactory.getLogger(AccessController.class);

    @Autowired
    private TrafficWfRecordAccessMapper trafficWfRecordAccessMapper;

    @Value("${custom.base-path}")//从配置文件中获取图片基础路径
    private String basePath;

    @Override
    public Result checkArgs(TrafficIllegalRecordCreateDto recordObj) {
        Result result = new Result();
        String recordId = recordObj.getRecordId();
        //判断记录是否重复
        String queryRecordId = trafficWfRecordAccessMapper.exist(recordId);
        if (queryRecordId != null) {
            logger.error(String.format("current record is repeated,record_id: %s", recordId));
            result.setCode(ExceptionCode.INSTESV_REPEATED_RECORD.code);
            result.setMessage(String.format(ExceptionCode.INSTESV_REPEATED_RECORD.message, recordId));
            return result;
        }
        //增加默认值
        recordObj.setCreateTime(DateUtils.getCurrentMilliSeconds());//记录创建时间
        //下载图片
        String imgUrl = recordObj.getImgUrl();
        byte[] imgByteArray = null;
        try {
            HttpClientUtil httpClient = new HttpClientUtil();
            imgByteArray = httpClient.getFile(imgUrl);
        } catch (Exception e) {
            logger.error(String.format("image download abnormal, url: %s, error: %s", imgUrl, e));
            return ResultGenerator.genFailResult(ExceptionCode.IMAGE_URL_INVALID.code, recordId, imgUrl);
        }
        //保存图片,根据违法时间分目录存储
        String illegalDate = DateUtils.formatTimeToYMD(recordObj.getIllegalTime());
        String imgFolder = basePath + File.separator + illegalDate;
        //图片相对\绝对路径
        String imgAbsolutePath = imgFolder + File.separator + recordId + ".jpg";
        String imgRelativePath = illegalDate + File.separator + recordId + ".jpg";
        recordObj.setImgPath(imgRelativePath);
        //判断目录是否存在,不存在则创建
        File imgDir = new File(imgFolder);
        if (!imgDir.exists()) {
            imgDir.mkdirs();
        }
        //保存文件到本地
        try {
            OutputStream os = new FileOutputStream(imgAbsolutePath);
            os.write(imgByteArray, 0, imgByteArray.length);
        } catch (FileNotFoundException e) {
            logger.error(String.format("image path not found, error: %s", e));
            return ResultGenerator.genFailResult(ExceptionCode.IMAGE_PATH_NOT_FOUND.code, ExceptionCode.IMAGE_PATH_NOT_FOUND.message);
        } catch (IOException e) {
            logger.error(String.format("image save failed, error: %s", e));
            return ResultGenerator.genFailResult(ExceptionCode.IMAGE_SAVE_ERROR.code, ExceptionCode.IMAGE_SAVE_ERROR.message);
        }
        // 写库
        try {
            trafficWfRecordAccessMapper.create(recordObj);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultGenerator.genFailResult(ExceptionCode.DATABASE_OPERATION_ERROR.code, ExceptionCode.DATABASE_OPERATION_ERROR.message);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * @return
     */
    @Override
    public int create(TrafficIllegalRecordCreateDto trafficIllegalRecordCreateDto) {
        return trafficWfRecordAccessMapper.create(trafficIllegalRecordCreateDto);
    }
}
