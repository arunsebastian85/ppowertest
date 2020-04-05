package com.ppower.reportgenerator.service;

import com.ppower.reportgenerator.boundary.BetDetailData;

public interface HTTPService {
    BetDetailData getBetDetailsOverHttp(String url);
}
