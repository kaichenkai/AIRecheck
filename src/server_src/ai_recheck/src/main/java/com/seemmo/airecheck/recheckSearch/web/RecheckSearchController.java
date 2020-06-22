package com.seemmo.airecheck.recheckSearch.web;

import com.seemmo.airecheck.core.ExceptionInfo;
import com.seemmo.airecheck.core.Response;
import com.seemmo.airecheck.core.ResponseGenerator;
import com.seemmo.airecheck.recheckSearch.model.RecheckResultExportData;
import com.seemmo.airecheck.recheckSearch.model.RecheckSearchData;
import com.seemmo.airecheck.recheckSearch.model.dto.RecheckResultExportDto;
import com.seemmo.airecheck.recheckSearch.model.dto.RecheckSearchDto;
import com.seemmo.airecheck.recheckSearch.service.RecheckResultExportService;
import com.seemmo.airecheck.recheckSearch.service.RecheckSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author: kaichenkai
 * @create: 6/9/2020 17:24
 * 复核查询控制器类
 */
@RestController
@RequestMapping("/main")
public class RecheckSearchController {
    private Logger logger = LoggerFactory.getLogger(RecheckSearchController.class);

    @Autowired
    private RecheckSearchService recheckSearchService;

    @Autowired
    private RecheckResultExportService recheckResultExportService;

    /**
     * 复核查询
     * @param recheckSearchDto
     * @return
     */
    @PostMapping(value = "recheckSearch", name = "复核结果查询")
    public Response<?> recheckSearch(@RequestBody RecheckSearchDto recheckSearchDto) {
        //限制查询时间区间
        Response response = recheckSearchService.astrictSearchTime(recheckSearchDto);
        if (!response.success()) {
            return response;
        }
        Map<String, Object> dataMap = new HashMap<>();
        try {
            //搜索数据
            List<RecheckSearchData> recheckDataList = recheckSearchService.search(recheckSearchDto);
            dataMap.put("recheckDataList", recheckDataList);
            dataMap.put("totalNum", recheckDataList.size());
            response.setData(dataMap);
            return response;
        } catch (Exception e) {
            logger.error(String.format("database operation error: %s", e));
            return ResponseGenerator.genErrorResp(ExceptionInfo.DATABASE_OPERATION_ERROR);
        }
    }

    /**
     * 复核结果导出
     * @param recheckResultExportDto
     * @return
     */
    @PostMapping(value = "export/recheckResult", name = "复核结果导出")
    public Response<?> export(@RequestBody RecheckResultExportDto recheckResultExportDto, HttpServletResponse response) throws UnsupportedEncodingException {
//        return recheckResultExportService.executePacking(recheckResultExportDto);
        File scFileDir = new File("E:\\temp\\20200608\\");
        File TrxFiles[] = scFileDir.listFiles();
        System.out.println(TrxFiles[0]);
        String fileName = TrxFiles[0].getName(); //下载的文件名


        // 如果文件名不为空，则进行下载
        if (fileName != null) {
            File file = new File(scFileDir, fileName);
            // 如果文件存在，则进行下载
            if (file.exists()) {
                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                // 实现文件下载
                byte[] buff = new byte[1024];
                BufferedInputStream bis = null;
                OutputStream os = null;
                try {
                    os = response.getOutputStream();
                    bis = new BufferedInputStream(new FileInputStream(file));
                    int i = bis.read(buff);
                    while (i != -1) {
                        os.write(buff, 0, buff.length);
                        os.flush();
                        i = bis.read(buff);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("success");
            }
        }
        return ResponseGenerator.genSuccessResp();
    }
}
