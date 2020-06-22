package com.seemmo.airecheck.recheckSearch.service.impl;

import com.google.common.collect.Maps;
import com.seemmo.airecheck.commons.PublicConfig;
import com.seemmo.airecheck.commons.model.ConstExportNames;
import com.seemmo.airecheck.constant.BaseConstant;
import com.seemmo.airecheck.constant.BusinessConstant;
import com.seemmo.airecheck.core.ExceptionInfo;
import com.seemmo.airecheck.core.Response;
import com.seemmo.airecheck.core.ResponseGenerator;
import com.seemmo.airecheck.recheckSearch.mapper.TrafficWfRecordSearchMapper;
import com.seemmo.airecheck.recheckSearch.model.RecheckResultExportData;
import com.seemmo.airecheck.recheckSearch.model.dto.RecheckResultExportDto;
import com.seemmo.airecheck.recheckSearch.service.RecheckResultExportService;
import com.seemmo.airecheck.recheckSearch.web.RecheckSearchController;
import com.seemmo.airecheck.utils.DateUtils;
import com.seemmo.airecheck.utils.FileUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author: kaichenkai
 * @create: 6/18/2020 15:59
 */
@Service
public class RecheckResultExportServiceImpl implements RecheckResultExportService{
    private Logger logger = LoggerFactory.getLogger(RecheckSearchController.class);

    @Autowired
    private TrafficWfRecordSearchMapper trafficWfRecordSearchMapper;

    @Autowired
    private PublicConfig config;

    public Response executePacking(RecheckResultExportDto recheckResultExportDto) {
        //限制查询时间区间
        Response response = this.astrictSearchTime(recheckResultExportDto);
        if (!response.success()) {
            return response;
        }
        //搜索数据
        List<RecheckResultExportData> exportDataList;
        try {
            exportDataList = trafficWfRecordSearchMapper.recheckExportSearch(recheckResultExportDto);
        } catch (Exception e) {
            logger.error(String.format("database operation error: %s", e));
            return ResponseGenerator.genErrorResp(ExceptionInfo.DATABASE_OPERATION_ERROR);
        }
        //压缩包文件名
        String zipFileName = ConstExportNames.EXPORTNAME_RECHECKSEARCH + "_" + DateUtils.getTodayYyyyMMddHHmmss() + ".zip";
        //生成带日期相对路径, 便于删除 (recheck_data/yyyyMMdd/)
        String exportRelativeDir = BusinessConstant.RECHECK_DATA + File.separator + DateUtils.getTodayYYYYMMDD() + File.separator;
        //绝对路径()
        String exportAbsoluteDir = config.getRecordRootDir() + File.separator + exportRelativeDir;
        FileUtil.createDir(exportAbsoluteDir);
        //创建临时目录
        String exportTmpDir = exportAbsoluteDir + UUID.randomUUID().toString() + File.separator;
        FileUtil.createDir(exportTmpDir);
        //创建一个zip包
        File targetZipFile = new File(exportAbsoluteDir + File.separator + zipFileName);
        try (FileOutputStream fos = new FileOutputStream(targetZipFile)){
            CheckedOutputStream cos = new CheckedOutputStream(fos, new CRC32());
            ZipOutputStream zipOutputStream = new ZipOutputStream(cos);
            //放入内容
            final int totalCount = exportDataList.size();
            boolean onlyExcel = recheckResultExportDto.getOnlyExcel();
            if (!onlyExcel) {
                //进入下载图片阶段(放入图片)
                writeImageFile(exportDataList, exportTmpDir, zipOutputStream);
            }
            //放入excel表格
            writeExcelFile(exportDataList, exportTmpDir, zipOutputStream);
            //删除临时目录
            FileUtil.delFolder(exportTmpDir);
            //返回压缩包文件路径
//            Map<String, Object> dataMap = Maps.newHashMap();
//            dataMap.put("file", targetZipFile);
//            dataMap.put("fileSize", targetZipFile.length());
            return ResponseGenerator.genSuccessResp(targetZipFile);
        } catch (Exception e) {
            logger.error(String.format("export failed, error:[]", e.getMessage()));
            return ResponseGenerator.genErrorResp(ExceptionInfo.EXPORT_ERROR);
        }
    }

    public Response astrictSearchTime(RecheckResultExportDto recheckResultExportDto) {
        long accessStartTime = recheckResultExportDto.getEntryStartTime();
        long accessEndTime = recheckResultExportDto.getEntryEndTime();
        long apartDays = (accessEndTime - accessStartTime) / DateUtils.DAY_MILLISECONDS;
        if (apartDays > BaseConstant.CONST31) {
            return ResponseGenerator.genErrorResp(ExceptionInfo.SEARCH_TIME_INTERVAL_OUT);
        } else {
            return ResponseGenerator.genSuccessResp();
        }
    }

    /**
     * 下载图片
     */
    private void writeImageFile(List<RecheckResultExportData> exportDataList, String exportTmpDir, ZipOutputStream zipOutputStream) throws IOException {
        //图片文件前缀(也可以指定命名规则)
        String imgFilePrefix = BusinessConstant.IMG_FILE_PREFIX;
        for (RecheckResultExportData record : exportDataList) {
            String imgRelativePath = record.getImgPath();
            String imgAbsolutePath = exportTmpDir + File.separator + imgRelativePath;
            String imgFileName = imgFilePrefix + File.separator + record.getRecordId();
            //
            ZipEntry zipImgFile = new ZipEntry(imgAbsolutePath);

//            ...
            zipOutputStream.putNextEntry(zipImgFile);
        }
    }

    /**
     * 下载表格
     */
    private void writeExcelFile(List<RecheckResultExportData> exportDataList, String exportTmpDir, ZipOutputStream zipOutputStream) throws IOException {
        //表格文件名
        String excelFileName = BusinessConstant.EXPORT_EXCEL_FILENAME;
        String excelAbsolutePath = exportTmpDir + excelFileName;
        try (FileOutputStream fout = new FileOutputStream(excelAbsolutePath)) {
            //创建 record.xlsx 表格文件
            Workbook wb = new HSSFWorkbook();
            Sheet sheetOne = wb.createSheet(BusinessConstant.SHEET_ONE_NAME);
            //
//            for (RecheckResultExportData record : exportDataList) {
//                wb.write(fout);
//            }
            Row row=sheetOne.createRow(0);
            Cell cell=row.createCell(0);
            cell.setCellValue(1);
            row.createCell(1).setCellValue(1.2);//创建第一行第二个单元格
            row.createCell(2).setCellValue("这是一个单元格");//创建第一行第三个单元格
            row.createCell(3).setCellValue(false);//创建第一行第四个单元格
            row.createCell(4).setCellValue(HSSFCell.ENCODING_COMPRESSED_UNICODE);
            Row row1 = sheetOne.createRow(1);
            row1.createCell(0).setCellValue("第二行第一列");
            row1.createCell(1).setCellValue(true);
            row1.createCell(2).setCellValue("第二行第三列");
            row1.createCell(3).setCellValue("第二行第四列");
            //
            wb.write(fout);
            ZipEntry zipEntry = new ZipEntry(excelAbsolutePath);
            zipOutputStream.putNextEntry(zipEntry);
        }
    }
}
