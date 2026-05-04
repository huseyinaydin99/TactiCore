package tr.com.huseyinaydin.service;

import tr.com.huseyinaydin.dto.match.ResultMatchDto;

public interface MatchStatusObserver {

    void onMatchStatusChange(ResultMatchDto match);
}
