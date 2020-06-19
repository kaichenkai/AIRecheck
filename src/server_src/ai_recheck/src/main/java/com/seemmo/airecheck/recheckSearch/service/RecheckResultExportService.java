package com.seemmo.airecheck.recheckSearch.service;

import com.seemmo.airecheck.core.Response;
import com.seemmo.airecheck.recheckSearch.model.RecheckResultExportData;
import com.seemmo.airecheck.recheckSearch.model.dto.RecheckResultExportDto;
import com.seemmo.airecheck.recheckSearch.model.dto.RecheckSearchDto;

import java.util.List;

/**
 * @author: kaichenkai
 * @create: 6/18/2020 15:59
 */
public interface RecheckResultExportService {
    public Response executePacking(RecheckResultExportDto recheckResultExportDto);
}
