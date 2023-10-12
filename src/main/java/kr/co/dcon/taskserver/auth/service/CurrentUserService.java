package kr.co.dcon.taskserver.auth.service;


import kr.co.dcon.taskserver.auth.dto.RealmInfoDTO;
import kr.co.dcon.taskserver.auth.dto.UserSimpleDTO;
import kr.co.dcon.taskserver.auth.dto.UserWorkDTO;

public interface CurrentUserService {
	UserWorkDTO getCurrentUser();

	RealmInfoDTO getRealmInfo();
	void setRealmInfo();
}
