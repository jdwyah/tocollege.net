package com.apress.progwt.server.service;

import java.util.List;

import com.apress.progwt.client.domain.dto.SearchResult;
import com.apress.progwt.client.exception.SiteException;

public interface SearchService {

    SearchResult search(String searchString) throws SiteException;

    List<String> searchForSchool(String searchString)
            throws SiteException;

    List<String> searchForSchool(String searchStringP, int start,
            int max_num_hits) throws SiteException;
}
