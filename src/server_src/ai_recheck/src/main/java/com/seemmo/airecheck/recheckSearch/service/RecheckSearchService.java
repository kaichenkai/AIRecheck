package com.seemmo.airecheck.recheckSearch.service;

import com.seemmo.airecheck.core.Result;
import com.seemmo.airecheck.recheckSearch.model.RecheckSearchData;
import com.seemmo.airecheck.recheckSearch.model.dto.RecheckSearchDto;

import java.util.List;

/**
 * @author: kaichenkai
 * @create: 6/9/2020 17:29
 */
public interface RecheckSearchService {
    public Result astrictSearchTime(RecheckSearchDto recheckSearchDto);

    public List<RecheckSearchData> recheckSearch(RecheckSearchDto recheckSearchDto);
}
