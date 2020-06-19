package com.seemmo.airecheck.utils;

import com.google.common.collect.Lists;
import com.seemmo.airecheck.annotation.ExcelField;
import com.seemmo.airecheck.commons.model.SystemEnums;
import com.seemmo.airecheck.constant.BaseConstant;
import com.seemmo.airecheck.constant.BusinessConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.*;

/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出 @see org.apache.poi.ss.SpreadsheetVersion）
 */
public class ExportExcel {

    private static Logger log = LoggerFactory.getLogger(com.seemmo.airecheck.utils.ExportExcel.class);

    /**
     * 工作薄对象
     */
    private SXSSFWorkbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 当前行号
     */
    private int rownum;

    /**
     * 是否展示图片超链接，true：带超链接；false：不带，
     * 导出的时候如果不带图片导出，可以设置该值为false
     */
    private boolean showImgLink = true;

    /**
     * 注解列表（Object[]{ ExcelField, Field/Method }）
     */
    List<Object[]> annotationList = Lists.newArrayList();
    /**
     * 链接是否关联子目录中
     */
    private boolean linkSubDirectory = false;

    /**
     * 链接对应的 数据类型
     */
    private Class clazz;

    /**
     * 链接名拼接 对应属性名
     */
    private String[] pros;

    /**
     * 构造函数
     *
     * @param title 表格标题，传“空值”，表示无标题
     * @param cls   实体对象，通过annotation.ExportField获取标题
     */
//    public ExportExcel(String title, Class<?> cls) {
//        this(title, cls, 1);
//    }


    /**
     * 构造函数
     *
     * @param title  表格标题，传“空值”，表示无标题
     * @param cls    实体对象，通过annotation.ExportField获取标题
     * @param type   导出类型（1:导出数据；2：导出模板）
     * @param groups 导入分组
     */
//    public ExportExcel(String title, Class<?> cls, int type, int... groups) {
//        Field[] fs = Reflections.getAllFields(cls);
//        for (Field f : fs) {
//            ExcelField ef = f.getAnnotation(ExcelField.class);
//            if (ef != null && (ef.type() == 0 || ef.type() == type)) {
//                if (groups != null && groups.length > 0) {
//                    boolean inGroup = false;
//                    for (int g : groups) {
//                        if (inGroup) {
//                            break;
//                        }
//                        for (int efg : ef.groups()) {
//                            if (g == efg) {
//                                inGroup = true;
//                                annotationList.add(new Object[]{ef, f});
//                                break;
//                            }
//                        }
//                    }
//                } else {
//                    annotationList.add(new Object[]{ef, f});
//                }
//            }
//        }
//        Method[] ms = cls.getMethods();
//        for (Method m : ms) {
//            ExcelField ef = m.getAnnotation(ExcelField.class);
//            if (ef != null && (ef.type() == 0 || ef.type() == type)) {
//                if (groups != null && groups.length > 0) {
//                    boolean inGroup = false;
//                    for (int g : groups) {
//                        if (inGroup) {
//                            break;
//                        }
//                        for (int efg : ef.groups()) {
//                            if (g == efg) {
//                                inGroup = true;
//                                annotationList.add(new Object[]{ef, m});
//                                break;
//                            }
//                        }
//                    }
//                } else {
//                    annotationList.add(new Object[]{ef, m});
//                }
//            }
//        }
//        Collections.sort(annotationList, Comparator.comparing(o -> new Integer(((ExcelField) o[0]).sort())));
//        List<String> headerList = Lists.newArrayList();
//        for (Object[] os : annotationList) {
//            String t = ((ExcelField) os[0]).title();
//            // 如果是导出，则去掉注释
//            if (type == 1) {
//                String[] ss = StringUtils.split(t, "**", 2);
//                if (ss.length == 2) {
//                    t = ss[0];
//                }
//            }
//            headerList.add(t);
//        }
//        initialize(title, headerList);
//    }

//    private void addHyperlink(Cell cell, Object val) {
//        // 使用creationHelpper来创建XSSFHyperlink对象
//        CreationHelper createHelper = wb.getCreationHelper();
//        XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
//        link.setAddress(val.toString());
//        cell.setHyperlink(link);
//
//        CellStyle linkStyle = wb.createCellStyle();
//        Font cellFont = wb.createFont();
//        cellFont.setUnderline((byte) 1);
//        cellFont.setColor(HSSFColor.BLUE.index);
//        linkStyle.setFont(cellFont);
//        cell.setCellStyle(linkStyle);
//    }


    /**
     * 构造函数
     *
     * @param title   表格标题，传“空值”，表示无标题
     * @param headers 表头数组
     */
//    public ExportExcel(String title, String[] headers) {
//        initialize(title, Lists.newArrayList(headers));
//    }

    /**
     * 构造函数
     *
     * @param title 表格标题，传“空值”，表示无标题
     *              * @param headerList 表头列表
     */
//    public ExportExcel(String title, List<String> headerList) {
//        initialize(title, headerList);
//    }

    /**
     * 初始化函数
     *
     * @param title      表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
//    private void initialize(String title, List<String> headerList) {
//        this.wb = new SXSSFWorkbook(500);
//        this.sheet = wb.createSheet("Export");
//        this.styles = createStyles(wb);
//        // Create title
//        if (StringUtils.isNotBlank(title)) {
//            Row titleRow = sheet.createRow(rownum++);
//            titleRow.setHeightInPoints(30);
//            Cell titleCell = titleRow.createCell(0);
//            titleCell.setCellStyle(styles.get("title"));
//            titleCell.setCellValue(title);
//            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), titleRow.getRowNum(),
//                    headerList.size() - 1));
//        }
//        // Create header
//        if (headerList == null) {
//            throw new RuntimeException("headerList not null!");
//        }
//        Row headerRow = sheet.createRow(rownum++);
//        headerRow.setHeightInPoints(16);
//        for (int i = 0; i < headerList.size(); i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellStyle(styles.get("header"));
//            String[] ss = StringUtils.split(headerList.get(i), "**", 2);
//            if (ss.length == 2) {
//                cell.setCellValue(ss[0]);
//                Comment comment = this.sheet.createDrawingPatriarch()
//                        .createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
//                comment.setString(new XSSFRichTextString(ss[1]));
//                cell.setCellComment(comment);
//            } else {
//                cell.setCellValue(headerList.get(i));
//            }
//            sheet.autoSizeColumn(i);
//        }
//        for (int i = 0; i < headerList.size(); i++) {
//            int colWidth = sheet.getColumnWidth(i) * 2;
//            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
//        }
//        if (log.isDebugEnabled()) {
//            log.debug("Initialize success.");
//        }
//    }

    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
//    private Map<String, CellStyle> createStyles(Workbook wb) {
//        Map<String, CellStyle> styles = new HashMap<String, CellStyle>(BaseConstant.CONST9);
//
//        CellStyle style = wb.createCellStyle();
//        style.setAlignment(CellStyle.ALIGN_CENTER);
//        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//        Font titleFont = wb.createFont();
//        titleFont.setFontName("Arial");
//        titleFont.setFontHeightInPoints((short) 16);
//        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
//        style.setFont(titleFont);
//        styles.put("title", style);
//
//        style = wb.createCellStyle();
//        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//        style.setBorderRight(CellStyle.BORDER_THIN);
//        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//        style.setBorderLeft(CellStyle.BORDER_THIN);
//        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//        style.setBorderTop(CellStyle.BORDER_THIN);
//        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//        style.setBorderBottom(CellStyle.BORDER_THIN);
//        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//        Font dataFont = wb.createFont();
//        dataFont.setFontName("Arial");
//        dataFont.setFontHeightInPoints((short) 10);
//        style.setFont(dataFont);
//        styles.put("data", style);
//
//        style = wb.createCellStyle();
//        style.cloneStyleFrom(styles.get("data"));
//        style.setAlignment(CellStyle.ALIGN_LEFT);
//        styles.put("data1", style);
//
//        style = wb.createCellStyle();
//        style.cloneStyleFrom(styles.get("data"));
//        style.setAlignment(CellStyle.ALIGN_CENTER);
//        styles.put("data2", style);
//
//        style = wb.createCellStyle();
//        style.cloneStyleFrom(styles.get("data"));
//        style.setAlignment(CellStyle.ALIGN_RIGHT);
//        styles.put("data3", style);
//
//        style = wb.createCellStyle();
//        style.cloneStyleFrom(styles.get("data"));
//        style.setAlignment(CellStyle.ALIGN_CENTER);
//        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        Font headerFont = wb.createFont();
//        headerFont.setFontName("Arial");
//        headerFont.setFontHeightInPoints((short) 10);
//        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
//        headerFont.setColor(IndexedColors.WHITE.getIndex());
//        style.setFont(headerFont);
//        styles.put("header", style);
//
//        return styles;
//    }

    /**
     * 添加一行
     *
     * @return 行对象
     */
    public Row addRow() {
        return sheet.createRow(rownum++);
    }

    /**
     * 添加一个单元格
     *
     * @param row    添加的行
     * @param column 添加列号
     * @param val    添加值
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val) {
        return this.addCell(row, column, val, 0, Class.class);
    }

    /**
     * 添加一个单元格
     *
     * @param row    添加的行
     * @param column 添加列号
     * @param val    添加值
     * @param align  对齐方式（1：靠左；2：居中；3：靠右）
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType) {
        Cell cell = row.createCell(column);
        CellStyle style = styles.get("data" + (align >= 1 && align <= 3 ? align : BaseConstant.EMPTYSTR));
        try {
            if (val == null) {
                cell.setCellValue(BaseConstant.EMPTYSTR);
            } else if (val instanceof String) {
                cell.setCellValue((String) val);
            } else if (val instanceof Integer) {
                cell.setCellValue((Integer) val);
            } else if (val instanceof Long) {
                //新建数字格式对象
                NumberFormat nf = NumberFormat.getInstance();
                //是否保留千分位
                nf.setGroupingUsed(false);
                cell.setCellValue(nf.format(val));
                //cell.setCellValue((Long) val);
            } else if (val instanceof Double) {
                //新建数字格式对象
                NumberFormat nf = NumberFormat.getInstance();
                //是否保留千分位
                nf.setGroupingUsed(false);
                cell.setCellValue(nf.format(val));
                //cell.setCellValue((Double) val);
            } else if (val instanceof Float) {
                cell.setCellValue((Float) val);
            } else if (val instanceof Date) {
                DataFormat format = wb.createDataFormat();
                style.setDataFormat(format.getFormat("yyyy-MM-dd"));
                cell.setCellValue((Date) val);
            } else {
                if (fieldType != Class.class) {
                    cell.setCellValue((String) fieldType.getMethod("setValue", Object.class).invoke(null, val));
                } else {
                    cell.setCellValue((String) Class
                            .forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(),
                                    "fieldtype." + val.getClass().getSimpleName() + "Type"))
                            .getMethod("setValue", Object.class)
                            .invoke(null, val));
                }
            }
        } catch (Exception ex) {
            log.error("Set cell value [" + row.getRowNum() + "," + column + "] error: " + ex.toString());
            cell.setCellValue(val.toString());
        }
        cell.setCellStyle(style);
        return cell;
    }

    public com.seemmo.airecheck.utils.ExportExcel setShowImgLink(boolean showImgLink) {
        this.showImgLink = showImgLink;
        return this;
    }

    public com.seemmo.airecheck.utils.ExportExcel setLinkSubDirectory(boolean linkSubDirectory, Class clazz, String... pros) {
        this.linkSubDirectory = linkSubDirectory;
        this.clazz = clazz;
        this.pros = pros;
        return this;
    }

    /**
     * 添加数据（通过annotation.ExportField添加数据）
     *
     * @return list 数据列表
     */
//    public <E> com.seemmo.aitraffic.utils.ExportExcel setDataList(List<E> list) {
//        for (E e : list) {
//            int colunm = 0;
//            Row row = this.addRow();
//            StringBuilder sb = new StringBuilder();
//            for (Object[] os : annotationList) {
//                ExcelField ef = (ExcelField) os[0];
//                Object val = null;
//                // Get entity value
//                try {
//                    if (StringUtils.isNotBlank(ef.value())) {
//                        val = Reflections.invokeGetter(e, ef.value());
//                    } else {
//                        if (os[1] instanceof Field) {
//                            if (StringUtils.isNotBlank(ef.fieldValue())) {
//                                val = Reflections.invokeGetter(e, ef.fieldValue());
//                            } else {
//                                val = Reflections.invokeGetter(e, ((Field) os[1]).getName());
//                            }
//                        } else if (os[1] instanceof Method) {
//                            val =
//                                    Reflections.invokeMethod(e, ((Method) os[1]).getName(), new Class[]{}, new Object[]{});
//                        }
//                    }
//                    // If is dict, get dict label
//                    if (ef.dictType() != SystemEnums.CODE_TYPE.NONE) {
//                        val = DicSysGbCodeCache.getInstans().getDictLabel(ef.dictType(), val);
//                    } else if (ef.fieldType() != Class.class) {
//                        val = (String) Class.forName(ef.fieldType().getCanonicalName()).getMethod(ef.methodName(), Object.class).invoke(null, val);
//                    }
//                    //System.out.println(((Field) os[1]).getName() + ",val = [" + val + "]" + ef.fieldValue());
//                } catch (Exception ex) {
//                    // Failure to ignore
//                    log.info(ex.toString());
//                    val = BaseConstant.EMPTYSTR;
//                }
//                Cell cell = this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
//                // 给单元格内容添加超链接
//                if (showImgLink && ef.isHyperlink() && null != val && BaseConstant.EMPTYSTR != val) {
//                    if (linkSubDirectory) {
//                        String subDirectoryPath = null;
//                        try {
//                            subDirectoryPath = setLinkWithPros(e, clazz, pros);
//                        } catch (Exception ep) {
//                            log.error(ep.toString());
//                        }
//                        addHyperlink(cell, subDirectoryPath + File.separator + val + BusinessConstant.LOWER_SPOTJPG);
//                    } else {
//                        addHyperlink(cell, val + BusinessConstant.LOWER_SPOTJPG);
//                    }
//                }
//                sb.append(val + ", ");
//            }
//            if (log.isDebugEnabled()) {
//                log.debug("Write success: [" + row.getRowNum() + "] " + sb.toString());
//            }
//        }
//        return this;
//    }

    /**
     * 输出数据流
     *
     * @param os 输出数据流
     */
    public com.seemmo.airecheck.utils.ExportExcel write(OutputStream os)
            throws IOException {
        wb.write(os);
        return this;
    }

    /**
     * 输出到客户端
     *
     * @param fileName 输出文件名
     */
    public com.seemmo.airecheck.utils.ExportExcel write(HttpServletResponse response, String fileName)
            throws IOException {
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
        write(response.getOutputStream());
        return this;
    }

    /**
     * 输出到文件
     *
     * @param name 输出文件名
     */
    public com.seemmo.airecheck.utils.ExportExcel writeFile(String name)
            throws FileNotFoundException, IOException {
        FileOutputStream os = new FileOutputStream(name);
        this.write(os);
        return this;
    }

    /**
     * 清理临时文件
     */
    public com.seemmo.airecheck.utils.ExportExcel dispose() {
        wb.dispose();
        return this;
    }

    private String setLinkWithPros(Object o, Class clazz, String... pros) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = pros.length; i < len; i++) {
            sb.append(Reflections.invokeGetter(clazz.cast(o), pros[i]));
            if (i != len - 1) {
                sb.append(BaseConstant.LOWERBAR);
            }
        }
        return sb.toString();
    }
}


