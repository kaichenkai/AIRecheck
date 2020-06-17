package com.seemmo.airecheck.dataGate.service.impl;

import com.seemmo.airecheck.core.ExceptionInfo;
import com.seemmo.airecheck.core.Response;
import com.seemmo.airecheck.core.ResponseGenerator;
import com.seemmo.airecheck.dataGate.mapper.TrafficWfRecordAccessMapper;
import com.seemmo.airecheck.dataGate.web.AccessController;
import com.seemmo.airecheck.dataGate.model.dto.TrafficIllegalRecordCreateDto;
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
    public Response checkArgs(TrafficIllegalRecordCreateDto recordObj) {
        Response response = new Response();
        String recordId = recordObj.getRecordId();
        //判断记录是否重复
        String queryRecordId = trafficWfRecordAccessMapper.exist(recordId);
        if (queryRecordId != null) {
            logger.error(String.format("current record is repeated,record_id: %s", recordId));
            response.setCode(ExceptionInfo.INSTESV_REPEATED_RECORD.code);
            response.setMessage(String.format(ExceptionInfo.INSTESV_REPEATED_RECORD.message, recordId));
            return response;
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
            ExceptionInfo exceptionInfo = ExceptionInfo.IMAGE_URL_INVALID;
            return ResponseGenerator.genFailResp(exceptionInfo.code, String.format(exceptionInfo.message, recordId, imgUrl));
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
        try (OutputStream os = new FileOutputStream(imgAbsolutePath)) {
            os.write(imgByteArray, 0, imgByteArray.length);
        } catch (FileNotFoundException e) {
            logger.error(String.format("image path not found, error: %s", e));
            return ResponseGenerator.genErrorResp(ExceptionInfo.IMAGE_PATH_NOT_FOUND);
        } catch (IOException e) {
            logger.error(String.format("image save failed, error: %s", e));
            return ResponseGenerator.genErrorResp(ExceptionInfo.IMAGE_SAVE_ERROR);
        }
        // 写库
        try {
            trafficWfRecordAccessMapper.create(recordObj);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseGenerator.genErrorResp(ExceptionInfo.DATABASE_OPERATION_ERROR);
        }
        return ResponseGenerator.genSuccessResp();
    }

    /**
     * @return
     */
    @Override
    public int create(TrafficIllegalRecordCreateDto trafficIllegalRecordCreateDto) {
        return trafficWfRecordAccessMapper.create(trafficIllegalRecordCreateDto);
    }
}
